package canonicalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import bean.Pair;

public class Canonicalizer {

	private String directoryIndex_path;
	private String titlesFile_path;

	public Canonicalizer() throws Exception{
		this.directoryIndex_path = "wikilinksIndex/";
		this.titlesFile_path = "wiki/titles_reduced.tsv";
		this.createIndex();
	}

	public Canonicalizer(String directoryIndex){
		this.directoryIndex_path = directoryIndex;
	}

	public void createIndex() throws Exception {
		HashMap<String, List<Pair<String, String>>> titles = new HashMap<String, List<Pair<String, String>>>(200000);
		// buffered file reader, reads the patterns file line by line
		BufferedReader r = new BufferedReader(new FileReader(this.titlesFile_path));
		String line; 
		while ((line = r.readLine()) != null) {
			String[] values = line.split("\t");
			String pageName = values[0];
			String entityLinkOut = values[1];
			String entityName = values[2];

			// fill map of patterns
			if (titles.containsKey(pageName))
				titles.get(pageName).add(new Pair<String, String>(entityLinkOut, entityName));
			else{
				List<Pair<String, String>> pairEntityLinkOut = new ArrayList<Pair<String, String>>();
				pairEntityLinkOut.add(new Pair<String, String>(entityLinkOut, entityName));
				titles.put(pageName, pairEntityLinkOut);
			}
		}
		r.close();
		IndexWriter writer = this.getWriter();
		writeIndex(writer, titles);
		writer.close();
	}

	public IndexWriter getWriter() throws IOException{
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_44, CharArraySet.EMPTY_SET);
		Directory directoryIndex = FSDirectory.open(new File(this.directoryIndex_path));
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_44, analyzer);
		config.setOpenMode(OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(directoryIndex, config);
		return writer;
	}

	public static void writeIndex(IndexWriter writer, Map<String, List<Pair<String, String>>> titles) throws IOException{
		for (Entry<String, List<Pair<String, String>>> entry : titles.entrySet()){
			for (Pair<String, String> linksOut : entry.getValue()){
				Document doc = new Document();
				doc.add(new TextField("articleName", entry.getKey(), Field.Store.YES));
				doc.add(new TextField("yagoIdLink", linksOut.key, Field.Store.YES));
				doc.add(new TextField("nameLink", linksOut.value, Field.Store.YES));
				writer.addDocument(doc);
			}
			Document doc = new Document();
			doc.add(new TextField("articleName", entry.getKey(), Field.Store.YES));
			doc.add(new TextField("yagoIdLink", entry.getKey(), Field.Store.YES));
			doc.add(new TextField("nameLink", entry.getKey().replace("_", " ").replace("<", "").replace(">", ""), Field.Store.YES));
			writer.addDocument(doc);
		}
	}

	public DirectoryReader getReader() throws IOException{
		Directory index_dir = FSDirectory.open(new File(this.directoryIndex_path));
		DirectoryReader reader = DirectoryReader.open(index_dir);
		return reader;
	}

	public String searchIndex(String querystring, String nameArticle, DirectoryReader reader) throws IOException, ParseException{
		if (nameArticle.contains(querystring)){
			return nameArticle;
		}else{
			int MAX_HITS = 100;
			Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_44, CharArraySet.EMPTY_SET);
			IndexSearcher searcher = new IndexSearcher(reader);
			Query queryNamedEntity = new QueryParser(Version.LUCENE_44, "nameLink", standardAnalyzer).parse(querystring);
			Query queryArticolName = new QueryParser(Version.LUCENE_44, "articleName", standardAnalyzer).parse(nameArticle);
			BooleanQuery queryFinal = new BooleanQuery();
			queryFinal.add(queryArticolName, Occur.MUST);
			queryFinal.add(queryNamedEntity, Occur.MUST);
			// MAX_HITS --> maximum number of results
			TopDocs results = searcher.search(queryFinal, MAX_HITS);
			ScoreDoc[] hits = results.scoreDocs;
			String yagoID = "none";
			if (hits.length > 0){
				for (int i = 0; i < 1; i++) {
					Document doc = reader.document(hits[i].doc);
					yagoID = doc.get("yagoIdLink");
					//System.out.println(yagoID);
				}
			}
			return yagoID;
		}
	}

	public static void main(String[] args) throws Exception{
		Canonicalizer can = new Canonicalizer();
		can.createIndex();
		DirectoryReader reader = can.getReader();
		System.out.println(can.searchIndex("Bletchley Park", "<Alan_Turing>", reader).toString());
	}

}

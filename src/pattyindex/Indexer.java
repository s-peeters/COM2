package pattyindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {

	private String directoryIndex_path;
	private String pattyFile_path;

	public Indexer() throws Exception{
		this.directoryIndex_path = "pattyindex/";
		this.pattyFile_path = "patty/yago-relation-paraphrases.txt";
		this.createIndex();
	}

	public Indexer(String directoryIndex){
		this.directoryIndex_path = directoryIndex;
	}

	public void createIndex() throws Exception {
		HashMap<String, List<String>> patty = new HashMap<String, List<String>>(50000);
		// buffered file reader, reads the patterns file line by line
		BufferedReader r = new BufferedReader(new FileReader(this.pattyFile_path));
		String line; 
		while ((line = r.readLine()) != null) {
			String[] values = line.split("\t");
			String relation = values[0];
			String pattern = values[1].replace(";", "");

			// fill map of patterns
			if (patty.containsKey(pattern))
				patty.get(pattern).add(relation);
			else{
				List<String> relations = new ArrayList<String>();
				relations.add(relation);
				patty.put(pattern, relations);
			}
		}
		r.close();
		IndexWriter writer = this.getWriter();
		writeIndex(writer, patty);
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

	public static void writeIndex(IndexWriter writer, Map<String, List<String>> patty) throws IOException{
		for (Map.Entry<String, List<String>> entry : patty.entrySet()){
			for (String relation : entry.getValue()){
				Document doc = new Document();
				doc.add(new TextField("pattern", entry.getKey(), Field.Store.YES));
				doc.add(new TextField("relation", relation, Field.Store.YES));
				writer.addDocument(doc);
			}		
		}
	}

	public DirectoryReader getReader() throws IOException{
		Directory index_dir = FSDirectory.open(new File(this.directoryIndex_path));
		DirectoryReader reader = DirectoryReader.open(index_dir);
		return reader;
	}
	
	public Set<String> searchIndex(String querystring, DirectoryReader reader) throws IOException, ParseException{
		int MAX_HITS = 100;
		Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_44, CharArraySet.EMPTY_SET);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser qp = new QueryParser(Version.LUCENE_44, "pattern", standardAnalyzer);
		Query query = qp.parse("\"" + querystring +"\"");

		// MAX_HITS --> maximum number of results
		TopDocs results = searcher.search(query, MAX_HITS);
		ScoreDoc[] hits = results.scoreDocs;
		Set<String> resultsSet = new HashSet<String>();

		for (int i = 0; i < hits.length; i++) {
			Document doc = reader.document(hits[i].doc);
			String patternMatched = doc.get("pattern");
			if (patternMatched.length() == querystring.length()){
				resultsSet.add(doc.get("relation"));
			}
		}
		
		return resultsSet;

	}

}

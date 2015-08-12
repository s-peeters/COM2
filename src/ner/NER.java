package ner;

import java.util.ArrayList;
import java.util.List;

import bean.Evidence;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;
import wikiminer.Article;

public class NER {
	
	private String path_classifier;
	
	public NER(){
		this.path_classifier = "classifiers/english.conll.4class.distsim.crf.ser.gz";
	}
	
	public List<Evidence> annotateArticle(Article article){
		
		
	}

}

package ner;

import java.util.ArrayList;
import java.util.List;

import bean.Evidence;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.util.Triple;
import wikiminer.Article;
import wikiminer.Phrase;

public class NER {

	private AbstractSequenceClassifier<CoreLabel> classifier;

	public NER(){
		this.classifier = CRFClassifier.getClassifierNoExceptions("classifiers/english.conll.4class.distsim.crf.ser.gz");
	}

	/**
	 * 
	 * @param article
	 * @return
	 */
	public List<Evidence> annotateArticle(Article article){
		List<Phrase> sentences = article.getPhrases();
		
		List<Evidence> output = new ArrayList<Evidence>();
		for (Phrase sent : sentences){
			String sentence = sent.getTextTagged();
			List<Triple<String,Integer,Integer>> out = classifier.classifyToCharacterOffsets(sentence);
			String currentInput = sentence;
			String currentMention = null;
			String mentionBefore = null;
			String pattern = null;
			Evidence evidenceBefore = new Evidence();

			for (Triple<String,Integer,Integer> mention : out){
				if (mentionBefore == null){
					currentMention = sentence.substring(mention.second, mention.third);
					evidenceBefore.setSubject_name(currentMention);
					evidenceBefore.setSubject_type(mention.first);
					mentionBefore = currentMention;
				}else{
					currentMention = sentence.substring(mention.second, mention.third);	
					evidenceBefore.setObject_name(currentMention);
					evidenceBefore.setObject_type(mention.first);
					pattern = currentInput.substring(currentInput.indexOf(mentionBefore) + mentionBefore.length(), currentInput.indexOf(currentMention)).trim();
					evidenceBefore.setPattern(pattern);
					output.add(evidenceBefore);
					
					currentInput = currentInput.substring(currentInput.indexOf(currentMention));
					
					Evidence evidenceStart = new Evidence();
					evidenceStart.setSubject_name(currentMention);
					evidenceStart.setSubject_type(mention.first);	
					
					evidenceBefore = evidenceStart;
					mentionBefore = currentMention;
				}
			}
		}
		
		System.out.println(output.toString());
		return output;

	}
	
	public static void main(String[] args){
		String text = "Albert Einstain was born [[det]] India. John Smith is a native of Finland and Helsinki and USA.";
		Article test = new Article(text);
		test.createPhrases();
		NER ner = new NER();
		ner.annotateArticle(test);
	}

}

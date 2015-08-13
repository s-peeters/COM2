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
			
			String pattern = null;
			Evidence evidenceBefore = new Evidence();
			String currentMention;
			Triple<String,Integer,Integer> mentionBefore = null;

			for (Triple<String,Integer,Integer> mention : out){
				if (mentionBefore == null){
					currentMention = getMention(sentence, mention.second, mention.third);
					evidenceBefore.setSubject_name(currentMention);
					evidenceBefore.setSubject_type(mention.first);
					mentionBefore = mention;
				}else{
					currentMention = getMention(sentence, mention.second, mention.third);

					if (currentMention.equals(mentionBefore))
						continue;
					evidenceBefore.setObject_name(currentMention);
					evidenceBefore.setObject_type(mention.first);
					
					pattern = sentence.substring(mentionBefore.third, mention.second).replaceAll("[^a-zA-Z0-9\\[\\] ]", "").replace("LRBLRB", "").replace("RRBRRB", "").toLowerCase().trim();
					
					evidenceBefore.setPattern(pattern);
					output.add(evidenceBefore);
										
					Evidence evidenceStart = new Evidence();
					evidenceStart.setSubject_name(currentMention);
					evidenceStart.setSubject_type(mention.first);	
					
					evidenceBefore = evidenceStart;
					mentionBefore = mention;
				}
			}
		}
				return output;
	}
	
	/**
	 * 
	 * @param sentence
	 * @param begin
	 * @param end
	 * @return
	 */
	public String getMention(String sentence, int begin, int end){
		String mention = sentence.substring(begin, end);
		return mention;
	}
	
	
	public static void main(String[] args){
		String text = "At the age of 8, Albert Einstein was transferred to the Luitpold Gymnasium (now known as the Albert Einstein Gymnasium), where Albert Einstein received advanced primary and secondary school education until Albert Einstein left Germany seven years later.";
		Article test = new Article(text);
		test.createPhrases();
		NER ner = new NER();
		ner.annotateArticle(test);
	}
	

}

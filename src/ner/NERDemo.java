package ner;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/** This is a demo of calling CRFClassifier programmatically.
 *  <p>
 *  Usage: {@code java -mx400m -cp "*" NERDemo [serializedClassifier [fileName]] }
 *  <p>
 *  If arguments aren't specified, they default to
 *  classifiers/english.all.3class.distsim.crf.ser.gz and some hardcoded sample text.
 *  If run with arguments, it shows some of the ways to get k-best labelings and
 *  probabilities out with CRFClassifier. If run without arguments, it shows some of
 *  the alternative output formats that you can get.
 *  <p>
 *  To use CRFClassifier from the command line:
 *  </p><blockquote>
 *  {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -textFile [file] }
 *  </blockquote><p>
 *  Or if the file is already tokenized and one word per line, perhaps in
 *  a tab-separated value format with extra columns for part-of-speech tag,
 *  etc., use the version below (note the 's' instead of the 'x'):
 *  </p><blockquote>
 *  {@code java -mx400m edu.stanford.nlp.ie.crf.CRFClassifier -loadClassifier [classifier] -testFile [file] }
 *  </blockquote>
 *
 *  @author Jenny Finkel
 *  @author Christopher Manning
 */

public class NERDemo {

	public static void main(String[] args) throws Exception {

		String serializedClassifier = "classifiers/english.conll.4class.distsim.crf.ser.gz";
		String input = "Samuel Peeters was born in Berlin and Albert Einstain comes from Germany and Albert Einstain was born in Juvaskula";

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		List<Triple<String,Integer,Integer>> out = classifier.classifyToCharacterOffsets(input);
		System.out.println();
		System.out.println("INPUT: " + input);
		System.out.println();
		
		String currentInput = input;
		String currentMention = null;
		String mentionBefore = null;
		String typeMentionBefore = null;
		String pattern = null;
		
		for (Triple<String,Integer,Integer> mention : out){
			if (pattern != null){
				System.out.print(normalize(mentionBefore) + "\t");
				System.out.print(typeMentionBefore + "\t" );
			}
			currentMention = input.substring(mention.second, mention.third);
//			String toReplace = "<" + currentMention + ">";
			System.out.print(normalize(currentMention) + "\t");
			System.out.print(mention.first + "\t" );
			typeMentionBefore = mention.first;
			
			if (mentionBefore != null){
				pattern = currentInput.substring(currentInput.indexOf(mentionBefore) + mentionBefore.length() , currentInput.indexOf(currentMention));
				currentInput = currentInput.substring(currentInput.indexOf(currentMention));
				System.out.println(pattern);
			}
			mentionBefore = currentMention;
			
		}

	}
	
	public static String normalize(String name){
		return "<" + name + ">";
	}

}
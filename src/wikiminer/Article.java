/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiminer;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.ArrayList;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.WordToSentenceProcessor;
import java.io.StringReader;
import java.util.List;

public class Article {
    private String text;
    private ArrayList<Phrase> phrases;
    private ArrayList facts;
    private String subject;
    private String yagoIdArticle;

	public Article(String text) {
        this.text = text;
        phrases = new ArrayList();
    }

    public Article(String text, String subject, String yagoId) {
        this.text = text;
        this.subject = subject;
        this.yagoIdArticle = yagoId;
        phrases = new ArrayList();
    }
    
    public void createPhrases(){ 
        //// Tokenize
        List<CoreLabel> tokens = new ArrayList<>();
        PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(text), new CoreLabelTokenFactory(), "");
        while (tokenizer.hasNext()) {
            tokens.add(tokenizer.next());
        }
        //// Split sentences from tokens
        List<List<CoreLabel>> sentences = new WordToSentenceProcessor<CoreLabel>().process(tokens);
        //// Join back together
        int end;
        int start = 0;
        for (List<CoreLabel> sentence: sentences) {
            end = sentence.get(sentence.size()-1).endPosition();
            phrases.add(new Phrase(text.substring(start, end).trim()));
            start = end;
        }
        
        /*Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        for (List<HasWord> sentence : dp) {
            String out = Sentence.listToString(sentence);
            //replace -LRB- and -RRB- with opening and closing brackets
            out = out.replace("-LRB-", "(");
            out = out.replace("-RRB-", ")");
            Phrase line = new Phrase(out);
            phrases.add(line);
            System.out.println(line.getText());
        }*/
    }
    
    /*public void dropPhrases(){
        for (int index = 0; index < phrases.size(); index++){
            if (StringUtils.countMatches(phrases.get(index).getTextAIDA(), ">") < 2) phrases.remove(index); //how will this influence the for loop? Do other phrases index--?
        }
    }*/
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Phrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(ArrayList<Phrase> sentences) {
        this.phrases = sentences;
    }

    public ArrayList getFacts() {
        return facts;
    }

    public void setFacts(ArrayList facts) {
        this.facts = facts;
    }
    
    public String getYagoIdArticle() {
		return yagoIdArticle;
	}

	public void setYagoIdArticle(String yagoIdArticle) {
		this.yagoIdArticle = yagoIdArticle;
	}

}

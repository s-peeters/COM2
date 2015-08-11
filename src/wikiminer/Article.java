/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiminer;
import java.util.ArrayList;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class Article {
    private String text;
    private ArrayList<Phrase> phrases;
    private ArrayList facts;
    private String subject;

    public Article(String text) {
        this.text = text;
        phrases = new ArrayList();
    }

    public Article(String text, String subject) {
        this.text = text;
        this.subject = subject;
    }
    
    public void createSentences(){ 
        Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        for (List<HasWord> sentence : dp) {
            Phrase line = new Phrase(Sentence.listToString(sentence));
            phrases.add(line);
        }
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Phrase> getSentences() {
        return phrases;
    }

    public void setSentences(ArrayList<Phrase> sentences) {
        this.phrases = sentences;
    }

    public ArrayList getFacts() {
        return facts;
    }

    public void setFacts(ArrayList facts) {
        this.facts = facts;
    }
}

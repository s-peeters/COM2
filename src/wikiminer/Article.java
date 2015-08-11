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
    private ArrayList<Phrase> sentences;
    private ArrayList facts;

    public Article(String text) {
        this.text = text;
        sentences = new ArrayList();
    }
    
    public void createSentences(){                  //filtering for the indication of new chapters needs to be added
        Reader reader = new StringReader(text);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);

        for (List<HasWord> sentence : dp) {
            Phrase line = new Phrase(Sentence.listToString(sentence));
            sentences.add(line);
        }
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Phrase> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<Phrase> sentences) {
        this.sentences = sentences;
    }

    public ArrayList getFacts() {
        return facts;
    }

    public void setFacts(ArrayList facts) {
        this.facts = facts;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagger;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import wikiminer.Article;
/**
 *
 * @author dxdiag
 */
public class POSTagger {

    private final static String stanford_Tags[] = {"_CC", "_CD", "_JJ", "_MD", "_DT", "_PRP$", "_PRP"};
    private final static String yago_Tags[] = {" [[con]]", " [[num]]", " [[adj]]", " [[mod]]", " [[det]]", " [[prp]]", " [[pro]]"};
    private final MaxentTagger tagger;
    private static String taggedText;

    public POSTagger() {
       tagger = new MaxentTagger("english-left3words-distsim.tagger");
    }
    
    public String Tag(String phrase) throws IOException, ClassNotFoundException {
        // Initialize the tagger  
        //The initial tagged string before tags replacement
        String tagged = tagger.tagString(phrase);
        //Tagged string
        for (int i = 0; i < stanford_Tags.length; i++) {
            // Replacing Stanford tags with Yago ones   
            tagged = tagged.replaceAll("\\s\\w+:?(" + stanford_Tags[i] + ")" + "|\\w+:?(" + stanford_Tags[i] + ")", yago_Tags[i]);
        }
        tagged = tagged.trim();
        //Replacing the other pos tags with NULL
        tagged = tagged.replaceAll("_\\w+", "");
        //Returning final tagged after replacement
        return tagged;
    }

      public void Tag(Article article) throws IOException, ClassNotFoundException {
          if(article.getPhrases().size() >0){
              for (int i=0;i<article.getPhrases().size();i++){
                  taggedText=Tag(article.getPhrases().get(i).getText());
                  article.getPhrases().get(i).setTextTagged(taggedText);
              }
          }
  }
  }


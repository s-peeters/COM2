/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tagger;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dxdiag
 */
public class Tagger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException,
            ClassNotFoundException {
        // Initialize the tagger  
      /*  MaxentTagger tagger = new MaxentTagger("left3words-wsj-0-18.tagger");

        //String sample = "Albert Einstein was born in Germany.";
        String sample = "A heat water in a large vessel";
        // The tagged string
        String tagged = tagger.tagString(sample);

        String stanford_Tags[] = {"/CC", "/CD", "/JJ", "/MD", "/DT", "/PRP$", "/PRP"};
        String yago_Tags[] = {" [[con]]", " [[num]]", " [[adj]]", " [[mod]]", " [[det]]", " [[prp]]", " [[pro]]"};

      //Tagged string
        System.out.println(tagged);

     //tagged=tagged.replaceAll(stanford_Tags[i], tagged);
        for (int i = 0; i < stanford_Tags.length; i++) {
            // Replacing Stanford tags with Yago ones   
            tagged = tagged.replaceAll("\\s\\w+:?(" + stanford_Tags[i] + ")" + "|\\w+:?(" + stanford_Tags[i] + ")", yago_Tags[i]);
        }
        tagged = tagged.trim();
        //Replacing the other pos tags with NULL
        tagged=tagged.replaceAll("/\\w+", "");
      //Final tagged after replacement
        System.out.println(tagged);
*/
    POSTagger tagger=new  POSTagger();
 String tagged= tagger.Tag("A beautiful bird in the nest");
        System.out.println(tagged);
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiminer;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author samuelpeeters
 */
public class Phrase {
    private String text;
    private String textAIDA;
    private String textTagged;
    private ArrayList<PhrasePart> phraseParts;

    public Phrase(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextTagged() {
        return textTagged;
    }

    public void setTextTagged(String textTagged) {
        this.textTagged = textTagged;
    }
    
    public void createTextTagged(){
        //TO DO
    }

    public String getTextAIDA() {
        return textAIDA;
    }

    public void setTextAIDA(String textAIDA) {
        this.textAIDA = textAIDA;
    }
    
    public void createPhraseParts(){
        phraseParts = new ArrayList();
        int entities = StringUtils.countMatches(textAIDA, ">"); //textAIDA
        String[] split = textTagged.split(">");   //textTagged
        for (int count = 0; count < entities-1; count++){
            String subject = split[count].split("<")[1];
            String predicate = split[count+1].split("<")[0];
            String object = split[count+1].split("<")[1];
            phraseParts.add(new PhrasePart(subject, predicate, object));
        }
        // <Albert Einstein> was born in <Ulm>, a <German> city.
        // <Albert Einstein          was born in <Ulm           , a <German          city.
        //      0                           1                       2                   3
    }
    
    
    
}

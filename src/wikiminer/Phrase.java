/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiminer;

/**
 *
 * @author samuelpeeters
 */
public class Phrase {
    private String text;
    private String textTagged;

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

    /*
    public void createPhraseParts(){
        int entities = StringUtils.countMatches(textAIDA, ">"); //textAIDA
        String[] split = textTagged.split(">");   //textTagged
        for (int count = 0; count < entities-1; count++){
            String subject = split[count].split("<")[1];
            String predicate = split[count+1].split("<")[0];
            String object = split[count+1].split("<")[1];
        }
    }*/
    
    
    
}

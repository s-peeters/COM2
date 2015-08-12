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
    private String textAIDA;
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
        
    }

    public String getTextAIDA() {
        return textAIDA;
    }

    public void setTextAIDA(String textAIDA) {
        this.textAIDA = textAIDA;
    }
    
    
    
    
    
}

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
public class PhrasePart {
    private String subject;
    private String object;
    private String predicate;

    public PhrasePart(String subject, String predicate, String object) {
        this.subject = subject;
        this.object = object;
        this.predicate = predicate;
    }    
}

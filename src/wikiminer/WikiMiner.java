/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiminer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuelpeeters
 */
public class WikiMiner {
    private static final boolean replaceHe = true;
    private static final String fileLocation = "/Users/samuelpeeters/Google Drive/School/COM2/Challenge/SummerSchoolData/IE-Challenge/Wiki-scientists/scientists-TEXT.txt";
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        File file = new File(fileLocation); //insert file location here
        try {
            mine(file, replaceHe);
            // TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(WikiMiner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mine(File file, boolean replaceHe) throws IOException{   //output type may need to be changed
        String raw = deserializeString(file);
        raw = raw.split("====PAGE-START====",2)[2]; //remove first page start
        while(raw.length()>5){
            String[] split = raw.split("====PAGE-START====",2);
            raw = split[2];
            //get subject
            split = split[1].split("https://en.wikipedia.org/wiki/",2);
            split = split[2].split("\n",2);
            String subject = split[1].replaceAll("_", " ");
            //replace he/she in split[2]
            String text = split[2];
            if (replaceHe) text = replaceHe(text, subject);
            //remove lines starting with = 
            text = text.replaceAll("(?m)^=.*", "");
            //create Article object
            Article article = new Article(split[2], subject); 
            article.createSentences();
            //AIDA --> replace text in phrase object
            //remove phrases with less than 2 entities
            //postag
            //replace 7 tags with the tag
            //
            //rest of mining process
        }
    }
    
    public static String replaceHe(String text, String subject){
            text = text.replaceAll("He ", " " + subject + " ");
            text = text.replaceAll(" he ", " " + subject + " ");
            text = text.replaceAll("She ", " " + subject + " ");
            text = text.replaceAll(" she ", " " + subject + " ");
            return text;
    }
    
    public static String deserializeString(File file) throws IOException {
      int len;
      char[] chr = new char[4096];
      final StringBuffer buffer = new StringBuffer();
      final FileReader reader = new FileReader(file);
      try {
          while ((len = reader.read(chr)) > 0) {
              buffer.append(chr, 0, len);
          }
      } finally {
          reader.close();
      }
      return buffer.toString();
  }
    
}

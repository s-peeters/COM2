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
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        File file = new File("/Users/samuelpeeters/Google Drive/School/COM2/Challenge/SummerSchoolData/IE-Challenge/Wiki-scientists/scientists-TEXT.txt"); //insert file location here
        try {
            mine(file);
            // TODO code application logic here
        } catch (IOException ex) {
            Logger.getLogger(WikiMiner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void mine(File file) throws IOException{   //output type may need to be changed
        String raw = deserializeString(file);
        raw = raw.split("====PAGE-START====",2)[2]; //remove first page start
        while(raw.length()>5){
            String[] split = raw.split("====PAGE-START====",2);
            raw = split[2];
            Article article = new Article(split[1]);
            article.createSentences();
            //rest of mining process
        }
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

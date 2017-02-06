/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import static java.nio.file.FileVisitOption.*;
import java.util.*;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;


import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * Finds a song by filename using glob engine
 * Made using this tutorial
 * https://docs.oracle.com/javase/tutorial/essential/regex/test_harness.html
 * @author Catalin Mazilu
 */
public class FindCommandRegexEngine 
extends SimpleFileVisitor<Path> 
{
    private final PathMatcher matcher;
    private int numMatches = 0;
    
    private String searchPattern="";
    
    private String searchCriteria="";

     FindCommandRegexEngine(String myCriteria, String pattern) {
            searchPattern = pattern;
            searchCriteria = myCriteria;
         
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern);
        }

        // Compares the glob pattern against
        // the file or directory name.
        void find(Path file) {
            Path name = file.getFileName();
          //  if (name != null && matcher.matches(name) && name.endsWith(".mp3")) {
              if (name != null && name.toString().endsWith(".mp3")) {
                  //System.out.println(file);
                  try (InputStream input = new FileInputStream(file.toString())) {
                    
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);
                    input.close();

                    // List all metadata
                    String[] metadataNames = metadata.names();

                                   
                   /**
                     * BUG - when the field given to matcher is empty
                     */
                    
                     
                    System.out.println("Search pattern is " + searchPattern);
                    Pattern pattern =  Pattern.compile(searchPattern);
                    if (searchCriteria.contentEquals("-album") && metadata.get("xmpDM:album")!=null ){
                      Matcher  mymatcher = pattern.matcher(metadata.get("xmpDM:album"));
                     
                           if (mymatcher.find()){
                                numMatches++;
                               System.out.println(file); 

                                System.out.println("Artists: " + metadata.get("xmpDM:artist"));
                                System.out.println("Composer : "+metadata.get("xmpDM:composer"));
                                System.out.println("Genre : "+metadata.get("xmpDM:genre"));
                                System.out.println("Album : "+metadata.get("xmpDM:album"));

                           }
                    
                    }
                    else if (searchCriteria.contentEquals("-artist") && metadata.get("xmpDM:artist")!=null){
                          Matcher   mymatcher = pattern.matcher(metadata.get("xmpDM:artist"));
                     
                           if (mymatcher.find()){
                                numMatches++;
                               System.out.println(file); 

                                System.out.println("Artists: " + metadata.get("xmpDM:artist"));
                                System.out.println("Composer : "+metadata.get("xmpDM:composer"));
                                System.out.println("Genre : "+metadata.get("xmpDM:genre"));
                                System.out.println("Album : "+metadata.get("xmpDM:album"));

                           }
                    
                    }
                    

                   
                    
                    } catch (FileNotFoundException e) {
                            
                            e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    } catch (SAXException e) {
                    e.printStackTrace();
                    } catch (TikaException e) {
                    e.printStackTrace();
                    }
                  
                  
                  
                  
                  
                  
                
            }
        }

        // Prints the total number of
        // matches to standard out.
        void done() {
            System.out.println("Matched: "
                + numMatches);
        }

        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file,
                BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        // Invoke the pattern matching
        // method on each directory.
        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    
    
}

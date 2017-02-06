/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.io.*;
import java.util.*;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;


import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



/**
 * Displays information about Mp3 file
 * @author Catalin Mazilu
 */
public class InfoCommand 
implements AudioCommand
{
        
    public String name ="info";
      /**
     * Returns the name of the command
     * @return name of command
     */
    public String getName(){
    
        return name;
        
    }
    
    /**
     * Displays info from audio file
     */
    public void runCommand(ShellPath path, String args)
    throws CommandException
    {
             StringTokenizer commandToken = new StringTokenizer(args);
        
            if (commandToken.countTokens()>2)
                throw(new CommandException("Invalid number of arguments"));
        
            commandToken.nextToken();
            String filename = commandToken.nextToken().toString();
            File f = new File(filename); 
            
            try {
                    InputStream input = new FileInputStream(new File(f.getAbsolutePath()));
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);
                    input.close();

                    // List all metadata
                    String[] metadataNames = metadata.names();

                  /*
                    for(String name : metadataNames){
                    System.out.println(name + ": " + metadata.get(name));
                    }
                   
                   */

                    // Retrieve the necessary info from metadata
                    // Names - title, xmpDM:artist etc. - mentioned below may differ based
                    System.out.println("----------------------------------------------");
                    System.out.println("Title: " + metadata.get("title"));
                    System.out.println("Artists: " + metadata.get("xmpDM:artist"));
                    System.out.println("Composer : "+metadata.get("xmpDM:composer"));
                    System.out.println("Genre : "+metadata.get("xmpDM:genre"));
                    System.out.println("Album : "+metadata.get("xmpDM:album"));

                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    } catch (SAXException e) {
                    e.printStackTrace();
                    } catch (TikaException e) {
                    e.printStackTrace();
                    }
                    
            
    
    }//End method
    
    
}

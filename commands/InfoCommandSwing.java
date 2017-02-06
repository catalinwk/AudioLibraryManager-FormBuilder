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
public class InfoCommandSwing

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
    public String runCommand(ShellPath path, String fileName)
    throws CommandException
    {
            File f = new File(fileName); 
            String output ="";
            
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

                    // Retrieve the necessary info from metadata
                    // Names - title, xmpDM:artist etc. - mentioned below may differ based
                    output =   ("----------------------------------------------\n");
                    output += ("Title: " + metadata.get("title")+"\n");
                    output += ("Artists: " + metadata.get("xmpDM:artist")+"\n");
                    output += ("Composer : "+metadata.get("xmpDM:composer")+"\n");
                    output += ("Genre : "+metadata.get("xmpDM:genre")+"\n");
                    output += ("Album : "+metadata.get("xmpDM:album")+"\n");

                    } catch (FileNotFoundException e) {
                           e.printStackTrace();
                    } catch (IOException e) {
                          e.printStackTrace();
                    } catch (SAXException e) {
                             e.printStackTrace();
                    } catch (TikaException e) {
                             e.printStackTrace();
                    }
                    
            return output;
    
    }//End method
    
    
}

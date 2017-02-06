/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.io.*;
import java.util.*;

//imports used for xml Parsing
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Add an audio  file to favorite lists, in XML format
 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * @author Catalin Mazilu
 */
public class FavCommandSwing 
implements AudioCommand
{
    /**
     * name of the command
     */       
    public String name ="fav";
    
    /**
     * Path to the favorite file list
     */
    public String favoritePath =".";
  
    /**
     * Initialize default current directory
     */
    public FavCommandSwing () {
        File f = new File(new File("favorite.xml").getAbsolutePath());
        favoritePath=f.getAbsolutePath();
    }
    
    
    
    /**
     * Returns the name of the command
     * @return name of command
     */
      public String getName(){
    
        return name;
        
    }
 
    
    /**
     * Add the audio file to favorites list
     */
    public void runCommand(ShellPath path, String filePath)
    throws CommandException
    {
            System.out.println("Favorite Path is" + favoritePath);
            System.out.println("==========================================================");
            File f = new File(new File(filePath).getAbsolutePath());
    
            if (f.exists()) {
                /*
                 * add the file to favorite file
                 */
                
                         try {
                            //String filepath = "c:\\file.xml";
                            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                            
                            //Document doc = docBuilder.parse(favoritePath);
                            Document doc = docBuilder.parse("C:\\app\\netbeansworkspace\\AudioLibraryManagerSwing\\favorite.xml");
                            

                            // Get the root element
                            Node favorite = doc.getFirstChild();

                            System.out.println(favorite);

                            // append a new node to staff
                            Element cale = doc.createElement("file");
                            cale.appendChild(doc.createTextNode(filePath));
                            favorite.appendChild(cale);

                           

                            // write the content into xml file
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);
                            StreamResult result = new StreamResult(new File(favoritePath));
                            transformer.transform(source, result);

                            

                       } catch (ParserConfigurationException pce) {
                            pce.printStackTrace();
                       } catch (TransformerException tfe) {
                            tfe.printStackTrace();
                       } catch (IOException ioe) {
                            ioe.printStackTrace();
                       } catch (SAXException sae) {
                            sae.printStackTrace();
                       }
                    
            
            
            
            }
                
    }
    
}

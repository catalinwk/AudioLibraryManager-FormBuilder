/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.io.*;
import java.util.*;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;



import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;



/**
 * Creates a Report from favorites songs
 * @author Catalin Mazilu
 */
public class ReportCommandSwing 

{
     /**
     * name of the command
     */       
    public String name ="report";
    
    /**
     * Path to the favorite file list
     */
    public String favoritePath =".";
  
    /**
     * Initialize default current directory
     */
    public ReportCommandSwing() {
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
     * Creates a Report
     */
    public void runCommand()
    throws CommandException
    {
          /*
         *  FreeMaker Tutorial from here 
         * http://viralpatel.net/blogs/freemaker-template-hello-world-tutorial/
         */
               
        
                //Freemarker configuration object
		Configuration cfg = new Configuration();
		try {
			//Load template from source folder
			Template template = cfg.getTemplate("audio_report.ftl");
			
			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("message", "Audio Libray Favorites Report");

			//List parsing 
			List<String> songs = new ArrayList<String>();

                        /*
                         * Reading song from favorites list
                         */
                                                                
                        try {

                                File fXmlFile = new File(favoritePath);
                                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                Document doc = dBuilder.parse(fXmlFile);

                                //optional, but recommended
                                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                                doc.getDocumentElement().normalize();

                                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                                NodeList nList = doc.getElementsByTagName("file");

                                //System.out.println("----------------------------");

                                for (int temp = 0; temp < nList.getLength(); temp++) {
                                        Node nNode = nList.item(temp);
                                        nNode.getAttributes();

                                        songs.add(nNode.getTextContent());

                                }

                            } catch (Exception e) {
                                throw(new CommandException(e.getMessage()));
                            }
                       
			data.put("songs", songs);

			
			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();

			// File output
			Writer file = new FileWriter (new File("audio_favorites_report.txt"));
			template.process(data, file);
			file.flush();
			file.close();
			
		} catch (IOException e) {
			throw(new CommandException(e.getMessage()));
		} catch (TemplateException e) {
			throw(new CommandException(e.getMessage()));
		}
        
    }
    
    
}

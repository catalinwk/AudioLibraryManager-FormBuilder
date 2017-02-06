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


/**
 * Creates a Report from favorites songs
 * @author Catalin Mazilu
 */
public class ReportCommand 
implements AudioCommand
{
     /**
     * name of the command
     */       
    public String name ="report";
    
    /**
     * Path to the favorite file list
     */
    public String favorite_path =".";
  
    /**
     * Initialize default current directory
     */
    public ReportCommand() {
        File f = new File(new File("favorite.txt").getAbsolutePath());
        favorite_path=f.getAbsolutePath();
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
    public void runCommand(ShellPath path, String args)
    throws CommandException
    {
          /*
         *  FreeMaker Tutorial from here 
         * http://viralpatel.net/blogs/freemaker-template-hello-world-tutorial/
         */
               
            System.out.println("Path of Favorite filelist is: " + favorite_path);
            
            StringTokenizer commandToken = new StringTokenizer(args);
        
            if (commandToken.countTokens()>1)
                throw(new CommandException("Invalid number of arguments"));
        
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
                        BufferedReader favfile = new BufferedReader(new FileReader(favorite_path));
                        String s = favfile.readLine();
                         while ( s!=null)
                         {
                             songs.add(s);
                             s=favfile.readLine();
                         }
                         favfile.close();
                       
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
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
        
        
        
    }
    
    
}

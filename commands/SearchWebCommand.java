/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.awt.Desktop;
import java.net.*;
import java.io.*;


/**
 * Add a file to favorite lists
 * @author Catalin Mazilu
 */
public class SearchWebCommand
implements AudioCommand
{
    String name = "searchweb";
    /**
     * Initialize default current directory
     */
    public SearchWebCommand () {

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
    public void runCommand(ShellPath path, String fileSearch)
    throws CommandException
    {
                File f = new File(fileSearch);
                String urlString="http://www.google.com/#q="+f.getName();
                Desktop d = Desktop.getDesktop();
                try {
                    
                    d.browse(new URL(urlString).toURI());
                    
                } catch (Exception e){
                    throw new CommandException(e.getMessage());
                }
          
    }
    
}

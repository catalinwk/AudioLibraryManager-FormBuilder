/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.io.*;
import java.util.*;


/**
 * Add a file to favorite lists
 * @author Catalin Mazilu
 */
public class FavCommand 
implements AudioCommand
{
    /**
     * name of the command
     */       
    public String name ="fav";
    
    /**
     * Path to the favorite file list
     */
    public String favorite_path =".";
  
    /**
     * Initialize default current directory
     */
    public FavCommand () {
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
     * Add the audio file to favorites list
     */
    public void runCommand(ShellPath path, String args)
    throws CommandException
    {
            System.out.println("Path of Favorite filelist is: " + favorite_path);
            
            StringTokenizer commandToken = new StringTokenizer(args);
        
            if (commandToken.countTokens()>2)
                throw(new CommandException("Invalid number of arguments"));
            
            commandToken.nextToken();

            String newpath = commandToken.nextToken().toString();
            File f = new File(new File(newpath).getAbsolutePath());
    
            if (f.exists()) {
                /*
                 * add the file to favorite file
                 */
                
                
                try (BufferedWriter favfile = new BufferedWriter(new FileWriter(favorite_path,true))) {
                   
                     favfile.write(f.getAbsolutePath().toString());
                     /*
                      * Adds new line - taken from BufferedWriter class
                      */
                     favfile.newLine();
                     
                   
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
                
            } else {

                throw(new CommandException("Incorect path"));

            }
    }
    
}

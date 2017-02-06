/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import java.io.*;
import java.util.*;


import audioformbuilder.*;

/**
 * Lists the content of current Path or given argument
 * @author Catalin Mazilu
 */
public class ListCommand 
implements AudioCommand


{
    
     /**
     * Name of the command
     */
    String name="list";
    
    /**
     * Returns the name of the command
     * @return name of command
     */
    public String getName(){
    
        return name;
        
    }
    
    /**
     * List the contents of a directory
     */
    public void runCommand(ShellPath path, String args)
    throws CommandException
    {
         StringTokenizer commandToken = new StringTokenizer(args);
        if (commandToken.countTokens()>2)
            throw(new CommandException("Invalid number of arguments"));
        
        File afile = new File(path.getPath());
        
        File[] filesList = afile.listFiles();
        
        for (File file : filesList){
            if(file.isDirectory())
                System.out.println(file.getName()+"<dir>");
            if(file.isFile()){
                if (file.getName().endsWith(".mp3") | file.getName().endsWith(".wav"))
                    System.out.println(file.getName());
            }
        
        
            
        
         }
    
    
    }
    
    
}

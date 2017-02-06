/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import static java.nio.file.FileVisitResult.*;
import static java.nio.file.FileVisitOption.*;
import java.util.*;

import java.util.*;



/**
 * Finds a song by name, author or album
 * @author CatalinMazilu
 */
public class FindCommand
implements AudioCommand
{
     /**
     * name of the command
     */       
    public String name ="find";
     
    /**
     * Returns the name of the command
     * @return name of command
     */
      public String getName(){
    
        return name;
        
    }
    
     /**
     * Find a song
     */
    public void runCommand(ShellPath path, String args)
    throws CommandException
    {
     
            StringTokenizer commandToken = new StringTokenizer(args);
        
            if (commandToken.countTokens()==1 || commandToken.countTokens()>3)
                throw(new CommandException("Usage: find [parameter] value\n-name find by name\n-author find by author\n-album find by album"));
            
            commandToken.nextToken();
            String parameter = commandToken.nextToken().toString();
            String search_pattern = commandToken.nextToken().toString();
            /*
             * find by name
             * FileVisitor Interface Explained
             * https://docs.oracle.com/javase/tutorial/essential/io/walk.html 
             */
            if (parameter.contentEquals("-name")){
                
                  System.out.println("Finding song by name");
                  
                  FindCommandGlobEngine finder = new FindCommandGlobEngine(search_pattern);
                  
                  Path startingDir = Paths.get(path.getPath());
                  
                  try {
                    Files.walkFileTree(startingDir, finder);
                    finder.done();
                  } catch (IOException e){
                      throw(new CommandException("IOException "+e.getMessage()));
                  }
                      
            } 
            /*
             * find by author
             */
            else if (parameter.contentEquals("-artist")){
                System.out.println("Find by artist");
                FindCommandRegexEngine finder = new FindCommandRegexEngine(parameter,search_pattern);
                  
                  Path startingDir = Paths.get(path.getPath());
                  
                  try {
                    Files.walkFileTree(startingDir, finder);
                    finder.done();
                  } catch (IOException e){
                      throw(new CommandException("IOException "+e.getMessage()));
                  }
                
                
                
            }
            /*
             * find by album
             */
            else if (parameter.contentEquals("-album")){
            
                    System.out.println("Find by album");
                    FindCommandRegexEngine finder = new FindCommandRegexEngine(parameter,search_pattern);
                  
                  Path startingDir = Paths.get(path.getPath());
                  
                  try {
                    Files.walkFileTree(startingDir, finder);
                    finder.done();
                  } catch (IOException e){
                      throw(new CommandException("IOException "+e.getMessage()));
                  }
            }
            else {
                throw(new CommandException("Incorect parameter.\nUsage: find [parameter] value\n-name find by name\n-artist find by artist\n-album find by album"));
            }
            
    
    }
}
    
    

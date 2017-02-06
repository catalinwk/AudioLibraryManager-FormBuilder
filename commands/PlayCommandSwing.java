/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.commands;
import audioformbuilder.commands.interfaces.*;
import audioformbuilder.*;

import java.io.*;
import java.util.*;
import java.awt.Desktop;


/**
 * Used to play an audio file
 * @author Catalin Mazilu
 */
public class PlayCommandSwing 
implements AudioCommand
{
    
    public String name ="play";
      /**
     * Returns the name of the command
     * @return name of command
     */
    public String getName(){
    
        return name;
        
    }
    
    /**
     * Play an audio file
     * @param path - current path of the shell, in ShellPath format
     * @param args - command arguments... play file.mp3
     * @throws CommandException d
     */
    public void runCommand(ShellPath path, String filePath)
    throws CommandException
    {
            
            File f = new File(new File(filePath).getAbsolutePath());
    
            if (f.exists()) {
                /*
                 * Plays the file
                 */
                
                Desktop d = Desktop.getDesktop();
                try {
                    //d.open(new File("c:\\app\\mp3\\doi\\ibelive.mp3"));
                    d.open(new File(f.getCanonicalPath()));
                } catch (IOException e){
                    System.out.println(e.getMessage());
                }
                
            } else {

                System.out.println(f.getAbsolutePath());
                throw(new CommandException("Incorect path"));

            }
    
    }
    
}


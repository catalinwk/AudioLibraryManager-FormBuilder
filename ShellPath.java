/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder;
import java.io.*;

/**
 * Used for storing current Path
 * @author Catalin Mazilu
 */
public class ShellPath {
    String path = ".";
    
    
    public String getPath(){
        return path;
    }
    
    /**
     *  sets and checks the given path 
     */
    public boolean setPath(String s){
        
        File sp = new File(s);
        if (sp.exists())
        {  
            path = s;
            return true;
        }
        
       
        
        return false;
    }
    
    @Override
    public String toString(){
        File fp = new File(path);
          try {
            return fp.getCanonicalPath() + "# ";
          } catch (IOException e){

            }
    
          return ".";
    }
}

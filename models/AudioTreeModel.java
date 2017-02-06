/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.models;
import javax.swing.tree.*;
import audioformbuilder.commands.*;


import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;



/**
 * This class creates a model for Audio Files JTree
 * @author Catalin Mazilu
 * made with this tutorial
 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class AudioTreeModel {
    public DefaultMutableTreeNode root;
    public String path;
    
    /**
     * default favorite file
     */
    public String favoritePath = "favorite.xml";
    /**
     * default favorite node
     */
    DefaultMutableTreeNode favorites;
    
    /**
     * Constructor - creates a tree from starting path
     * @param path - string given path
     */
    public AudioTreeModel (String path) throws CommandException {
        root = new DefaultMutableTreeNode("root", true);
        this.path = path;
        
        //adding favorites list
        favorites = new DefaultMutableTreeNode("FAVORITES");
       
        loadFavorites(favoritePath,favorites);
        root.add(favorites);
        
        getFileList(root,new File(path));
    }
    
    /**
     * returns the data for the JTree
     * @return - DefaultMutableTreeNode, JTree Model
     */
   public  DefaultMutableTreeNode getTreeModel(){
        
        return root;
        
    }
   
   public void addFavoriteModel(String favname){
       DefaultMutableTreeNode child = new DefaultMutableTreeNode(favname);
       favorites.add(child);
               
   }
    
    public  void getFileList(DefaultMutableTreeNode node, File f) {
     if(!f.isDirectory()) {
         // We keep only JAVA source file for display in this HowTo
         if (f.getName().endsWith(".mp3")) {
            //System.out.println("FILE  -  " + f.getName());
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
            node.add(child);
            }
         }
     else {
        // System.out.println("DIRECTORY  -  " + f.getName());
         DefaultMutableTreeNode child = new DefaultMutableTreeNode(f);
         node.add(child);
         File fList[] = f.listFiles();
         for(int i = 0; i  < fList.length; i++)
             getFileList(child, fList[i]);
         }
    }
  
    /**
     * Loads the favorites from default file located in current working directory
     * @param f filename of favorites
     */
    public void loadFavorites(String filePath, DefaultMutableTreeNode node) throws CommandException {
        
        
        /**
         * OLd code, replaced with XML parsing
         */
        /*
        File f = new File(new File(filePath).getAbsolutePath());
    
            if (f.exists()) {
               
                 //add the file to favorite file
                
                try (BufferedReader favfile = new BufferedReader(new FileReader(filePath))) {
                   
                    while (favfile.ready())
                    {
                        String file_name =  favfile.readLine();
                        DefaultMutableTreeNode child = new DefaultMutableTreeNode(file_name);
                        node.add(child);  
                    }
                    
                    
                   
                } catch (IOException e){
                    //System.out.println(e.getMessage());
                    throw(new CommandException(e.getMessage()));
                }
                
            } else {

                throw(new CommandException("Incorect path"));

            }
        
        */
         
        
        try {

	File fXmlFile = new File(filePath);
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

                String file_name =  nNode.getTextContent();
                DefaultMutableTreeNode child = new DefaultMutableTreeNode(file_name);
                node.add(child);  

	}
        
    } catch (Exception e) {
	throw(new CommandException(e.getMessage()));
    }
        
        
        
        
        
        
        
    }
    
    
}

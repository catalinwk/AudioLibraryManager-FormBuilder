/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package audioformbuilder.models;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;



import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;


import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Creates a data Model for JTbale containing the MP3 File info
 * @author Catalin Mazilu
 */
public class AudioInfoTableModel 
extends AbstractTableModel
{
     private String[] columnNames = {"File ", "Title","Artist","Composer","Genre","Album"};
        private Object[][] data = new Object[10][6];
        //index of object
        private int oIndex=0;
        /**
         * Creates the JTable data by scanning mp3 info from directory
         * @param path Path do directory containing Mp3 files
         */
       public AudioInfoTableModel(String path){
            File f = new File(path);
            
            File fList[] = f.listFiles();
             for(int i = 0; i  < fList.length; i++){
                    
                 //String xpath = fList[i].getAbsolutePath();
                 if (!fList[i].isDirectory()) addMP3Info(fList[i]);
             }
                 
       }
        
        public void addMP3Info(File f){
             
            
            try {
                    InputStream input = new FileInputStream(new File(f.getAbsolutePath()));
                    ContentHandler handler = new DefaultHandler();
                    Metadata metadata = new Metadata();
                    Parser parser = new Mp3Parser();
                    ParseContext parseCtx = new ParseContext();
                    parser.parse(input, handler, metadata, parseCtx);
                    input.close();

                    // List all metadata
                    String[] metadataNames = metadata.names();

                  /*
                    for(String name : metadataNames){
                    System.out.println(name + ": " + metadata.get(name));
                    }
                   
                   */

                    // Retrieve the necessary info from metadata
                    // Names - title, xmpDM:artist etc. - mentioned below may differ based
                   data[oIndex][0] = f.getName();
                   data[oIndex][1] = metadata.get("title");
                   data[oIndex][2] = metadata.get("xmpDM:artist");
                   data[oIndex][3] = metadata.get("xmpDM:composer");      
                   data[oIndex][4] = metadata.get("xmpDM:genre");
                   data[oIndex][5] = metadata.get("xmpDM:album");
                              
                   
                   oIndex++;
                   
                    } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    } catch (SAXException e) {
                    e.printStackTrace();
                    } catch (TikaException e) {
                    e.printStackTrace();
                    }
            
        }
                
                
        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        /*
        public Class getColumnClass(int c) {
            //return getValueAt(0, c).getClass();
        }
         */
        
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        /*
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        
         }
             */
        
        
        
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        /*
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    */
}

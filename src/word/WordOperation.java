package word;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import org.apache.poi.hwpf.HWPFDocument;

import utilities.Info;

public class WordOperation {
	
	static  private String OUTPUT_FILE = "new.doc";
	
	
	 public static   String  saveWord(String filePath, HWPFDocument doc) throws FileNotFoundException, IOException{
	        FileOutputStream out = null;
	        try{
	            out = new FileOutputStream(filePath);
	            doc.write(out);
	                    
	        }
	        catch(IOException ex)
	        {
	        	try
	        	{
	        	filePath = OUTPUT_FILE ;
	            out = new FileOutputStream(filePath);
	            doc.write(out);
	        	}
	        	catch(IOException e)
	            {
	        	JOptionPane.showMessageDialog(null, "Файл new.doc открыт. Закройте его и повторите операцию");
	            }
	        }
	        finally{
	        	if(out!=null)
	        		out.close();
	            
	        }
	        return filePath;
	    }
	    
	    public static void openWord(String filePath) {
	        Runtime rt = Runtime.getRuntime();
	        String prefix = "";
	        String prefixCurrentDir = "file:" + System.getProperty("user.dir") + System.getProperty("file.separator");
	        if(filePath.equals("new.doc"))
	        	prefix = prefixCurrentDir;
	        try {
	        	//System.out.println(CDUtilities.WORD_PATH + " \"" + prefix + "test.doc" + "\"");
	            rt.exec(Info.WORD_PATH + " \"" + prefix + filePath + "\"");
	        }
	        catch (IOException e) {
	        	 
	        	  
	        	JOptionPane.showMessageDialog(null, "Не возможно открыть"+e.getMessage());
	        }
	    }
	    
	
}

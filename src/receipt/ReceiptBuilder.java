package receipt;

import java.io.FileInputStream;

import javax.swing.JOptionPane;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import utilities.Info;
import word.WordOperation;

public class ReceiptBuilder {
	  private static String filepath =Info.PATERN_PATH+"/Pattern/receipt.doc";
	  private static POIFSFileSystem fs = null;
	    
public static void createReceipt(String curYear, String contractNumber, String faculty, String name, String priceDigit, String priceString) {
	  try{
	    String OUTPUT_FILE = "new.doc";
     
       	   fs = new POIFSFileSystem(new FileInputStream(filepath));
           HWPFDocument doc = new HWPFDocument(fs);
           doc = replaceText(doc, "ContractNumber", contractNumber);
           doc = replaceText(doc, "Studname", name);
       
           doc = replaceText(doc, "faculty", faculty);
          
        
           doc = replaceText(doc, "priceFigure", priceDigit);
   
           doc = replaceText(doc, "Curyear", curYear);
           doc = replaceText(doc, "priceString", priceString);
         
             
        
          OUTPUT_FILE= WordOperation.saveWord(OUTPUT_FILE, doc);
          doc.close();
          WordOperation.openWord(OUTPUT_FILE);
	  }
	  catch(Exception ex){ 
		  ex.printStackTrace();
		  JOptionPane.showMessageDialog(null,ex.getMessage());
      }
}
private static HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText){
    Range r1 = doc.getRange();

    for (int i = 0; i < r1.numSections(); ++i ) {
        Section s = r1.getSection(i);
        for (int x = 0; x < s.numParagraphs(); x++) {
            Paragraph p = s.getParagraph(x);
            for (int z = 0; z < p.numCharacterRuns(); z++) {
                CharacterRun run = p.getCharacterRun(z);
                String text = run.text();
                if(text.contains(findText)) {
                    run.replaceText(findText, replaceText);
                }
            }
        }
    }
    return doc;
}

}

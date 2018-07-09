package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class Faculties {

    public static  ArrayList<String> faculties = new ArrayList<String>();
    public static  ArrayList<String> listCodesUkr = new ArrayList<String>();
    public static  ArrayList<String> listCodesForeign = new ArrayList<String>();
	  static{
    	  BufferedReader bufRead=null;
    	  	try
    	  	{
    	  		FileReader input = new FileReader(Info.PATERN_PATH+"/faculties.txt");
    	     
    	      bufRead  = new BufferedReader(input);
    	      String myLine;
    	                   
    	      while ((myLine = bufRead.readLine()) != null){
    	         String temp[]=myLine.split(";");
    	         
    	         faculties.add(temp[0]);
    	         listCodesForeign.add(temp[1]+DateUtilities.getCurYearPrefix()+"-");    	         
    	      
    	         listCodesUkr.add(temp[2]+DateUtilities.getCurYearPrefix()+"-");
    	      
    	         
    	      }
    	     
    	  	}
    	  	catch(Exception e)
    	  	{
    	  		JOptionPane.showMessageDialog(null,e.getMessage());
    	  	}
    	  	finally
    	  	{
    	  		if(bufRead!=null)
    					try {
    						bufRead.close();
    					} catch (IOException e) {
    						
    						e.printStackTrace();
    					}
    	  		
    	  	}
        }
	  
}

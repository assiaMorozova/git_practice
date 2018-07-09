package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.poi.util.SystemOutLogger;

import contract.ua.Speciality;

public class Firms {

	  public static  ArrayList<String> listFirmsCodes=new ArrayList<String>();
	  public static ArrayList<String> listFirmsUa=new ArrayList<String>();

	 public static  ArrayList<String> listFirmsEng=new ArrayList<String>();
	
	  public static ArrayList<String> listFirmsHeads=new ArrayList<String>();
	  public static  ArrayList<String> listFirmsHeadsEng=new ArrayList<String>();
	  
	  public static ArrayList<String> listFirmsRequisite =new ArrayList<String>();
	  public static  ArrayList<String> listFirmsRequisiteEng=new ArrayList<String>();	  
	  
	  
	  static
	  {
		 
		  BufferedReader bufRead=null;
			try
			{
		    FileReader input = new FileReader( Info.PATERN_PATH+ "/firms.txt");
		   
		    bufRead  = new BufferedReader(input);
		    String myLine;
		                 
		    while ((myLine = bufRead.readLine()) != null){
		        String[] arr = myLine.split(";");
		       
		        listFirmsCodes.add(arr[0]); 
		        listFirmsUa.add(arr[1]);
		        listFirmsEng.add(arr[2]);
		        listFirmsHeads.add(arr[3]);
		        listFirmsHeadsEng.add(arr[4]);
		        //listFirmsRequisite.add(arr[5]);
		        //listFirmsRequisiteEng.add(arr[5]);
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
		////////////////
			try
			{
		    FileReader input = new FileReader( Info.PATERN_PATH+ "/firms_requisites.csv");
		   
		    bufRead  = new BufferedReader(input);
		    String myLine;
		    String s="";
		    int i=0;
		    while((myLine = bufRead.readLine())!= null){
	            String[] arr = myLine.split(";");
	            if(!arr[0].equals("") ){ //this means the NEW firm 
	            	if(i!=0)
	            		listFirmsRequisiteEng.add(s);
	            	i++;
	            	s="";
                	s+=arr[2]+"\r";
                	
                	
                }
	            if(arr[0].equals("") && arr[1].equals("")){ //this means the same firm and language
	               	s+=arr[2]+"\r";
	                	
	               }
	            if(arr[0].equals("") && !arr[1].equals("")){ //this means the same firm and other language //eng
	            		listFirmsRequisite.add(s);
	            		s="";
	                	s+=arr[2]+"\r";
	                	
	                }
		    
		   
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

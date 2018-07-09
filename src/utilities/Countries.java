package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Countries {
	  public static ArrayList<String> listCountriesUa=new ArrayList<String>();
	  public static ArrayList<String> listCountriesRu=new ArrayList<String>();
	  public static  ArrayList<String> listCountriesEng=new ArrayList<String>();
	  public static  ArrayList<Integer> listCountriesID=new ArrayList<Integer>();
	  /*public static  ArrayList<String> listFirmsEng=new ArrayList<String>();
	  public static  ArrayList<String> listFirmsCodes=new ArrayList<String>();
	  public static ArrayList<String> listFirmsHeads=new ArrayList<String>();
	  public static  ArrayList<String> listFirmsHeadsEng=new ArrayList<String>();
	  
	  public static ArrayList<String> listFirmsRu=new ArrayList<String>();
	  */
	  
	  
	  static
	  {
		 
		    BufferedReader bufRead=null;
			  	try
			  	{
			  		FileReader input = new FileReader(Info.PATERN_PATH+ "/country.txt");
			     
			      bufRead  = new BufferedReader(input);
			      String myLine;
			                   
			      while ((myLine = bufRead.readLine()) != null){
			          String[] arr = myLine.split(";");
			          listCountriesUa.add(arr[0]);
			          listCountriesEng.add(arr[1]);
			          listCountriesRu.add(arr[2]);
			          listCountriesID.add(Integer.parseInt(arr[3]));
			               
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


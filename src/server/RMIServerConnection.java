package server;

import java.rmi.Naming;

import javax.swing.JOptionPane;

import univer.oko.database.rmi.RMI;
import utilities.CDUtilities;

public class RMIServerConnection {
	  public static void instantiateServer() {
			CDUtilities.loadOptions();
	        try {
	        
	     	       DBWorker.instantiate((RMI) Naming.lookup(CDUtilities.SERVER_URL + "/oko"));
     	                        
	               System.out.println("Connect to server");
	               
	           
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Ошибка соединения с сервером!\n" + e.getMessage());
	           
	        }
	    }
}

package univer.oko.database.rmi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import univer.oko.Message;
import univer.oko.student.Entrant;


public interface RMI extends Remote {

	public Message saveEntrant(Entrant s) throws RemoteException;
	
}
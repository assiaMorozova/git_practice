package univer.oko.student;

import java.io.Serializable;
import java.util.Date;

public class Entrant implements Serializable{
		
	    public String StudSurname = "";
	    public String StudName = "";
	    public String StudMiddlename = "";
	    public int Ref_LtoP;
	    public int Ref_EducForm;
	    public int Ref_Country;
	    public int Ref_ArrivalLine=0;
	    public String ArrivalLine = "";
	    public int Course;
	    public int Ref_Language;
	    public float Years;
	    public int Ref_Direction;
	    
	    public String contractNumber = "";
	    public double contractValue;
	    public int Ref_ContractType;
	    public String payer="";
	    public Date dateOfBirth;
}

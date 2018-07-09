package contract.eng;
/**
 * Created by РЎР»Р°РІР° on 23.05.2017.
 */
public class Speciality {
    String specName;
    String eduProgram;
    String specNameEng;
    String eduProgramEng;
    int idDirection;

    /**
     * Price DAY bachelor
     */

    String priceBachDayDigits="0";
    String priceBachDayString="";
    String priceBachDayStringEng="";
    /**
     * Price DAY master
     */

    String priceMagDayDigits="0";
    String priceMagDayString="";
    String priceMagDayStringEng="";


    /**
     * Price ZAOCHNAYA bachelor
     */

    String priceBachZaDigits="0";
    String priceBachZaString="";
    String priceBachZaStringEng="";


    /**
     * Price ZAOCHNAYA master
     */

    String priceMagZaDigits="0";;
    String priceMagZaString="";
    String priceMagZaStringEng="";

    //цена магистр ОНП
    String priceMagDenDigitsONP;
    String priceMagDenStringONP;
    String priceMagDenStringONPEng;
    String priceMagZaDigitsONP;
    String priceMagZaStringONP;
    String priceMagZaStringONPEng;
    
    int numberZaYears=4;

    
 
    public Speciality(){

    }

    public String toString(){
        return (specName + ", " + eduProgram + ", " + priceBachDayDigits + ", " + priceBachDayString);
    }
}

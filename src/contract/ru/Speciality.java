package contract.ru;
/**
 * Created by РЎР»Р°РІР° on 23.05.2017.
 */
public class Speciality {
    String specName;
    String eduProgram;
    int idDirection;

    /**
     * Price DAY bachelor
     */

    String priceBachDayDigits="0";
    String priceBachDayString="";

    /**
     * Price DAY master
     */

    String priceMagDayDigits="0";
    String priceMagDayString="";


    /**
     * Price ZAOCHNAYA bachelor
     */

    String priceBachZaDigits="0";
    String priceBachZaString="";


    /**
     * Price ZAOCHNAYA master
     */

    String priceMagZaDigits="0";;
    String priceMagZaString="";

    //цена магистр ОНП
    String priceMagDenDigitsONP;
    String priceMagDenStringONP;
    String priceMagZaDigitsONP;
    String priceMagZaStringONP;
    
    int numberZaYears=4;

    
 
    public Speciality(){

    }

    public void print(){
        System.out.println(specName + ", " + eduProgram + ", " + priceBachDayDigits + ", " + priceBachDayString);
    }
}

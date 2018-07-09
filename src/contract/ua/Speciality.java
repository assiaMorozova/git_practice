package contract.ua;
/**
 * Created by Ð¡Ð»Ð°Ð²Ð° on 23.05.2017.
 */
public class Speciality {
    String specName;
    String eduProgram;
    int idDirection;

    /**
     * Price DAY bachelor
     */

    String priceBachDayUkrDigits;
    String priceBachDayUkrString;
    String priceBachDayEngDigits;
    String priceBachDayEngString;

    /**
     * Price DAY master
     */

    String priceMagDayUkrDigits;
    String priceMagDayUkrString;
    String priceMagDayEngDigits;
    String priceMagDayEngString;

    /**
     * Price ZAOCHNAYA bachelor
     */

    String priceBachZaUkrDigits;
    String priceBachZaUkrString;
    String priceBachZaEngDigits;
    String priceBachZaEngString;

    /**
     * Price ZAOCHNAYA master
     */

    String priceMagZaUkrDigits;
    String priceMagZaUkrString;
    String priceMagZaEngDigits;
    String priceMagZaEngString;
    
    //öåíà ìàãèñòð ÎÍÏ
    String priceMagDenDigitsONP;
    String priceMagDenStringONP;
    String priceMagZaDigitsONP;
    String priceMagZaStringONP;
    
    int numberZaYears;
   

    @Override
    public String toString() {
        return "Speciality{" +
                "specName='" + specName + '\'' +
                ", eduProgram='" + eduProgram + '\'' +
                ", priceBachDayUkrDigits='" + priceBachDayUkrDigits + '\'' +
                ", priceBachDayUkrString='" + priceBachDayUkrString + '\'' +
                ", priceBachDayEngDigits='" + priceBachDayEngDigits + '\'' +
                ", priceBachDayEngString='" + priceBachDayEngString + '\'' +
                ", priceMagDayUkrDigits='" + priceMagDayUkrDigits + '\'' +
                ", priceMagDayUkrString='" + priceMagDayUkrString + '\'' +
                ", priceMagDayEngDigits='" + priceMagDayEngDigits + '\'' +
                ", priceMagDayEngString='" + priceMagDayEngString + '\'' +
                '}'+System.getProperty("line.separator");
    }

    public Speciality(String specName, String eduProgram, String priceBachDayUkrDigits, String priceBachDayUkrString, String priceBachDayEngDigits, String priceBachDayEngString, String priceMagDayUkrDigits, String priceMagDayUkrString, String priceMagDayEngDigits, String priceMagDayEngString) {
        this.specName = specName;
        this.eduProgram = eduProgram;
        this.priceBachDayUkrDigits = priceBachDayUkrDigits;
        this.priceBachDayUkrString = priceBachDayUkrString;
        this.priceBachDayEngDigits = priceBachDayEngDigits;
        this.priceBachDayEngString = priceBachDayEngString;
        this.priceMagDayUkrDigits = priceMagDayUkrDigits;
        this.priceMagDayUkrString = priceMagDayUkrString;
        this.priceMagDayEngDigits = priceMagDayEngDigits;
        this.priceMagDayEngString = priceMagDayEngString;
    }

    public Speciality(){

    }

    public void print(){
        System.out.println(specName + ", " + eduProgram + ", " + priceBachDayUkrDigits + ", " + priceBachDayUkrString);
    }
}

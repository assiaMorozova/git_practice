package utilities;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Arrays;

/**
 * @author Tatiana Semenova
 */
public final class NumberUtilities {
	public static  Font font = new Font("Arial", Font.PLAIN, 16);
	  
    public static String formatDouble2Print(double d) {
        DecimalFormat numberFormatter = new DecimalFormat("###,###,##0.00");
        DecimalFormatSymbols formatSymb = numberFormatter.getDecimalFormatSymbols();
        formatSymb.setDecimalSeparator('.');
        numberFormatter.setDecimalFormatSymbols(formatSymb);
        return numberFormatter.format(d);
    }

    public static String formatInt2Print(double d) {
        DecimalFormat numberFormatter = new DecimalFormat("###,###,##0");
        return numberFormatter.format(d);
    }

    public static String doubleMoneyFormat(double d) {
        return doubleFormat(d, 2);
    }

    public static String doubleFormat(double d, int fraction) {
        Double D = new Double(d);
        if (D.isNaN() || D.isInfinite()) {
            return "";
        }
        String pattern = "########0";
        if (fraction > 0) pattern += ".";
        char [] arr = new char[fraction];
        Arrays.fill(arr, '0');
        pattern += new String(arr);
        return new DecimalFormat(pattern).format(d);
    }

    public static boolean verifyDouble(String number) {
        boolean isDouble = false;
        if (number == null || number.equals("")) {
            return false;
        }
        if (number.charAt(0) != '-' && !Character.isDigit(number.charAt(0))) {
            return false;
        }
        for (int i = 1; i < number.length(); i++) {
            if ((number.charAt(i) == '.' || number.charAt(i) == ',') && !isDouble) {
                isDouble = true;
            } else if ((number.charAt(i) == '.' || number.charAt(i) == ',') && isDouble) {
                return false;
            } else if (!Character.isDigit(number.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static double doubleParse(String number) {
        if (number.equals("")) {
            return 0.0;
        }

        int index;
        if (!verifyDouble(number)) {
            return Double.NaN;
        }

        index = number.indexOf(".");
        try {
            if (index != -1) {
                DecimalFormat df = new DecimalFormat("########0.00");
                DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
                dfs.setDecimalSeparator('.');
                df.setDecimalFormatSymbols(dfs);
                return df.parse(number).doubleValue();
            }

            return new DecimalFormat("########0,00").parse(number).doubleValue();
        }
        catch (ParseException e) {
            return Double.NaN;
        }
    }

    public static int intParse(String number) {
        if (number.equals("")) return 0;
        try {
            return Integer.parseInt(number);
        }
        catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

    public static String convertToRoman(int arabicNumber) {
        // I = 1, � =10, � = 100, � = 1000 � �� ������� V = 5, L = 50, D = 500.
        // TODO:  number translator must be implemented
        switch (arabicNumber) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
            case 11:
                return "XI";
            case 12:
                return "XII";
            case 13:
                return "XIII";
            case 14:
                return "XIV";
            case 15:
                return "XV";
            case 16:
                return "XVI";
            case 17:
                return "XVII";
            case 18:
                return "XVIII";
            case 19:
                return "XIX";
            case 20:
                return "XX";
        }
        return "";
    }
}

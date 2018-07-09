package utilities;
import javax.swing.*;

import org.jdesktop.swingx.plaf.MonthViewAddon;
import org.jdesktop.swingx.sort.SortUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;


public final class DateUtilities {
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    private static final int currentDateUpdaterDelay = 7200000; //every two hours
    /**
     * Current date is initialized in the beginning of
     * Client-side module execution with the help
     * of information retrieved from server.
     * (i.e. Client uses date that is set on the Server)
     */
    private static Date curDate;
    /**
     * String represents cur Date in format dd/MM/yyyy
     */
    private static String apFormatedCurDate;
    /**
     * String represents cur Date in format suitable for
     * current JDBC driver.
     * For MSSQLServer driver  and jdbc-odbc driver <code>{d 'yyyy-MM-dd'}</code> will be OK.
     */
    private static String baseFormatedCurDate;
    private static int currentPaymentYearID = Integer.MIN_VALUE;
    private static String currentPaymentYearName;
    private static final SimpleDateFormat full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // private static CurrentDateUpdater dateUpdater;

    public static Date NULL_DATE;
    public static Date YEAR_START;


    static {
        Calendar c = Calendar.getInstance();
        c.set(1, 0, 1, 0, 0, 0);
        DateUtilities.NULL_DATE = c.getTime();

        c = Calendar.getInstance();
        if (c.get(Calendar.MONTH) < Calendar.JULY) {
            c.set(c.get(Calendar.YEAR) - 1, Calendar.JULY, 1, 0, 0, 0);
        } else {
            c.set(c.get(Calendar.YEAR), Calendar.JULY, 1, 0, 0, 0);
        }
        YEAR_START = c.getTime();
    }

    public static boolean isNullDate(Date d) {
        return sdf1.format(d).equals(sdf1.format(NULL_DATE));
    }

    static public String reformatApp2Database(String s) {
        Date date = applicationParse(s);
        return databaseFormat(date);
    }

    static public String reformatDatabase2App(String s) {
        Date date = databaseParse(s);
        return applicationFormat(date);
    }

    static public Date getFirstMonthDate(int month) {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), month, 1, 0, 0, 0);
        return c.getTime();

    }

    static public Date getFirstMonthDate(int month, int year) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1, 0, 0, 0);
        return c.getTime();
    }

    public static String databaseFormat(Date date) {
        if (date == null || sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return "NULL";
        }
//        return "#"+sdf1.format(date)+"#";
        return "{d '" + sdf1.format(date) + "'}";

    }

    /*public static String getApFormatedCurDate() {
        if (apFormatedCurDate == null) {
            apFormatedCurDate = applicationFormat(getCurrentDate());
        }
        return apFormatedCurDate;
    }*/

    public static String getCurYearPrefix()
    {
    	/* Calendar c = Calendar.getInstance();
    	 String s = String.valueOf(c.get(Calendar.YEAR));
    	 return s.substring(2);
    	 */
    	Calendar c = Calendar.getInstance();
        c.setTime(DateUtilities.YEAR_START);
        String s = String.valueOf(c.get(Calendar.YEAR));
        return s.substring(2);
    }
    public static Date getCurYearBegin()
    {
    	 
    	 
    	Calendar c = Calendar.getInstance();
    	 c.setTime(getStartYearDate(c.getTime()));
    	  c.set(Calendar.DAY_OF_MONTH, 1);
    	  c.set(Calendar.MONTH,Calendar.SEPTEMBER);
    	  
    	  return c.getTime();

    }
    public static String getBaseFormatedCurDate() {
        if (baseFormatedCurDate == null) {
            // baseFormatedCurDate = databaseFormat(getCurrentDate());
        }
        return baseFormatedCurDate;
    }

   /* public static int getCurrentPaymentYearID() {
        if (currentPaymentYearID == Integer.MIN_VALUE) {
            RMILogger.log("пїЅпїЅпїЅпїЅпїЅпїЅ! пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ Payment Current Year id!");
            throw new RuntimeException("пїЅпїЅпїЅпїЅпїЅпїЅ! пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ Payment Current Year id!");
        }
        return currentPaymentYearID;
    }*/

    public static void setCurrentPaymentYearID(int id) {
        currentPaymentYearID = id;
    }

    private static Date databaseParse(String date) {
        try {
            return sdf1.parse(date);
        }
        catch (ParseException e) {
            return NULL_DATE;
        }
    }

    public static String applicationFormat(Date date) {
        if (date == null || sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return "";
        }
        return sdf2.format(date);
    }

    public static Date applicationParse(String date) {
        try {
            return sdf2.parse(date);
        }
        catch (ParseException e) {
            return NULL_DATE;
        }
    }

    public static String defaultFormat(Date date) {
        if (date == null || sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return "";
        }
        return DateUtilities.full.format(date);
    }

    /**
     * пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ:
     * пїЅпїЅпїЅпїЅ пїЅпїЅ 1 пїЅпїЅ 31 (30,29,28) - пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ
     * пїЅпїЅпїЅпїЅпїЅ пїЅпїЅ 1 пїЅпїЅ 12
     *
     * @param date -   пїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ: "пїЅпїЅ/пїЅпїЅ/пїЅпїЅпїЅпїЅ"
     * @return true - пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ, false - пїЅпїЅпїЅпїЅ пїЅпїЅпїЅ.
     */
    public static boolean verifyDate(Date date) {
        if (sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return true;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return verifyDate(
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.MONTH),
                c.get(Calendar.YEAR));
    }

    /**
     * пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ:
     * пїЅпїЅпїЅпїЅ пїЅпїЅ 1 пїЅпїЅ 31 (30,29,28) - пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ
     * пїЅпїЅпїЅпїЅпїЅ пїЅпїЅ 1 пїЅпїЅ 12
     *
     * @param date -   пїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ: "пїЅпїЅ/пїЅпїЅ/пїЅпїЅпїЅпїЅ"
     * @return true - пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ, false - пїЅпїЅпїЅпїЅ пїЅпїЅпїЅ.
     */
    public static boolean verifyDate(String date) {
        if (sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return true;
        }
        StringTokenizer st_in = new StringTokenizer(date, "/");
        String day;
        String month;
        String year;
        try {
            day = st_in.nextToken();
            month = st_in.nextToken();
            year = st_in.nextToken();
        }
        catch (Exception e) {
            return false;
        }

        if (day.length() > 2 || month.length() > 2 || year.length() != 4) {
            return false;
        }

        int iDay;
        int iMonth;
        int iYear;
        try {
            iDay = NumberUtilities.intParse(day);
            iMonth = NumberUtilities.intParse(month) - 1;
            iYear = NumberUtilities.intParse(year);
        }
        catch (Exception e) {
            return false;
        }

        return verifyDate(iDay, iMonth, iYear);
    }

    public static boolean verifyYear(int year) {
        if (year < 1950 || year > 2049) {
            return false;
        }
        return true;
    }

    private static boolean verifyDate(int day, int month, int year) {
        if (month < Calendar.JANUARY || month > Calendar.DECEMBER) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if (year < 1950 || year > 2049) {
            return false;
        }

        if (month == Calendar.FEBRUARY) {
            if (year % 4 == 0 && day > 29) {
                return false;
            } else if (year % 4 != 0 && day > 28) {
                return false;
            }
        } else if ((month == Calendar.APRIL || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.NOVEMBER)
                && day > 30) {
            return false;
        }

        return true;
    }

    public static java.sql.Date convertToSQLDate(Date date) {
        if (sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    public static Timestamp convertToTimestamp(Date date) {
        if (sdf1.format(date).equals(sdf1.format(NULL_DATE))) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static String getCurrentPaymentYearName() {
        if (currentPaymentYearName == null) {
            Calendar c = Calendar.getInstance();
            //   c.setTime(getCurrentDate());
            int currentYear = c.get(Calendar.YEAR);
            int currentMonth = c.get(Calendar.MONTH);

            if (currentMonth < Calendar.JULY) {
                currentPaymentYearName = (currentYear - 1) + "/" + currentYear;
            } else {
                currentPaymentYearName = currentYear + "/" + (currentYear + 1);
            }
        }

        return currentPaymentYearName;
    }


    public static String getPaymentYearName(int month, int year) {
        String res;

        if (month < Calendar.JULY) {
            res = (year - 1) + "/" + year;
        } else {
            res = year + "/" + (year + 1);
        }
        return res;
    }

    /**
     * Note! Before calling this method CurDate must be initialized!
     *
     * @param date
     * @return true if date is within currentYear
     */
    public static boolean isInCurrentPaymentYear(Date date) {
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        // calendar.setTime(getCurrentDate());
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        Date start;
        Date end;
        if (currentMonth < Calendar.JULY) {
            calendar.set(currentYear - 1, Calendar.JULY, 1, 0, 0, 0);
            start = calendar.getTime();
            calendar.set(currentYear, Calendar.JULY, 1, 0, 0, 0);
            end = calendar.getTime();
        } else {
            calendar.set(currentYear, Calendar.JULY, 1, 0, 0, 0);
            start = calendar.getTime();
            calendar.set(currentYear + 1, Calendar.JULY, 1, 0, 0, 0);
            end = calendar.getTime();
        }

        return date.after(start) && date.before(end);
    }

    public static Date getStartYearDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.MONTH) < Calendar.JULY) {
            c.set(c.get(Calendar.YEAR) - 1, Calendar.JUNE, 30, 23, 59, 59);
        } else {
            c.set(c.get(Calendar.YEAR), Calendar.JUNE, 30, 23, 59, 59);
        }
        return c.getTime();
    }

    public static Date getEndYearDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.MONTH) < Calendar.JULY) {
            c.set(c.get(Calendar.YEAR), Calendar.JULY, 1, 0, 0, 0);
        } else {
            c.set(c.get(Calendar.YEAR) + 1, Calendar.JULY, 1, 0, 0, 0);
        }
        return c.getTime();
    }

    public static Date getPlanDateOut(Date dateIn, double years_edu) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateIn);

        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);
        boolean drob = ((years_edu * 10) % 10 != 0);

        if (!drob && (m >= Calendar.JULY && m <= Calendar.DECEMBER)) {//осенний целое
            d = 30;
            m = Calendar.JUNE;
            y += (int) years_edu;
        } else if (!drob && (m >= Calendar.JANUARY && m <= Calendar.JUNE)) {//весенний целое
            d = 31;
            m = Calendar.DECEMBER;
            y += (int) years_edu - 1;
        } else if (drob && (m >= Calendar.JULY && m <= Calendar.DECEMBER)) {//осенний дробь
            d = 31;
            m = Calendar.DECEMBER;
            y += Math.floor(years_edu);
        } else if (drob && (m >= Calendar.JANUARY && m <= Calendar.JUNE)) {//весенний дробь
            d = 30;
            m = Calendar.JUNE;
            y += Math.floor(years_edu);
        }
        calendar.set(y, m, d);
        return calendar.getTime();
    }
    
   
    
    
    public static  Date getCurrentDate() {
        if (curDate == null) {
            Calendar c = Calendar.getInstance();
            curDate = c.getTime();
        }
        return curDate;
    }


   /* public static void setCurrentDate(Date curDate1) {
        curDate = curDate1;
        if (dateUpdater == null) {
            dateUpdater = new CurrentDateUpdater(currentDateUpdaterDelay);
            dateUpdater.start();
            RMILogger.log("dateUpdater = new CurrentDateUpdater(" + currentDateUpdaterDelay + ");");
        } else {
            RMILogger.log("Date was updated to " + curDate);
        }
    }*/

   /* public static int getCurYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(getCurrentDate());
        return c.get(Calendar.YEAR);
    }*/

    /*public static int getCurMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(getCurrentDate());
        return c.get(Calendar.MONTH);
    }*/

   /* public static int getCurDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(getCurrentDate());
        return c.get(Calendar.DAY_OF_MONTH);
    }*/

   /* private static class CurrentDateUpdater implements ActionListener {
        private int delay;
        private Timer timer;

        public CurrentDateUpdater(int delay) {
            this.delay = delay;
        }

        public void start() {
            if (timer == null) {
                timer = new Timer(delay, this);
                timer.start();
            }
        }

       /* public void actionPerformed(ActionEvent e1) {
            try {
                DateUtilities.setCurrentDate(DBWorker.instance().getCurrentDate());
            } catch (RemoteException e) {
                RMILogger.log(e);
                JOptionPane.showMessageDialog(null, e.getMessage() + "пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ.", "пїЅпїЅпїЅпїЅпїЅпїЅ", JOptionPane.ERROR_MESSAGE);
            }
        }
    }*/
}

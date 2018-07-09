package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public final class CDUtilities {
    public static final boolean DEBUG = false;
    public static final boolean LOCAL_DESKTOP_MODE = false;  // запускать ли приложение в локальном режиме
    public static final String FILE_CONNECTION_PROPERTIES = "Connection.properties";
    //--------------------------------------------------------------------------
    // идентификатор для выбора как студентов так и студентов перед переводом на след. курс

    // абитуриент
    public static final int ENTRANT = 0;
 
    //--------------------------------------------------------------------------


 
    //--------------------------------------------------------------------------
    //
    public static String SERVER_URL =   "rmi://localhost";
    //
    public static String EXCEL_PATH;
    //
    public static String BROWSER_PATH;
    //
    public static String WORD_PATH;
    //
    public static String DATABASE_PATH;
    //--------------------------------------------------------------------------


   

   
    public static void loadOptions() {
        String[] options = new String[4];
        String tmp;
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("ContractDepartment.ini"));
            for (int i = 0; (tmp = bfr.readLine()) != null; i++) {
                options[i] = tmp.substring(tmp.indexOf("=") + 1);
            }
//178.165.76.100:1099
            SERVER_URL = "rmi://" + options[0];//localhost
            EXCEL_PATH = options[1];
            BROWSER_PATH = options[2];
            WORD_PATH = options[3];
        }
        catch (IOException e) {
            System.err.println("Ошибка чтения файла конфигурации. Будут восстановлены начальные параметры.");
            
        }
    }

   

    
}

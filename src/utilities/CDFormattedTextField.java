package utilities;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.Date;

public class CDFormattedTextField extends JFormattedTextField {
    public static final String DATE = "DATE";
    public static final String YEARS = "YEARS";
    public static final String YEAR = "YEAR";

    public CDFormattedTextField() {
        this(DATE);
    }

    public CDFormattedTextField(int x, int y, int width, int height) {
        this(DATE);
        this.setBounds(x, y, width, height);
    }

    public CDFormattedTextField(String type) {
        if (type.equals(DATE)) {
            CDFormattedTextField.createDateFormatter().install(this);
        } else if (type.equals(YEARS)) {
            CDFormattedTextField.createYearsFormatter().install(this);
        } else if (type.equals(YEAR)) {
            CDFormattedTextField.createYearFormatter().install(this);
        }
    }

    public CDFormattedTextField(String type, int x, int y, int width, int height) {
        this(type);
        this.setBounds(x, y, width, height);
    }

    public Date getDate() {
        return DateUtilities.applicationParse(super.getText());
    }

    private static MaskFormatter createDateFormatter() {
        MaskFormatter f = null;
        try {
            f = new MaskFormatter("##/##/####");
            f.setPlaceholderCharacter('_');
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    private static MaskFormatter createYearFormatter() {
        MaskFormatter f = null;
        try {
            f = new MaskFormatter("####");
            f.setPlaceholderCharacter('_');
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    private static MaskFormatter createYearsFormatter() {
        MaskFormatter f = null;
        try {
            f = new MaskFormatter("####/####");
            f.setPlaceholderCharacter('_');
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return f;
    }
}
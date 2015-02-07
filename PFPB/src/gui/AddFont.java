package gui;

import java.awt.Font;
import java.io.*;

public class AddFont {

    private static Font ttfBase = null;
    private static Font telegraficoFont = null;
    private static InputStream myStream = null;    

    public static Font createFont(String FONT_PATH_TELEGRAFICO, int fontSize) {


            try {
                myStream = new BufferedInputStream(new FileInputStream(FONT_PATH_TELEGRAFICO));
                ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
                telegraficoFont = ttfBase.deriveFont(Font.PLAIN, fontSize);               
            } catch (Exception ex) {
                ex.printStackTrace();
                System.err.println("Font not loaded.");
            }
            return telegraficoFont;
    }
}

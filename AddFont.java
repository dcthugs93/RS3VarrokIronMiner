package scripts;

import org.tribot.api.General;

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.*;

public class AddFont {

	private static Font ttfBase = null;
    private static Font telegraficoFont = null;
    private static InputStream myStream = null;

    public static Font createFont(){
	
		  String dataFolder = System.getenv("APPDATA");
    	   File afile =new File(dataFolder,".tribot\\bin\\scripts\\oj.ttf");
            try {
                myStream = new BufferedInputStream(
                        new FileInputStream(afile));
                ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
                telegraficoFont = ttfBase.deriveFont(Font.PLAIN, 32);               
            } catch (Exception ex) {
                ex.printStackTrace();
                //General.println("Font not loaded.");
            }
            return telegraficoFont;
    }
}
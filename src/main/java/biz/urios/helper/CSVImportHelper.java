package biz.urios.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class CSVImportHelper {

	public final static int EMDBID = 0;
	public final static int IMDBID = 1;
	public final static int TITLE = 2;
	public final static int AKATITLE = 3;
	public final static int YEAR = 4;
	public final static int DIRECTORS = 5;
	public final static int TAGS = 6;
	public final static int COUNTRY = 7;
	public final static int GENRES = 8;
	public final static int COLLECTIONS = 9;
	public final static int MEDIA = 10;
	
	public final static String CHARSETNAME = "UTF-16";
	
	public static Reader getReaderFromFileForCHARSETNAME(String pathToFile) throws FileNotFoundException {
		File initialFile = new File(pathToFile);
		InputStream inputStream = new FileInputStream(initialFile);

		Reader isreader = new InputStreamReader(inputStream, Charset.forName(CHARSETNAME));
		return isreader;
	}

	public static int getIntFormString(String s)
	{
		int i=Integer.parseInt(s);
		return i;
	}
      
	public static long getLongFormString(String s)
	{
		long l=Long.parseLong(s);
		return l;
	}
	
	
}

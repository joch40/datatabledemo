package biz.urios.helper;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class FilmManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(FilmManager.class);

	private static FilmManager theFilmManager = null;

	public static FilmManager getFilmManager() {
		if (theFilmManager == null) {
			theFilmManager = new FilmManager();
			try {
				theFilmManager.importCSVfile("emdb-kurz.csv");
			} catch (CsvValidationException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return theFilmManager;
	}

	// End Static Factory
	
	
	private ArrayList<Film> theFilms;
	private HashMap<String, Film> imdbMap;
	private int importok = 0;
	private int doublecopy = 0;
	private int wrongimdbid = 0;

	public FilmManager() {
		super();
		theFilms = new ArrayList<Film>();
		imdbMap = new HashMap<String, Film>();
	}

	public int importCSVfile(String pathToFile) throws CsvValidationException, IOException {
		String[] nextLine;

		Reader isreader = CSVImportHelper.getReaderFromFileForCHARSETNAME(pathToFile);

		CSVReader reader = new CSVReaderBuilder(isreader)
				.withCSVParser(new CSVParserBuilder().withIgnoreQuotations(true).withSeparator('\t').build())
				.withSkipLines(1).build();
		int index = 0;
		while ((nextLine = reader.readNext()) != null) {
			Film f = new Film();
			f.fillFromArray(nextLine,index);
			String imdbid = f.getImdbId();
			if (!imdbid.isBlank()) {
				Film test = imdbMap.get(imdbid);
				if (test == null) {
					// der ist wirklich neu
					imdbMap.put(imdbid, f);
					theFilms.add(f);
					index++;
					importok++;
//					log.info("Add to List: " + f.toString());
				} else {
//					log.info("Film on list allready: " + f.toString());
					doublecopy++;
				}

			} else {
//				log.info("no corect imdb info : " + f.toString());
				wrongimdbid++;
			}
		}
		log.info("ScanResult: : " + this.toString());
		return 0;
	}

	public ArrayList<Film> getFilms() {

		return theFilms;
	}
	@Override
	public String toString() {
		return "FilmManager [theFilmsSize=" + theFilms.size() + ", importok=" + importok + ", doublecopy=" + doublecopy
				+ ", wrongimdbid=" + wrongimdbid + "]";
	}

}

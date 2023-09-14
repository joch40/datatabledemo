package biz.urios.helper;

import java.io.Serializable;



public class Film implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// private static Logger log = LoggerFactory.getLogger(Film.class);
	private long id;
	private long emdbId;
	private String imdbId;
	private String mainTitle;
	private String akaTitle;
	private int year;
	private String country;
	private String collection;

	// Dies sind potentiell n felder (durch komma getrennt)
	private String directors;
	private String tags;
	private String generes;

	// die gehen später in die FilmKopie
	private String media; // steckt in comment


	public int fillFromArray(String[] line, long id) {
		// log.info("fill from array");
		this.id = id;
		emdbId = CSVImportHelper.getLongFormString(line[CSVImportHelper.EMDBID].trim());
		imdbId = line[CSVImportHelper.IMDBID];
		mainTitle = line[CSVImportHelper.TITLE];
		akaTitle = line[CSVImportHelper.AKATITLE];
		
		if (!line[CSVImportHelper.YEAR].isBlank())
		year = CSVImportHelper.getIntFormString(line[CSVImportHelper.YEAR].trim());
		
		country = line[CSVImportHelper.COUNTRY];
		collection = line[CSVImportHelper.COLLECTIONS];

		directors = line[CSVImportHelper.DIRECTORS];
		tags = line[CSVImportHelper.TAGS];
		generes = line[CSVImportHelper.GENRES];
		media = line[CSVImportHelper.MEDIA];
		
		// log.info(toString());
		return 0;
	}

	@Override
	public String toString() {
		return "Film [emdbId=" + emdbId + ", imdbId=" + imdbId + ", mainTitle=" + mainTitle + ", akaTitle=" + akaTitle
				+ ", year=" + year + ", country=" + country + ", collection=" + collection + ", directors=" + directors
				+ ", tags=" + tags + ", generes=" + generes + ", media=" + media  + "]";
	}

	
	public long getId() {
		return id;
	}

	public long getEmdbId() {
		return emdbId;
	}

	public String getImdbId() {
		return imdbId;
	}

	public String getmainTitle() {
		return mainTitle;
	}

	public String getAkaTitle() {
		return akaTitle;
	}

	public int getYear() {
		return year;
	}

	public String getCountry() {
		return country;
	}

	public String getCollection() {
		return collection;
	}

	public String getDirectors() {
		return directors;
	}

	public String getTags() {
		return tags;
	}

	public String getGeneres() {
		return generes;
	}

	public String getMedia() {
		return media;
	}

}

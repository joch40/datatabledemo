package biz.urios.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValue;

/**
 * Some comment
 * 
 * here we have some additional comments
 * 
 */
public class FilmDataProvider extends SortableDataProvider<Film, String> {

	private static final long serialVersionUID = 1L;

	private final String searchFilter;
	
	FilmManager myFilmManager = null;

	public FilmDataProvider() {
		super();
		myFilmManager = null;
		searchFilter = null;
	}

	public FilmDataProvider(String searchFilter) {
		this.searchFilter = searchFilter != null ? searchFilter.toLowerCase() : null;
	}

	public void setFilmManager(FilmManager fm) {
		myFilmManager = fm;

	}

	@Override
	public Iterator<? extends Film> iterator(long first, long count) {

		ArrayList<Film> theFilms = myFilmManager.getFilms();
		int last = (int) first + (int) count - 1;
		List<? extends Film> sublist;
		if (last >= theFilms.size()) {
			sublist = theFilms.subList(0, (int) theFilms.size());
		} else {
			sublist = theFilms.subList((int) first, last);
		}
		return sublist.iterator();
	}

	@Override
	public long size() {
		return myFilmManager.getFilms().size();
	}

	@Override
	public IModel<Film> model(Film object) {
		return Model.of(object);
	}

	public Film getFilmFromStringID(StringValue sv) {
		Film ret = null;
		ArrayList<Film> theFilms = myFilmManager.getFilms();
		int index = Integer.parseInt(sv.toString());
		if ((index >= 0) && (index < theFilms.size()))
		{
			ret = theFilms.get(index);
		}
		return ret;
	}

	public String getSearchFilter() {
		return searchFilter;
	}
}

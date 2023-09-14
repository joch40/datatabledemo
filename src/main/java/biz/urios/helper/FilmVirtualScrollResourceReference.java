package biz.urios.helper;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.datatables.virtualscroll.AbstractVirtualScrollResourceReference;

import com.github.openjson.JSONObject;

/**
 * A resource reference that delivers data for the demo of virtual scrolling
 */
public class FilmVirtualScrollResourceReference extends AbstractVirtualScrollResourceReference<Film> {

	private static final long serialVersionUID = 1L;

	public FilmVirtualScrollResourceReference() {
		super("inifiniteScroll");
	}

	@Override
	protected void populateDataJson(final JSONObject response, final IDataProvider<Film> dataProvider) {
		// use a really big number for a good demo of virtual scrolling
		int size = (int) dataProvider.size();
		response.put(RECORDS_TOTAL_RESPONSE_FIELD, size);
		response.put(RECORDS_FILTERED_RESPONSE_FIELD, size);
	}

	@Override
	protected void populateEntryJson(final JSONObject entryJson, final Film f) {
		entryJson.put("DT_RowId",  f.getId());
		entryJson.put("DT_RowClass", "custom");

		entryJson.put("mainTitle", f.getmainTitle());
//		entryJson.put("imdbId", f.getImdbId());
		entryJson.put("directors", f.getDirectors());
		entryJson.put("generes", f.getGeneres());
		entryJson.put("year", f.getYear());
	}

	@Override
	protected IDataProvider<Film> getDataProvider(PageParameters parameters) {
		final String searchFilter = parameters.get(SEARCH_VALUE_PARAMETER).toString();
		FilmManager myFilmManager = FilmManager.getFilmManager();
		FilmDataProvider fdp = new FilmDataProvider(searchFilter);
		fdp.setFilmManager(myFilmManager);
		return fdp;
	}
}

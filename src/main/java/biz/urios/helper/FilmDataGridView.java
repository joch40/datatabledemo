package biz.urios.helper;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FilmDataGridView<T> extends DataGridView<T> {

	private static final long serialVersionUID = 1L;

	// private int maxrows = 2000;

	public FilmDataGridView(String id, List<IColumn<T, String>> populators, IDataProvider<T> dataProvider, int max) {
		super(id, populators, dataProvider);
		// maxrows = max;
	}

	@Override
	public long getItemsPerPage() {
		return 2000;
	}

//	
//	protected void populateEntryJson(final JSONObject entryJson,  Film f) {
//		entryJson.put("DT_RowId",  f.getId());
//		entryJson.put("DT_RowClass", "custom");
//
//		entryJson.put("mainTitle", f.getmainTitle());
////		entryJson.put("imdbId", f.getImdbId());
//		entryJson.put("directors", f.getDirectors());
//		entryJson.put("generes", f.getGeneres());
//		entryJson.put("year", f.getYear());
//	}
//	
	@Override
	protected Item<T> newRowItem(String id, int index, IModel<T> model) {
		// get the real Object
		Film f = (Film) model.getObject();
		// get the id that should later show up
		long filmid = f.getId();
		// create a new row
		Item<T> row = new Item<T>(id, index, model);
		
		// Var 1 put the key word DT_RowId together with the id in a String 
		String idstring = "DT_RowId" + Long.toString(filmid);
		// add the idstring to a row modifier 
		row.add(AttributeModifier.replace(id, Model.of(idstring )));
		
		// Var 2 add just the id (as a long) to a row modifier 
		// row.add(AttributeModifier.replace(id, Model.of(filmid)));
		return row;

		// Standard:
		// return new Item<T>(id, index, f);
	}

}

package biz.urios.helper;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

public class FilmDataGridView<T> extends DataGridView<T> {
	
	private static final long serialVersionUID = 1L;
	
	public FilmDataGridView(String id, List<IColumn<T, String>> populators,
			IDataProvider<T> dataProvider) {
		super(id, populators, dataProvider);
	
	}

	@Override
	public long getItemsPerPage() {
		return 30;
	}
	
	@Override
	protected Item<T> newRowItem(String id, int index, IModel<T> model)
	{
		System.out.println("at newRowItem index:"+index+ " id "+ id+ " model "+ model);
		return new Item<>(id, index, model);
	}

}

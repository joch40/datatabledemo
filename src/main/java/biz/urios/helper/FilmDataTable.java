package biz.urios.helper;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.resource.CoreLibrariesContributor;
import org.wicketstuff.datatables.DataTables;

public class FilmDataTable<T,S> extends DataTables<T, S> {

	private static final long serialVersionUID = 1L;

	private AjaxEventBehavior selectBehavior;
//	public FilmDataTable(String id, List columns, IDataProvider dataProvider, long rowsPerPage) {
//		super(id, columns, dataProvider, rowsPerPage);
//		// TODO Auto-generated constructor stub
//	}

	public FilmDataTable(java.lang.String id, List<IColumn<T, S>> columns, IDataProvider<T> dataProvider,
			long rowsPerPage, AjaxEventBehavior sB) {
		super(id, columns, dataProvider, rowsPerPage);
		selectBehavior = sB;

	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		Application app = getApplication();
		CoreLibrariesContributor.contributeAjax(app, response);
		CallbackParameter evt = CallbackParameter.explicit("evt");
		CallbackParameter dt = CallbackParameter.explicit("dt");
		CallbackParameter type = CallbackParameter.explicit("type");
		CallbackParameter indexes = CallbackParameter.explicit("indexes");

		java.lang.String callbackFunction = selectBehavior.getCallbackFunction(evt, dt, type, indexes).toString();
		response.render(OnDomReadyHeaderItem
				.forScript(java.lang.String.format("$('#%s').on('select.dt', %s)", getMarkupId(), callbackFunction)));

	}

//	@Override
//	protected DataGridView<Person> newDataGridView(final String id,
//			final List<IColumn<Person, String>> iColumns, final IDataProvider<Person> dataProvider) {
//		return new DataGridView<Person>(id, iColumns, dataProvider) {
//			
//			
//			
//			@Override
//			public long getItemsPerPage() {
//				return rowsPerPage;
//			}
//		};
//	}
	public FilmDataGridView<Person> getGridView(String id, List<IColumn<Person, String>> populators,
			IDataProvider<Person> dataProvider)
	{
		FilmDataGridView fdgv = new FilmDataGridView(id,populators,dataProvider);
		return fdgv;
	}
	
	//  protected DataGridView<T> newDataGridView(final String id, final List<? extends IColumn<T, S>> iColumns, final IDataProvider<T> dataProvider)
	
	@Override
	protected DataGridView<T> newDataGridView (final String id, final List<? extends IColumn<T, S>> populators,
			final IDataProvider<T> dataProvider)
	{
		DataGridView fdgv = new FilmDataGridView(id,populators,dataProvider);
		return fdgv;
	}
	

}

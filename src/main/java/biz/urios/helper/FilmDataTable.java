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
	
	private int maxrows; 

	public FilmDataTable(java.lang.String id, List<IColumn<T, S>> columns, IDataProvider<T> dataProvider,
			long rowsPerPage, AjaxEventBehavior sB, int max) {
		super(id, columns, dataProvider, rowsPerPage);
		selectBehavior = sB;
		maxrows = max; 

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

	//  protected DataGridView<T> newDataGridView(final String id, final List<? extends IColumn<T, S>> iColumns, final IDataProvider<T> dataProvider)
	
	@Override
	protected DataGridView<T> newDataGridView (final String id, final List<? extends IColumn<T, S>> populators,
			final IDataProvider<T> dataProvider)
	{
		DataGridView fdgv = new FilmDataGridView(id,populators,dataProvider, maxrows);
		return fdgv;
	}
	

}

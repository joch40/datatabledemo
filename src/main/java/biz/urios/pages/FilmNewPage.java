package biz.urios.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.datatables.Sort;
import org.wicketstuff.datatables.columns.SpanColumn;
import org.wicketstuff.datatables.columns.SpanHeadersToolbar;
import org.wicketstuff.datatables.columns.SpanPropertyColumn;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.options.SelectOptions;
import org.wicketstuff.datatables.themes.BootstrapTheme;

import biz.urios.helper.Film;
import biz.urios.helper.FilmDataProvider;
import biz.urios.helper.FilmDataTable;
import biz.urios.helper.FilmManager;
import de.agilecoders.wicket.jquery.util.Json;

/**
 *
 */
public class FilmNewPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	FilmManager myFilmManager;
	
	public FilmNewPage(PageParameters parameters) {
		super(parameters);

		//  Spalten Definition
		List<IColumn<Film, String>> columns = new ArrayList<>();
		
		columns.add(new SpanPropertyColumn<Film, String>(Model.of("id"), "id", "id") {
			private static final long serialVersionUID = 1L;

			@Override
			public int getRowspan() {
				return 2;
			}
		});
		
		columns.add(new PropertyColumn<>(Model.of("mainTitle"), "mainTitle", "mainTitle"));
		columns.add(new PropertyColumn<>(Model.of("directors"), "directors", "directors"));
		columns.add(new PropertyColumn<>(Model.of("generes"), "generes", "generes"));
		columns.add(new SpanPropertyColumn<Film, String>(Model.of("year"), "year", "year") {
			private static final long serialVersionUID = 1L;

			@Override
			public int getRowspan() {
				return 2;
			}
		});

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		feedback.setOutputMarkupId(true);

		// Psydo Datenbank
		myFilmManager = FilmManager.getFilmManager();

		// Instanz von SortableDataProvider welche für den DataTable gebraucht wird
		FilmDataProvider dataProvider = new FilmDataProvider();
		dataProvider.setFilmManager(myFilmManager);

		final AjaxEventBehavior selectBehavior = new AjaxEventBehavior("select.dt") {
			private static final long serialVersionUID = -1127930233103803502L;

			@Override
			protected void onEvent(final AjaxRequestTarget target) {
				Request request = RequestCycle.get().getRequest();
				List<StringValue> values = request.getRequestParameters().getParameterValues("id");
				if (values == null || values.isEmpty())
				{
					System.out.println(" Got Call Back from Table but Parameters are empty");
				}
				else
				{
					System.out.println(" Got Call Back from Table Parameters are: ");
					for (StringValue sv : values) {
						System.out.println("StringValue: " + sv);
					}
					info("Selected: " + values);
					target.add(feedback);
				}
			}

			@Override
			protected void updateAjaxAttributes(final AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);

				attributes.getDynamicExtraParameters()
						.add("var arr=[]; dt.rows(indexes).every(function() {arr=arr.concat({\"name\":\"id\","
								+ "\"value\": $(this.node()).attr('id')})}); return arr;");
			}

			@Override
			public void renderHead(final Component component, final IHeaderResponse response) {
				// do not contribute the default
			}
		};
		
		final FilmDataTable<Film, String> table = new FilmDataTable <Film, String>("table", columns, dataProvider, 2000,selectBehavior,myFilmManager.getFilms().size());
		
		table.add(selectBehavior);
		add(table);
		
		

		SpanColumn<Film, String> namesColumn = new SpanColumn<Film, String>(Model.of("mainTitle"), null) {
			@Override
			public int getColspan() {
				return 2;
			}
		};
		table.addTopToolbar(new SpanHeadersToolbar<String>(table, namesColumn));
		table.addTopToolbar(new SpanHeadersToolbar<String>(table));
//        table.addTopToolbar(new HeadersToolbar<String>(table, dataProvider));

		

        SelectOptions selectOptions = new SelectOptions()
                .style(SelectOptions.Style.OS);

        
		Options options = table.getOptions();
		table.add(new BootstrapTheme(options));
		options.order(new Sort(2, Sort.Direction.ASC)); // single column ordering
		options.stateDuration(3600)
		.stateSave(true)
		.pagingType(Options.PagingType.simple)
		.select(selectOptions)
		.retrieve(true)
		.rowId(new Json.RawValue("0"));
		;

	}

}

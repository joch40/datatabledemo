package biz.urios.pages;

import static de.agilecoders.wicket.jquery.JQuery.$;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.resource.CoreLibrariesContributor;
import org.apache.wicket.util.string.StringValue;
import org.wicketstuff.datatables.DataTables;
import org.wicketstuff.datatables.columns.SpanColumn;
import org.wicketstuff.datatables.columns.SpanHeadersToolbar;
import org.wicketstuff.datatables.columns.SpanPropertyColumn;
import org.wicketstuff.datatables.options.Column;
import org.wicketstuff.datatables.options.Options;
import org.wicketstuff.datatables.options.ScrollerOptions;
import org.wicketstuff.datatables.options.SelectOptions;
import org.wicketstuff.datatables.themes.BootstrapTheme;

import biz.urios.helper.Film;
import biz.urios.helper.FilmDataProvider;
import biz.urios.helper.FilmManager;
import biz.urios.helper.FilmVirtualScrollResourceReference;
import de.agilecoders.wicket.jquery.function.JavaScriptInlineFunction;
import de.agilecoders.wicket.jquery.util.Json;

/**
 * 
 * This works somehow
 *
 */
public class FilmPage extends WebPage {

	private static final long serialVersionUID = -3737249804804384464L;
	FilmManager myFilmManager;
	// final FeedbackPanel feedback;
	final private Label iDlabel;
	final private Label namelabel;

	public FilmPage(PageParameters parameters) {
		super(parameters);

		// Psydo Datenbank
		myFilmManager = FilmManager.getFilmManager();

		// Instanz von SortableDataProvider welche für den DataTable gebraucht wird
		FilmDataProvider dataProvider = new FilmDataProvider();
		dataProvider.setFilmManager(myFilmManager);

		// Lable auf der HTML Seite die per Ajax gefüllt werden sollen
		iDlabel = new Label("idlable", new Model<String>(""));
		iDlabel.setOutputMarkupId(true);
		add(iDlabel);
		namelabel = new Label("nameLabel", new Model<String>(""));
		namelabel.setOutputMarkupId(true);
		add(namelabel);

		// Eigentliche Spalten
		List<IColumn<Film, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<>(Model.of("mainTitle"), "mainTitle", "mainTitle"));
		// columns.add(new PropertyColumn<>(Model.of("imdbId"), "imdbId", "imdbId"));
		columns.add(new PropertyColumn<>(Model.of("directors"), "directors", "directors"));
		columns.add(new PropertyColumn<>(Model.of("generes"), "generes", "generes"));
		columns.add(new SpanPropertyColumn<Film, String>(Model.of("year"), "year", "year") {
			private static final long serialVersionUID = 1L;

			@Override
			public int getRowspan() {
				return 2;
			}
		});

		// Noch mal Spalten
		// the column definitions needed by DataTables.js to map the Ajax response data
		// to the table columns
		List<Column> jsColumns = new ArrayList<>();
		jsColumns.add(new Column("mainTitle"));
//		jsColumns.add(new Column("imdbId"));
		jsColumns.add(new Column("directors"));
		jsColumns.add(new Column("generes"));
		jsColumns.add(new Column("year"));

		// Wenn wir eine Rückmeldung von der Tabelle haben wollen, müssen wir eine
		// Stelle implementieren an der die "Rückmeldung" eingeht.

		final AjaxEventBehavior selectBehavior = new AjaxEventBehavior("select.dt") {
			// Sollte die stelle sein, in der der Rückruf eingeht.
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
//					target.add(feedback);
				}
				
//				for (StringValue sv : values) {
//					System.out.println("StringValue: " + sv);
//					iDlabel.setDefaultModelObject(sv);
//					Film f = dataProvider.getFilmFromStringID(sv);
//					namelabel.setDefaultModelObject(f.getmainTitle());
//				}

//				info("Selected: " + values);
				// Hier müssen - nach entsprechender Vorbereitung die Empfängerfelder die
				// geändert werden sollen eingetragen werden.
				target.add(iDlabel);
				target.add(namelabel);
			}

//			@Override
//			protected void updateAjaxAttributes(final AjaxRequestAttributes attributes) {
//				super.updateAjaxAttributes(attributes);
//				// Verstehe ich immer noch nicht
//				attributes.getDynamicExtraParameters()
//						.add("var arr=[]; dt.rows(indexes).every(function() {arr=arr.concat({\"name\":\"id\","
//								+ "\"value\": $(this.node()).attr('id')})}); return arr;");
//			}
//
//			@Override
//			public void renderHead(final Component component, final IHeaderResponse response) {
//				// do not contribute the default
//			}
		};

		/**
		 * Nach allen Vorbereitungen kann jetzt der DataTable
		 */
		final DataTables<Film, String> table = new DataTables<Film, String>("table", columns, dataProvider,
				myFilmManager.getFilms().size()) {

			@Override
			public void renderHead(IHeaderResponse response) {
				super.renderHead(response);
				Application app = getApplication();
				CoreLibrariesContributor.contributeAjax(app, response);
				CallbackParameter evt = CallbackParameter.explicit("evt");
				CallbackParameter dt = CallbackParameter.explicit("dt");
				CallbackParameter type = CallbackParameter.explicit("type");
				CallbackParameter indexes = CallbackParameter.explicit("indexes");
				String callbackFunction = selectBehavior.getCallbackFunction(evt, dt, type, indexes).toString();
				response.render(OnDomReadyHeaderItem
						.forScript(String.format("$('#%s').on('select.dt', %s)", getMarkupId(), callbackFunction)));

				// see rowSelector below
//				response.render(OnDomReadyHeaderItem.forScript($(this)
//						.on("click", "tr", new JavaScriptInlineFunction("$(this).toggleClass('selected');")).get()));
			}
		};
		add(table);

		SpanColumn<Film, String> namesColumn = new SpanColumn<Film, String>(Model.of("XXXX"), null) {
			@Override
			public int getColspan() {
				return 2;
			}
		};
		table.add(selectBehavior);

		table.addTopToolbar(new SpanHeadersToolbar<>(table));

//		CharSequence ajaxUrl = urlFor(new FilmVirtualScrollResourceReference(), null);

		ScrollerOptions scrollerOptions = new ScrollerOptions();
		scrollerOptions.loadingIndicator(true).displayBuffer(100).serverWait(500);

		SelectOptions selectOptions = new SelectOptions().style(SelectOptions.Style.OS);

		Options options = table.getOptions();
		table.add(new BootstrapTheme(options));

		options.stateDuration(3600)
		.stateSave(true)
		.pagingType(Options.PagingType.simple)
		// 
		.select(selectOptions)
		.retrieve(true)
		.rowId(new Json.RawValue("0"));

//				.select(selectOptions)
//				.serverSide(true)
//				.ordering(false)
//				.searching(true)
//				.paging(true)
//				.pagingType(Options.PagingType.full)
////				.scrollY("450") // <-- If on click will be active but paging off 
////								//	if off paging will be active but click will go unnoticed 
//				.deferRender(true)
//				.scroller(scrollerOptions)
////				.ajax(ajaxUrl)
//				.stateSave(true)
//				.info(true)
//				.processing(true)
//				.retrieve(true)
//				;
	}

}

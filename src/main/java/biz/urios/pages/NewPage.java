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

import biz.urios.helper.FilmDataTable;
import biz.urios.helper.PeopleDataProvider;
import biz.urios.helper.Person;

/**
 *
 */
public class NewPage extends WebPage {

	public NewPage(PageParameters parameters) {
		super(parameters);

		List<IColumn<Person, String>> columns = new ArrayList<>();
		columns.add(new PropertyColumn<>(Model.of("First"), "firstName", "firstName"));
		columns.add(new PropertyColumn<>(Model.of("Last"), "lastName", "lastName"));
		columns.add(new SpanPropertyColumn<Person, String>(Model.of("Age"), "age", "age") {
			@Override
			public int getRowspan() {
				return 2;
			}
		});

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		add(feedback);
		feedback.setOutputMarkupId(true);

		PeopleDataProvider dataProvider = new PeopleDataProvider(null);


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
		
		final FilmDataTable<Person, String> table = new FilmDataTable <Person, String>("table", columns, dataProvider, 30,selectBehavior);
		
		table.add(selectBehavior);
		add(table);
		
		

		SpanColumn<Person, String> namesColumn = new SpanColumn<Person, String>(Model.of("Names"), null) {
			@Override
			public int getColspan() {
				return 2;
			}
		};
		table.addTopToolbar(new SpanHeadersToolbar<>(table, namesColumn));
		table.addTopToolbar(new SpanHeadersToolbar<>(table));
//        table.addTopToolbar(new HeadersToolbar<String>(table, dataProvider));

		

        SelectOptions selectOptions = new SelectOptions()
                .style(SelectOptions.Style.OS);

        
		Options options = table.getOptions();
		table.add(new BootstrapTheme(options));
		options.order(new Sort(2, Sort.Direction.ASC)); // single column ordering
//        table.getOptions().order(new Sort(2, Sort.Direction.DESC), new Sort(0, Sort.Direction.ASC)); // multi column ordering
		options.stateDuration(3600)
		.stateSave(true)
		.pagingType(Options.PagingType.simple)
		// 
		.select(selectOptions)
		.retrieve(true)

//            .paging(false)
//            .scrollY("350px")
//            .scrollCollapse(true)

//            .scrollX(true)

				// highlights the second row
				// recommendation: load the String with PackageTextTemplate
//				.rowCallback(new Json.RawValue(
//						"function(row, data, displayIndex) {if(displayIndex == 1) {$(row).addClass('selected')}}"))

				// makes the age in bold and underlined
				// recommendation: load the String with PackageTextTemplate
//				.createdRow(new Json.RawValue(
//						"function(row, data, displayIndex) { $('td', row).eq(2).wrapInner('<b><u></u></b>');}"))

//            .lengthMenu(new Integer[]{1,2,5,10}, new String[]{"One", "Two", "Five", "Ten"})
		;
	}

}

package biz.urios;

import org.apache.wicket.csp.CSPDirective;
import org.apache.wicket.csp.CSPDirectiveSrcValue;
import org.apache.wicket.markup.html.WebPage;

import biz.urios.helper.FilmManager;
import biz.urios.pages.FilmPageAlt;

/**
 * Demo for a certain datatable problem:
 * 
 * There are 3 pages
 * 
 * <br>
 * NewPage<br>
 * FilmPage<br>
 * FilmPageAlt<br>
 * 
 * <br>
 * NewPage<br>
 * seems to be the natural way. Defining a PeopleDataProvider handing it // * to
 * the DataTable. All fine. But (there allway is a but): When the user clicks on
 * the table the AjaxEventBehavior gets activated (you see it in the console)
 * but there are no usefull information in the parameters.... it is more less
 * empty and so a little bit useless.
 * 
 * FilmPage <br>
 * That is the page that nearly works. Click is shown correctly etc. But this
 * time no pages etc.
 * 
 * FilmPageAlt <br>
 * That is a variation of FilmPage (the only difference is in line 187. With the
 * .scrollY("450") commented are the click doesn't work
 * 
 * 
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see biz.urios.Start#main(String[])
 */
public class WicketApplication extends AbstractUnifiedWicketApp {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return FilmPageAlt.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		// needed for the styling used by the quickstart
		getCspSettings().blocking().add(CSPDirective.STYLE_SRC, CSPDirectiveSrcValue.SELF)
				.add(CSPDirective.STYLE_SRC, "https://fonts.googleapis.com/css")
				.add(CSPDirective.FONT_SRC, "https://fonts.gstatic.com");

		// add your configuration here
		FilmManager theFilmManager = FilmManager.getFilmManager();
	}
}

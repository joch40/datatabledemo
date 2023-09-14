package biz.urios;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.JQueryResourceReference;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ThemeProvider;
import de.agilecoders.wicket.jquery.WicketJquerySelectors;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchTheme;
import de.agilecoders.wicket.themes.markup.html.bootswatch.BootswatchThemeProvider;
import de.agilecoders.wicket.webjars.WicketWebjars;

public abstract class AbstractUnifiedWicketApp extends WebApplication
{

	@Override
	protected void init () 
	{
		super.init();
		Application application = this;
		
        WicketWebjars.install(this);
        WicketJquerySelectors.install(this);

        getMarkupSettings().setStripWicketTags(true);
		
        final IBootstrapSettings settings = new BootstrapSettings();
        final ThemeProvider themeProvider = new BootswatchThemeProvider(BootswatchTheme.Lumen);

        settings.setThemeProvider(themeProvider);
        settings.setDeferJavascript(true);

        application.getJavaScriptLibrarySettings().setJQueryReference(JQueryResourceReference.INSTANCE_3);
        Bootstrap.install(application, settings);

        infiniteScroll();
	    configureResourceGuard();

        getCspSettings().blocking().disabled();
		// getCspSettings().blocking().clear();
        
        mountPages();
	}
	
	private void mountPages() {
        // mountPage("new", NewPage.class);
		
	}

	/**
	 * Verstehe ich im Moment auch noch nicht daher auch aus. 
	 */
	private void configureResourceGuard() {
		SecurePackageResourceGuard packageResourceGuard = (SecurePackageResourceGuard) getResourceSettings().getPackageResourceGuard();
		packageResourceGuard.addPattern("+*.css.map");
	}

	/**
	 * Verstehe ich noch nicht daher aus
	 */
	private void infiniteScroll() {
//		mountPage("infiniteScroll", InfiniteScrollDemoPage.class);
//		mountResource("infiniteScrollData", new VirtualScrollDemoResourceReference());
	}

}

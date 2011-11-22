package org.flacro.module;

import org.flacro.RestletServlet;

public class ServletModule extends com.google.inject.servlet.ServletModule {
		
	@Override
	protected void configureServlets() {
		serve("/zhishidian/*").with(RestletServlet.class);
	}
	

}

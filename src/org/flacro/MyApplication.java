package org.flacro;

import org.flacro.resources.NodeResource;
import org.flacro.resources.NodesResource;
import org.flacro.resources.UserResource;
import org.flacro.resources.UsernodeResource;
import org.flacro.resources.UsernodesResource;
import org.flacro.resources.UsersResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.ext.freemarker.ContextTemplateLoader;
import org.restlet.routing.Router;

import com.google.inject.Injector;

import freemarker.template.Configuration;

public class MyApplication extends Application {
	private Injector injector;
	/** The Freemarker's configuration. */
    private Configuration configuration;
    
    MyApplication(Injector injector){
    	super();
    	this.injector = injector;
    }
    
    @Override
    public Restlet createInboundRoot() {
        // initialize the Freemarker's configuration
    	// Freemarker暂时无法使用，搁置
        configuration = new Configuration();        
        configuration.setTemplateLoader(new ContextTemplateLoader(getContext(),
                "/templates/"));

        Router router = new GuiceRouter(injector, getContext()) {
			@Override
			protected void attachRoutes() {
				attach("/users",UsersResource.class);
				attach("/users/{userid}",UserResource.class);
				attach("/users/{userid}/usernodes",UsernodesResource.class);
				attach("/users/{userid}/usernodes/{usernodeid}",UsernodeResource.class);
				attach("/users/{userid}/nodes",NodesResource.class);
				attach("/users/{userid}/nodes/{nodeid}",NodeResource.class);
			}
		};        
        return router;
    }

	public Configuration getConfiguration() {
		return configuration;
	}
    
    
}

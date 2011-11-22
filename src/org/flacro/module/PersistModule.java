package org.flacro.module;

import javax.inject.Singleton;

import org.flacro.Log4JTypeListener;
import org.flacro.po.DefaultGraphDB;
import org.flacro.po.GraphDB;
import org.flacro.service.NodeService;
import org.flacro.service.NodeServiceImpl;
import org.flacro.service.UsernodeService;
import org.flacro.service.UsernodeServiceImpl;
import org.flacro.service.UserService;
import org.flacro.service.UserServiceImpl;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class PersistModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GraphDB.class).to(DefaultGraphDB.class).in(Singleton.class);
		bind(UserService.class).to(UserServiceImpl.class);
		bind(UsernodeService.class).to(UsernodeServiceImpl.class);
		bind(NodeService.class).to(NodeServiceImpl.class);
		bindListener(Matchers.any(), new Log4JTypeListener());
	}
}

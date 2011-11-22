package org.flacro.po;

import java.util.Map;
import java.util.ResourceBundle;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class DefaultGraphDB implements GraphDB {
	private GraphDatabaseService graphDB = null;;

	DefaultGraphDB() {
		ResourceBundle rb = ResourceBundle
				.getBundle("org.flacro.service.ConsumerResource");
		String DB_PATH = rb.getString("db_path");
		String DB_Property = rb.getString("db_property");
		Map<String, String> config = EmbeddedGraphDatabase
				.loadConfigurations(DB_Property);
		graphDB = new EmbeddedGraphDatabase(DB_PATH, config);
		registerShutdownHook(graphDB);
	}

	@Override
	public GraphDatabaseService getGraphDB() {
		return graphDB;
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDB) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running example before it's completed)
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				graphDB.shutdown();
			}
		});
	}

}

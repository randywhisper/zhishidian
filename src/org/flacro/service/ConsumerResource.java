package org.flacro.service;

import java.util.ListResourceBundle;

public class ConsumerResource extends ListResourceBundle {

	static final Object[][] contents = {
			{"db_property","E:\\graphdb\\conf\\neo4j.properties"},
			{"db_path","E:\\graphdb\\data\\graph.db"},
			{"db_property1","E:\\neo4j-community-1.4.2\\conf\\neo4j.properties"},
			{"db_path1","E:\\neo4j-community-1.4.2\\data\\graph.db"}
	};

	@Override
	protected Object[][] getContents() {
		return contents;
	}

}

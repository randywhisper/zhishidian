package org.flacro.service;

import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.Node;

public interface NodeService {
	long createNode(long parentid, Map<String, String> property);
	Node getNode(long nodeid);
	int updateNode(long nodeid, Map<String, String> property);
	int deleteNode(long nodeid);
	
	List<Node> getNodes(long root);
}

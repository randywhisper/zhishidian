package org.flacro.service;

import java.util.List;
import java.util.Map;

import org.flacro.po.GraphDB;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;

import com.google.inject.Inject;

public class NodeServiceImpl implements NodeService {

	public static enum HasRelationshipType implements RelationshipType {
		Has
	}

	@Inject
	private GraphDB defaultgraphDB;

	@Override
	public long createNode(long parentid, Map<String, String> property) {
		GraphDatabaseService graphDB = defaultgraphDB.getGraphDB();
		Transaction tx = graphDB.beginTx();
		try {
			Node node = graphDB.createNode();
			for (String s : property.keySet()) {
				node.setProperty(s, property.get(s));
			}
			if (parentid >= 0) {
				Node parent = graphDB.getNodeById(parentid);
				RelationshipType rt = HasRelationshipType.Has;
				parent.createRelationshipTo(node, rt);
			}
			tx.success();
			return node.getId();
		} finally {
			tx.finish();
		}
	}

	@Override
	public Node getNode(long nodeid) {
		GraphDatabaseService graphDB = defaultgraphDB.getGraphDB();
		Transaction tx = graphDB.beginTx();
		try {
			Node node = graphDB.getNodeById(nodeid);
			tx.success();
			return node;
			
		} finally {
			tx.finish();
		}
	}

	@Override
	public int updateNode(long nodeid, Map<String, String> property) {
		GraphDatabaseService graphDB = defaultgraphDB.getGraphDB();
		Transaction tx = graphDB.beginTx();
		try {
			Node node = graphDB.getNodeById(nodeid);
			for (String s : property.keySet()) {
				node.setProperty(s, property.get(s));
			}			
			tx.success();
			return 1;
		} finally {
			tx.finish();
		}
	}

	@Override
	public int deleteNode(long nodeid) {
		GraphDatabaseService graphDB = defaultgraphDB.getGraphDB();
		Transaction tx = graphDB.beginTx();
		try {
			Node node = graphDB.getNodeById(nodeid);			
			Traverser t = node.traverse(Order.BREADTH_FIRST, StopEvaluator.END_OF_GRAPH, ReturnableEvaluator.ALL, HasRelationshipType.Has, Direction.OUTGOING);
			for(Node n : t){
				System.out.println("node:"+n.getId());
				Iterable<Relationship> ir = n.getRelationships();
				for(Relationship r : ir){
					r.delete();
				}
				n.delete();
			}
			tx.success();
			return 1;
		} finally {
			tx.finish();
		}
	}

	@Override
	public List<Node> getNodes(long root) {
		GraphDatabaseService graphDB = defaultgraphDB.getGraphDB();
		Transaction tx = graphDB.beginTx();
		try{
			return null;
		}finally{
			tx.finish();
		}
	}
	
	public int deleteTree(long rootid){
		GraphDatabaseService graphDB = defaultgraphDB.getGraphDB();
		Transaction tx = graphDB.beginTx();
		try{
			return 0;
		}finally{
			tx.finish();
		}
	}
	
}

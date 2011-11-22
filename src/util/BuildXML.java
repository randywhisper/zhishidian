package util;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BuildXML {
	public static Element buildObjectXML(Object object, Document doc) {
		try {
			if (object != null) {
				Class c = object.getClass();
				// Field[] field = c.getDeclaredFields();
				Method[] methods = c.getDeclaredMethods();

				Element root = doc.createElement(c.getSimpleName()
						.toLowerCase());
				for (Method m : methods) {
					if (m.getName().startsWith("get")) {
						String name = m.getName().substring(3).toLowerCase();
						root.setAttribute(name, "" + m.invoke(object, null));
					}
				}
				return root;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static Element buildNodeXML(Node node, Document doc) {
		try {
			if (node != null) {
				String base = "" + node.getId();
				Element root = doc.createElement("node");				
				root.setAttribute("id", base);	
				for(String key : node.getPropertyKeys()){
					root.setAttribute(key, "" + node.getProperty(key));
				}
				return root;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static Element traverseNodeXML(Node root, Document doc) {
		try {
			Node node = root;
			Element e = buildNodeXML(node, doc);
			if(node != null){
				Iterable<Relationship> ir = node.getRelationships(Direction.OUTGOING);
				//在这里，迭代是无序的，所以不需要区分顺序，只用一个Iterable来解决
				for(Relationship rs : ir){
					e.appendChild(traverseNodeXML(rs.getEndNode(), doc));
				}
			}
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static List<Node> getRelatedNode(Node node){
		List<Node> nlist = new ArrayList<Node>();
		if(node != null){
			Iterable<Relationship> ir = node.getRelationships(Direction.OUTGOING);
			for(Relationship rs : ir){
				nlist.add(rs.getEndNode());
			}
		}
		return nlist; 
	}
}

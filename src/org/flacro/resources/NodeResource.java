package org.flacro.resources;

import java.util.Map;

import org.flacro.service.NodeService;
import org.neo4j.graphdb.Node;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.Inject;

import util.BuildXML;

public class NodeResource extends ServerResource {
	@Inject
	private NodeService nodeservice;
	
	@Get
	public Representation get() {
		try {
			Map<String, Object> map = getRequest().getAttributes();
			long nodeid = Long.parseLong((String) map.get("nodeid"));
			Node node = nodeservice.getNode(nodeid);
			// 生成XML表示
			DomRepresentation r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			//Element root = doc.createElement("nodes");
			Element root = BuildXML.buildNodeXML(node, doc);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Post
	public Representation create(Representation entity) {
		DomRepresentation r = null;
		try {
			//node information
			Map<String, Object> map = getRequest().getAttributes();
			Form form =  new Form(entity);			
			long nodeid = Long.parseLong((String)map.get("nodeid"));
			Map<String, String> property = form.getValuesMap();
			nodeservice.updateNode(nodeid, property);			
			Node node = nodeservice.getNode(nodeid);
			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = BuildXML.buildNodeXML(node, doc);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

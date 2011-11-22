package org.flacro.resources;

import java.util.Map;

import org.flacro.po.Usernode;
import org.flacro.service.NodeService;
import org.flacro.service.UsernodeService;
import org.neo4j.graphdb.Node;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.BuildXML;

import com.google.inject.Inject;

public class NodesResource extends ServerResource {

	@Inject
	private UsernodeService usernodeservice;
	@Inject
	private NodeService nodeservice;
	
	@Post
	public Representation create(Representation entity) {
		DomRepresentation r = null;
		try {
			//node information
			Map<String, Object> map = getRequest().getAttributes();
			Form form =  new Form(entity);			
			long parentid = Long.parseLong(form.getFirstValue("parentid"));
			Map<String, String> property = form.getValuesMap();
			property.remove("parentid");
			property.remove("description");
			property.remove("pub");
			property.remove("title");
			long nodeid = nodeservice.createNode(parentid, property);
			Usernode un = new Usernode();
			if(parentid<0){
				//usernode information
				String description = form.getFirstValue("description");
				int userid = Integer.parseInt((String)map.get("userid"));
				String p = form.getFirstValue("pub");
				Boolean pub = p.equals("1")?true:false;
				String title = form.getFirstValue("title");
				un.setDescription(description);
				un.setPub(pub);
				un.setTitle(title);
				un.setRoot(nodeid);
				un.setUserid(userid);
				usernodeservice.createUsernode(un);
			}
			Node node = nodeservice.getNode(nodeid);
			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = doc.createElement("nodetree");
			Element unmeta = BuildXML.buildObjectXML(un, doc);
			Element nodetree = BuildXML.traverseNodeXML(node, doc);
			root.appendChild(unmeta);
			root.appendChild(nodetree);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

package org.flacro.resources;

import java.util.Map;

import org.apache.log4j.Logger;
import org.flacro.InjectLogger;
import org.flacro.po.User;
import org.flacro.po.Usernode;
import org.flacro.service.NodeService;
import org.flacro.service.UsernodeService;
import org.neo4j.graphdb.Node;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.BuildXML;

import com.google.inject.Inject;

public class UsernodeResource extends ServerResource {
	@InjectLogger
	private Logger log;
	@Inject
	private NodeService nodeservice;
	@Inject
	private UsernodeService usernodeservice;

	@Get
	public Representation get() {
		log.debug("traverse user node");
		DomRepresentation r = null;
		try {
			// 获取树的元信息
			Map<String, Object> map = getRequest().getAttributes();
			int usernodeid = Integer.parseInt((String) map.get("usernodeid"));
			Usernode un = usernodeservice.getUsernode(usernodeid);
			long rootnode = un.getRoot();
			Node node = nodeservice.getNode(rootnode);
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
			log.error("traverse user node failed");
			return null;
		}
	}

	@Post
	public Representation create(Representation entity) {
		DomRepresentation r = null;
		try {
			Map<String, Object> map = getRequest().getAttributes();
			int usernodeid = Integer.parseInt((String) map.get("usernodeid"));
			Form form = new Form(entity);
			String description = form.getFirstValue("description");
			String title = form.getFirstValue("title");
			String pub = form.getFirstValue("pub");
			boolean p = pub.equals("1") ? true : false;
			Usernode un = usernodeservice.getUsernode(usernodeid);
			un.setDescription(description);
			un.setTitle(title);
			un.setPub(p);
			usernodeservice.updateUsernode(un);
			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = BuildXML.buildObjectXML(un, doc);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Delete
	public Representation delete(Representation entity) {
		DomRepresentation r = null;
		try {
			Map<String, Object> map = getRequest().getAttributes();
			int usernodeid = Integer.parseInt((String)map.get("usernodeid"));
			int result = usernodeservice.deleteUsernode(usernodeid);
			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = doc.createElement("usernode");
			root.setAttribute("delete", "" + result);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

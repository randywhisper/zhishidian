package org.flacro.resources;

import java.util.List;
import java.util.Map;

import org.flacro.po.User;
import org.flacro.po.Usernode;
import org.flacro.service.UsernodeService;
import org.flacro.service.UserService;
import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.BuildXML;

import com.google.inject.Inject;

public class UsernodesResource extends ServerResource {
	@Inject
	private UsernodeService usernodeservice;

	@Get
	public Representation get() {
		try {
			Map map = getRequest().getAttributes();
			int id = Integer.parseInt((String) map.get("userid"));
			List<Usernode> nlist = usernodeservice.getUsernodes(id);
			// 生成XML表示
			DomRepresentation r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = doc.createElement("nodes");
			root.setAttribute("userid", "" + id);
			for(Usernode un : nlist){
				root.appendChild(BuildXML.buildObjectXML(un, doc));
			}
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

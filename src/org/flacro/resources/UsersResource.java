package org.flacro.resources;

import org.apache.log4j.Logger;
import org.flacro.InjectLogger;
import org.flacro.po.User;
import org.flacro.service.UserService;
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

public class UsersResource extends ServerResource {
	@InjectLogger
	private Logger log;
	@Inject
	private UserService userservice;

	@Post
	public Representation create(Representation entity) {
		DomRepresentation r = null;
		log.debug("create user");
		try {			
			// create User
			Form form = new Form(entity);
			String nickname = form.getFirstValue("nickname");
			User u = new User();
			u.setNickname(nickname);			
			userservice.createUser(u);

			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = BuildXML.buildObjectXML(u, doc);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			log.error("create user failed");
			return null;
		}
	}

}
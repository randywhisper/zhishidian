package org.flacro.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.flacro.InjectLogger;
import org.flacro.po.User;
import org.flacro.po.Usernode;
import org.flacro.service.UserService;
import org.neo4j.graphdb.Node;
import org.restlet.data.Encoding;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RepresentationContext;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.inject.Inject;

import util.BuildXML;

public class UserResource extends ServerResource {
	@InjectLogger
	private Logger log;
	@Inject
	private UserService userservice;
	
	@Get
	public Representation get() {
		log.debug("get user data");
		try {
			Map<String, Object> map = getRequest().getAttributes();
			int id = Integer.parseInt((String)map.get("userid"));
			User u = userservice.getUser(id);

			// 生成XML表示
			DomRepresentation r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = BuildXML.buildObjectXML(u, doc);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			log.error("get user data failed");
			return null;
		}
	}
	
	@Post
	public Representation create(Representation entity) {
		DomRepresentation r = null;
		try {
			Map<String, Object> map = getRequest().getAttributes();
			int userid = Integer.parseInt((String)map.get("userid"));
			Form form =  new Form(entity);			
			String nickname = form.getFirstValue("nickname");
			User u = userservice.getUser(userid);
			u.setNickname(nickname);
			userservice.updateUser(u);
			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = BuildXML.buildObjectXML(u, doc);
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
			int userid = Integer.parseInt((String)map.get("userid"));
			int result = userservice.deleteUser(userid);
			// 生成XML表示
			r = new DomRepresentation(MediaType.TEXT_XML);
			Document doc = r.getDocument();
			Element root = doc.createElement("user");
			root.setAttribute("delete", "" + result);
			doc.appendChild(root);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

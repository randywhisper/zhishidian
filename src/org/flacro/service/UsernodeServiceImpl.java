package org.flacro.service;

import java.util.ArrayList;
import java.util.List;

import org.flacro.po.GraphDB;
import org.flacro.po.Usernode;
import org.flacro.po.UsernodeExample.Criteria;
import org.flacro.po.UsernodeExample;
import org.flacro.po.UsernodeMapper;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.google.inject.Inject;

public class UsernodeServiceImpl implements UsernodeService {
	@Inject
	private UsernodeMapper usernodemapper;

	@Override
	public int createUsernode(Usernode un) {
		try {
			return usernodemapper.insert(un);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public Usernode getUsernode(int usernodeid) {
		try {
			return usernodemapper.selectByPrimaryKey(usernodeid);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<Usernode> getUsernodes(int userid) {
		try {

			UsernodeExample ne = new UsernodeExample();
			Criteria c = ne.createCriteria();
			c.andUseridEqualTo(userid);
			List<Usernode> nlist = usernodemapper.selectByExample(ne);
			return nlist;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Usernode>();
		}
	}

	@Override
	public int updateUsernode(Usernode un) {
		try {
			return usernodemapper.updateByPrimaryKey(un);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int deleteUsernode(int usernodeid) {
		try {
			//return usernodemapper.deleteByPrimaryKey(usernodeid);
			Usernode un = getUsernode(usernodeid);
			un.setUserid(null);
			return usernodemapper.updateByPrimaryKey(un);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	
	
}

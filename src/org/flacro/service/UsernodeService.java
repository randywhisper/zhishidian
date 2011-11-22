package org.flacro.service;

import java.util.List;

import org.flacro.po.Usernode;

public interface UsernodeService {
	int createUsernode(Usernode un);
	Usernode getUsernode(int usernodeid);
	List<Usernode> getUsernodes(int userid);
	int updateUsernode(Usernode un);
	int deleteUsernode(int usernodeid);
}

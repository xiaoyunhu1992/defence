package com.buaa.hxy.pojo.AttackGraphNodeType;
import java.util.ArrayList;



public class StateNode extends Node{
	
	ArrayList preActionNode = new ArrayList();
	public ArrayList getPreActionNode() {
		return preActionNode;
	}
	public void setPreActionNode(ArrayList preActionNode) {
		this.preActionNode = preActionNode;
	}
	public ArrayList getPostActionNode() {
		return postActionNode;
	}
	public void setPostActionNode(ArrayList postActionNode) {
		this.postActionNode = postActionNode;
	}
	ArrayList postActionNode= new ArrayList();
}

package com.buaa.hxy.pojo.AttackGraphNodeType;
import java.util.ArrayList;



public class ActionNode extends Node{
	
	ArrayList<StateNode> preStateNode = new ArrayList<StateNode>();
	ArrayList<StateNode> postStateNode = new ArrayList<StateNode>();
	public ArrayList<StateNode> getPreStateNode() {
		return preStateNode;
	}
	public void setPreStateNode(ArrayList<StateNode> preStateNode) {
		this.preStateNode = preStateNode;
	}
	public ArrayList<StateNode> getPostStateNode() {
		return postStateNode;
	}
	public void setPostStateNode(ArrayList<StateNode> postStateNode) {
		this.postStateNode = postStateNode;
	}
}

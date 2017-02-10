package com.buaa.hxy.pojo.AttackGraphNodeType;
import java.util.ArrayList;



public class InitStateNode extends Node{
ArrayList<ActionNode> postActionNode= new ArrayList<ActionNode>();

public ArrayList<ActionNode> getPostActionNode() {
	return postActionNode;
}

public void setPostActionNode(ArrayList<ActionNode> postActionNode) {
	this.postActionNode = postActionNode;
}
}

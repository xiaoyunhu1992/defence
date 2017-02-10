package com.buaa.hxy.pojo.AttackGraphNodeType;

public class Node {
	
	int accurance = 1;
	public int getAccurance() {
		return accurance;
	}
	public void setAccurance(int accurance) {
		this.accurance = accurance;
	}

	int nodeNum = 0;
	String NodeName;
	
	public String getNodeName() {
		return NodeName;
	}
	public void setNodeName(String NodeName) {
		this.NodeName = NodeName;
	}
	
	public void setnodeNum(int i){
		this.nodeNum = i;
	}
	
	public int getnodeNum(){
		return this.nodeNum;
	}

}

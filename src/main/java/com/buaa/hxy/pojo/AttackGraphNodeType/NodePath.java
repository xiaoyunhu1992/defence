package com.buaa.hxy.pojo.AttackGraphNodeType;

public class NodePath {
	//�ӵ�һ��Node ָ�� �ڶ���Node
	int FirstNode = 0;
	int SecondNode = 0;
	
	public void setConnection(Node PreNode, Node PostNode){
		this.FirstNode = PreNode.getnodeNum();
		this.SecondNode = PostNode.getnodeNum();
		
	}

}

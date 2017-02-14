package com.buaa.hxy.pojo.subNodeType;

import com.buaa.hxy.pojo.AttackGraphNodeType.StateNode;
import com.buaa.hxy.pojo.FClass.Computer;


public class Connection extends StateNode{
	Computer source;
	Computer destiny;
	String protocal;
	String prot;
	public String getprotocal() {
		return protocal;
	}
	public void setProtcal(String protocal) {
		this.protocal = protocal;
	}
	public String getProt() {
		return prot;
	}
	public void setProt(String prot) {
		this.prot = prot;
	}
	public Computer getSource() {
		return source;
	}
	public void setSource(Computer source) {
		this.source = source;
	}
	public Computer getDestiny() {
		return destiny;
	}
	public void setDestiny(Computer destiny) {
		this.destiny = destiny;
	}


}

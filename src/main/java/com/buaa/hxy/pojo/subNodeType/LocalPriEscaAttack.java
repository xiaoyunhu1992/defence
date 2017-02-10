package com.buaa.hxy.pojo.subNodeType;

import com.buaa.hxy.pojo.AttackGraphNodeType.ActionNode;
import com.buaa.hxy.pojo.FClass.Attacker;
import com.buaa.hxy.pojo.FClass.Computer;

public class LocalPriEscaAttack extends ActionNode{

	Attacker attacker = new Attacker();
	Computer destiny = new Computer();

	String vulID;
	int zdayflag = 0 ;
	
	public int getzdayflag() {
		return zdayflag;
	}
	public void setzdayflag(int zdayflag) {
		this.zdayflag = zdayflag;
	}
	
	public Attacker getAttacker() {
		return attacker;
	}
	public void setAttacker(Attacker attacker) {
		this.attacker = attacker;
	}
	public Computer getDestiny() {
		return destiny;
	}
	public void setDestiny(Computer destiny) {
		this.destiny = destiny;
	}
	public String getVulID() {
		return vulID;
	}
	public void setVulID(String vulID) {
		this.vulID = vulID;
	}
	
}

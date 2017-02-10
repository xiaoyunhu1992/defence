package com.buaa.hxy.pojo.subNodeType;

import com.buaa.hxy.pojo.AttackGraphNodeType.StateNode;
import com.buaa.hxy.pojo.FClass.Attacker;
import com.buaa.hxy.pojo.FClass.Computer;

public class HostPrivilege extends StateNode{

	Attacker attacker = new Attacker();
	Computer destiny = new Computer();
    int priviledge;
    int zdayTimes = 0;
    
    public int getZdayTimes(){
    	return zdayTimes;
    }
    public void setZdayTimes(int num){
    	this.zdayTimes = num;
    }
    
	public Attacker getAttacker() {
		return attacker;
	}
	public void setAttacker(Attacker source) {
		this.attacker = source;
	}
	public Computer getDestiny() {
		return destiny;
	}
	public void setDestiny(Computer destiny) {
		this.destiny = destiny;
	}
	public int getPriviledge() {
		return priviledge;
	}
	public void setPriviledge(int priviledge) {
		this.priviledge = priviledge;
	}	
}

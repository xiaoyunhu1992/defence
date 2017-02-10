package com.buaa.hxy.pojo.FClass;
import java.util.ArrayList;


public class Attacker {
	Computer attackerComputer = new Computer();
	int privilege;
	public Computer getAttackerComputer() {
		return attackerComputer;
	}
	public void setAttackerComputer(Computer attackerComputer) {
		this.attackerComputer = attackerComputer;
	}
	public int getPrivilege() {
		return privilege;
	}
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}


}

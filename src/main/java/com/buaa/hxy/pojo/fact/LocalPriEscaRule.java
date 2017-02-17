package com.buaa.hxy.pojo.fact;

import java.util.ArrayList;

import com.buaa.hxy.pojo.subNodeType.Connection;
import com.buaa.hxy.pojo.subNodeType.HostPrivilege;
import com.buaa.hxy.pojo.subNodeType.HostService;
import com.buaa.hxy.pojo.subNodeType.HostVulnerability;
import com.buaa.hxy.pojo.subNodeType.LocalPriEscaAttack;


public class LocalPriEscaRule extends AttackRule{

	HostPrivilege hp = new HostPrivilege();
	HostPrivilege thp = new HostPrivilege();
	public HostPrivilege getThp() {
		return thp;
	}

	public void setThp(HostPrivilege thp) {
		this.thp = thp;
	}

	HostVulnerability hv = new HostVulnerability();
	HostService hs = new HostService();

	LocalPriEscaAttack pea = new LocalPriEscaAttack();

	public HostPrivilege getHp() {
		return hp;
	}

	public void setHp(HostPrivilege hp) {
		this.hp = hp;
	}
	

	public HostVulnerability getHv() {
		return hv;
	}

	public void setHv(HostVulnerability hv) {
		this.hv = hv;
	}

	public HostService getHs() {
		return hs;
	}

	public void setHs(HostService hs) {
		this.hs = hs;
	}

	public LocalPriEscaAttack getPea() {
		return pea;
	}

	public void setPea(LocalPriEscaAttack pea) {
		this.pea = pea;
	}
	
	public ArrayList<LocalPriEscaRule> initLPER(){
		ArrayList <LocalPriEscaRule> lper = new ArrayList <LocalPriEscaRule>();
		
		//CVE_2003_0542
		
		LocalPriEscaRule cve_2003_0542 = new LocalPriEscaRule();
		cve_2003_0542.getHs().getService().setServeceName("Apache");
		cve_2003_0542.getHs().getService().setPort("8080");
		cve_2003_0542.getHs().getService().setProtocal("tcp");
		cve_2003_0542.getHs().getService().setPrivilege(0);
		cve_2003_0542.getHs().getService().setVersion("1.3.29");
		
		
		cve_2003_0542.getHv().setVulID("CVE_2003_0542");
		cve_2003_0542.getHv().setServiceName("Apache");
				
		cve_2003_0542.getHp().setPriviledge(1);
		
		cve_2003_0542.getPea().setVulID("CVE_2003_0542");
		
		cve_2003_0542.getThp().setPriviledge(2);
		
		lper.add(cve_2003_0542);
		return lper;
	}
}

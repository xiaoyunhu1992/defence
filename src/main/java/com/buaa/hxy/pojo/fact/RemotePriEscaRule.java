package com.buaa.hxy.pojo.fact;

import java.util.ArrayList;
import java.util.Iterator;

import com.buaa.hxy.pojo.FClass.Computer;

import com.buaa.hxy.pojo.subNodeType.Connection;
import com.buaa.hxy.pojo.subNodeType.HostPrivilege;
import com.buaa.hxy.pojo.subNodeType.HostService;
import com.buaa.hxy.pojo.subNodeType.HostVulnerability;
import com.buaa.hxy.pojo.subNodeType.RemotePriEscaAttack;

import com.buaa.hxy.pojo.fact.InitialFact;

public class RemotePriEscaRule extends AttackRule{

	HostPrivilege hp = new HostPrivilege();
	HostVulnerability hv = new HostVulnerability();
	HostService hs = new HostService();
	Connection co = new Connection();
	
	RemotePriEscaAttack pea = new RemotePriEscaAttack();
	
	HostPrivilege thp = new HostPrivilege();
	

	
//	public ArrayList<RemotePriEscaRule> initRPER(){
//		//CVE_2010_2730
//		/*** 
//		 Webserver
//		**/
//		RemotePriEscaRule cve_2010_2730 = new RemotePriEscaRule();
//		cve_2010_2730.getHs().getService().setServeceName("iis");
//		cve_2010_2730.getHs().getService().setPort("80");
//		cve_2010_2730.getHs().getService().setProtocal("tcp");
//		cve_2010_2730.getHs().getService().setPrivilege(0);
//		cve_2010_2730.getHs().getService().setVersion("7.5");
//		
//		
//		cve_2010_2730.getHv().setVulID("CVE_2010_2730");
//		cve_2010_2730.getHv().setServiceName("iis");
//		
//		cve_2010_2730.getCo().setProt("80");
//		cve_2010_2730.getCo().setProtcal("tcp");
//		
//		cve_2010_2730.getHp().setPriviledge(2);
//		
//		cve_2010_2730.getPea().setVulID("CVE_2010_2730");
//		
//		cve_2010_2730.getThp().setPriviledge(1);
//		
//		
//		//CVE_2008_6534
//		/*** 
//		 FTP
//
//		**/
//		RemotePriEscaRule cve_2008_6534 = new RemotePriEscaRule();
//		cve_2008_6534.getHs().getService().setServeceName("NULL FTP Server Pro");
//		cve_2008_6534.getHs().getService().setPort("21");
//		cve_2008_6534.getHs().getService().setProtocal("tcp");
//		cve_2008_6534.getHs().getService().setPrivilege(0);
//		cve_2008_6534.getHs().getService().setVersion("1.1.0.7");
//		
//		
//		cve_2008_6534.getHv().setVulID("CVE_2008_6534");
//		cve_2008_6534.getHv().setServiceName("NULL FTP Server Pro");
//		
//		cve_2008_6534.getCo().setProt("21");
//		cve_2008_6534.getCo().setProtcal("tcp");
//		
//		cve_2008_6534.getHp().setPriviledge(2);
//		
//		cve_2008_6534.getPea().setVulID("CVE_2008_6534");
//		
//		cve_2008_6534.getThp().setPriviledge(2);
//		
//		//CVE_2014_0133
//		/*** 
//		SMTP
//
//
//		**/
//		RemotePriEscaRule cve_2014_0133 = new RemotePriEscaRule();
//		cve_2014_0133.getHs().getService().setServeceName("Nginx");
//		cve_2014_0133.getHs().getService().setPort("25");
//		cve_2014_0133.getHs().getService().setProtocal("tcp");
//		cve_2014_0133.getHs().getService().setPrivilege(0);
//		cve_2014_0133.getHs().getService().setVersion("1.5.10");
//		
//		
//		cve_2014_0133.getHv().setVulID("CVE_2014_0133");
//		cve_2014_0133.getHv().setServiceName("Nginx");
//		
//		cve_2014_0133.getCo().setProt("25");
//		cve_2014_0133.getCo().setProtcal("tcp");
//		
//		cve_2014_0133.getHp().setPriviledge(2);
//		
//		cve_2014_0133.getPea().setVulID("CVE_2014_0133");
//		
//		cve_2014_0133.getThp().setPriviledge(2);
//		
//		//CVE_2014_2408
//		/*** 
//		Database
//		**/
//		RemotePriEscaRule cve_2014_2408 = new RemotePriEscaRule();
//		cve_2014_2408.getHs().getService().setServeceName("Oracle Database Server");
//		cve_2014_2408.getHs().getService().setPort("1433");
//		cve_2014_2408.getHs().getService().setProtocal("tcp");
//		cve_2014_2408.getHs().getService().setPrivilege(2);
//		cve_2014_2408.getHs().getService().setVersion("12.1.0.1");
//		
//		
//		cve_2014_2408.getHv().setVulID("CVE_2014_2408");
//		cve_2014_2408.getHv().setServiceName("Oracle Database Server");
//		
//		cve_2014_2408.getCo().setProt("1433");
//		cve_2014_2408.getCo().setProtcal("tcp");
//		
//		cve_2014_2408.getHp().setPriviledge(2);
//		
//		cve_2014_2408.getPea().setVulID("CVE_2014_2408");
//		
//		cve_2014_2408.getThp().setPriviledge(2);
//		
//		
//
//		
//		//CVE_2014_2928
//		/*** 
//		User1/User2
//		**/
//		RemotePriEscaRule cve_2014_2928 = new RemotePriEscaRule();
//		cve_2014_2928.getHs().getService().setServeceName("F5 BIG-IP LTM");
//		cve_2014_2928.getHs().getService().setPort("5190");
//		cve_2014_2928.getHs().getService().setProtocal("tcp");
//		cve_2014_2928.getHs().getService().setPrivilege(0);
//		cve_2014_2928.getHs().getService().setVersion("11.0.0");
//		
//		
//		cve_2014_2928.getHv().setVulID("CVE_2014_2928");
//		cve_2014_2928.getHv().setServiceName("F5 BIG-IP LTM");
//		
//		cve_2014_2928.getCo().setProt("5190");
//		cve_2014_2928.getCo().setProtcal("tcp");
//		
//		cve_2014_2928.getHp().setPriviledge(2);
//		
//		cve_2014_2928.getPea().setVulID("CVE_2014_2928");
//		
//		cve_2014_2928.getThp().setPriviledge(2);
//		
//		
//		
//		
//		//CVE_2007_3039
//		/***
//		 * DNSserver
//		 */
//		RemotePriEscaRule cve_2007_3039 = new RemotePriEscaRule();
//		cve_2007_3039.getHs().getService().setServeceName("Microsoft Server");
//		cve_2007_3039.getHs().getService().setPort("21");
//		cve_2007_3039.getHs().getService().setProtocal("tcp");
//		cve_2007_3039.getHs().getService().setPrivilege(0);
//		cve_2007_3039.getHs().getService().setVersion("2000");
//		
//		
//		cve_2007_3039.getHv().setVulID("CVE_2007_3039");
//		cve_2007_3039.getHv().setServiceName("Microsoft Server");
//		
//		cve_2007_3039.getCo().setProt("21");
//		cve_2007_3039.getCo().setProtcal("tcp");
//		
//		cve_2007_3039.getHp().setPriviledge(2);
//		
//		cve_2007_3039.getPea().setVulID("CVE_2007_3039");
//		
//		cve_2007_3039.getThp().setPriviledge(2);
//		
//		//CVE_2009_3796
//		/**
//		 * User3
//		 */
//		RemotePriEscaRule cve_2009_3796 = new RemotePriEscaRule();
//		cve_2009_3796.getHs().getService().setServeceName("Adobe Flash Player");
//		cve_2009_3796.getHs().getService().setPort("5190");
//		cve_2009_3796.getHs().getService().setProtocal("tcp");
//		cve_2009_3796.getHs().getService().setPrivilege(0);
//		cve_2009_3796.getHs().getService().setVersion("10.0.0");
//		
//		
//		cve_2009_3796.getHv().setVulID("CVE_2009_3796");
//		cve_2009_3796.getHv().setServiceName("Adobe Flash Player");
//		
//		cve_2009_3796.getCo().setProt("5190");
//		cve_2009_3796.getCo().setProtcal("tcp");
//		
//		cve_2009_3796.getHp().setPriviledge(2);
//		
//		cve_2009_3796.getPea().setVulID("CVE_2009_3796");
//		
//		cve_2009_3796.getThp().setPriviledge(2);
//		/**
//		 * add to list
//		 */
//		ArrayList<RemotePriEscaRule> rper = new ArrayList<RemotePriEscaRule>();
//		rper.add(cve_2010_2730);
//		rper.add(cve_2008_6534);
//		rper.add(cve_2014_0133);
//		rper.add(cve_2014_2408);
//		rper.add(cve_2014_2928);
//		rper.add(cve_2007_3039);
//		rper.add(cve_2009_3796);
//		rper.add(cve_2014_7226);

		

//		ArrayList<RemotePriEscaRule> rper0day = new ArrayList<RemotePriEscaRule>();
//		rper0day = initRPER0day();
//		
//		rper.addAll(rper0day);
//		
		
//		return rper;
//		
//	}
	
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

	public Connection getCo() {
		return co;
	}

	public void setCo(Connection co) {
		this.co = co;
	}

	public RemotePriEscaAttack getPea() {
		return pea;
	}

	public void setPea(RemotePriEscaAttack pea) {
		this.pea = pea;
	}

	public HostPrivilege getThp() {
		return thp;
	}

	public void setThp(HostPrivilege thp) {
		this.thp = thp;
	}
}

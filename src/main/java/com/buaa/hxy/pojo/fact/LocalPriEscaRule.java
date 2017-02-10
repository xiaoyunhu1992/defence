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
	
	//动作节点
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
		/*** 
		对于漏洞CVE_2003_0542是一个缓冲区溢出漏洞，可能允许攻击者在有漏洞的主机上执行任意代码。
		hostPrivilege(Attacker,HostName,root):_
			hostService(HostName, ‘Apache’, ‘1.3.11’, tcp,8080, nolimit),
			hostVulnerability(HostName, ‘CVE_2003_0542’,’ Apache’),
			hostPrivilege(Attacker, HostName,user),
			exploit(Attacker, HostName,’ CVE_2002_0364’).

		**/
		LocalPriEscaRule cve_2003_0542 = new LocalPriEscaRule();
		cve_2003_0542.getHs().getService().setServeceName("Apache");
		cve_2003_0542.getHs().getService().setPort("8080");
		cve_2003_0542.getHs().getService().setProtocal("tcp");
		cve_2003_0542.getHs().getService().setPrivilege(0);
		cve_2003_0542.getHs().getService().setVersion("1.3.29");
		
		
		cve_2003_0542.getHv().setVulID("CVE_2003_0542");
		cve_2003_0542.getHv().setServiceName("Apache");
				
		cve_2003_0542.getHp().setPriviledge(1);//运用该漏洞需要的权限
		
		cve_2003_0542.getPea().setVulID("CVE_2003_0542");
		
		cve_2003_0542.getThp().setPriviledge(2);//利用漏洞后获得的权限
		
		lper.add(cve_2003_0542);
		return lper;
	}
}

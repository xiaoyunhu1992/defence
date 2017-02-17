package com.buaa.hxy.pojo.fact;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.buaa.hxy.pojo.subNodeType.Connection;
import com.buaa.hxy.service.IEntityService;
import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.FClass.Attacker;
import com.buaa.hxy.pojo.FClass.Computer;
import com.buaa.hxy.pojo.FClass.Service;

public class InitialFact {

	public ArrayList<Attacker> returnAttackerList(){
////		ArrayList<Attacker> b = new ArrayList<Attacker>();
////		List<AttackerEntity> attackerlist = this.entityservice.getAttackerList();
////		System.out.print(s);
////		for(AttackerEntity item : attackerlist){
////			Computer com = new Computer();
////			com.setComputerName(item.gethostName());
////			HostEntity host = this.entityservice.getComputer(item.gethostName());
////			com.setIPAddress(host.getIP());
////			com.setMask(host.getMASK());
////			Attacker attacker = new Attacker();
////			attacker.setAttackerComputer(com);
////			String priviledge = item.getpriviledge();
////			if (priviledge.equals("root")){
////				attacker.setPrivilege(2);
////			}
////			else if (priviledge.equals("user")){
////				attacker.setPrivilege(1);
////			}
////			else if (priviledge.equals("guest")){
////				attacker.setPrivilege(0);
////
////			}
////			a.add(attacker);
////		}
////		return a;
//		
		Computer malicious = new Computer();
		malicious.setComputerName("Malicious");
		malicious.setIPAddress("219.224.166.131");
		malicious.setMask("255.255.255.0");
		
		Attacker attacker = new Attacker();
		attacker.setAttackerComputer(malicious);
		attacker.setPrivilege(2);
		
		ArrayList<Attacker> a = new ArrayList<Attacker>();
		a.add(attacker);
		
		Computer user_2 = new Computer();
		user_2.setComputerName("User_2");
		user_2.setIPAddress("10.0.0.12");
		user_2.setMask("255.255.255.0");
		
		Attacker attacker2 = new Attacker();
		attacker2.setAttackerComputer(user_2);
		attacker2.setPrivilege(2);
		a.add(attacker2);
		
		return a;
	}
	
	public ArrayList<Computer> returnComputerList(){
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		
		Computer dnsServer = new Computer();
		dnsServer.setComputerName("DNSserver");
		dnsServer.setIPAddress("192.168.1.120");
		dnsServer.setMask("255.255.255.0");
		
		Service dnsService = new Service();
		dnsService.setPort("21");
		dnsService.setServeceName("Microsoft Server");
		dnsService.setVersion("2000");
		dnsService.setPrivilege(0);
		dnsService.setProtocal("tcp");
		dnsService.setVul("CVE_2007_3039");
		
		dnsServer.getServiceList().add(dnsService);
		

		
		Computer webServer = new Computer();
		webServer.setComputerName("Webserver");
		webServer.setIPAddress("192.168.1.110");
		webServer.setMask("255.255.255.0");
		
		Service webService = new Service();
		webService.setPort("80");
		webService.setServeceName("iis");
		webService.setVersion("7.5");
		webService.setPrivilege(0);
		webService.setProtocal("tcp");
		webService.setVul("CVE_2010_2730");
		
		
		Service webService2 = new Service();
		webService2.setPort("8080");
		webService2.setServeceName("apache");
		webService2.setVersion("1.3.29");
		webService2.setPrivilege(0); 
		webService2.setProtocal("tcp");
		webService2.setVul("CVE_2003_0542");
		
		//=============================
		webServer.getServiceList().add(webService);
		webServer.getServiceList().add(webService2);
	
		Computer ftpServer_1 = new Computer();
		ftpServer_1.setComputerName("FTPserver_1");
		ftpServer_1.setIPAddress("192.168.1.80");
		ftpServer_1.setMask("255.255.255.0");
		
		Service ftpService_1 = new Service();
		ftpService_1.setPort("21");
		ftpService_1.setServeceName("NULL FTP Server Pro");
		ftpService_1.setVersion("1.1.0.7");
		ftpService_1.setPrivilege(0);
		ftpService_1.setProtocal("tcp");
		ftpService_1.setVul("CVE_2008_6534");
		
		ftpServer_1.getServiceList().add(ftpService_1);
		
		
		//////////////SMTP�ļ����״̬�ͷ���״̬
		Computer smtpServer = new Computer();
		smtpServer.setComputerName("SMTPserver");
		smtpServer.setIPAddress("192.168.1.65");
		smtpServer.setMask("255.255.255.0");
		
		Service smtpService = new Service();
		smtpService.setPort("25");
		smtpService.setServeceName("Nginx");
		smtpService.setVersion("1.5.10");
		smtpService.setPrivilege(0);
		smtpService.setProtocal("tcp");
		smtpService.setVul("CVE_2014_0133");
		
		smtpServer.getServiceList().add(smtpService);
		
		
		//////////////database�ļ����״̬�ͷ���״̬
		Computer databaseServer = new Computer();
		databaseServer.setComputerName("Databaseserver");
		databaseServer.setIPAddress("12.1.0.1");
		databaseServer.setMask("255.255.255.0");
		
		Service databaseService = new Service();
		databaseService.setPort("1433");
		databaseService.setServeceName("Oracle Database Server");
		databaseService.setVersion("12.1.0.1");
		databaseService.setPrivilege(0);
		databaseService.setProtocal("tcp");
		databaseService.setVul("CVE_2014_2408");
		
		databaseServer.getServiceList().add(databaseService);
		
		
		//////////////fileServer�ļ����״̬�ͷ���״̬
		Computer fileServer = new Computer();
		fileServer.setComputerName("Rejetto HTTP File Server");
		fileServer.setIPAddress("10.0.0.49");
		fileServer.setMask("255.255.255.0");
		
		Service fileService = new Service();
		fileService.setPort("21");
		fileService.setServeceName("Easewe FTP OCX");
		fileService.setVersion("2.3");
		fileService.setPrivilege(0);
		fileService.setProtocal("tcp");
		fileService.setVul("CVE_2014_7226");
		
		fileServer.getServiceList().add(fileService);
		
		
		
		//////////////ftp_2�ļ����״̬�ͷ���״̬
		Computer ftpServer_2 = new Computer();
		ftpServer_2.setComputerName("FTPserver_2");
		ftpServer_2.setIPAddress("192.168.1.80");
		ftpServer_2.setMask("255.255.255.0");
		
		Service ftpService_2 = new Service();
		ftpService_2.setPort("21");
		ftpService_2.setServeceName("NULL FTP Server ro ");
		ftpService_2.setVersion("1.1.0.7");
		ftpService_2.setPrivilege(0);
		ftpService_2.setProtocal("tcp");
		ftpService_2.setVul("CVE_2008_6534");
		
		ftpServer_2.getServiceList().add(ftpService_1);
		

		
		//////////////user1�ļ����״̬�ͷ���״̬
		Computer user_1 = new Computer();
		user_1.setComputerName("User_1");
		user_1.setIPAddress("10.0.0.11");
		user_1.setMask("255.255.255.0");
		
		Service userService_1 = new Service();
		userService_1.setPort("5190");
		userService_1.setServeceName("F5 BIG-IP LTM");
		userService_1.setVersion("11.0.0");
		userService_1.setPrivilege(0);
		userService_1.setProtocal("tcp");
		userService_1.setVul("CVE_2014_2928");
		
		user_1.getServiceList().add(userService_1);

		
		//////////////user2�ļ����״̬�ͷ���״̬
		Computer user_2 = new Computer();
		user_2.setComputerName("User_2");
		user_2.setIPAddress("10.0.0.12");
		user_2.setMask("255.255.255.0");
		
		Service userService_2 = new Service();
		userService_2.setPort("5190");
		userService_2.setServeceName("F5 BIG-IP LTM");
		userService_2.setVersion("11.0.0");
		userService_2.setPrivilege(0);
		userService_2.setProtocal("tcp");
		userService_2.setVul("CVE_2014_2928");
		
		user_2.getServiceList().add(userService_2);
		

		
//////////////user3�ļ����״̬�ͷ���״̬
		Computer user_3 = new Computer();
		user_3.setComputerName("User_3");
		user_3.setIPAddress("10.0.0.13");
		user_3.setMask("255.255.255.0");
		
		Service userService_3 = new Service();
		userService_3.setPort("5190");
		userService_3.setServeceName("Adobe Flash Player");
		userService_3.setVersion("10.0.0");
		userService_3.setPrivilege(0);
		userService_3.setProtocal("tcp");
		userService_3.setVul("CVE_2009_3796");
		
		user_3.getServiceList().add(userService_3);
		

		///////////////////������������ڳ�ʼ����
			///�����������ⲿ����
		Computer malicious = new Computer();
		malicious.setComputerName("Malicious");
		malicious.setIPAddress("219.224.166.131");
		malicious.setMask("255.255.255.0");
		
//		Attacker attacker = new Attacker();
//		attacker.setAttackerComputer(malicious);
//		
//		ArrayList<Attacker> attackerList = new ArrayList<Attacker>();
//		attackerList.add(attacker);
		
		
		///////////////////////////////������ӹ�ϵ
	
		Connection m2dns = new Connection();
		m2dns.setSource(malicious);
		m2dns.setDestiny(dnsServer);
		m2dns.setProtcal(dnsServer.getServiceList().get(0).getProtocal());
		m2dns.setProt(dnsServer.getServiceList().get(0).getPort());
		
		
		Connection m2web = new Connection();
		m2web.setSource(malicious);
		m2web.setDestiny(webServer);
		m2web.setProtcal(webServer.getServiceList().get(0).getProtocal());
		m2web.setProt(webServer.getServiceList().get(0).getPort());

		
		Connection m2ftp_1 = new Connection();
		m2ftp_1.setSource(malicious);
		m2ftp_1.setDestiny(ftpServer_1);
		m2ftp_1.setProtcal(ftpServer_1.getServiceList().get(0).getProtocal());
		m2ftp_1.setProt(ftpServer_1.getServiceList().get(0).getPort());
		

//		
		Connection m2smtp = new Connection();
		m2smtp.setSource(malicious);
		m2smtp.setDestiny(smtpServer);
		m2smtp.setProtcal(smtpServer.getServiceList().get(0).getProtocal());
		m2smtp.setProt(smtpServer.getServiceList().get(0).getPort());
		

		
		malicious.getConnectionList().add(m2dns);
		malicious.getConnectionList().add(m2web);
		malicious.getConnectionList().add(m2ftp_1);
		malicious.getConnectionList().add(m2smtp);

		//-----------------------------------------------�������� maliciousΪ source������
		
		Connection web2ftp_1 = new Connection();
		web2ftp_1.setSource(webServer);
		web2ftp_1.setDestiny(ftpServer_1);
		web2ftp_1.setProtcal(ftpServer_1.getServiceList().get(0).getProtocal());
		web2ftp_1.setProt(ftpServer_1.getServiceList().get(0).getPort());
		
		
//		
		Connection web2dns = new Connection();
		web2dns.setSource(webServer);
		web2dns.setDestiny(dnsServer);
		web2dns.setProtcal(dnsServer.getServiceList().get(0).getProtocal());
		web2dns.setProt(dnsServer.getServiceList().get(0).getPort());
		

		Connection web2database = new Connection();
		web2database.setSource(webServer);
		web2database.setDestiny(databaseServer);
		web2database.setProtcal(databaseServer.getServiceList().get(0).getProtocal());
		web2database.setProt(databaseServer.getServiceList().get(0).getPort());
	
		webServer.getConnectionList().add(web2ftp_1);
		webServer.getConnectionList().add(web2database);
		
		
		Connection ftp_1tdatabase = new Connection();
		ftp_1tdatabase.setSource(ftpServer_1);
		ftp_1tdatabase.setDestiny(databaseServer);
		ftp_1tdatabase.setProtcal(databaseServer.getServiceList().get(0).getProtocal());
		ftp_1tdatabase.setProt(databaseServer.getServiceList().get(0).getPort());
		
		
		Connection ftp_1tsmtp = new Connection();
		ftp_1tsmtp.setSource(ftpServer_1);
		ftp_1tsmtp.setDestiny(smtpServer);
		ftp_1tsmtp.setProtcal(smtpServer.getServiceList().get(0).getProtocal());
		ftp_1tsmtp.setProt(smtpServer.getServiceList().get(0).getPort());
		
		
		ftpServer_1.getConnectionList().add(ftp_1tdatabase);
		ftpServer_1.getConnectionList().add(ftp_1tsmtp);

		
		
		Connection smtp2ftp_1 = new Connection();
		smtp2ftp_1.setSource(smtpServer);
		smtp2ftp_1.setDestiny(ftpServer_1);
		smtp2ftp_1.setProtcal(ftpServer_1.getServiceList().get(0).getProtocal());
		smtp2ftp_1.setProt(ftpServer_1.getServiceList().get(0).getPort());
		
	
		
//		-----origin----
		
//		Connection smtp2web = new Connection();
//		smtp2web.setSource(smtpServer);
//		smtp2web.setDestiny(webServer);
//		smtp2web.setProtcal(webServer.getServiceList().get(0).getProtocal());
//		smtp2web.setProt(webServer.getServiceList().get(0).getPort());
//		
//		smtpServer.getConnectionList().add(smtp2web);
//		-----origin----

		smtpServer.getConnectionList().add(smtp2ftp_1);
		


		Connection database2ftp_2 = new Connection();
		database2ftp_2.setSource(databaseServer);
		database2ftp_2.setDestiny(ftpServer_2);
		database2ftp_2.setProtcal(ftpServer_2.getServiceList().get(0).getProtocal());
		database2ftp_2.setProt(ftpServer_2.getServiceList().get(0).getPort());

		
		
		databaseServer.getConnectionList().add(database2ftp_2);
		

		
		Connection file2ftp_2 = new Connection();
		file2ftp_2.setSource(fileServer);
		file2ftp_2.setDestiny(ftpServer_2);
		file2ftp_2.setProtcal(ftpServer_2.getServiceList().get(0).getProtocal());
		file2ftp_2.setProt(ftpServer_2.getServiceList().get(0).getPort());
		
		
		
		fileServer.getConnectionList().add(file2ftp_2);
		
		
		
		Connection ftp_2tfile = new Connection();
		ftp_2tfile.setSource(ftpServer_2);
		ftp_2tfile.setDestiny(fileServer);
		ftp_2tfile.setProtcal(fileServer.getServiceList().get(0).getProtocal());
		ftp_2tfile.setProt(fileServer.getServiceList().get(0).getPort());
		
		
		ftpServer_2.getConnectionList().add(ftp_2tfile);

		
		Connection user_1tdns = new Connection();
		user_1tdns.setSource(user_1);
		user_1tdns.setDestiny(dnsServer);
		user_1tdns.setProtcal(dnsServer.getServiceList().get(0).getProtocal());
		user_1tdns.setProt(dnsServer.getServiceList().get(0).getPort());
		
		
		user_1.getConnectionList().add(user_1tdns);
		

		
		Connection user_1tweb = new Connection();
		user_1tweb.setSource(user_1);
		user_1tweb.setDestiny(webServer);
		user_1tweb.setProtcal(webServer.getServiceList().get(0).getProtocal());
		user_1tweb.setProt(webServer.getServiceList().get(0).getPort());


		
		user_1.getConnectionList().add(user_1tweb);
		

		
		Connection user_1tftp_1 = new Connection();
		user_1tftp_1.setSource(user_1);
		user_1tftp_1.setDestiny(ftpServer_1);
		user_1tftp_1.setProtcal(ftpServer_1.getServiceList().get(0).getProtocal());
		user_1tftp_1.setProt(ftpServer_1.getServiceList().get(0).getPort());
		
	
		
		user_1.getConnectionList().add(user_1tftp_1);
		

		
		Connection user_1tsmtp = new Connection();
		user_1tsmtp.setSource(user_1);
		user_1tsmtp.setDestiny(smtpServer);
		user_1tsmtp.setProtcal(smtpServer.getServiceList().get(0).getProtocal());
		user_1tsmtp.setProt(smtpServer.getServiceList().get(0).getPort());
		
		
		
		user_1.getConnectionList().add(user_1tsmtp);
		

		
		Connection user_1tdatabase = new Connection();
		user_1tdatabase.setSource(user_1);
		user_1tdatabase.setDestiny(databaseServer);
		user_1tdatabase.setProtcal(databaseServer.getServiceList().get(0).getProtocal());
		user_1tdatabase.setProt(databaseServer.getServiceList().get(0).getPort());
		
	
		
		user_1.getConnectionList().add(user_1tdatabase);
		
		
		
		Connection user_1tfile = new Connection();
		user_1tfile.setSource(user_1);
		user_1tfile.setDestiny(fileServer);
		user_1tfile.setProtcal(fileServer.getServiceList().get(0).getProtocal());
		user_1tfile.setProt(fileServer.getServiceList().get(0).getPort());
		
		
		user_1.getConnectionList().add(user_1tfile);
		
		
		Connection user_1tftp_2 = new Connection();
		user_1tftp_2.setSource(user_1);
		user_1tftp_2.setDestiny(ftpServer_2);
		user_1tftp_2.setProtcal(ftpServer_2.getServiceList().get(0).getProtocal());
		user_1tftp_2.setProt(ftpServer_2.getServiceList().get(0).getPort());
		
	
		
		user_1.getConnectionList().add(user_1tftp_2);
		
		
		
		Connection user_1tuser_2 = new Connection();
		user_1tuser_2.setSource(user_1);
		user_1tuser_2.setDestiny(user_2);
		user_1tuser_2.setProtcal(user_2.getServiceList().get(0).getProtocal());
		user_1tuser_2.setProt(user_2.getServiceList().get(0).getPort());
		
		
		
		user_1.getConnectionList().add(user_1tuser_2);
		
		
		
		Connection user_2tuser_1 = new Connection();
		user_2tuser_1.setSource(user_2);
		user_2tuser_1.setDestiny(user_1);
		user_2tuser_1.setProtcal(user_1.getServiceList().get(0).getProtocal());
		user_2tuser_1.setProt(user_1.getServiceList().get(0).getPort());
		
	
		
		user_2.getConnectionList().add(user_2tuser_1);
		

		Connection user_3tuser_1 = new Connection();
		user_3tuser_1.setSource(user_3);
		user_3tuser_1.setDestiny(user_1);
		user_3tuser_1.setProtcal(user_1.getServiceList().get(0).getProtocal());
		user_3tuser_1.setProt(user_1.getServiceList().get(0).getPort());
		user_3.getConnectionList().add(user_3tuser_1);
		
		Connection dns2user_3 = new Connection();
		dns2user_3.setSource(dnsServer);
		dns2user_3.setDestiny(user_3);
		dns2user_3.setProtcal(user_3.getServiceList().get(0).getProtocal());
		dns2user_3.setProt(user_3.getServiceList().get(0).getPort());
		dnsServer.getConnectionList().add(dns2user_3);
		

		
		
		
		
		////////////���뵽�б?����
		computerList.add(dnsServer);
		computerList.add(smtpServer);
		computerList.add(databaseServer);
		computerList.add(ftpServer_1);
		computerList.add(ftpServer_2);
		computerList.add(fileServer);
		computerList.add(webServer);
		computerList.add(malicious);
		computerList.add(user_1);
		computerList.add(user_2);
		computerList.add(user_3);
		return computerList;
	}
}

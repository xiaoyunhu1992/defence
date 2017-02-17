package com.buaa.hxy.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.ServiceEntity;
import com.buaa.hxy.pojo.VulnEntity;
import com.buaa.hxy.service.IEntityService;

import sun.print.resources.serviceui;

@Controller
@RequestMapping("/fileupload")
public class UploadNetwrokController {
	@Autowired
    @Qualifier("entityService")
	private IEntityService entityservice;
	@RequestMapping(value = "/network", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file){
		System.out.println("start");
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String path = "/Users/hxy/Documents/workspace/defence/WebContent/static";
                File networkfile = new File(path,"upload-network.txt");
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(networkfile));
                stream.write(bytes);
//                System.out.println(stream);
                stream.close();
                parseNetwork(path+"/upload-network.txt");
                return "upload success";
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            return "can't upload empty file";
        }
	}
	
//	@RequestMapping(value = "/evidence", method = RequestMethod.POST)
//	@ResponseBody
//	public String uploadevi(@RequestParam("file") MultipartFile file){
//		if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                String path = "/Users/hxy/Documents/workspace/defence/WebContent/static";
//                File networkfile = new File(path,"upload-evi.txt");
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(networkfile));
//                stream.write(bytes);
////                System.out.println(stream);
//                stream.close();
//                parseEvidence(path+"/upload-evi.txt");
//                return "upload success";
//            } catch (Exception e) {
//                return e.getMessage();
//            }
//        } else {
//            return "can't upload empty file";
//        }
//	}
//	
//	@RequestMapping(value = "/rule", method = RequestMethod.POST)
//	@ResponseBody
//	public String uploadrule(@RequestParam("file") MultipartFile file){
//		if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                String path = "/Users/hxy/Documents/workspace/defence/WebContent/static";
//                File networkfile = new File(path,"upload-rule.txt");
//                BufferedOutputStream stream =
//                        new BufferedOutputStream(new FileOutputStream(networkfile));
//                stream.write(bytes);
////                System.out.println(stream);
//                stream.close();
//                parseRule(path+"/upload-rule.txt");
//                return "upload success";
//            } catch (Exception e) {
//                return e.getMessage();
//            }
//        } else {
//            return "can't upload empty file";
//        }
//	}
	
	public void parseNetwork(String path) {
        try {
        	
        	// read file content from file
        	ArrayList<String> networktext = new ArrayList<String>();
        	FileReader reader = new FileReader(path);
        	BufferedReader br = new BufferedReader(reader);
        	String str = null;
        	while((str = br.readLine()) != null) {
        		networktext.add(str);
//        		System.out.print(str);
        	}
        	this.entityservice.delConn();
        	this.entityservice.delHost();
        	this.entityservice.delVuln();
        	this.entityservice.delService();
        	this.entityservice.delAttacker();
        	
        	for (String item : networktext){
        		
        		String[] itempart = item.split("\\(|\\)");
//         		System.out.print(itempart[0]+'\n');
//        		System.out.print(itempart[1]+'\n');
        		if (itempart[0].equals("connection")){
        			String[] element = itempart[1].split(",");
        			ConnEntity conn = new ConnEntity();
        			conn.setsourceName(element[0]);
        			conn.setdesName(element[1]);
        			conn.setprotocol(element[2]);
        			conn.setport(element[3]);
                	this.entityservice.setConnEntity(conn);
        				
        		}
        		else if (itempart[0].equals("host")){
        			String[] element = itempart[1].split(",");
        			HostEntity host = new HostEntity();
        			host.sethostName(element[0]);
//        			System.out.print(element[1]+'\n');
        			host.setIP(element[1]);
        			host.setMASK(element[2]);
//        			System.out.print(element[2]+'\n');
                	this.entityservice.setHostEntity(host);
        				
        		}
        		else if (itempart[0].equals("hostVulnerability")){
        			String[] element = itempart[1].split(",");
        			VulnEntity vuln = new VulnEntity();
        			vuln.sethostName(element[0]);
//        			System.out.print(element[1]+'\n');
        			vuln.setVulID(element[1]);
        			vuln.setServiceName(element[2]);
//        			System.out.print(element[2]+'\n');
                	this.entityservice.setVulnEntity(vuln);
        				
        		}
        		else if (itempart[0].equals("hostService")){
        			String[] element = itempart[1].split(",");
        			ServiceEntity service = new ServiceEntity();
        			service.sethostName(element[0]);
        			service.setServiceName(element[1]);
        			service.setVersion(element[2]);
        			service.setprotocol(element[3]);
        			service.setport(element[4]);
        			service.setpriviledge(element[5]);
                	this.entityservice.setServiceEntity(service);
        				
        		}
        		else if (itempart[0].equals("attacker")){
        			String[] element = itempart[1].split(",");
        			AttackerEntity attacker = new AttackerEntity();
        			attacker.sethostName(element[0]);
        			attacker.setpriviledge(element[1]);
                	this.entityservice.setAttackerEntity(attacker);
        		}
        		
//        		System.out.print(itempart[0]+'\n');
//        		System.out.print(itempart[1]+'\n');
        	}
        br.close();
        reader.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
        	e.printStackTrace();
        }
	}
//	public void parseEvidence(String path) {
//        try {
//        	
//        	// read file content from file
//        	ArrayList<String> networktext = new ArrayList<String>();
//        	FileReader reader = new FileReader(path);
//        	BufferedReader br = new BufferedReader(reader);
//        	String str = null;
//        	while((str = br.readLine()) != null) {
//        		networktext.add(str);
////        		System.out.print(str);
//        	}
//        	this.entityservice.delSafeEvent();
//        	for (String item : networktext){
//            	this.entityservice.delSafeEvent();
//        		String[] itempart = item.split("\\(|\\)");
////         		System.out.print(itempart[0]+'\n');
////        		System.out.print(itempart[1]+'\n');
//        		if(itempart[0].equals("safeEvent")){
//        			String[] element = itempart[1].split(",");
//        			SafeEventEntity safeEvent = new SafeEventEntity();
//        			safeEvent.sethostName(element[0]);
//        			safeEvent.setserviceName(element[1]);
//        			safeEvent.setpriviledge(element[2]);
//        			this.entityservice.setSafeEventEntity(safeEvent);
//        		}
//        		
//        	}
//        br.close();
//        reader.close();
//        }
//        catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch(IOException e) {
//        	e.printStackTrace();
//        }
//	}
//	public void parseRule(String path) {
//        try {
//        	
//        	// read file content from file
//        	ArrayList<String> networktext = new ArrayList<String>();
//        	FileReader reader = new FileReader(path);
//        	BufferedReader br = new BufferedReader(reader);
//        	String str = null;
//        	while((str = br.readLine()) != null) {
//        		networktext.add(str);
////        		System.out.print(str);
//        	}
//        	
//        	for (String item : networktext){
//            	this.entityservice.delSafeEvent();
//        		String[] itempart = item.split("\\(|\\)");
////         		System.out.print(itempart[0]+'\n');
////        		System.out.print(itempart[1]+'\n');
//        		if(itempart[0].equals("rper")){
//        			String[] element = itempart[1].split(",");
//        			SafeEventEntity safeEvent = new SafeEventEntity();
//        			safeEvent.sethostName(element[0]);
//        			safeEvent.setserviceName(element[1]);
//        			safeEvent.setpriviledge(element[2]);
//        			this.entityservice.setSafeEventEntity(safeEvent);
//        		}
//        		
//        	}
//        br.close();
//        reader.close();
//        }
//        catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch(IOException e) {
//        	e.printStackTrace();
//        }
//	}
}

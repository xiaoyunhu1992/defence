package com.buaa.hxy.pojo.Graphoutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;

import org.omg.PortableServer.AdapterActivator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.buaa.hxy.pojo.fact.RemotePriEscaRule;
import com.buaa.hxy.pojo.subNodeType.Connection;
import com.buaa.hxy.pojo.subNodeType.HostPrivilege;
import com.buaa.hxy.pojo.subNodeType.HostService;
import com.buaa.hxy.pojo.subNodeType.HostVulnerability;
import com.buaa.hxy.pojo.subNodeType.LocalPriEscaAttack;
import com.buaa.hxy.pojo.subNodeType.RemotePriEscaAttack;
import com.buaa.hxy.service.IEntityService;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.AttackGraphNodeType.ActionNode;
import com.buaa.hxy.pojo.AttackGraphNodeType.Node;
import com.buaa.hxy.pojo.AttackGraphNodeType.StateNode;
import com.buaa.hxy.pojo.FClass.Attacker;
import com.buaa.hxy.pojo.FClass.Computer;
import com.buaa.hxy.pojo.FClass.Service;


public class GraphOutUnsimply {
	@Autowired
    @Qualifier("entityService")
	private IEntityService entityservice;
	static String targetName =  "Databaseserver";


        public void start(ArrayList<Node> ag,int safePri,String safeEventHost) {
       	
        	GraphViz gv = new GraphViz();
                
                Map<Integer, String> num_Privilege = new HashMap();
                num_Privilege.put(0, "guest");
                num_Privilege.put(1, "user");
                num_Privilege.put(2, "root");
                
                Map<String, Integer> node_num = new LinkedHashMap();
                int num = 1;

                gv.addln(gv.start_graph());
                gv.addln("size=\"10,10\"");
                
                int hpNum = 0;
                int hsNum = 0;
                int hvNum = 0;
                int cNum = 0;
                int rpeaNum = 0;
                int lpeaNum = 0;
                
                
                Iterator<Node> itNode = ag.iterator();
                while(itNode.hasNext()){
                	Node agNode = (Node) itNode.next();
                	if(agNode instanceof StateNode){
                		if(agNode instanceof HostPrivilege){
                			String NodeName = "";
                			NodeName = "Hp"+((HostPrivilege)agNode).getDestiny().getComputerName()+""+num_Privilege.get(((HostPrivilege)agNode).getPriviledge());
                			
                			if(node_num.containsKey(NodeName)){
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(node_num.get(NodeName));
                			}
                			else{
                				node_num.put(NodeName, num);
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(num);
//                				System.out.println(num);
//                				System.out.println(NodeName);
//                				if (agNode.getnodeNum() == 6){
                				if(((HostPrivilege) agNode).getDestiny().getComputerName().equals(targetName)){
                					System.out.println(((HostPrivilege) agNode).getDestiny().getComputerName());
                					gv.addln("" + NodeName + "[style=filled, color=tomato]");

                				}
                				else if(((HostPrivilege) agNode).getDestiny().getComputerName().equals(safeEventHost)&&((HostPrivilege) agNode).getPriviledge()==safePri){
                					gv.addln("Evidence"+"[shape=octagon,fontcolor=white,style=filled, color=gray16]");
                					gv.addln(""+NodeName);
                					gv.addln("Evidence->"+NodeName);
                					
                				}
                				else{
                					gv.addln(""+NodeName);
                				}
                				num +=1 ;
                			}
                		}
                		if(agNode instanceof HostService){
                			hsNum++;
                			String NodeName = "Hs"+((HostService)agNode).getDestiny().getComputerName();
                			if(node_num.containsKey(NodeName)){
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(node_num.get(NodeName));
                			}
                			else{
                				node_num.put(NodeName, num);
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(num);
                				gv.addln(""+NodeName);
                				num +=1 ;
                			}
                			
                		}
                		if(agNode instanceof HostVulnerability){
                			hvNum++;
                			String NodeName = "Hv"+((HostVulnerability)agNode).getDestiny().getComputerName();
                			if(node_num.containsKey(NodeName)){
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(node_num.get(NodeName));
                			}
                			else{
                				node_num.put(NodeName, num);
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(num);
                				gv.addln(""+NodeName);
                				num +=1 ;
                			}
                			
                		}
                		if(agNode instanceof Connection){
                			cNum++;
                			String NodeName = ((Connection)agNode).getSource().getComputerName()+"_"+((Connection)agNode).getDestiny().getComputerName();
                			if(node_num.containsKey(NodeName)){
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(node_num.get(NodeName));
                			}
                			else{
                				node_num.put(NodeName, num);
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(num);
                				gv.addln(""+NodeName);

                				num +=1 ;
                			}
                			
                		}
                	}
                	             	
                	if(agNode instanceof ActionNode){
                		if(agNode instanceof RemotePriEscaAttack){
	            			rpeaNum++;
	            			String NodeName = new String();
	            			
	            			if (((RemotePriEscaAttack)agNode).getVulID().contains("zday")){
	            				NodeName = "zday_rpea: "+((RemotePriEscaAttack)agNode).getVulID();
		            			
		            			if(node_num.containsKey(NodeName)){
	                				agNode.setNodeName(NodeName);
	                				agNode.setnodeNum(node_num.get(NodeName));
	                			}
	                			else{
	                				node_num.put(NodeName, num);
	                				agNode.setNodeName(NodeName);
	                				agNode.setnodeNum(num);
	                				gv.addln(""+NodeName+"[shape = box,style = filled,fillcolor = \".6 .2 .7\"]");
	                				num +=1 ;
	                				
	                				Iterator<StateNode> itpost = ((ActionNode)agNode).getPreStateNode().iterator();
	                            	while(itpost.hasNext()){
	                            		Node desNode = itpost.next();
	                            		if(desNode instanceof HostPrivilege){
	                            			gv.addln("" + desNode.getNodeName() + "[style = filled,fillcolor = \".6 .2 .7\"]");

	                            		}
	                            	}
	                            	
	                			}
	            			}
	            			
	            			else{
	            				
	                			NodeName = "rpea_"+((RemotePriEscaAttack)agNode).getVulID()+"_"+((RemotePriEscaAttack)agNode).getSource().getComputerName()+"_"+((RemotePriEscaAttack)agNode).getDestiny().getComputerName();

		            			if(node_num.containsKey(NodeName)){
	                				agNode.setNodeName(NodeName);
	                				agNode.setnodeNum(node_num.get(NodeName));
	                			}
	                			else{
	                				node_num.put(NodeName, num);
	                				agNode.setNodeName(NodeName);
	                				agNode.setnodeNum(num);
	                				gv.addln(""+NodeName+"[shape = box]");
	                				num +=1 ;
	                			}
	            			}	
	            			
                		}
                		if(agNode instanceof LocalPriEscaAttack){
	            			lpeaNum++;
	            			String NodeName = "lpea_"+((LocalPriEscaAttack)agNode).getVulID()+""+((LocalPriEscaAttack)agNode).getDestiny().getComputerName();

	            			if(node_num.containsKey(NodeName)){
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(node_num.get(NodeName));
                			}
                			else{
                				node_num.put(NodeName, num);
                				agNode.setNodeName(NodeName);
                				agNode.setnodeNum(num);
                				gv.addln(""+NodeName+"[shape = box]");

                				num +=1 ;
                			}

                		}
                		
            		}
                }
                
                Iterator<Node> itCon = ag.iterator();
                ArrayList<String> FinalPath = new ArrayList<String>();
                
                while (itCon.hasNext()){
                	
                	Node SourceNode = itCon.next();
                	
                	String sourceName = SourceNode.getNodeName();
                	int sourceNum = SourceNode.getnodeNum();
                	
                	String newOrder = new String();
                	String fileOutPut = new String();
                	
                	if(SourceNode instanceof StateNode){
                		Iterator<StateNode> itpost = ((StateNode)SourceNode).getPostActionNode().iterator();
                    	while(itpost.hasNext()){
                    		Node desNode = itpost.next();
                    		String desName = desNode.getNodeName();
                    		int desNum = desNode.getnodeNum();
                    		
                    		newOrder = sourceName + "->" + desName +";";
                    		fileOutPut = sourceName + "->" + desName +";";
                    		
                    		Iterator<String> itString = FinalPath.iterator();
                    		boolean notinPath = true;
                    		
                    		while(itString.hasNext()){
                    			String eachString = itString.next();
                    			if(eachString.equals(newOrder)){
                    				notinPath = false;
                    				break;
                    			}
                    		}
                    		if(notinPath){
                    			FinalPath.add(newOrder);
                    		}
                    	}
                	}
                	if(SourceNode instanceof ActionNode){
                		Iterator<StateNode> itpost = ((ActionNode)SourceNode).getPostStateNode().iterator();
                    	while(itpost.hasNext()){
                    		Node desNode = itpost.next();
                    		String desName = desNode.getNodeName();
                    		int desNum = desNode.getnodeNum();
                    		
                    		newOrder = sourceName + "->" + desName +";";
                    		fileOutPut = sourceName + "->" + desName +";";
                    		
                    		Iterator<String> itString = FinalPath.iterator();
                    		boolean notinPath = true;
                    		
                    		while(itString.hasNext()){
                    			String eachString = itString.next();
                    			if(eachString.equals(newOrder)){
                    				notinPath = false;
                    				break;
                    			}
                    		}
                    		
                    		if(notinPath){
                    			FinalPath.add(newOrder);
                    		}
                    	}
                	}
                	
                	
                }
                
                Iterator<String> itString = FinalPath.iterator();
                while(itString.hasNext()){
        			String eachString = itString.next();
        			gv.addln(eachString);
        			
        		}
                
                gv.addln(gv.end_graph());


                String type = "svg";
                File out = new File("/Users/hxy/Documents/workspace/defence/WebContent/static/images/systemmanager/outunsimple." + type);
                gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);       
          
        }
        

}
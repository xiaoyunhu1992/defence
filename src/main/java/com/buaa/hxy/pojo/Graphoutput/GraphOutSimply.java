package com.buaa.hxy.pojo.Graphoutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.buaa.hxy.pojo.subNodeType.HostPrivilege;
import com.buaa.hxy.pojo.subNodeType.LocalPriEscaAttack;
import com.buaa.hxy.pojo.subNodeType.RemotePriEscaAttack;
import com.buaa.hxy.pojo.AttackGraphNodeType.ActionNode;
import com.buaa.hxy.pojo.AttackGraphNodeType.Node;
import com.buaa.hxy.pojo.AttackGraphNodeType.StateNode;

public class GraphOutSimply {
    public static void main(String[] args) {
        GraphOutUnsimply p = new GraphOutUnsimply();
        // p.start2();
    }
    public void start(ArrayList<Node> ag) {
        GraphViz gv = new GraphViz();
        
        Map<Integer, String> num_Privilege = new HashMap();
        num_Privilege.put(0, "guest");
        num_Privilege.put(1, "user");
        num_Privilege.put(2, "root");
        
        Map<String, Integer> node_num = new LinkedHashMap();
        int num = 1;

        gv.addln(gv.start_graph());
        gv.addln("size=\"5,5\"");
        
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

        			//String NodeName = "HostPrivilege: "+((HostPrivilege)agNode).getZdayTimes()+", "+((HostPrivilege)agNode).getDestiny().getComputerName()+", "+num_Privilege.get(((HostPrivilege)agNode).getPriviledge());
        			String NodeName = "Status_"+((HostPrivilege)agNode).getDestiny().getComputerName()+"_"+num_Privilege.get(((HostPrivilege)agNode).getPriviledge());
        			
        			if(node_num.containsKey(NodeName)){
        				agNode.setNodeName(NodeName);
        				agNode.setnodeNum(node_num.get(NodeName));
//        				Iterator<ActionNode> itpost = ((HostPrivilege)agNode).getPostActionNode().iterator();
//                    	while (itpost.hasNext()){
//                    		Node acNode = itpost.next();
//                    		if (acNode instanceof RemotePriEscaAttack){
//                    			if (((RemotePriEscaAttack)acNode).getVulID().contains("zday")){
//                    				gv.addln("" + num + "[style = filled,fillcolor = \".6 .2 .7\"]");
//                    				System.out.println("zheliyouyige");
//                    			}
//                    		}
//                    	}
        			}
        			else{
        				node_num.put(NodeName, num);
        				agNode.setNodeName(NodeName);
        				agNode.setnodeNum(num);
//        				Iterator<ActionNode> itpost = ((HostPrivilege)agNode).getPostActionNode().iterator();
//                    	while (itpost.hasNext()){
//                    		Node acNode = itpost.next();
//                    		if (acNode instanceof RemotePriEscaAttack){
//                    			if (((RemotePriEscaAttack)acNode).getVulID().contains("zday")){
//                    				gv.addln("" + num + "[style = filled,fillcolor = \".6 .2 .7\"]");
//                    			}
////                    			else{
////                    				gv.addln("" + num);	
////                    			}
//                    		}
////                    		else{
////                    			gv.addln("" + num);
////                    		}
//                    	}
        				//gv.addln("" + num);
        				System.out.println(num);
        				System.out.println(NodeName);
        				if (agNode.getnodeNum() == 3){
//        					gv.addln("" + num + "[style=filled, color=tomato]");
        					gv.addln("" + NodeName + "[style=filled, color=tomato]");

        				}

        				else if(agNode.getnodeNum() == 6 || agNode.getnodeNum()== 9){
        					gv.addln("" + NodeName + "[style=filled, color=gray90]");
        				}
        				else if(agNode.getnodeNum() == 16){
        					gv.addln("Evidence"+"[shape=octagon,fontcolor=white,style=filled, color=gray16]");
        					gv.addln("Evidence->"+NodeName);
        				} 
        				else{
//        					gv.addln("" + num);
        					gv.addln("" + NodeName);
        				}
        				num +=1 ;
        			}
        		}
//        		if(agNode instanceof HostService){
//        			hsNum++;
//        			String NodeName = "HostService: "+((HostService)agNode).getDestiny().getComputerName();
//        			if(node_num.containsKey(NodeName)){
//        				agNode.setNodeName(NodeName);
//        				agNode.setnodeNum(node_num.get(NodeName));
//        			}
//        			else{
//        				node_num.put(NodeName, num);
//        				agNode.setNodeName(NodeName);
//        				agNode.setnodeNum(num);
//        				gv.addln(""+num);
//        				num +=1 ;
//        			}
//        			
//        		}
//        		if(agNode instanceof HostVulnerability){
//        			hvNum++;
//        			String NodeName = "HostVulnerability: "+((HostVulnerability)agNode).getDestiny().getComputerName();
//        			if(node_num.containsKey(NodeName)){
//        				agNode.setNodeName(NodeName);
//        				agNode.setnodeNum(node_num.get(NodeName));
//        			}
//        			else{
//        				node_num.put(NodeName, num);
//        				agNode.setNodeName(NodeName);
//        				agNode.setnodeNum(num);
//        				gv.addln(""+num);
//        				num +=1 ;
//        			}
//        			
//        		}
//        		if(agNode instanceof Connection){
//        			cNum++;
//        			String NodeName = "Connection: "+((Connection)agNode).getSource().getComputerName()+", "+((Connection)agNode).getDestiny().getComputerName();
//        			if(node_num.containsKey(NodeName)){
//        				agNode.setNodeName(NodeName);
//        				agNode.setnodeNum(node_num.get(NodeName));
//        			}
//        			else{
//        				node_num.put(NodeName, num);
//        				agNode.setNodeName(NodeName);
//        				agNode.setnodeNum(num);
//        				gv.addln(""+num);
//        				num +=1 ;
//        			}
//        			
//        		}
        	}
        	             	
        	if(agNode instanceof ActionNode){
        		if(agNode instanceof RemotePriEscaAttack){
        			rpeaNum++;
        			String NodeName = new String();
        			
        			if (((RemotePriEscaAttack)agNode).getVulID().contains("zday")){
            			//NodeName = "zday_rpea: "+((RemotePriEscaAttack)agNode).getAttacker().getAttackerComputer().getComputerName()+", "+((RemotePriEscaAttack)agNode).getDestiny().getComputerName();
        				NodeName = "zday_rpea: "+((RemotePriEscaAttack)agNode).getDestiny().getComputerName();
            			
            			if(node_num.containsKey(NodeName)){
            				agNode.setNodeName(NodeName);
            				agNode.setnodeNum(node_num.get(NodeName));
            			}
            			else{
            				node_num.put(NodeName, num);
            				agNode.setNodeName(NodeName);
            				agNode.setnodeNum(num);
            				gv.addln(""+num+"[shape = box,style = filled,fillcolor = \".6 .2 .7\"]");
            				num +=1 ;
            				
//            				Iterator<StateNode> itpost = ((ActionNode)agNode).getPreStateNode().iterator();
//                        	while(itpost.hasNext()){
//                        		Node desNode = itpost.next();
//                        		if(desNode instanceof HostPrivilege){
//                        			gv.addln("" + desNode.getnodeNum() + "[style = filled,fillcolor = \".6 .2 .7\"]");
//                        		}
//                        	}
                        	
            			}
        			}
        			
        			else{
        				
            			//NodeName = "rpea: "+((RemotePriEscaAttack)agNode).getzdayflag()+", "+((RemotePriEscaAttack)agNode).getAttacker().getAttackerComputer().getComputerName()+", "+((RemotePriEscaAttack)agNode).getDestiny().getComputerName();
            			NodeName = "rpea_"+((RemotePriEscaAttack)agNode).getVulID()+""+((RemotePriEscaAttack)agNode).getDestiny().getComputerName();
            			
            			if(node_num.containsKey(NodeName)){
            				agNode.setNodeName(NodeName);
            				agNode.setnodeNum(node_num.get(NodeName));
            			}
            			else{
            				node_num.put(NodeName, num);
            				agNode.setNodeName(NodeName);
            				agNode.setnodeNum(num);
//            				gv.addln(""+num+"[shape = box]");
            				gv.addln(""+NodeName+"[shape = box]");
            				num +=1 ;
            			}
        			}	
        			
        		}
        		if(agNode instanceof LocalPriEscaAttack){
        			lpeaNum++;
        			//String NodeName = "lpea: "+((LocalPriEscaAttack)agNode).getzdayflag()+", "+((LocalPriEscaAttack)agNode).getAttacker().getAttackerComputer().getComputerName();
        			String NodeName = "lpea_"+((LocalPriEscaAttack)agNode).getVulID()+""+((LocalPriEscaAttack)agNode).getAttacker().getAttackerComputer().getComputerName();
        			
        			if(node_num.containsKey(NodeName)){
        				agNode.setNodeName(NodeName);
        				agNode.setnodeNum(node_num.get(NodeName));
        			}
        			else{
        				node_num.put(NodeName, num);
        				agNode.setNodeName(NodeName);
        				agNode.setnodeNum(num);
//        				gv.addln(""+num+"[shape = box]");
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
        	
        	if(SourceNode instanceof HostPrivilege){
        		Iterator<HostPrivilege> itpost = ((HostPrivilege)SourceNode).getPostActionNode().iterator();
            	while(itpost.hasNext()){
            		Node desNode = itpost.next();
            		String desName = desNode.getNodeName();
            		int desNum = desNode.getnodeNum();
            		
//            		newOrder = "" + sourceNum + "->" + desNum +";";
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
            		
//            		newOrder = "" + sourceNum + "->" + desNum +";";
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
        //System.out.println(gv.getDotSource());


//        String type = "png";
        // String type = "dot";
        // String type = "fig"; // open with xfig
        // String type = "pdf";
        // String type = "ps";
         String type = "svg"; // open with inkscape
        // String type = "png";
        // String type = "plain";
        File out = new File("/Users/hxy/Documents/workspace/defence/WebContent/static/images/systemmanager/outsimple." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
        
        
//        File outfile = new File("/Users/hxy/Documents/�������/graphviz��ͼ/out_explain.txt"); 
        
//        try{
//        	
//        	FileWriter fileWriter = new FileWriter(outfile);
//        	String s = new String();
//        	
//        	for(Map.Entry<String, Integer>entry : node_num.entrySet()){
//        		s = s + entry.getValue()+": "+ entry.getKey()+"\r\n";
//        	}
//        	
//        	fileWriter.write(s);  
//            fileWriter.close();
//        
//        }catch(IOException e){
//        	 e.printStackTrace(); 
//        }        
}
}

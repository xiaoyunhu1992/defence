package com.buaa.hxy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.LperEntity;
import com.buaa.hxy.pojo.RiskEntity;
import com.buaa.hxy.pojo.RperEntity;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.ServiceEntity;
import com.buaa.hxy.pojo.User;
import com.buaa.hxy.pojo.VulnEntity;
import com.buaa.hxy.service.IEntityService;
import com.buaa.hxy.service.IUserService;

import com.buaa.hxy.pojo.AttackGraphNodeType.StateNode;
import com.buaa.hxy.pojo.FClass.Service;
import com.buaa.hxy.pojo.fact.AttackRule;
import com.buaa.hxy.pojo.fact.InitialFact;
import com.buaa.hxy.pojo.subNodeType.Connection;
import com.buaa.hxy.pojo.subNodeType.DDOS;
import com.buaa.hxy.pojo.subNodeType.FileConfidentility;
import com.buaa.hxy.pojo.subNodeType.HostService;
import com.buaa.hxy.pojo.subNodeType.HostVulnerability;
import com.buaa.hxy.pojo.subNodeType.LocalPriEscaAttack;
import com.buaa.hxy.pojo.subNodeType.RemotePriEscaAttack;

import com.buaa.hxy.pojo.Graphoutput.GraphOutSimply;
import com.buaa.hxy.pojo.Graphoutput.GraphOutUnsimply;
import com.buaa.hxy.pojo.subNodeType.HostPrivilege;

import com.buaa.hxy.pojo.AttackGraphNodeType.Node;
import com.buaa.hxy.pojo.FClass.Attacker;
import com.buaa.hxy.pojo.FClass.Computer;
import com.buaa.hxy.pojo.fact.LocalPriEscaRule;
import com.buaa.hxy.pojo.fact.RemotePriEscaRule;


@Controller
@RequestMapping("/attackgraph")
public class AttackGraph {
	@Autowired
    @Qualifier("entityService")
	private IEntityService entityservice;
	static String targetName =  "Databaseserver";
	static int privilege =2;
	static int times = 0;
	static int sim;//0表示未化简，1表示化简后
	static ArrayList<Node> ag = new ArrayList<Node>();
	static ArrayList<Node> tempAG = new ArrayList<Node>();		

    @RequestMapping(value ="/generate",method = RequestMethod.POST)
	@ResponseBody
	public void generate(HttpServletRequest req) throws IOException, InterruptedException{
    	sim = Integer.valueOf(req.getParameter("simply").trim()).intValue();
    	ArrayList<Computer> computerList = initComputer();
    	ArrayList<Attacker> attackerList = initFactAttacker();
    	ArrayList<LocalPriEscaRule> lperlist = initLperlist();
    	ArrayList<RemotePriEscaRule> rperList = initRperlist();
    	HostPrivilege targetNode = AttackGraph.getTargetNode(computerList);//获取目标状态；				
		getAttackGraph(targetNode,attackerList,computerList,lperlist,rperList);
		String evidencehost = null;
		int evidencepri =0;
		List<SafeEventEntity> safeentity = this.entityservice.getSafeEventEntity();
		if(safeentity!=null){
			for(SafeEventEntity sf:safeentity){
				evidencehost=sf.gethostName();
				if(sf.getpriviledge().equals("root")){
					evidencepri=2;
				}else if(sf.getpriviledge().equals("user")){
					evidencepri=1;
				}
			}
		}
		if (sim == 1){
			GraphOutSimply newGraph = new GraphOutSimply();
			
			newGraph.start(ag,evidencepri,evidencehost);
		}
		else{
			GraphOutUnsimply newGraph = new GraphOutUnsimply();
			
			newGraph.start(ag,evidencepri,evidencehost);
		}
	}
    public ArrayList<RemotePriEscaRule> initRperlist(){
    	ArrayList<RemotePriEscaRule> rperlist  = new ArrayList<RemotePriEscaRule>();
    	List<RperEntity> rperentitylist = this.entityservice.getRperEntityList();
    	for (RperEntity rperentity : rperentitylist){
    		RemotePriEscaRule rper = new RemotePriEscaRule();
    		rper.getHs().getService().setServeceName(rperentity.getServiceName());
    		rper.getHs().getService().setPort(rperentity.getport());
    		rper.getHs().getService().setProtocal(rperentity.getprotocol());
    		rper.getHs().getService().setPrivilege(0);
    		rper.getHs().getService().setVersion(rperentity.getversion());
    		rper.getHv().setVulID(rperentity.getvulID());
    		rper.getHv().setServiceName(rperentity.getServiceName());
    		rper.getCo().setProt(rperentity.getport());
    		rper.getCo().setProtcal(rperentity.getprotocol());
    		rper.getHp().setPriviledge(Integer.valueOf(rperentity.getorp()).intValue());
    		rper.getPea().setVulID(rperentity.getvulID());
    		rper.getThp().setPriviledge(Integer.valueOf(rperentity.getarp()).intValue());
    		rperlist.add(rper);
    		
    	}
    	return rperlist;
    }
    public ArrayList<LocalPriEscaRule> initLperlist(){
    	ArrayList<LocalPriEscaRule> lperlist  = new ArrayList<LocalPriEscaRule>();
    	List<LperEntity> lperentitylist = this.entityservice.getLperEntityList();
    	for (LperEntity lperentity : lperentitylist){
    		LocalPriEscaRule lper = new LocalPriEscaRule();
    		lper.getHs().getService().setServeceName(lperentity.getServiceName());
    		lper.getHs().getService().setPort(lperentity.getport());
    		lper.getHs().getService().setProtocal(lperentity.getprotocol());
    		lper.getHs().getService().setPrivilege(0);
    		lper.getHs().getService().setVersion(lperentity.getversion());
    		lper.getHv().setVulID(lperentity.getvulID());
    		lper.getHv().setServiceName(lperentity.getServiceName());
    		lper.getHp().setPriviledge(Integer.valueOf(lperentity.getorp()).intValue());
    		lper.getPea().setVulID(lperentity.getvulID());
    		lper.getThp().setPriviledge(Integer.valueOf(lperentity.getarp()).intValue());
    		lperlist.add(lper);
    		
    	}
    	return lperlist;
    }
    public ArrayList<Computer> initComputer(){
    	ArrayList<Computer> c = new ArrayList<Computer>();
    	List<HostEntity> hostlist = this.entityservice.getHostList();
    	for(HostEntity host:hostlist){
//    		System.out.print("host "+host.gethostName());
    		Computer com = new Computer();
    		com.setComputerName(host.gethostName());
    		com.setIPAddress(host.getIP());
    		com.setMask(host.getMASK());
    		ArrayList<Service> servlist = new ArrayList<Service>();
    		
    		List<ServiceEntity> serventitylist = this.entityservice.getServiceEntityList(host.gethostName());
    		for(ServiceEntity serventity:serventitylist){
    			Service serv = new Service();
//    			System.out.println("service "+serventity.getServiceName());
    			serv.setPort(serventity.getport());
    			serv.setProtocal(serventity.getprotocol());
    			serv.setServeceName(serventity.getServiceName());
    			serv.setVersion(serventity.getVersion());
    			serv.setPrivilege(0);
    			List<VulnEntity> vulnentityList = this.entityservice.getvulEntityList(serventity.getServiceName());
    			for(VulnEntity vulnentity :vulnentityList){
//    				System.out.print("vuln "+vulnentity.getVulID());
    				if (vulnentity.gethostName().equals(serventity.gethostName())){
    					serv.setVul(vulnentity.getVulID());
    				}
    			}
    			servlist.add(serv);
    		}
    		com.setServiceList(servlist);
    		c.add(com);
    	}
    	for(Computer com :c){
//    		System.out.println("source "+com.getComputerName());
//    		ArrayList<Connection> connectionlist = new ArrayList<Connection>();
    		List<ConnEntity> connentitylist = this.entityservice.getConnEntityList(com.getComputerName());
    		for (ConnEntity connentity:connentitylist){
//    			System.out.println("connection "+connentity.getdesName());
    			Connection conn = new Connection();
    			conn.setProt(connentity.getport());
    			conn.setProtcal(connentity.getprotocol());
    			conn.setSource(com);
    			for(Computer des:c){
    				if(des.getComputerName().equals(connentity.getdesName())){
    					conn.setDestiny(des);
    				}
    				
    			}
//    			connectionlist.add(conn);
    			com.getConnectionList().add(conn);
    		}
//    		com.setConnectionList(connectionlist);
    		
    	}
//    	System.out.println("return c");
    	return c;
    }
    public  ArrayList<Attacker> initFactAttacker(){
    	ArrayList<Attacker> a = new ArrayList<Attacker>();
		List<AttackerEntity> attackerlist = this.entityservice.getAttackerList();
		for(AttackerEntity item : attackerlist){
//			System.out.println(item.gethostName());
			Computer com = new Computer();
			com.setComputerName(item.gethostName());
			HostEntity host = this.entityservice.getComputer(item.gethostName());
			com.setIPAddress(host.getIP());
			com.setMask(host.getMASK());
			Attacker attacker = new Attacker();
			attacker.setAttackerComputer(com);
			String priviledge = item.getpriviledge();
			if (priviledge.equals("root")){
				attacker.setPrivilege(2);
			}
			else if (priviledge.equals("user")){
				attacker.setPrivilege(1);
			}
			else if (priviledge.equals("guest")){
				attacker.setPrivilege(0);

			}
//			System.out.print(item.gethostName());
			a.add(attacker);
		}
		return a;
    }
    public boolean getAttackGraph(Node targetNode,ArrayList<Attacker> attackerList,ArrayList<Computer> computerList,ArrayList<LocalPriEscaRule> lperlist,ArrayList<RemotePriEscaRule> rperList ){
	
    	//表示这次调用是否完成攻击
    	boolean existAccess = false;
    	boolean getAccess = false;
	
	
    	if(targetNode instanceof HostPrivilege){//判断谓词是不是一个提权攻击；
    		//判断寻路是不是结束
    		if(judgeHpInList((HostPrivilege)targetNode,attackerList)){//如果在里面则寻径结束
    			ag.addAll(tempAG);
    			getAccess = true;
    			return getAccess;
    		}else{
			
			Computer destiny = ((HostPrivilege)targetNode).getDestiny();
			ArrayList<Connection> rCon = getRCon(computerList,destiny);//获取和目标节点有连接关系，并且是以目标节点为目的连接节点的连接关系
			ArrayList<String> vulList = getVulList(destiny);//获取漏洞列表；
			
			if(rCon.isEmpty()||vulList.isEmpty()){//如果该目标节点没有连接或者没有漏洞
				
				return false;
			}else{				
				Iterator vulListIt = vulList.iterator();
				while(vulListIt.hasNext()){
					String vulID = (String)vulListIt.next();
					
					
					AttackRule ar = getAttackRule(vulID,((HostPrivilege)targetNode).getPriviledge(),rperList,lperlist);
					
					if(ar == null){
						getAccess = false;
						
					}
					else if(ar instanceof RemotePriEscaRule){//判断是不是一个远程攻击
						if(((RemotePriEscaRule)ar).getThp().getPriviledge() < ((HostPrivilege)targetNode).getPriviledge() ){
							continue;
						}
						
						Iterator conListIt = getServConList(rCon,(RemotePriEscaRule)ar).iterator();
						
						//获取和规则匹配的连接关系
						//获取和漏洞匹配的连接关系，因为一个主机上可能存在多个服务，一个服务可能存在多个漏洞
						while(conListIt.hasNext()){
							//已经获取了和规则匹配的连接关系，需要将这个关系里面的前件，动作和后件实例化
							Connection con = (Connection) conListIt.next();
							
							/***
							 * test
							 */
//							if(con.getDestiny().getComputerName()=="User_3"){
//								System.out.println(con.getSource().getComputerName());
//							}
							
							//开始对节点进行实例化；
							HostPrivilege hp = new HostPrivilege();
							HostService hs = new HostService();
							HostVulnerability hv = new HostVulnerability();
							Connection c = new Connection();
							RemotePriEscaAttack rpea = new RemotePriEscaAttack();
							HostPrivilege target = new HostPrivilege();
							hs.setService(((RemotePriEscaRule)ar).getHs().getService());
							hs.setDestiny(con.getDestiny());//目标主机上有什么服务
							hv.setServiceName(((RemotePriEscaRule)ar).getHs().getService().getServeceName());
							hv.setVulID(vulID);//
							hv.setDestiny(con.getDestiny());//主机上有什么漏洞
							
							c.setDestiny(con.getDestiny());
							c.setSource(con.getSource());
							c.setProt(con.getProt());
							c.setProtcal(con.getprotocal());//主机连接关系
							
							Attacker at = new Attacker();
							at.setAttackerComputer(con.getSource());
							at.setPrivilege(((RemotePriEscaRule)ar).getHp().getPriviledge());
							
							hp.setAttacker(at);
							hp.setDestiny(con.getSource());
							hp.setPriviledge(((RemotePriEscaRule)ar).getHp().getPriviledge());//攻击者在源主机上的权限
							
							rpea.setAttacker(at);
							rpea.setDestiny(con.getDestiny());
							rpea.setSource(con.getSource());
							rpea.setVulID(vulID);//攻击动作
							
							//本次的target， 新建一个实例
							target.setAttacker(at);
							target.setDestiny(destiny);
							target.setPriviledge(((HostPrivilege)targetNode).getPriviledge());
							target.getPreActionNode().add(rpea);
															
							rpea.getPostStateNode().add(target);//目标动作的前件状态
							rpea.getPreStateNode().add(hs);
							rpea.getPreStateNode().add(hv);
							rpea.getPreStateNode().add(c);
							rpea.getPreStateNode().add(hp);//目标动作的后件状态4个
							
							hs.getPostActionNode().add(rpea);
							hv.getPostActionNode().add(rpea);
							c.getPostActionNode().add(rpea);
							hp.getPostActionNode().add(rpea);//前件状态的后件动作
							
							//将节点加入到临时图中，并对临时图中的节点进行合并；先要在加入过程中对临时图里面的节点
							//进行识别，看看节点是不是一样的状态节点，因为在整个过程中都是通过NEW的方式新建对象，
							//所以对象之间不存在引用一样的情况，所以需要将对象进行合并。判断两个hs或者其他状态/动作节点对象是否一样。
							ArrayList<Node> inList = new ArrayList<Node>();
							inList.add(hs);
							inList.add(hv);
							inList.add(c);
							inList.add(hp);//这个hp是非终止态
							inList.add(rpea);
							inList.add(target);

							boolean HpintempAGFlag = false;
							HpintempAGFlag = returnHpintempAGFlag(hp);//判断是否有环
							//上述6个谓词中，除了hp其他谓词都已经是确定在事实库里面了。
							//下面确定hp是不是在AttackerList里面，如果不在则
							//以hp为新的结果进行寻找，如果在则将tempAG加入到AG中。
							if(HpintempAGFlag == false){//无环
																	
								tempAG.addAll(inList);
								getAccess = getAttackGraph(hp,attackerList,computerList,lperlist,rperList);
								existAccess = getAccess || existAccess;
								
								if(getAccess == false){//
									ArrayList<Node> inListdel = new ArrayList<Node>();
									inListdel.add(hs);
									inListdel.add(hv);
									inListdel.add(c);
									inListdel.add(hp);//这个hp是非终止态
									inListdel.add(rpea);
									inListdel.add(target);
									
									tempAG.removeAll(inListdel);
								}
								else{
									ArrayList<Node> inListdel = new ArrayList<Node>();
									inListdel.add(hs);
									inListdel.add(hv);
									inListdel.add(c);
									inListdel.add(hp);//这个hp是非终止态
									inListdel.add(rpea);
									inListdel.add(target);
									
									tempAG.removeAll(inListdel);
								}
							}
						}
					}else if (ar instanceof LocalPriEscaRule){
						HostPrivilege hp = new HostPrivilege();
						HostService hs = new HostService();
						HostVulnerability hv = new HostVulnerability();
						LocalPriEscaAttack lpea = new LocalPriEscaAttack();
						HostPrivilege target = new HostPrivilege();
						
						hs.setService(((LocalPriEscaRule)ar).getHs().getService());
						hs.setDestiny(((HostPrivilege)targetNode).getDestiny());//目标主机上有什么服务
						
						hv.setServiceName(((LocalPriEscaRule)ar).getHs().getService().getServeceName());
						hv.setVulID(vulID);//
						hv.setDestiny(((HostPrivilege)targetNode).getDestiny());//主机上有什么漏洞
						
						Attacker at = new Attacker();
						at.setAttackerComputer(((HostPrivilege)targetNode).getAttacker().getAttackerComputer());
						at.setPrivilege(((LocalPriEscaRule)ar).getHp().getPriviledge());
						
						hp.setAttacker(at);
						hp.setDestiny(((HostPrivilege)targetNode).getDestiny());
						hp.setPriviledge(((LocalPriEscaRule)ar).getHp().getPriviledge());//攻击者在源主机上的权限
						
						lpea.setAttacker(at);
						lpea.setDestiny(((HostPrivilege)targetNode).getDestiny());
						lpea.setVulID(vulID);//攻击动作
						
						//本次的target， 新建一个实例
						target.setAttacker(at);
						target.setDestiny(destiny);
						target.setPriviledge(((HostPrivilege)targetNode).getPriviledge());
						target.getPreActionNode().add(lpea);
													
						lpea.getPostStateNode().add(target);//目标动作的前件状态
						lpea.getPreStateNode().add(hs);
						lpea.getPreStateNode().add(hv);
						lpea.getPreStateNode().add(hp);//目标动作的后件状态3个
						
						hs.getPostActionNode().add(lpea);
						hv.getPostActionNode().add(lpea);
						hp.getPostActionNode().add(lpea);//前件状态的后件动作
							
						//将节点加入到临时图中，并对临时图中的节点进行合并；先要在加入过程中对临时图里面的节点
						//进行识别，看看节点是不是一样的状态节点，因为在整个过程中都是通过NEW的方式新建对象，
						//所以对象之间不存在引用一样的情况，所以需要将对象进行合并。判断两个hs或者其他状态/动作节点对象是否一样。
						ArrayList<Node> inList = new ArrayList<Node>();
						inList.add(hs);
						inList.add(hv);
						inList.add(hp);//这个hp是非终止态
						inList.add(lpea);
						inList.add(target);
				
						boolean HpintempAGFlag = false;
						HpintempAGFlag = returnHpintempAGFlag(hp);//判断是否有环以及是否多个0day漏洞
							
						//上述6个谓词中，除了hp其他谓词都已经是确定在事实库里面了。
						//下面确定hp是不是在AttackerList里面，如果不在则
						//以hp为新的结果进行寻找，如果在则将tempAG加入到AG中。
								
						if(HpintempAGFlag == false){//无环
							tempAG.addAll(inList);
							getAccess = getAttackGraph(hp,attackerList,computerList,lperlist,rperList);
							existAccess = getAccess || existAccess;
								
							if(getAccess == false){//
									
								ArrayList<Node> inListdel = new ArrayList<Node>();
								inListdel.add(hs);
								inListdel.add(hv);
								inListdel.add(hp);//这个hp是非终止态
								inListdel.add(lpea);
								inListdel.add(target);
									
								tempAG.removeAll(inListdel);
							}
							else{//
								
								ArrayList<Node> inListdel = new ArrayList<Node>();
								inListdel.add(hs);
								inListdel.add(hv);
								inListdel.add(hp);//这个hp是非终止态
								inListdel.add(lpea);
								inListdel.add(target);
									
								tempAG.removeAll(inListdel);
							}
						}
					}	
				}
			}
		}
	}else if (targetNode instanceof DDOS){//对于DDOS

	} else if(targetNode instanceof FileConfidentility){//对于信息泄露

	}else{

	}

	return existAccess;
	
}

//判断获取的谓词是否在已知事实里面。
static boolean returnHpintempAGFlag(HostPrivilege hp){
//	cycle judge
	boolean HpintempAGFlag = false;
	Iterator<Node> tempit = tempAG.iterator();//放到函数里面。。。。。。。。。。。。。。。。。
	
	while(tempit.hasNext())
	{
		Node n = (Node) tempit.next();
		if(n instanceof HostPrivilege){
			String computerName = ((HostPrivilege)n).getDestiny().getComputerName();
			int privilege = ((HostPrivilege)n).getPriviledge();
			
			String computerNameHp = hp.getDestiny().getComputerName();
			int privilegeHp = hp.getPriviledge();
			
			if(computerName.equals(computerNameHp)
					&&privilege == privilegeHp){
				HpintempAGFlag = true;
				return HpintempAGFlag;
			}
		}
	}
	
	return HpintempAGFlag;
}

static boolean judgeHpInList(HostPrivilege hp, ArrayList <Attacker> attackerList){
	//Attacker attack = hp.getAttacker();
	String hpDestinyName = hp.getDestiny().getComputerName();
	int p = hp.getPriviledge();
	boolean b = false;
	Iterator<Attacker> it = attackerList.iterator();
	while (it.hasNext()){
		Attacker at = (Attacker) it.next();
		
		if(at.getPrivilege()>= p
				&& at.getAttackerComputer().getComputerName().equals(hpDestinyName)){
			b = true;
			break;
		}
	}
	return b;
}

//获取目标 
static HostPrivilege getTargetNode(ArrayList<Computer> computerList) {
	Iterator<Computer> it = computerList.iterator();
	String computerName = null;
	HostPrivilege t = new HostPrivilege();
	while (it.hasNext()) {

		Computer computer = (Computer) it.next();
		computerName = computer.getComputerName();
//		System.out.println(computerName);
		if (computerName.equals(targetName)) {
			t.setDestiny(computer);
			t.setPriviledge(privilege);

			Attacker at = new Attacker();
			at.setAttackerComputer(computer);
			at.setPrivilege(privilege);
			
			t.setAttacker(at);
			break;
		}
	}
	return t;
}

static ArrayList<String> getVulList(Computer destiny){
	Iterator computerIt = destiny.getServiceList().iterator();
	ArrayList<String> vulList = new ArrayList<String>();
	while (computerIt.hasNext()){
		Service s = (Service)computerIt.next();			
		vulList.add(s.getVul());
	}
	return vulList;
}

static AttackRule getAttackRule(String vulID,int priviledge, ArrayList<RemotePriEscaRule> rperList, 
		ArrayList<LocalPriEscaRule> lperList){
	Iterator rperListIt = rperList.iterator();
	Iterator lperListIt = lperList.iterator();

	AttackRule ar = new AttackRule();
	while(rperListIt.hasNext()){
		ar = (RemotePriEscaRule) rperListIt.next();
		if(vulID.equals(((RemotePriEscaRule)ar).getHv().getVulID()) && priviledge <= (((RemotePriEscaRule)ar).getThp().getPriviledge())){
			
			return ar;
		}
	}
	while(lperListIt.hasNext()){
		ar = (LocalPriEscaRule) lperListIt.next();
		if(vulID.equals(((LocalPriEscaRule)ar).getHv().getVulID()) && priviledge == (((LocalPriEscaRule)ar).getThp().getPriviledge())){
			return ar;
		}
		
	}	
	return null;
}

static ArrayList<Connection> getServConList(ArrayList <Connection> connectionList,RemotePriEscaRule rper){
	String port = rper.getCo().getProt();//漏洞，以及端口和协议的连接关系才是必要条件
	String protocal = rper.getCo().getprotocal();
	Iterator it = connectionList.iterator();
	ArrayList<Connection> rconList = new ArrayList<Connection>();
	while(it.hasNext()){
		Connection con = (Connection) it.next();
		if(port.equals(con.getProt())&&protocal.equals(con.getprotocal())){
			rconList.add(con);
		}
	}
	return rconList;
}

static ArrayList<Connection> getRCon(ArrayList<Computer> computerList,Computer destiny){
	Iterator it = computerList.iterator();
//	System.out.println("destiny:"+destiny.getComputerName()+'\n');
	ArrayList<Connection> rco = new ArrayList<Connection>();
	while(it.hasNext()){
		Computer source = (Computer) it.next();
//		System.out.print("getRconSource:"+source.getComputerName()+'\n');
		ArrayList<Connection> coL = source.getConnectionList();
//		System.out.println(coL.size());
		Iterator itc = coL.iterator();
		while(itc.hasNext()){
			Connection co = (Connection) itc.next();
//			System.out.print("getRconDes:"+co.getDestiny().getComputerName()+'\n');

			if(co.getDestiny().getComputerName().equals(destiny.getComputerName())&&
			co.getDestiny().getIPAddress().equals(destiny.getIPAddress())&&
			co.getDestiny().getMask().equals(destiny.getMask())){
//				System.out.println(co.getSource().getComputerName()	);
				rco.add(co);
			}
		}
	}
	
	return rco;

}
//将后一个列表中与前一列表相同的节点删除掉
static ArrayList<Node> deleteList(ArrayList<Node> inListdel, ArrayList<Node> compListdel){

	Iterator it = inListdel.iterator();
	Iterator itt = compListdel.iterator();
	
	while(it.hasNext()){
		Node n = (Node) it.next();
		int i = 0;				
		while(itt.hasNext()){
			Node ncom = (Node) itt.next();
			if(n.getClass()==ncom.getClass()){//是同一个类
				if(n instanceof HostService){
					String computerNameN = ((HostService)n).getDestiny().getComputerName();
					String serviceNameN = ((HostService)n).getService().getServeceName();
					String versionN = ((HostService)n).getService().getVersion();
					int privilegeN = ((HostService)n).getService().getPrivilege();
					String portN = ((HostService)n).getService().getPort();
					
					String computerNameNcom = ((HostService)ncom).getDestiny().getComputerName();							String serviceNameNcom = ((HostService)ncom).getService().getServeceName();
					String versionNcom = ((HostService)ncom).getService().getVersion();
					int privilegeNcom = ((HostService)ncom).getService().getPrivilege();
					String portNcom = ((HostService)ncom).getService().getPort();
						
					if(computerNameN.equals(computerNameNcom)
							&&serviceNameN.equals(serviceNameNcom)
							&&versionN.equals(versionNcom)
							&&portN.equals(portNcom)
							&&privilegeN==privilegeNcom){
						
						
						((HostService)ncom).getPostActionNode().removeAll(((HostService)n).getPostActionNode());
						//删掉n节点的前续和后续中由于ncom引入的	Merge的反操作
						((HostService)ncom).getPreActionNode().removeAll(((HostService)n).getPreActionNode());
						
						//如果删掉前续后续后为空，则把该节点删掉
						if(((HostService)ncom).getPostActionNode().isEmpty() && ((HostService)ncom).getPreActionNode().isEmpty()){
							itt.remove();
						}
						
						break;
					}
				}else if(n instanceof HostVulnerability){
					String computerNameN = ((HostVulnerability)n).getDestiny().getComputerName();
					String serviceNameN = ((HostVulnerability)n).getServiceName();
					String vulIDN = ((HostVulnerability)n).getVulID();
						
					String computerNameNcom = ((HostVulnerability)ncom).getDestiny().getComputerName();
					String serviceNameNcom = ((HostVulnerability)ncom).getServiceName();
					String vulIDNcom = ((HostVulnerability)ncom).getVulID();
						
					if(computerNameN.equals(computerNameNcom)
							&&serviceNameN.equals(serviceNameNcom)
							&&vulIDNcom.equals(vulIDNcom)){
						
						((HostVulnerability)ncom).getPostActionNode().removeAll(((HostVulnerability)n).getPostActionNode());
						//删掉n节点的前续和后续中由于ncom引入的	Merge的反操作
						((HostVulnerability)ncom).getPreActionNode().removeAll(((HostVulnerability)n).getPreActionNode());
						
						//如果删掉前续后续后为空，则把该节点删掉
						if( ((HostVulnerability)ncom).getPostActionNode().isEmpty() && ((HostVulnerability)ncom).getPreActionNode().isEmpty()){
							itt.remove();
						}
						
						break;
					}
						
				}else if(n instanceof Connection){
					String sourceName = ((Connection)n).getSource().getComputerName();
					String destinyName = ((Connection)n).getDestiny().getComputerName();
					String port  = ((Connection)n).getProt();
					String protocal = ((Connection)n).getprotocal();
						
					String sourceNameCom = ((Connection)ncom).getSource().getComputerName();
					String destinyNameCom = ((Connection)ncom).getDestiny().getComputerName();
					String portCom = ((Connection)ncom).getProt();
					String protocalCom = ((Connection)ncom).getprotocal();
						
					if(sourceName.equals(sourceNameCom)
							&&destinyName.equals(destinyNameCom)
							&&port.equals(portCom)
							&&protocal.equals(protocalCom)){
						((Connection)ncom).getPostActionNode().removeAll(((Connection)n).getPostActionNode());
						
						((Connection)ncom).getPreActionNode().removeAll(((Connection)n).getPreActionNode());
						if(((Connection)ncom).getPreActionNode().isEmpty() && ((Connection)ncom).getPreActionNode().isEmpty()){
							itt.remove();
						}
						
						break;
					}
						
				}else if(n instanceof HostPrivilege){
					String attackerName = ((HostPrivilege)n).getAttacker().getAttackerComputer().getComputerName();
					String computerName = ((HostPrivilege)n).getDestiny().getComputerName();
					int privilege = ((HostPrivilege)n).getPriviledge();
						
					String attackerNameCom = ((HostPrivilege)ncom).getAttacker().getAttackerComputer().getComputerName();
					String computerNameCom = ((HostPrivilege)ncom).getDestiny().getComputerName();
					int privilegeCom = ((HostPrivilege)ncom).getPriviledge();
						
					if(attackerName != null && attackerName.equals(attackerNameCom)
							&&computerName.equals(computerNameCom)
							&&privilege==privilegeCom){
						((HostPrivilege)ncom).getPostActionNode().removeAll(((HostPrivilege)n).getPostActionNode());
						//n的所有后继节点加入到ncom中
						((HostPrivilege)ncom).getPreActionNode().removeAll(((HostPrivilege)n).getPreActionNode());
						if(((HostPrivilege)ncom).getPostActionNode().isEmpty() && ((HostPrivilege)ncom).getPreActionNode().isEmpty()){
							itt.remove();
						}
						break;
					}
				}else if(n instanceof RemotePriEscaAttack){
					String attackerName = ((RemotePriEscaAttack)n).getAttacker().getAttackerComputer().getComputerName();
					String sourceName = ((RemotePriEscaAttack)n).getSource().getComputerName();
					String destinyName = ((RemotePriEscaAttack)n).getDestiny().getComputerName();
					String vulID = ((RemotePriEscaAttack)n).getVulID();
						
					String attackerNameCom = ((RemotePriEscaAttack)ncom).getAttacker().getAttackerComputer().getComputerName();
					String sourceNameCom = ((RemotePriEscaAttack)ncom).getSource().getComputerName();
					String destinyNameCom = ((RemotePriEscaAttack)ncom).getDestiny().getComputerName();
					String vulIDCom = ((RemotePriEscaAttack)ncom).getVulID();
						
					if(attackerName.equals(attackerNameCom)
							&&sourceName.equals(sourceNameCom)
							&&destinyName.equals(destinyNameCom)
							&&vulID.equals(vulIDCom)){
						
						((RemotePriEscaAttack)ncom).getPostStateNode().removeAll(((RemotePriEscaAttack)n).getPostStateNode());
						//n的所有后继节点加入到ncom中
						((RemotePriEscaAttack)ncom).getPreStateNode().removeAll(((RemotePriEscaAttack)n).getPreStateNode());
						if(((RemotePriEscaAttack)ncom).getPostStateNode().isEmpty() && ((RemotePriEscaAttack)ncom).getPreStateNode().isEmpty()){
							itt.remove();
						}
						
						break;

					}
				}//从这里eles if 继续添加其他谓词
			}
			i++;
		}
	}
	
	inListdel.clear();
	return compListdel;
}
//将两个列表中相同的节点的前置和后继节点合并
static ArrayList<Node> mergeList(ArrayList<Node> inList, ArrayList<Node> compList){
	Iterator it = inList.iterator();
	Iterator itt = compList.iterator();
	
	if(compList.isEmpty()){
		compList.addAll(inList);
	}else{
		while(it.hasNext()){
			Node n = (Node) it.next();
			int i = 0;
			while(itt.hasNext()){
				Node ncom = (Node) itt.next();
				if(n.getClass()==ncom.getClass()){//是同一个类
					if(n instanceof HostService){
						String computerNameN = ((HostService)n).getDestiny().getComputerName();
						String serviceNameN = ((HostService)n).getService().getServeceName();
						String versionN = ((HostService)n).getService().getVersion();
						int privilegeN = ((HostService)n).getService().getPrivilege();
						String portN = ((HostService)n).getService().getPort();
						
						String computerNameNcom = ((HostService)ncom).getDestiny().getComputerName();
						String serviceNameNcom = ((HostService)ncom).getService().getServeceName();
						String versionNcom = ((HostService)ncom).getService().getVersion();
						int privilegeNcom = ((HostService)ncom).getService().getPrivilege();
						String portNcom = ((HostService)ncom).getService().getPort();
						
						if(computerNameN.equals(computerNameNcom)
								&&serviceNameN.equals(serviceNameNcom)
								&&versionN.equals(versionNcom)
								&&portN.equals(portNcom)
								&&privilegeN==privilegeNcom){
							
							Iterator<Node> itPost = ((HostService)n).getPostActionNode().iterator();
							while(itPost.hasNext()){
								Node tempPostNode = itPost.next();
								if (((HostService)ncom).getPostActionNode().contains(tempPostNode)){
									;
								}else{
									((HostService)ncom).getPostActionNode().add(tempPostNode);
								}
							}
							
							Iterator<Node> itPre = ((HostService)n).getPreActionNode().iterator();
							while(itPre.hasNext()){
								Node tempPreNode = itPre.next();
								if (((HostService)ncom).getPreActionNode().contains(tempPreNode)){
									;
								}else{
									((HostService)ncom).getPreActionNode().add(tempPreNode);
								}
							}
							
							it.remove();
							break;
						}
					}else if(n instanceof HostVulnerability){
						String computerNameN = ((HostVulnerability)n).getDestiny().getComputerName();
						String serviceNameN = ((HostVulnerability)n).getServiceName();
						String vulIDN = ((HostVulnerability)n).getVulID();
						
						String computerNameNcom = ((HostVulnerability)ncom).getDestiny().getComputerName();
						String serviceNameNcom = ((HostVulnerability)ncom).getServiceName();
						String vulIDNcom = ((HostVulnerability)ncom).getVulID();
						
						if(computerNameN.equals(computerNameNcom)
								&&serviceNameN.equals(serviceNameNcom)
								&&vulIDNcom.equals(vulIDNcom)){
							
							Iterator<Node> itPost = ((HostVulnerability)n).getPostActionNode().iterator();
							while(itPost.hasNext()){
								Node tempPostNode = itPost.next();
								if (((HostVulnerability)ncom).getPostActionNode().contains(tempPostNode)){
									;
								}else{
									((HostVulnerability)ncom).getPostActionNode().add(tempPostNode);
								}
							}
							
							Iterator<Node> itPre = ((HostVulnerability)n).getPreActionNode().iterator();
							while(itPre.hasNext()){
								Node tempPreNode = itPre.next();
								if (((HostVulnerability)ncom).getPreActionNode().contains(tempPreNode)){
									;
								}else{
									((HostVulnerability)ncom).getPreActionNode().add(tempPreNode);
								}
							}
							
							it.remove();
							break;
						}
						
					}else if(n instanceof Connection){
						String sourceName = ((Connection)n).getSource().getComputerName();
						String destinyName = ((Connection)n).getDestiny().getComputerName();
						String port  = ((Connection)n).getProt();
						String protocal = ((Connection)n).getprotocal();
						
						String sourceNameCom = ((Connection)ncom).getSource().getComputerName();
						String destinyNameCom = ((Connection)ncom).getDestiny().getComputerName();
						String portCom = ((Connection)ncom).getProt();
						String protocalCom = ((Connection)ncom).getprotocal();
						
						if(sourceName.equals(sourceNameCom)
								&&destinyName.equals(destinyNameCom)
								&&port.equals(portCom)
								&&protocal.equals(protocalCom)){
							
							Iterator<Node> itPost = ((Connection)n).getPostActionNode().iterator();
							while(itPost.hasNext()){
								Node tempPostNode = itPost.next();
								if (((Connection)ncom).getPostActionNode().contains(tempPostNode)){
									;
								}else{
									((Connection)ncom).getPostActionNode().add(tempPostNode);
								}
							}
							
							Iterator<Node> itPre = ((Connection)n).getPreActionNode().iterator();
							while(itPre.hasNext()){
								Node tempPreNode = itPre.next();
								if (((Connection)ncom).getPreActionNode().contains(tempPreNode)){
									;
								}else{
									((Connection)ncom).getPreActionNode().add(tempPreNode);
								}
							}
							
							//((Connection)ncom).getPostActionNode().addAll(((Connection)n).getPostActionNode());
							//n的所有后继节点加入到ncom中
							//((Connection)ncom).getPreActionNode().addAll(((Connection)n).getPreActionNode());
							it.remove();
							break;
						}
						
					}else if(n instanceof HostPrivilege){
						String attackerName = ((HostPrivilege)n).getAttacker().getAttackerComputer().getComputerName();
						String computerName = ((HostPrivilege)n).getDestiny().getComputerName();
						int privilege = ((HostPrivilege)n).getPriviledge();
						
						String attackerNameCom = ((HostPrivilege)ncom).getAttacker().getAttackerComputer().getComputerName();
						String computerNameCom = ((HostPrivilege)ncom).getDestiny().getComputerName();
						int privilegeCom = ((HostPrivilege)ncom).getPriviledge();
						
						if(attackerName != null && attackerName.equals(attackerNameCom)
								&&computerName.equals(computerNameCom)
								&&privilege==privilegeCom){
							
							Iterator<Node> itPost = ((HostPrivilege)n).getPostActionNode().iterator();
							while(itPost.hasNext()){
								Node tempPostNode = itPost.next();
								if (((HostPrivilege)ncom).getPostActionNode().contains(tempPostNode)){
									;
								}else{
									((HostPrivilege)ncom).getPostActionNode().add(tempPostNode);
								}
							}
							
							Iterator<Node> itPre = ((HostPrivilege)n).getPreActionNode().iterator();
							while(itPre.hasNext()){
								Node tempPreNode = itPre.next();
								if (((HostPrivilege)ncom).getPreActionNode().contains(tempPreNode)){
									;
								}else{
									((HostPrivilege)ncom).getPreActionNode().add(tempPreNode);
								}
							}
							
							it.remove();
							break;
						}
					}else if(n instanceof RemotePriEscaAttack){
						String attackerName = ((RemotePriEscaAttack)n).getAttacker().getAttackerComputer().getComputerName();
						String sourceName = ((RemotePriEscaAttack)n).getSource().getComputerName();
						String destinyName = ((RemotePriEscaAttack)n).getDestiny().getComputerName();
						String vulID = ((RemotePriEscaAttack)n).getVulID();
						
						String attackerNameCom = ((RemotePriEscaAttack)ncom).getAttacker().getAttackerComputer().getComputerName();
						String sourceNameCom = ((RemotePriEscaAttack)ncom).getSource().getComputerName();
						String destinyNameCom = ((RemotePriEscaAttack)ncom).getDestiny().getComputerName();
						String vulIDCom = ((RemotePriEscaAttack)ncom).getVulID();
						
						if(attackerName.equals(attackerNameCom)
								&&sourceName.equals(sourceNameCom)
								&&destinyName.equals(destinyNameCom)
								&&vulID.equals(vulIDCom)){
							
							Iterator<StateNode> itPost = ((RemotePriEscaAttack)n).getPostStateNode().iterator();
							while(itPost.hasNext()){
								Node tempPostNode = itPost.next();
								if (((RemotePriEscaAttack)ncom).getPostStateNode().contains(tempPostNode)){
									;
								}else{
									((RemotePriEscaAttack)ncom).getPostStateNode().add((StateNode) tempPostNode);
								}
							}
							
							Iterator<StateNode> itPre = ((RemotePriEscaAttack)n).getPreStateNode().iterator();
							while(itPre.hasNext()){
								Node tempPreNode = itPre.next();
								if (((RemotePriEscaAttack)ncom).getPreStateNode().contains(tempPreNode)){
									;
								}else{
									((RemotePriEscaAttack)ncom).getPreStateNode().add((StateNode) tempPreNode);
								}
							}
							
							it.remove();
							break;

						}
					}//从这里eles if 继续添加其他谓词
				}
			}
		i++;
		}
		compList.addAll(inList);
	}

	inList.clear();
	return compList;
}
  
	@RequestMapping(value ="/initrisk",method = RequestMethod.POST)
	@ResponseBody
	public Map initrisk(HttpServletRequest req) throws IOException, InterruptedException{
		List<RiskEntity> risk =  this.entityservice.getRiskList();
		Map map=new HashMap();
		ArrayList<String> name = new ArrayList<String>();
		name.add("Status_Malicious_root");
		name.add("Status_DNSserver_root");
		name.add("Status_User_2_root");
		name.add("Status_User_3_root");
		name.add("Status_User_1_root");
		name.add("Status_Webserver_user");
		name.add("Status_SMTPserver_root");
		name.add("Status_Webserver_root");
		name.add("Status_FTPserver_1_root");
		name.add("Status_Databaseserver_root");
		float riskvalue = 0;
		ArrayList<Float> pro = new ArrayList<Float>();
		for (RiskEntity item:risk){
			pro.add(item.getone());
			pro.add(item.gettwo());
			pro.add(item.getthree());
			pro.add(item.getfour());
			pro.add(item.getfive());
			pro.add(item.getsix());
			pro.add(item.getseven());
			pro.add(item.geteight());
			pro.add(item.getnine());
			pro.add(item.getten());
			riskvalue=item.getrisk();
		}
		
		map.put("pro", pro);
		map.put("name", name);
		map.put("riskvalue", riskvalue);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(map);
		return map;
	}
    
    
    
//    @RequestMapping(value = "/showUser", method = RequestMethod.GET)  
//    public String showUser(HttpServletRequest request, HttpServletResponse response){  
//        int userId = Integer.parseInt(request.getParameter("id"));  
//        User user = this.userService.getUserById(String.valueOf(userId)); 
//       // String user = "wcj";
//        System.out.println(user.getUserName());
//        return "showUser";  
//    }  
}  

package com.buaa.hxy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.buaa.hxy.dao.IAttackerEntityDao;
import com.buaa.hxy.dao.IConnEntityDao;
import com.buaa.hxy.dao.IHostEntityDao;
import com.buaa.hxy.dao.ILperEntityDao;
import com.buaa.hxy.dao.IRperEntityDao;
import com.buaa.hxy.dao.ISafeEventEntityDao;
import com.buaa.hxy.dao.ISchemeDao;
import com.buaa.hxy.dao.IServiceEntityDao;
import com.buaa.hxy.dao.IUserDao;
import com.buaa.hxy.dao.IVulnEntityDao;
import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.LperEntity;
import com.buaa.hxy.pojo.RperEntity;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.Scheme;
import com.buaa.hxy.pojo.ServiceEntity;
import com.buaa.hxy.pojo.VulnEntity;
import com.buaa.hxy.service.IEntityService;
import com.buaa.hxy.service.ISchemeService;
import com.buaa.hxy.service.IUserService;

@Service("entityService")  

public class EntityServiceImpl implements IEntityService {  
    @Resource  
    private IHostEntityDao hostentitydao;  
    @Resource
    private IConnEntityDao connentitydao;
    @Resource
    private IVulnEntityDao vulnentitydao;
    @Resource
    private IServiceEntityDao serviceentitydao;
    @Resource
    private IAttackerEntityDao attackerentitydao;
    @Resource
    private ISafeEventEntityDao safeevententitydao;
    @Resource
    private IRperEntityDao rperentitydao;
    @Resource
    private ILperEntityDao lperentitydao;
    @Override
    public void setLperEntity(LperEntity lper){
    	this.lperentitydao.setLperEntity(lper);
    }
    public void delLper(){
    	this.lperentitydao.delLper();
    }
    public List<LperEntity> getLperEntityList(){
    	return this.lperentitydao.getLperEntityList();
    }
    @Override
    public List<RperEntity> getRperEntityList(){
    	return this.rperentitydao.getRperEntityList();
    }
    public void setRperEntity(RperEntity rper){
    	this.rperentitydao.setRperEntity(rper);
    }
    public void delRper(){
    	this.rperentitydao.delRper();
    }
    @Override  
    public void setHostEntity(HostEntity host) {  
        // TODO Auto-generated method stub  
    	this.hostentitydao.setHostEntity(host);
    } 
    public void delHost(){
    	this.hostentitydao.delHost();
    }
	public HostEntity getComputer(String name){
		return this.hostentitydao.getComputer(name);
	}
	public List<HostEntity> getHostList(){
		return this.hostentitydao.getHostList();
	}

    @Override
    public void setConnEntity(ConnEntity conn) {  
        // TODO Auto-generated method stub  
    	this.connentitydao.setConnEntity(conn);
    } 
    public void delConn(){
    	this.connentitydao.delConn();
    }
    public List<ConnEntity> getConnEntityList(String sourceName){
    	return this.connentitydao.getConnEntityList(sourceName);
    }
    
    
    @Override
    public void setVulnEntity(VulnEntity vuln) {  
        // TODO Auto-generated method stub  
    	this.vulnentitydao.setVulnEntity(vuln);
    } 
    public void delVuln(){
    	this.vulnentitydao.delVuln();
    }
    public List<VulnEntity> getvulEntityList(String serviceName){
    	return this.vulnentitydao.getvulEntityList(serviceName);
    }
    @Override
    public void setServiceEntity(ServiceEntity serviceentity){
    	this.serviceentitydao.setServiceEntity(serviceentity);
    	
    }
    public void delService(){
    	this.serviceentitydao.delService();
    }
	public List<ServiceEntity> getServiceEntityList(String hostName){
		return this.serviceentitydao.getServiceEntityList(hostName);
	}

	
    public void setAttackerEntity(AttackerEntity attackerentity){
    	this.attackerentitydao.setAttackerEntity(attackerentity);
    }
    public void delAttacker(){
    	this.attackerentitydao.delAttacker();
    }
    public List<AttackerEntity> getAttackerList(){
    	return this.attackerentitydao.getAttackerList();
    }
    public void setSafeEventEntity(SafeEventEntity safeEvent){
    	this.safeevententitydao.setSafeEventEntity(safeEvent);
    }
    public void delSafeEvent(){
    	this.safeevententitydao.delSafeEvent();
    }
    public List<SafeEventEntity> getSafeEventEntity(){
    	return this.safeevententitydao.getSafeEventEntity();
    }
  
}  

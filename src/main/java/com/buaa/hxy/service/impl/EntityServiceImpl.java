package com.buaa.hxy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.buaa.hxy.dao.IConnEntityDao;
import com.buaa.hxy.dao.IHostEntityDao;
import com.buaa.hxy.dao.ISchemeDao;
import com.buaa.hxy.dao.IServiceEntityDao;
import com.buaa.hxy.dao.IUserDao;
import com.buaa.hxy.dao.IVulnEntityDao;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
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
    @Override  
    public void setHostEntity(HostEntity host) {  
        // TODO Auto-generated method stub  
    	this.hostentitydao.setHostEntity(host);
    } 
    public void delHost(){
    	this.hostentitydao.delHost();
    }
    @Override
    public void setConnEntity(ConnEntity conn) {  
        // TODO Auto-generated method stub  
    	this.connentitydao.setConnEntity(conn);
    } 
    public void delConn(){
    	this.connentitydao.delConn();
    }
    @Override
    public void setVulnEntity(VulnEntity vuln) {  
        // TODO Auto-generated method stub  
    	this.vulnentitydao.setVulnEntity(vuln);
    } 
    public void delVuln(){
    	this.vulnentitydao.delVuln();
    }
    @Override
    public void setServiceEntity(ServiceEntity serviceentity){
    	this.serviceentitydao.setServiceEntity(serviceentity);
    	
    }
    public void delService(){
    	this.serviceentitydao.delService();
    }
  
}  

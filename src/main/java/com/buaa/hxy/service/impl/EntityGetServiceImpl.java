package com.buaa.hxy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.buaa.hxy.dao.IAttackerEntityDao;
import com.buaa.hxy.dao.IConnEntityDao;
import com.buaa.hxy.dao.IHostEntityDao;
import com.buaa.hxy.dao.ISafeEventEntityDao;
import com.buaa.hxy.dao.ISchemeDao;
import com.buaa.hxy.dao.IServiceEntityDao;
import com.buaa.hxy.dao.IUserDao;
import com.buaa.hxy.dao.IVulnEntityDao;
import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.Scheme;
import com.buaa.hxy.pojo.ServiceEntity;
import com.buaa.hxy.pojo.VulnEntity;
import com.buaa.hxy.service.IEntityGetService;
import com.buaa.hxy.service.IEntityService;
import com.buaa.hxy.service.ISchemeService;
import com.buaa.hxy.service.IUserService;

@Service("entityGetService")  
public class EntityGetServiceImpl implements IEntityGetService {  
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
    @Override  
	public HostEntity getComputer(String name){
		return this.hostentitydao.getComputer(name);
	}
    @Override
    public List<AttackerEntity> getAttackerList(){
    	return this.attackerentitydao.getAttackerList();
    }

  
}  

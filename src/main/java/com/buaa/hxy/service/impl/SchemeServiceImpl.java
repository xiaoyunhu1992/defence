package com.buaa.hxy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.buaa.hxy.dao.ISchemeDao;
import com.buaa.hxy.dao.IUserDao;
import com.buaa.hxy.pojo.Scheme;
import com.buaa.hxy.pojo.User;
import com.buaa.hxy.service.ISchemeService;
import com.buaa.hxy.service.IUserService;

@Service("schemeService")  
public class SchemeServiceImpl implements ISchemeService {  
    @Resource  
    private ISchemeDao schemeDao;  
    @Override  
    public List<Scheme> getAllSchemes() {  
        // TODO Auto-generated method stub  
        return this.schemeDao.selectAllSchemes();  
    }  
  
}  

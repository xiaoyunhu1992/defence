package com.buaa.hxy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.buaa.hxy.dao.IUserDao;
import com.buaa.hxy.pojo.User;
import com.buaa.hxy.service.IUserService;

@Service("userService")  
public class UserServiceImpl implements IUserService {  
    @Resource  
    private IUserDao userDao;  
    @Override  
    public User getUserById(String userName) {  
        // TODO Auto-generated method stub  
        return this.userDao.selectByPrimaryKey(userName);  
    }  
  
}  

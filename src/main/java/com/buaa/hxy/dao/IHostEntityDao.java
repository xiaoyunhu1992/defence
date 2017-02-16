package com.buaa.hxy.dao;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;


@Repository
public interface IHostEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setHostEntity(HostEntity hostentity);//insert an connection item into database
    void delHost();
    HostEntity getComputer(String hostName);
    String getConSource();
    String getConDes();
    String getConPortocol();
    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

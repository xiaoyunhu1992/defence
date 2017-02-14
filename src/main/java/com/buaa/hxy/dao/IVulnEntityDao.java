package com.buaa.hxy.dao;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.VulnEntity;


@Repository
public interface IVulnEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setVulnEntity(VulnEntity vulnentity);//insert an connection item into database
    void delVuln();
//    String getConSource();
//    String getConDes();
//    String getConPortocol();
//    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

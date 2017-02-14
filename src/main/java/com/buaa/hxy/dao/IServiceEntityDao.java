package com.buaa.hxy.dao;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.ServiceEntity;


@Repository
public interface IServiceEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setServiceEntity(ServiceEntity serviceentity);//insert an connection item into database
    void delService();
//    String getConSource();
//    String getConDes();
//    String getConPortocol();
//    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

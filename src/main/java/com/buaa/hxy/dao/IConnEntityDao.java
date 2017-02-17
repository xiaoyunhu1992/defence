package com.buaa.hxy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.ConnEntity;


@Repository
public interface IConnEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setConnEntity(ConnEntity conn);//insert an connection item into database
    void delConn();
    List<ConnEntity> getConnEntityList(String sourceName);
    String getConSource();
    String getConDes();
    String getConPortocol();
    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

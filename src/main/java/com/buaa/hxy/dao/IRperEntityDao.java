package com.buaa.hxy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.LperEntity;
import com.buaa.hxy.pojo.RperEntity;


@Repository
public interface IRperEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setRperEntity(RperEntity rper );//insert an connection item into database
    void delRper();
    List<RperEntity> getRperEntityList();
//    List<AttackerEntity> getAttackerList();
    
//    String getConSource();
//    String getConDes();
//    String getConPortocol();
//    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

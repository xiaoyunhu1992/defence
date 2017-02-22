package com.buaa.hxy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.LperEntity;
import com.buaa.hxy.pojo.RperEntity;


@Repository
public interface ILperEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setLperEntity(LperEntity lper );//insert an connection item into database
    void delLper();
    List<LperEntity> getLperEntityList();
//    List<AttackerEntity> getAttackerList();
    
//    String getConSource();
//    String getConDes();
//    String getConPortocol();
//    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

package com.buaa.hxy.dao;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.SafeEventEntity;


@Repository
public interface ISafeEventEntityDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    void setSafeEventEntity(SafeEventEntity safeEvent);//insert an connection item into database
    void delSafeEvent();
    
//    String getConSource();
//    String getConDes();
//    String getConPortocol();
//    String getConPort();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

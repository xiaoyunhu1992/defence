package com.buaa.hxy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.RiskEntity;


@Repository
public interface IRiskDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);


    List<RiskEntity> getRiskList();


//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

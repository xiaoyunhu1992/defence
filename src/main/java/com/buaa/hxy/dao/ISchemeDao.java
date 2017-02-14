package com.buaa.hxy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.buaa.hxy.pojo.Scheme;
@Repository
public interface ISchemeDao {
//	int deleteByPrimaryKey(String userName);
//
//    int insert(User record);

//    int insertSelective(User record);

    List<Scheme> selectAllSchemes();

//    int updateByPrimaryKeySelective(User record);
//
//    int updateByPrimaryKey(User record);
}

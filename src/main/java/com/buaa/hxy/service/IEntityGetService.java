package com.buaa.hxy.service;


import java.util.List;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.ServiceEntity;
import com.buaa.hxy.pojo.VulnEntity;


public interface IEntityGetService {
	public List<AttackerEntity> getAttackerList();
	public HostEntity getComputer(String name);
}

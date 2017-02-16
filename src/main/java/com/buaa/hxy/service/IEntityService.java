package com.buaa.hxy.service;


import java.util.List;

import com.buaa.hxy.pojo.AttackerEntity;
import com.buaa.hxy.pojo.ConnEntity;
import com.buaa.hxy.pojo.HostEntity;
import com.buaa.hxy.pojo.SafeEventEntity;
import com.buaa.hxy.pojo.ServiceEntity;
import com.buaa.hxy.pojo.VulnEntity;


public interface IEntityService {
	public void setHostEntity(HostEntity host);//insert a connentity into database
	public void delHost();//truncate the table connection
	public void setConnEntity(ConnEntity coon);//insert a connentity into database
	public void delConn();//truncate the table connection
	public void setVulnEntity(VulnEntity vul);
	public void delVuln();
	public void setServiceEntity(ServiceEntity service);
	public void delService();
	public void setAttackerEntity(AttackerEntity attacker);
	public void delAttacker();
	public void setSafeEventEntity(SafeEventEntity safeEvent);
	public void delSafeEvent();
	
	public List<AttackerEntity> getAttackerList();
	public HostEntity getComputer(String name);
}

package com.buaa.hxy.pojo.subNodeType;

import com.buaa.hxy.pojo.AttackGraphNodeType.StateNode;
import com.buaa.hxy.pojo.FClass.Computer;
import com.buaa.hxy.pojo.FClass.Service;

public class HostService extends StateNode {
	Computer destiny  = new Computer();
	public Computer getDestiny() {
		return destiny;
	}

	public void setDestiny(Computer destiny) {
		this.destiny = destiny;
	}

	Service service = new Service();
	

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

}

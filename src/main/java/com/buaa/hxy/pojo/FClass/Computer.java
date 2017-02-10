package com.buaa.hxy.pojo.FClass;
import java.util.ArrayList;

import com.buaa.hxy.pojo.subNodeType.Connection;


public class Computer {
	String computerName;
	String IPAddress;
	String mask;
	ArrayList <Service> serviceList= new ArrayList<Service>();
	ArrayList <Connection> connectionList= new ArrayList<Connection>();
	public ArrayList<Connection> getConnectionList() {
		return connectionList;
	}
	public void setConnectionList(ArrayList<Connection> connectionList) {
		this.connectionList = connectionList;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	public ArrayList<Service> getServiceList() {
		return serviceList;
	}
	public void setServiceList(ArrayList<Service> serviceList) {
		this.serviceList = serviceList;
	}

	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

}

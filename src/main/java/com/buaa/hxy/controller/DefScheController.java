package com.buaa.hxy.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buaa.hxy.pojo.Scheme;
import com.buaa.hxy.pojo.User;
import com.buaa.hxy.service.ISchemeService;
import com.buaa.hxy.service.IUserService;

@Controller
@RequestMapping("/scheme")
public class DefScheController {
	@Autowired
    @Qualifier("schemeService")
    private ISchemeService schemeService;
/**
 * my first java program to call python
 * @author meiyun
 * @throws IOException 
 * @throws InterruptedException 
 */    
	@RequestMapping(value ="/normal",method = RequestMethod.POST)
	@ResponseBody
	public Map schemeNormal(HttpServletRequest req, HttpServletResponse resp) throws IOException, InterruptedException {
		Map map=new HashMap();
		String pop = req.getParameter("pop").trim();
		String gen = req.getParameter("gen").trim();
		String num = req.getParameter("num").trim();
		String riskcon = req.getParameter("riskcon").trim();
		String costcon = req.getParameter("costcon").trim();
		if (pop=="") {
			map.put("back", "fail");
			map.put("msg", "种群数量不能为空");
			return map;
		}
		if (gen=="") {
			map.put("back", "fail");
			map.put("msg", "遗传代数不能为空");
			return map;
		}
		if (costcon=="") {
			map.put("back", "fail");
			map.put("msg", "成本阈值不能为空");
			return map;
		}
		if(Integer.valueOf(num).intValue()>Integer.valueOf(pop).intValue()){
			map.put("back", "fail");
			map.put("msg", "可选防御措施数量不能大于种群数");
			return map;
		}
		if (num=="") {
			num = pop;
		}
		String cmd = "/Library/anaconda/bin/python /Users/hxy/Documents/pytmatlab/defselect.py";
		cmd = cmd +' '+pop+' '+gen+' '+num+' '+riskcon +' '+costcon;
		
//		System.out.println(cmd);
		Process pr = Runtime.getRuntime().exec(cmd);
		pr.waitFor();  
		
		List<Scheme> scheme = this.schemeService.getAllSchemes();
		Iterator<Scheme> it = scheme.iterator();
		List<String> component = new ArrayList<String>();
		List<Float> risk = new ArrayList<Float>();
		List<Float> cost = new ArrayList<Float>();
		for (Scheme each :scheme){
			component.add(each.getComponent());
			risk.add(each.getRisk());
			cost.add(each.getCost());
		}
		List<StringBuffer> realmean = new ArrayList<StringBuffer>();
		
		for (String each : component){//将防御方案解释为实际含义
			StringBuffer real = new StringBuffer();
			for (int i =0;i<each.length();i++){
				
				if(each.charAt(i)=='1'){
					real.append("M"+i);
				}
			}
			realmean.add(real);
//			System.out.println(real);
			
		}
//		System.out.println(realmean);
//		System.out.println(risk);
		map.put("back", "success");
		map.put("realmean", realmean);
		map.put("risk", risk);
		map.put("cost", cost);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(map);
		return map;
		
	}

}

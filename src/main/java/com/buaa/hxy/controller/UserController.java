package com.buaa.hxy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buaa.hxy.pojo.User;
import com.buaa.hxy.service.IUserService;


@Controller
@RequestMapping("/user")
public class UserController {
    //@Resource 
	@Autowired
    @Qualifier("userService")
    private IUserService userService;  
     
    @RequestMapping(value ="/login",method = RequestMethod.POST)
	@ResponseBody
	public Map login(HttpServletRequest req, HttpServletResponse resp){
    	String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
        Map map=new HashMap();
		if (username=="") {
			map.put("back", "fail");
			map.put("msg", "用户名不能为空");
			return map;
		}
		User user = this.userService.getUserById(username);
		//if(user==null||!user.getPassword().equals(password))
		if(user==null||!(user.getPassword().equals(password)))
		{
			map.put("back", "fail");
			map.put("msg", "用户名或密码错误，请重新输入！");
			return map;
		}
		map.put("url", "NewsTransmission");
		map.put("back", "success");
		
		HttpSession session = req.getSession();
		session.setAttribute("userName",username);
		session.setAttribute("password",password);
		return map;
    }
    
    @RequestMapping(value ="/getLoginName",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> getLoginName(HttpSession session){
    	Map<String,String> map=new HashMap<String,String>();
    	
    	String username = (String) session.getAttribute("userName");
    	String password= (String) session.getAttribute("password");
    	
        if(null!=username){
            map.put("userName", username);
            map.put("password", password);
        }
        return map;
    }
    
    @RequestMapping(value ="/quit",method = RequestMethod.POST)
	@ResponseBody
	public void quit(HttpSession session){
		session.removeAttribute("userName");
		session.removeAttribute("password");
		session.invalidate();
    }
    
    
    
//    @RequestMapping(value = "/showUser", method = RequestMethod.GET)  
//    public String showUser(HttpServletRequest request, HttpServletResponse response){  
//        int userId = Integer.parseInt(request.getParameter("id"));  
//        User user = this.userService.getUserById(String.valueOf(userId)); 
//       // String user = "wcj";
//        System.out.println(user.getUserName());
//        return "showUser";  
//    }  
}  

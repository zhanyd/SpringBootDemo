package com.zhanyd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.zhanyd.model.UserInfo;
import com.zhanyd.service.UserService;


@RestController
@EnableAutoConfiguration
public class IndexController {

	@Autowired
	UserService userService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${name:World}")
    private String name;
    

    @RequestMapping("/addUser")
    public String addUser(){
    	UserInfo userInfo = new UserInfo();
    	userInfo.setUserName("zhan");
    	userInfo.setAge(34);
    	userInfo.setAddress("wenzhou");
    	userService.insertSelective(userInfo);
        return "added";
    }
    
    
    @RequestMapping("/getUser")
    public UserInfo getUser(String userId){
    	System.out.println(userId);
    	PageHelper.startPage(1, 10);
    	UserInfo userInfo = userService.selectByPrimaryKey(1);
    	logger.trace("getUser trace");
    	logger.debug("getUser debuger");
    	logger.info("getUser info");
    	logger.error("getUser error");
        return userInfo;
    }
    
    @RequestMapping("/")
    public String sayHello(){
        return String.format("hello:%s", name);
    }
    
   /* public static void main(String[] args) throws Exception {
		SpringApplication.run(IndexController.class, args);
	}*/

}

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



/**
 * RestController是Spring 4.0推出的新特性,
 * 使用其标注表示当前类为一个@Controller,并且
 * 使用@RequestMapping所标注的方法的返回值默认会被认为使用
 * 了@ResponseBody进行标注，因此不再使用视图解析的方式进行处理，
 * 而是将内容通过HTTP响应体返回给客户端。
 */
@RestController
@EnableAutoConfiguration
public class IndexController {

	@Autowired
	UserService userService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
     * spring boot会自动读取application.properties,
     * 并且将其作为系统参数进行注入,用户也可以在启动应用的时候
     * 通过-Dname=xxx来手动注入，手动注入会覆盖配置文件中的参数
     * 如果没有指定值，那么name的默认值就是World。
     */
    @Value("${name:World}")
    private String name;
    
    /**
     * 由于使用了@RestControlelr,因此无需在使用@ResponseBody来标注返回的结果
     */
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
    public UserInfo getUser(){
    	PageHelper.startPage(1, 10);
    	UserInfo userInfo = userService.selectByPrimaryKey(1);
    	logger.trace("getUser trace");
    	logger.debug("getUser debuger");
    	logger.info("getUser info");
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

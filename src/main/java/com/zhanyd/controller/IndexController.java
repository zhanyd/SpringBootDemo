package com.zhanyd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/")
    public String sayHello(){
        return String.format("hello:%s", name);
    }
    
   /* public static void main(String[] args) throws Exception {
		SpringApplication.run(IndexController.class, args);
	}*/

}

package com.zhanyd.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhanyd.biz.model.Users;
import com.zhanyd.biz.service.UserService;
import com.zhanyd.common.ApiResult;

@RestController
@RequestMapping("/api/user")
public class UserController {

	 @Autowired
     private UserService userService;
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	 //获取用户信息
	 @PostMapping("getUserInfo")
     public ApiResult<Users> getUserInfo(Integer id){
		 ApiResult<Users> result = new ApiResult<Users>();
		 Users user =  userService.selectByPrimaryKey(id);
         return result.success(user);
	 }
	 //用户注册
	 @PostMapping("signup")
	 public ApiResult<String> signUp(Users user) {
		 ApiResult<String> result = new ApiResult<String>();
         user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try{
        	 userService.insertSelective(user);
        }catch (Exception e) {
			result.fail(001, "用户注册失败");
			e.printStackTrace();
		}
        result.success("用户注册成功");
		return result;
       
    }
}

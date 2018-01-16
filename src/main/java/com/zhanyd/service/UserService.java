package com.zhanyd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanyd.mapper.UserInfoMapper;
import com.zhanyd.model.UserInfo;

@Service
public class UserService {

	@Autowired
	UserInfoMapper userInfoMapper;
	
	public int insertSelective(UserInfo record){
		return userInfoMapper.insertSelective(record);
	}
	
	public UserInfo selectByPrimaryKey(Integer id){
		return userInfoMapper.selectByPrimaryKey(id);
	}
}

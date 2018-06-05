package com.zhanyd.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanyd.biz.mapper.UsersMapper;
import com.zhanyd.biz.model.Users;


@Service
public class UserService {

	@Autowired
	UsersMapper usersMapper;
	
	public int insertSelective(Users record){
		return usersMapper.insertSelective(record);
	}
	
	public Users selectByPrimaryKey(Integer id){
		return usersMapper.selectByPrimaryKey(id);
	}
}

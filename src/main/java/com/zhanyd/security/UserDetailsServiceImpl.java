package com.zhanyd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zhanyd.biz.mapper.UsersMapper;
import com.zhanyd.biz.model.Users;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

	@Autowired
    private UsersMapper usersMapper;

    /**
     * 通过构造器注入UserRepository
     * @param userRepository
     */
    public UserDetailsServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Users user = usersMapper.selectByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
    }

}

package com.taotao.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.sso.dao.UserMapper;
import com.taotao.sso.pojo.User;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	/** 检查对应类型的用户是否存在:用户名是否存在，手机是否存在，邮箱是否存在。<br>
	 *  存在返回false，不存在返回true */
	public Boolean check(String param, Integer type) {

		User record = new User();
		switch (type) {
		case 1:
			record.setUsername(param);
			break;
		case 2:
			record.setPhone(param);
			break;
		case 3:
			record.setEmail(param);
			break;
		default:
			// 参数有误
			return null;
		}

		return this.userMapper.selectOne(record) == null;
	}
}

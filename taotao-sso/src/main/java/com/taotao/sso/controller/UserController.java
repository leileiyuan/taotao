package com.taotao.sso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}

	@RequestMapping(value = "{param}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> check(@PathVariable String param, @PathVariable Integer type) {
		try {
			Boolean bool = this.userService.check(param, type);
			if (bool == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}

			// bool值，不存在为true,
			return ResponseEntity.ok(!bool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult) {
		Map<String, Object> result = new HashMap<>();
		// 校验
		if (bindingResult.hasErrors()) {
			result.put("status", "400");
			List<String> errorMessages = new ArrayList<>();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError objectError : errors) {
				errorMessages.add(objectError.getDefaultMessage());
			}
			
			result.put("data", "参数有误"+StringUtils.join(errorMessages,"|"));
			return result;
		}

		try {
			Boolean bool = this.userService.doRegister(user);
			if (bool) {
				result.put("status", "200");
			} else {
				result.put("data", "注册失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("data", "注册失败");
		}
		return result;
	}
}

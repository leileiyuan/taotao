package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.service.ItemService;
import com.taotao.service.RedisService;

@Controller
@RequestMapping("item/cache")
public class ItemCacheController {

	@Autowired
	private RedisService redisService;

	/**
	 * 后台系统通知，前台系统更新缓存
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> deleteCache(@PathVariable("itemId") long itemId) {
		String key = ItemService.REDIS_KEY + itemId;
		try {
			this.redisService.del(key);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}

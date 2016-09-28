package com.taotao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.pojo.ItemDesc;
import com.taotao.service.ItemDescService;

@Controller
@RequestMapping("/item/desc")
public class ItemDescController {
	private static Logger logger = LoggerFactory.getLogger(ItemDescController.class);
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ItemDesc> queryItemDescByItmeId(@PathVariable("itemId") long itemId) {
		try {
			logger.info("根据商品ID查询商品描述,itemId = {}", itemId);
			ItemDesc itemDesc = itemDescService.queryById(itemId);
			if (itemDesc == null) {
				// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			logger.error("查询商品描述成功,itemDesc = {}", itemDesc);
			return ResponseEntity.ok(itemDesc);
		} catch (Exception e) {
			logger.error("查询商品描述失败,itemId = {}", itemId, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}

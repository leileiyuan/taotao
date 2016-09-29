package com.taotao.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.EasyUIResult;
import com.taotao.pojo.Item;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	private static Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemService itemService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc) {
		try {
			logger.info("新增商品，item = {}, desc = {}", item, desc);
			if (StringUtils.isEmpty(item.getTitle())) {
				// 参数有误 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			boolean bool = this.itemService.saveItem(item, desc);
			if (!bool) {
				logger.info("新增商品失败,item = {}", item);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			logger.info("新增商品成功,itemId = {}", item.getId());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			logger.error("新增商品失败", e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryItemList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "30") int rows) {
		try {
			EasyUIResult easyUIResult = itemService.queryItemList(page, rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 修改商品
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item, @RequestParam("desc") String desc) {
		try {
			logger.info("修改商品，item = {}, desc = {}", item, desc);
			if (StringUtils.isEmpty(item.getTitle()) || StringUtils.length(item.getTitle()) > 100) {
				// 参数有误 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			boolean bool = this.itemService.updateItem(item, desc);
			if (!bool) {
				logger.info("修改商品失败,item = {}", item);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			// 204
			logger.info("修改商品成功,itemId = {}", item.getId());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			logger.error("修改商品失败,item  = " + item + ", desc = " + desc, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}

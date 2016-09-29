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
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.EasyUIResult;
import com.taotao.pojo.ItemParam;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	private static Logger logger = LoggerFactory.getLogger(ItemParamController.class);

	@Autowired
	private ItemParamService itemParamService;

	/**
	 * 根据商品类目ID，查询规格参数模板
	 * 
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
	public ResponseEntity<ItemParam> queryByItemCatId(@PathVariable("itemCatId") long itemCatId) {
		try {
			logger.info("根根据商品类目ID，查询规格参数模板,itemCatId = {}", itemCatId);
			ItemParam record = new ItemParam();
			record.setItemCatId(itemCatId);
			ItemParam itemParam = itemParamService.queryOne(record);
			if (itemParam == null) {
				// 404
				logger.info("根根据商品类目ID，查询规格参数模板 ，没有相应的模板信息，iteCatId = {}", itemCatId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			logger.info("根据商品类目ID，查询规格参数模板 成功,itemParam = {}", itemParam);
			return ResponseEntity.ok(itemParam);
		} catch (Exception e) {
			logger.error("根据商品类目ID，查询规格参数模板失败,iteCatId = {}", itemCatId, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 新增规格参数模板
	 * 
	 * @param itemCatId
	 * @param paramData
	 * @return
	 */
	@RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
	public ResponseEntity<Void> saveItemCatId(@PathVariable("itemCatId") long itemCatId, String paramData) {
		try {
			logger.info("新增 规格参数模板,itemCatId = {}, paramData = {}", itemCatId, paramData);
			ItemParam record = new ItemParam();
			record.setId(null);
			record.setItemCatId(itemCatId);
			record.setParamData(paramData);
			this.itemParamService.save(record);

			logger.info("新增 规格参数模板 成功,itemCatId = {}, paramData = {}", itemCatId, paramData);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			logger.error("新增 规格参数模板失败,itemCatId = {}, paramData = {}", itemCatId, paramData, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	/**
	 * 查询规格参数模板列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> saveItemCatId(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "30") int rows) {
		try {
			logger.info("查询规格参数模板列表,page = {}, rows = {}", page, rows);
			EasyUIResult result = this.itemParamService.queryItemParamList(page, rows);
			logger.info("查询规格参数模板列表成功,result = {}", result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			logger.error("查询规格参数列表 失败，page = {}, rows = {}", page, rows, e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}

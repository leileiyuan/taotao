package com.taotao.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.ItemCatResult;
import com.taotao.service.ItemCatService;

/**
 * 对外提供的接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/item/cat")
public class ApiItemCatController {
	@Autowired
	private ItemCatService itemCatService;

	/**
	 *  查询所有类目数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ItemCatResult> queryItemCatList() {
		try {
			ItemCatResult itemCatResult = itemCatService.queryAllToTree();
			return ResponseEntity.ok(itemCatResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}

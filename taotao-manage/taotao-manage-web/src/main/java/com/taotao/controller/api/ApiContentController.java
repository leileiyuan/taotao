package com.taotao.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.EasyUIResult;
import com.taotao.service.ContentService;

@RequestMapping("api/content")
@Controller
public class ApiContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<EasyUIResult> queryListByCategoryId(long categoryId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows) {
		try {
			EasyUIResult easyUIResult = this.contentService.queryListByCategoryId(categoryId, page, rows);
			return ResponseEntity.ok(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}

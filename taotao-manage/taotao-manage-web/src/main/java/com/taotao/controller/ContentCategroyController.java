package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.pojo.ContentCategory;
import com.taotao.service.ContentCategoryService;

@RequestMapping("/content/category")
@Controller
public class ContentCategroyController {
	@Autowired
	private ContentCategoryService contentCategoryService;

	/**
	 * 根据父节点，查询内容分类列表
	 * 
	 * @param pid
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContentCategory>> queryListByParentId(
			@RequestParam(value = "id", defaultValue = "0") long pid) {
		try {
			ContentCategory record = new ContentCategory();
			record.setParentId(pid);
			List<ContentCategory> list = this.contentCategoryService.queryListByWhere(record);
			if (list == null || list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
			return ResponseEntity.ok(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ContentCategory> saveContenCategory(ContentCategory contenCategory) {
		try {
			this.contentCategoryService.saveContentCategory(contenCategory);
			return ResponseEntity.status(HttpStatus.CREATED).body(contenCategory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/**
	 * 重命名
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> rename(long id, String name) {
		try {
			ContentCategory record = new ContentCategory();
			record.setId(id);
			record.setName(name);
			this.contentCategoryService.updateSelective(record);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(ContentCategory contentCategory) {
		try {
			this.contentCategoryService.deleteAll(contentCategory);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}

package com.taotao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.dao.ContentMapper;
import com.taotao.pojo.Content;

@Service
public class ContentService extends BaseService<Content> {
	@Autowired
	private ContentMapper contentMapper;

	public EasyUIResult queryListByCategoryId(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);
		List<Content> list = this.contentMapper.queryContentList(categoryId);
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

}

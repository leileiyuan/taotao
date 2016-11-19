package com.taotao.dao;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.taotao.pojo.Content;
import com.taotao.pojo.ContentCategory;

public interface ContentMapper extends Mapper<ContentCategory> {
	/**
	 * 根据 categoryId查询内容列表，并且按照更新时间倒序排序
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Content> queryContentList(long categoryId) ;
}

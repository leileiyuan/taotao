package com.taotao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.ItemCatMapper;
import com.taotao.pojo.ItemCat;

@Service
public class ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	public List<ItemCat> queryItemCatListByParentId(long pid) {
		ItemCat record = new ItemCat();
		record.setParentId(pid);
		return itemCatMapper.select(record);
	}

}

package com.taotao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.mapper.Mapper;
import com.taotao.dao.ItemCatMapper;
import com.taotao.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat>{

	@Autowired
	private ItemCatMapper itemCatMapper;

/*	public List<ItemCat> queryItemCatListByParentId(long pid) {
		ItemCat record = new ItemCat();
		record.setParentId(pid);
		return itemCatMapper.select(record);
	}
*/
	@Override
	public Mapper<ItemCat> getMapper() {
		return itemCatMapper;
	}

}

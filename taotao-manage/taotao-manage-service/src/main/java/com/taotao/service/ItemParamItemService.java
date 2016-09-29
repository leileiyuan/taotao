package com.taotao.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.taotao.dao.ItemParamItemMapper;
import com.taotao.pojo.ItemParamItem;

@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	public int updateItemParamItem(long itemId, String itemParams) {
		// 要更新的数据
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setParamData(itemParams);
		itemParamItem.setUpdated(new Date());

		// 更新的条件
		Example example = new Example(ItemParamItem.class);
		example.createCriteria().andEqualTo("itemId", itemId);

		return this.itemParamItemMapper.updateByExampleSelective(itemParamItem, example);
	}

}

package com.taotao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item> {
	
	@Autowired
	private ItemDescService itemDescService;
	
	public boolean saveItem(Item item, String desc){
		// 初始值
		item.setStatus(1);
		item.setId(null);	// 出于安全考虑，强制设置id为null，通过数据库自增得到
		//this.itemService.save(item);
		int count1 = super.save(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		int count2 = this.itemDescService.save(itemDesc);
		
		return (count1 == 1) && (count2 == 1);
	}
}

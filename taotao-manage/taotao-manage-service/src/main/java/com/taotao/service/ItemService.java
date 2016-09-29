package com.taotao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.dao.ItemMapper;
import com.taotao.pojo.Item;
import com.taotao.pojo.ItemDesc;
import com.taotao.pojo.ItemParamItem;

@Service
public class ItemService extends BaseService<Item> {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private ItemDescService itemDescService;

	@Autowired
	private ItemParamItemService itemParamItemService;

	public boolean saveItem(Item item, String desc, String itemParams) {
		// 初始值
		item.setStatus(1);
		item.setId(null); // 出于安全考虑，强制设置id为null，通过数据库自增得到
		// this.itemService.save(item);
		int count1 = super.save(item);

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		int count2 = this.itemDescService.save(itemDesc);

		ItemParamItem record = new ItemParamItem();
		record.setItemId(item.getId());
		record.setParamData(itemParams);
		int count3 = this.itemParamItemService.save(record);

		return (count1 == 1) && (count2 == 1) && (count3 == 1);
	}

	public EasyUIResult queryItemList(int page, int rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);
		Example example = new Example(Item.class);
		example.setOrderByClause("updated DESC");
		List<Item> list = itemMapper.selectByExample(example);
		PageInfo<Item> info = new PageInfo<>(list);
		return new EasyUIResult(info.getTotal(), info.getList());
	}

	public boolean updateItem(Item item, String desc) {
		item.setStatus(null); // 强制设置 status为null,不更新该字段
		int count1 = super.updateSelective(item);

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		int count2 = itemDescService.updateSelective(itemDesc);

		return (count1 == 1) && (count2 == 1);
	}
}

package com.taotao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.taotao.common.ItemCatData;
import com.taotao.common.ItemCatResult;
import com.taotao.pojo.ItemCat;

@Service
public class ItemCatService extends BaseService<ItemCat> {
	@Autowired
	private RedisService redisService;
	
	private static final String REDIS_KEY = "TAOTAO_MAMAGE_ITEM_CAT_API"; // key命名项目名_模块名_业务名
	
	private static final int REIDS_TIME = 60 * 60 * 24; //
	/**
	 * 全部查询，并且生成树状结构
	 * 
	 * @return
	 */
	public ItemCatResult queryAllToTree() {
		ItemCatResult result = new ItemCatResult();
		
		// 原则：加入的缓存，如果出错，不能影响我正常业务逻辑的处理。要使用最大的Exception捕获
		try {
			//先从缓存中命中，如果没有命中，继续执行
			String cacheData = redisService.get(REDIS_KEY);
			if(StringUtils.isNotEmpty(cacheData)){
				// 命中 则返回
				return  JSON.parseObject(cacheData, ItemCatResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 全部查出，并且在内存中生成树形结构
		List<ItemCat> cats = super.queryAll();

		// 转为map存储，key为父节点ID，value为数据集合
		Map<Long, List<ItemCat>> itemCatMap = new HashMap<Long, List<ItemCat>>();
		for (ItemCat itemCat : cats) {
			if (!itemCatMap.containsKey(itemCat.getParentId())) {
				itemCatMap.put(itemCat.getParentId(), new ArrayList<ItemCat>());
			}
			itemCatMap.get(itemCat.getParentId()).add(itemCat);
		}

		// 封装一级对象
		List<ItemCat> itemCatList1 = itemCatMap.get(0L);
		for (ItemCat itemCat : itemCatList1) {
			ItemCatData itemCatData = new ItemCatData();
			itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
			itemCatData.setName("<a href='" + itemCatData.getUrl() + "'>" + itemCat.getName() + "</a>");
			result.getItemCats().add(itemCatData);
			if (!itemCat.getIsParent()) {
				continue;
			}

			// 封装二级对象
			List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
			List<ItemCatData> itemCatData2 = new ArrayList<ItemCatData>();
			itemCatData.setItems(itemCatData2);
			for (ItemCat itemCat2 : itemCatList2) {
				ItemCatData id2 = new ItemCatData();
				id2.setName(itemCat2.getName());
				id2.setUrl("/products/" + itemCat2.getId() + ".html");
				itemCatData2.add(id2);
				if (itemCat2.getIsParent()) {
					// 封装三级对象
					List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
					List<String> itemCatData3 = new ArrayList<String>();
					id2.setItems(itemCatData3);
					for (ItemCat itemCat3 : itemCatList3) {
						itemCatData3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
					}
				}
			}
			if (result.getItemCats().size() >= 14) {
				break;
			}
		}
		
		// 原则：加入的缓存，如果出错，不能影响我正常业务逻辑的处理。要使用最大的Exception捕获
		try {
			// 将数据库查询到的结果集 写入到缓存中。
			String jsonString = JSON.toJSONString(result);
			this.redisService.set(REDIS_KEY, jsonString, REIDS_TIME); // 缓存一天
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

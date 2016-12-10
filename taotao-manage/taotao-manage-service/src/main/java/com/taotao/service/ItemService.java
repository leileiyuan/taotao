package com.taotao.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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

	@Autowired
	private RedisService redisService;

	@Autowired
	private ApiService apiService;

	@Value("${TAOTAO_WEB_URL}")
	private String TAOTAO_WEB_URL;

	public static final String REDIS_KEY = "TAOTAO_WEB_ITEM_DETAIL";

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

	public boolean updateItem(Item item, String desc, String itemParams) {
		item.setStatus(null); // 强制设置 status为null,不更新该字段
		int count1 = super.updateSelective(item);

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		int count2 = itemDescService.updateSelective(itemDesc);

		int count3 = this.itemParamItemService.updateItemParamItem(item.getId(), itemParams);

		// 更新商品时，通知其它系统。该商品已经更新
		String url = TAOTAO_WEB_URL + "/item/cache/" + item.getId() + ".html";
		try {
			this.apiService.doPost(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (count1 == 1) && (count2 == 1) && (count3 == 1);
	}

	/** 根据商品Id 查询商品数据 */
	public Item queryById(Long itemId) {
		String key = REDIS_KEY + itemId;
		try {
			String cacheData = this.redisService.get(key);
			if (StringUtils.isNotEmpty(cacheData)) {
				return JSON.parseObject(cacheData, Item.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return itemMapper.selectByPrimaryKey(itemId);
	}
}

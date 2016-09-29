package com.taotao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.EasyUIResult;
import com.taotao.dao.ItemParamMapper;
import com.taotao.pojo.ItemParam;

@Service
public class ItemParamService extends BaseService<ItemParam> {

	@Autowired
	private ItemParamMapper itemParamMapper;

	public EasyUIResult queryItemParamList(int page, int rows) {
		PageHelper.startPage(page, rows);
		Example example = new Example(ItemParam.class);
		example.setOrderByClause("updated DESC");
		List<ItemParam> list = itemParamMapper.selectByExample(example);
		PageInfo<ItemParam> pageInfo = new PageInfo<>(list);
		return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
	}

}

package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taotao.common.EasyUIResult;

@Service
public class IndexService {

	@Autowired
	private ApiService apiService;

	@SuppressWarnings("unchecked")
	public Object queryIndexAD1() throws ClientProtocolException, IOException {
		String url = "http://manage.taotao.com/rest/api/content?categoryId=31&page=1&rows=6";
		List<Map<String, Object>> list = new ArrayList<>();
		
		String json = apiService.doGet(url);
		
		EasyUIResult result = JSON.parseObject(json, EasyUIResult.class);
		List<JSONObject> rows = (List<JSONObject>) result.getRows();
		for (JSONObject obj : rows) {
			Map<String, Object> map = new HashMap<>();
			map.put("srcB", obj.get("pic")); // 图片
			map.put("heightB", 240);
			map.put("widthB", 550);
			map.put("alt", obj.get("title"));
			map.put("height", 240);
			map.put("width", 670);
			map.put("src", obj.get("pic")); // 图片2
			map.put("href", obj.get("url"));
			list.add(map);
		}
		return JSON.toJSON(list);
	}

}

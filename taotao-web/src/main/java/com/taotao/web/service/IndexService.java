package com.taotao.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taotao.common.EasyUIResult;

@SuppressWarnings("unchecked")
@Service
public class IndexService {

	@Autowired
	private ApiService apiService;

	@Value("${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;

	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL;

	@Value("${INDEX_AD2_URL}")
	private String INDEX_AD2_URL;

	/**
	 * 首页 大广告
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Object queryIndexAD1() throws ClientProtocolException, IOException {
		String url = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
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
	
	/**
	 * 首页 小广告
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Object queryIndexAD2() throws ClientProtocolException, IOException {
		String url = TAOTAO_MANAGE_URL + INDEX_AD2_URL;
		List<Map<String, Object>> list = new ArrayList<>();
		
		String json = apiService.doGet(url);
		
		EasyUIResult result = JSON.parseObject(json, EasyUIResult.class);
		List<JSONObject> rows = (List<JSONObject>) result.getRows();
		for (JSONObject obj : rows) {
			Map<String, Object> map = new HashMap<>();
			map.put("width", 310);
			map.put("height", 70);
			map.put("src", obj.get("pic")); // 图片
			map.put("href", obj.get("url"));
			map.put("alt", obj.get("title"));
			map.put("widthB", 210);
			map.put("heightB", 70);
			map.put("srcB", obj.get("pic")); // 图片
			list.add(map);
		}
		return JSON.toJSON(list);
	}

}

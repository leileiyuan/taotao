package com.taotao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.pojo.ContentCategory;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

	public void saveContentCategory(ContentCategory contenCategory) {
		contenCategory.setId(null);
		contenCategory.setIsParent(false);
		contenCategory.setSortOrder(1);
		contenCategory.setStatus(1);
		super.save(contenCategory);

		// 父节点的isParent不是true，修改为true
		ContentCategory parent = super.queryById(contenCategory.getId());
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			super.update(parent);
		}
	}

	public void deleteAll(ContentCategory contentCategory) {
		List<Object> ids = new ArrayList<>();
		ids.add(contentCategory.getId());

		this.findAllSubNode(ids, contentCategory.getId());

		super.deleteByIds(ids, ContentCategory.class, "id");

		// 判断该节点，还有兄弟节点，如果没有，应该修改该节点isparent为false
		ContentCategory record = new ContentCategory();
		record.setParentId(contentCategory.getParentId());
		List<ContentCategory> list = super.queryListByWhere(record);
		if (list == null || list.isEmpty()) {
			ContentCategory parent = new ContentCategory();
			parent.setId(contentCategory.getParentId());
			parent.setIsParent(false);
			super.updateSelective(parent);
		}

	}

	private void findAllSubNode(List<Object> ids, long pid) {
		ContentCategory record = new ContentCategory();
		record.setParentId(pid);
		List<ContentCategory> list = super.queryListByWhere(record);
		for (ContentCategory contentCategory : list) {
			ids.add(contentCategory.getId());
			if (contentCategory.getIsParent()) {
				// 递归
				this.findAllSubNode(ids, contentCategory.getId());
			}
		}
	}
}

package com.taotao.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.pojo.BasePojo;

public abstract class BaseService<T extends BasePojo> {

	// 使用spring4的泛型注入
	//public abstract Mapper<T> getMapper();
	@Autowired
	public Mapper<T> mapper;

	/**
	 * queryById
	 * 
	 * @param id
	 * @return
	 */
	public T queryById(long id) {
		return mapper.selectByPrimaryKey(id);
	}

	public List<T> queryAll() {
		return mapper.select(null);
	}

	/**
	 * 根据条件查询一条数据，如果查询的数据 为多条 ，抛出异常。
	 * 
	 * @param record
	 * @return
	 */
	public T queryOne(T record) {
		return mapper.selectOne(record);
	}

	/**
	 * 根据条件查询多条数据
	 * 
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record) {
		return mapper.select(record);
	}

	/**
	 * 根据条件分页查询
	 * 
	 * @param record
	 * @param page
	 * @param rows
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T record, int page, int rows) {
		PageHelper.startPage(page, rows);
		List<T> list = mapper.select(record);
		return new PageInfo<T>(list);
	}

	public int save(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insert(record);
	}

	/**
	 * 选择不为null的字段值，作为要保存的数据
	 * 
	 * @param record
	 * @return
	 */
	public int saveSelective(T record) {
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return mapper.insertSelective(record);
	}

	public int update(T record) {
		record.setUpdated(new Date());
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 根据主键更新，只更新字段值不null的字段。
	 * 
	 * @param record
	 * @return
	 */
	public int updateSelective(T record) {
		record.setUpdated(new Date());
		record.setCreated(null); // 强制设置创建时间为null,永远不更新创建时间。
		return mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 物理删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(long id) {
		return mapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除（物理删除）
	 * 
	 * @param ids
	 * @param clazz
	 * @param property
	 * @return
	 */
	public int deleteByIds(List<Object> ids, Class<T> clazz, String property) {
		Example example = new Example(clazz);
		example.createCriteria().andIn(property, ids);
		return mapper.deleteByExample(example);
	}

	/**
	 * 根据条件删除
	 * @param record
	 * @return
	 */
	public int deleteByWhere(T record) {
		return mapper.delete(record);
	}

}

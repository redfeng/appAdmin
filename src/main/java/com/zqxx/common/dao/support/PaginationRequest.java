package com.zqxx.common.dao.support;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

public class PaginationRequest<T> {
	private List<String> joinEntitys = new ArrayList<String>();
	private List<String> propertyNames = new ArrayList<String>();
	private List<Object> values = new ArrayList<Object>();
	
	/**
	 * 开始查询行索引
	 */
	private int offset;
	/**
	 * 分页大小
	 */
	private int limit;
	
	private int page;
	
	/**
	 * 排序字段
	 */
	private List<Order> orders = new ArrayList<Order>();

	public PaginationRequest(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}
	
	
	public PaginationRequest(int page, int offset, int limit) {
		this.page = page;
		this.offset = offset;
		this.limit = limit;
	}
	
	public void addCondition(String propertyName, Object value) {
		if(value != null) {
			if((value instanceof String) && (StringUtils.hasText((String)value))) {
				propertyNames.add(propertyName);
				values.add(value);
			} else if(!(value instanceof String)) {
				propertyNames.add(propertyName);
				values.add(value);
			}
		}
	}
	
	public void addLikeCondition(String propertyName, String value) {
		if(value != null) {
			if((value instanceof String) && (StringUtils.hasText((String)value))) {
				propertyNames.add(propertyName);
				values.add(Restrictions.like(propertyName, value));
			} else if(!(value instanceof String)) {
				propertyNames.add(propertyName);
				values.add(Restrictions.like(propertyName, value));
			}
		}
	}
	
	public void addJoinEntity(String entityName) {
		joinEntitys.add(entityName);
	}
	
	public void addOrder(String propertyName, boolean ascending) {
		if(ascending)
			orders.add(Order.asc(propertyName));
		else
			orders.add(Order.desc(propertyName));
	}
	
	public void addOrder(String propertyName, String mode) {
		if("ASC".equals(mode.toUpperCase()))
			orders.add(Order.asc(propertyName));
		else
			orders.add(Order.desc(propertyName));
	}
	
	public Object getPropertyValue(String propertyName) {
		for(int i=0, len=propertyNames.size(); i<len; i++) {
			if(propertyNames.get(i).equals(propertyName)) {
				return values.get(i);
			}
		}
		return null;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public List<String> getJoinEntitys() {
		return joinEntitys;
	}

	public List<String> getPropertyNames() {
		return propertyNames;
	}

	public List<Object> getValues() {
		return values;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Order> getOrders() {
		return orders;
	}
}
package com.taotao.service;

public interface Function<T, E> {
	/**
	 * T 返回 类型，不确定，E 入参 不确定
	 * @param e
	 * @return
	 */
	public T calback(E e);
}

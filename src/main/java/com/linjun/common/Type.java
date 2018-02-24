package com.linjun.common;

/**
 * 消息类型定义
 * @author tanyaowu
 * 2017年3月26日 下午8:18:13
 */
public interface Type {

	/**
	 * 登录消息请求
	 */
	byte LOGIN_REQ = 1;
	/**
	 * 登录消息响应
	 */
	byte LOGIN_RESP = 2;


	byte HEART_BEAT_REQ = 99;

}

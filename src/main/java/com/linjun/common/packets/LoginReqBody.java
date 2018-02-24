package com.linjun.common.packets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录请求
 * @author tanyaowu
 * 2017年3月25日 上午8:22:06
 */
public class LoginReqBody extends BaseBody {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(LoginReqBody.class);

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	private String deviceID;

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
}

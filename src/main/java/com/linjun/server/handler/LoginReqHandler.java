package com.linjun.server.handler;


import com.linjun.common.ShowcasePacket;
import com.linjun.common.ShowcaseSessionContext;
import com.linjun.common.Type;
import com.linjun.common.intf.AbsShowcaseBsHandler;
import com.linjun.common.packets.LoginReqBody;
import com.linjun.common.packets.LoginRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class LoginReqHandler extends AbsShowcaseBsHandler<LoginReqBody> {
	private static Logger log = LoggerFactory.getLogger(LoginReqHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	AtomicLong tokenSeq = new AtomicLong();

	/**
	 *
	 * @author tanyaowu
	 */
	public LoginReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<LoginReqBody> bodyClass() {
		return LoginReqBody.class;
	}

	/**
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public Object handler(ShowcasePacket packet, LoginReqBody bsBody, ChannelContext channelContext) throws Exception {
		log.info("收到登录请求消息:{}", Json.toJson(bsBody));
		LoginRespBody loginRespBody = new LoginRespBody();
		loginRespBody.setCode(LoginRespBody.Code.SUCCESS);
		loginRespBody.setToken(newToken());

		String deviceId = bsBody.getDeviceID();
		Aio.bindUser(channelContext, deviceId);

		ShowcaseSessionContext showcaseSessionContext = (ShowcaseSessionContext) channelContext.getAttribute();
		showcaseSessionContext.setUserid(deviceId);

		ShowcasePacket respPacket = new ShowcasePacket();
		respPacket.setType(Type.LOGIN_RESP);
		respPacket.setBody(Json.toJson(loginRespBody).getBytes(ShowcasePacket.CHARSET));
		Aio.send(channelContext, respPacket);

		return null;
	}

	private String newToken() {
		return System.currentTimeMillis() + "_" + tokenSeq.incrementAndGet();
	}
}

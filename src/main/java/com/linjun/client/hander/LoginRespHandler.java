package com.linjun.client.hander;


import com.linjun.common.ShowcasePacket;
import com.linjun.common.ShowcaseSessionContext;
import com.linjun.common.intf.AbsShowcaseBsHandler;
import com.linjun.common.packets.LoginRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class LoginRespHandler extends AbsShowcaseBsHandler<LoginRespBody> {
	private static Logger log = LoggerFactory.getLogger(LoginRespHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public LoginRespHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<LoginRespBody> bodyClass() {
		return LoginRespBody.class;
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
	public Object handler(ShowcasePacket packet, LoginRespBody bsBody, ChannelContext channelContext) throws Exception {
		System.out.println("收到登录响应消息:" + Json.toJson(bsBody));
		if (LoginRespBody.Code.SUCCESS.equals(bsBody.getCode())) {
			ShowcaseSessionContext showcaseSessionContext = (ShowcaseSessionContext) channelContext.getAttribute();
			showcaseSessionContext.setToken(bsBody.getToken());
			System.out.println("登录成功，token是:" + bsBody.getToken());
		}

		return null;
	}
}

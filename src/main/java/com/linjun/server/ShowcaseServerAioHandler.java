package com.linjun.server;


import com.linjun.common.ShowcaseAbsAioHandler;
import com.linjun.common.ShowcasePacket;
import com.linjun.common.Type;
import com.linjun.common.intf.AbsShowcaseBsHandler;
import com.linjun.server.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tanyaowu
 *
 */
public class ShowcaseServerAioHandler extends ShowcaseAbsAioHandler implements ServerAioHandler {
	private static Logger log = LoggerFactory.getLogger(ShowcaseServerAioHandler.class);

	private static Map<Byte, AbsShowcaseBsHandler<?>> handlerMap = new HashMap<>();
	static {
		handlerMap.put(Type.HEART_BEAT_REQ, new HeartbeatReqHandler());
		handlerMap.put(Type.LOGIN_REQ, new LoginReqHandler());
	}

	/**
	 * 处理消息
	 */
	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		ShowcasePacket showcasePacket = (ShowcasePacket) packet;
		Byte type = showcasePacket.getType();
		AbsShowcaseBsHandler<?> showcaseBsHandler = handlerMap.get(type);
		if (showcaseBsHandler == null) {
			log.error("{}, 找不到处理类，type:{}", channelContext, type);
			return;
		}
		showcaseBsHandler.handler(showcasePacket, channelContext);
		return;
	}
}

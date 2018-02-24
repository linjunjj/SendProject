package com.linjun.server.handler;


import com.linjun.common.ShowcasePacket;
import com.linjun.common.intf.AbsShowcaseBsHandler;
import com.linjun.common.packets.HeartbeatBody;
import com.linjun.common.packets.LoginReqBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

/**
 * 心跳处理
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class HeartbeatReqHandler extends AbsShowcaseBsHandler<HeartbeatBody> {
	private static Logger log = LoggerFactory.getLogger(HeartbeatReqHandler.class);

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
	public HeartbeatReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<HeartbeatBody> bodyClass() {
		return HeartbeatBody.class;
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
	public Object handler(ShowcasePacket packet, HeartbeatBody bsBody, ChannelContext channelContext) throws Exception {
		//心跳消息,啥也不用做
		System.out.println("这是心跳");
		return null;
	}
}

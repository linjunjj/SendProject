package com.linjun.client;

import com.linjun.common.ShowcaseSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.utils.json.Json;

/**
 * @author tanyaowu
 * 2017年3月26日 下午8:20:51
 */
public class ShowcaseClientAioListener implements ClientAioListener {
	private static Logger log = LoggerFactory.getLogger(ShowcaseClientAioListener.class);

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
	public ShowcaseClientAioListener() {
	}

	/**
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @throws Exception
	 * @author tanyaowu
	 */


	/**
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
		log.info("onAfterConnected channelContext:{}, isConnected:{}, isReconnect:{}", channelContext, isConnected, isReconnect);

		//连接后，需要把连接会话对象设置给channelContext
		channelContext.setAttribute(new ShowcaseSessionContext());

	}

	@Override
	public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception {

	}

	@Override
	public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception {

	}

	/**
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @throws Exception
	 * @author tanyaowu
	 */

	/**
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
		log.info("onAfterSent channelContext:{}, packet:{}, isSentSuccess:{}", channelContext, Json.toJson(packet), isSentSuccess);
	}

	@Override
	public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception {

	}

	@Override
	public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
	}

}

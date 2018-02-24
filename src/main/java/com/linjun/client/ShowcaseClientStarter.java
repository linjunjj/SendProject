package com.linjun.client;


import com.linjun.common.Const;
import com.linjun.common.ShowcasePacket;
import com.linjun.common.Type;
import com.linjun.common.packets.LoginReqBody;
import org.apache.commons.lang3.StringUtils;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;
import org.tio.utils.json.Json;

/**
 *
 * @author tanyaowu
 */
public class ShowcaseClientStarter {
	static String serverIp = "127.0.0.1";
	static int serverPort = Const.PORT;

	private static Node serverNode = new Node(serverIp, serverPort);

	//用来自动连接的，不想自动连接请设为null
	private static ReconnConf reconnConf = new ReconnConf(5000L);

	private static ClientAioHandler aioClientHandler = new ShowcaseClientAioHandler();
	private static ClientAioListener aioListener = new ShowcaseClientAioListener();
	private static ClientGroupContext clientGroupContext = new ClientGroupContext(aioClientHandler, aioListener, reconnConf);

	private static AioClient aioClient = null;

	static ClientChannelContext clientChannelContext;

	public static void command() throws Exception {
		@SuppressWarnings("resource")
		java.util.Scanner sc = new java.util.Scanner(System.in);
		int i = 1;
		StringBuilder sb = new StringBuilder();
		sb.append("使用指南:\r\n");
		sb.append(i++ + "、需要帮助，输入 '?'.\r\n");
		sb.append(i++ + "、登录，输入 'login loginname password'.\r\n");
		sb.append(i++ + "、进入群组，输入 'join group1'.\r\n");
		sb.append(i++ + "、群聊，输入 'groupMsg group1 text'.\r\n");
		sb.append(i++ + "、点对点聊天，输入 'p2pMsg loginname text'.\r\n");

		sb.append(i++ + "、退出程序，输入 'exit'.\r\n");

		System.out.println(sb);

		String line = sc.nextLine(); // 这个就是用户输入的数据
		while (true) {
			if ("exit".equalsIgnoreCase(line)) {
				System.out.println("Thanks for using! bye bye.");
				break;
			} else if ("?".equals(line)) {
				System.out.println(sb);
			}

			processCommand(line);

			line = sc.nextLine(); // 这个就是用户输入的数据
		}

		aioClient.stop();
		System.exit(0);
	}

	public static void main(String[] args) throws Exception {
	    clientGroupContext.setHeartbeatTimeout(5000);
		aioClient = new AioClient(clientGroupContext);
		clientChannelContext = aioClient.connect(serverNode);
		command();
	}

	public static void processCommand(String line) throws Exception {
		if (StringUtils.isBlank(line)) {
			return;
		}

		String[] args = StringUtils.split(line, " ");
		String command = args[0];

		if ("login".equalsIgnoreCase(command)) {
			String deviceid = args[1];


			LoginReqBody loginReqBody = new LoginReqBody();
			loginReqBody.setDeviceID(deviceid);

			ShowcasePacket reqPacket = new ShowcasePacket();
			reqPacket.setType(Type.LOGIN_REQ);
			reqPacket.setBody(Json.toJson(loginReqBody).getBytes(ShowcasePacket.CHARSET));
			Aio.send(clientChannelContext, reqPacket);

		}

	}
}

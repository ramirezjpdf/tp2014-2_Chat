package br.ufrj.tp.protocol;

import br.ufrj.tp.client.Client;

public class ProtocolChatMsg {
	private Client sender;
	private String chatId;
	private String msg;
	
	public ProtocolChatMsg(Client sender, String chatId, String msg) {
		this.sender = sender;
		this.chatId = chatId;
		this.msg = msg;
	}

	public Client getSender() {
		return sender;
	}

	public String getChatId() {
		return chatId;
	}

	public String getMsg() {
		return msg;
	}
	
	
}

package br.ufrj.tp.protocol;

import br.ufrj.tp.client.Client;

public class ProtocolChatEndMsg {
	private Client sender;
	private String chatId;
	
	public ProtocolChatEndMsg(Client sender, String chatId) {
		this.sender = sender;
		this.chatId = chatId;
	}

	public Client getSender() {
		return sender;
	}

	public String getChatId() {
		return chatId;
	}
}

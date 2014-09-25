package br.ufrj.tp.protocol;

import java.util.Set;

import br.ufrj.tp.client.Client;

public class ProtocolChatCreatedMsg {
	private String chatId;
	private Set<Client> clientSet;
	
	public ProtocolChatCreatedMsg(String chatId, Set<Client> clientSet) {
		this.chatId = chatId;
		this.clientSet = clientSet;
	}
	
	public String getChatId() {
		return chatId;
	}
	public Set<Client> getClientSet() {
		return clientSet;
	}
	
	
}

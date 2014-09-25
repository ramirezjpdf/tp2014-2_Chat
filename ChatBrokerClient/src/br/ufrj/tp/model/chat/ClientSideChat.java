package br.ufrj.tp.model.chat;

import java.util.Set;

import br.ufrj.tp.client.Client;

public class ClientSideChat {
	private String chatId;
	private Set<Client> participants;
	
	public ClientSideChat(String chatId, Set<Client> participants) {
		this.chatId = chatId;
		this.participants = participants;
	}

	public ClientSideChat(Set<Client> participants) {
		super();
		this.participants = participants;
	}

	public ClientSideChat(String chatId) {
		this.chatId = chatId;
	}

	public void receiveMsg(Client sender, String msg){
		//TODO
	}
	
	public void informLeaveFrom(Client sender){
		//TODO
	}
	
	public String getChatId() {
		return chatId;
	}
}

package br.ufrj.tp.model.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import br.ufrj.tp.client.Client;
import br.ufrj.tp.model.remoteManager.RemoteManager;
import br.ufrj.tp.protocol.ProtocolChatCreatedMsg;
import br.ufrj.tp.protocol.ProtocolChatEndMsg;
import br.ufrj.tp.protocol.ProtocolChatMsg;

public class ChatManager extends Observable{
	private Client client;
	private Set<Client> clientSet;
	private Map<String, ClientSideChat> clientSideChatMap;
	private RemoteManager remoteManager;
	
	public ChatManager(Client client){
		this.client = client;
		this.clientSet = new HashSet<Client>();
		this.clientSideChatMap = new HashMap<String, ClientSideChat>();
	}
	
	public void forwardMsgoClientSideChat(ProtocolChatMsg chatMsg){
		ClientSideChat chat = clientSideChatMap.get(chatMsg.getChatId());
		chat.receiveMsg(chatMsg.getSender(), chatMsg.getMsg());
	}
	
	public void alertEndChat(ProtocolChatEndMsg protocolChatEndMsgObject) {
		ClientSideChat chat = clientSideChatMap.get(protocolChatEndMsgObject.getChatId());
		chat.informLeaveFrom(protocolChatEndMsgObject.getSender());
	}
	
	public void createClientSideChat(ProtocolChatCreatedMsg protocolChatCreatedMsgObject) {
		ClientSideChat chat = new ClientSideChat(protocolChatCreatedMsgObject.getChatId());
		clientSideChatMap.put(chat.getChatId(), chat);
		//TODO
	}
	
	public void setRemoteManager(RemoteManager rm) {
		this.remoteManager = rm;
	}

	public Client getClient() {
		return client;
	}

	public Set<Client> getClientSet() {
		return clientSet;
	}

	public void setClientSet(Set<Client> clientSet) {
		this.clientSet = clientSet;
		setChanged();
		notifyObservers(clientSet);
	}

}

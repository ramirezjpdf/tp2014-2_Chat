package br.ufrj.tp.model.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

import br.ufrj.tp.client.Client;
import br.ufrj.tp.model.remoteManager.RemoteManager;
import br.ufrj.tp.protocol.ProtocolChatMsg;

public class ChatManager extends Observable{
	private Client client;
	private Set<Client> clientSet;
	private Map<String, ClientSideChat> myChats;
	private RemoteManager remoteManager;
	
	public ChatManager(Client client){
		this.client = client;
		this.clientSet = new HashSet<Client>();
		this.myChats = new HashMap<String, ClientSideChat>();
	}
	
	public void forwardMsg(ProtocolChatMsg chatMsg){
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

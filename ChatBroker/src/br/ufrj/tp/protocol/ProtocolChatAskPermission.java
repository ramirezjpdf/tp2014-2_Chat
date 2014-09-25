package br.ufrj.tp.protocol;

import java.util.Set;

import br.ufrj.tp.client.Client;

public class ProtocolAskChatPermission {

	private Client asker;
	private Set<Client> askedClients;

	public ProtocolAskChatPermission(Client asker, Set<Client> askedClientsSet) {
		this.asker = asker;
		this.askedClients = askedClientsSet;
	}

	public Client getAsker() {
		return asker;
	}

	public Set<Client> getAskedClients() {
		return askedClients;
	}

	
}

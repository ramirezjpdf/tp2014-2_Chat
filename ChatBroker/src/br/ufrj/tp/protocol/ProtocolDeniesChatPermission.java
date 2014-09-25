package br.ufrj.tp.protocol;

import br.ufrj.tp.client.Client;

public class ProtocolDeniesChatPermission {

	private Client asked;
	private Client asker;

	public ProtocolDeniesChatPermission(Client asked, Client asker) {
		this.asked = asked;
		this.asker = asker;
	}

	public Client getAsked() {
		return asked;
	}

	public Client getAsker() {
		return asker;
	}
	

}

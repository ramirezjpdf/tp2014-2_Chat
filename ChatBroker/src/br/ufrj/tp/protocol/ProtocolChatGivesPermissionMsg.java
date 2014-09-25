package br.ufrj.tp.protocol;

import br.ufrj.tp.client.Client;

public class ProtocolChatGivesPermissionMsg {
	private Client asked;
	private Client asker;
	
	public ProtocolChatGivesPermissionMsg(Client asked, Client asker) {
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

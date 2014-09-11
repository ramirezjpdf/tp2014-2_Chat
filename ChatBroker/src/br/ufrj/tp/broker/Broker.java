package br.ufrj.tp.broker;

import java.util.List;

import br.ufrj.tp.client.Client;

public class Broker implements Runnable{
	private List<Client> clientList;
	
	
	public Broker(List<Client> clientList) {
		this.clientList = clientList;
	}


	@Override
	public void run() {
		
	}
	
}

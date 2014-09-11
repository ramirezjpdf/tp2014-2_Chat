package br.ufrj.tp.broker;


import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable{
	private SockConnection sock;
	
	
	public Broker(SockConnection sockBroker) {
		this.sock = sockBroker;
	}


	@Override
	public void run() {
		
	}
	
}

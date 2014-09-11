package br.ufrj.tp.broker;


import br.ufrj.tp.sockBroker.SockBroker;

public class Broker implements Runnable{
	private SockBroker sock;
	
	
	public Broker(SockBroker sockBroker) {
		this.sock = sockBroker;
	}


	@Override
	public void run() {
		
	}
	
}

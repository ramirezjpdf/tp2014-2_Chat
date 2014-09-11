package br.ufrj.tp.broker;


import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable{
	private SockConnection sockConn;
	
	
	public Broker(SockConnection sockConn) {
		this.sockConn = sockConn;
	}


	@Override
	public void run() {
		
	}
	
}

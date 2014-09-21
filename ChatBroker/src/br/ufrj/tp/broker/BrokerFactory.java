package br.ufrj.tp.broker;

import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	private int quantbrokersmade = 0;
	
	public Broker getBroker(SockConnection sockConn){
		quantbrokersmade++;
		return new Broker(sockConn);
	}
	
	public int getBrokerquant(){
		return quantbrokersmade;;;
	}
}

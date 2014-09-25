package br.ufrj.tp.broker;

import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	public Broker getBroker(SockConnection sockConn){
		Broker newbroker = new Broker(sockConn);
		return newbroker;
	}
}

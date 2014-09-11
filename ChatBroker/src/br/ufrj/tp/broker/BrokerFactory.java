package br.ufrj.tp.broker;

import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	public Broker getBroker(SockConnection sockConn){
		return new Broker(sockConn);
	}
}

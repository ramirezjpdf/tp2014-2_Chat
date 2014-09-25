package br.ufrj.tp.broker;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	private Server server;
	
	
	public BrokerFactory(Server server){
		this.server = server;
	}
	
	public Broker getBroker(SockConnection sockConn){
		Broker newbroker = new Broker(sockConn, this);
		server.addBroker(newbroker);
		return newbroker;
	}
}

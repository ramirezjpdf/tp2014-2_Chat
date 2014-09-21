package br.ufrj.tp.broker;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	public Server servidor;
	private int quantbrokersmade = 0;
	
	public BrokerFactory(Server serv){
		servidor = serv;
	}
	
	public Broker getBroker(SockConnection sockConn){
		quantbrokersmade++;
		return new Broker(sockConn);
	}
	
	public int getBrokerquant(){
		return quantbrokersmade;
	}
}

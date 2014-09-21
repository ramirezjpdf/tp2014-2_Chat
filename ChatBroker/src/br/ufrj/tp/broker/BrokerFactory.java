package br.ufrj.tp.broker;

import br.ufrj.tp.server.Database;
import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	public Database db;
	private int quantbrokersmade = 0;
	
	public BrokerFactory(Database db){
		this.db = db;
	}
	
	public Broker getBroker(SockConnection sockConn){
		quantbrokersmade++;
		return new Broker(sockConn, db);
	}
	
	public int getBrokerquant(){
		return quantbrokersmade;
	}
}

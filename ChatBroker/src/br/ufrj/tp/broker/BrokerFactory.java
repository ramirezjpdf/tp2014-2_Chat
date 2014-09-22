package br.ufrj.tp.broker;

import java.util.ArrayList;

import br.ufrj.tp.server.Database;
import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	public Database db;
	private int quantbrokersmade = 0;
	
	
	public BrokerFactory(Database db){
		this.db = db;
	}
	
	public Broker getBroker(SockConnection sockConn){
		quantbrokersmade++;
		Broker newbroker = new Broker(sockConn, db);
		db.addBroker(newbroker);
		return newbroker;
	}
	
	public int getBrokerquant(){
		return quantbrokersmade;
	}
}

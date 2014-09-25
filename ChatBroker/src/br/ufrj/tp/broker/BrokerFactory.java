package br.ufrj.tp.broker;

import java.util.ArrayList;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class BrokerFactory {
	
	private Server server;
	private int quantbrokersmade = 0;
	
	
	public BrokerFactory(Server server){
		this.server = server;
	}
	
	public Broker getBroker(SockConnection sockConn){
		Broker newbroker = new Broker(sockConn, this);
		server.addBroker(newbroker);
		return newbroker;
	}
	
	public Broker getBrokerForChat(String nome){
		return server.getBroker(nome);
	}
	
	public String getUserList(){
		return server.getUserList();
	}
	
	public boolean existeCliente(String nome){
		return server.existeCliente(nome);
	}
	
	public int getBrokerquant(){
		return quantbrokersmade;
	}
}

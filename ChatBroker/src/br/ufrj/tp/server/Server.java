package br.ufrj.tp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;
import br.ufrj.tp.sockListener.SockListener;

public class Server{

	//TODO = Tratar exceções
	private SockListener welcomeSocket;
	private BrokerFactory brokerFactory;
	private Database data;
	
	public Server(SockListener listener) {
		this.welcomeSocket = listener;
		data = new Database();
		this.brokerFactory = new BrokerFactory(data);
	}
	
	public void initiateChat(Chat chat){
		data.initiateChat(chat);
    }
    
    public void closeChat(Chat chat){
    	data.closeChat(chat);
    }
    
    public void createChat(List<Broker> participants){
    	data.createChat(participants);
    }
	
	public void start()  throws IOException {
		
		welcomeSocket.listen(brokerFactory);
		
	}
}

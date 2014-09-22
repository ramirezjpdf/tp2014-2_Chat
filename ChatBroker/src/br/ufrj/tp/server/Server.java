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

public class Server implements Runnable{

	//TODO = Tratar exceções
	private int i = 0;
	private ArrayList<Thread> threads;
	private ArrayList<SockListener> welcomeSockets;
	private BrokerFactory brokerFactory;
	private Database data;
	
	public Server(SockListener listener) {
		this.welcomeSockets.add(listener);
		data = new Database();
		this.brokerFactory = new BrokerFactory(data);
		this.threads = new ArrayList<Thread>();
		threads.add(new Thread(this));
	}
	
	public Server(ArrayList<SockListener> listener) {
		this.welcomeSockets = listener;
		data = new Database();
		this.brokerFactory = new BrokerFactory(data);
		this.threads = new ArrayList<Thread>();
		for(int k =0; k < welcomeSockets.size(); k++){
			threads.add(new Thread(this));
		}
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
	
    //TODO = Proteção decente de acesso aos arrays
	public void start() throws IOException{
		for(int j = 0; j < threads.size(); j++){
			threads.get(j).start();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
	}

	@Override
	public void run() {
		
		try {
			welcomeSockets.get(i).listen(brokerFactory, i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

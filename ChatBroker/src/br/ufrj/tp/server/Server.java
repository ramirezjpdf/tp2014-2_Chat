package br.ufrj.tp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.sockListener.SockListener;

public class Server implements Runnable{

	
	//TODO = Proteção decente de acesso aos arrays
	//TODO = Tratar exceções
	private int i = 0;
	private ArrayList<Thread> threads;
	private ArrayList<SockListener> welcomeSockets;
	private BrokerFactory brokerFactory;
	private Map<String, Chat> myChats;
	
	public Server(SockListener listener) {
		this.welcomeSockets.add(listener);
		this.brokerFactory = new BrokerFactory(this);
		this.threads = new ArrayList<Thread>();
		threads.add(new Thread(this));
		myChats = new HashMap<String, Chat>();
	}
	
	public Server(ArrayList<SockListener> listener) {
		this.welcomeSockets = listener;
		this.brokerFactory = new BrokerFactory(this);
		this.threads = new ArrayList<Thread>();
		for(int k =0; k < welcomeSockets.size(); k++){
			threads.add(new Thread(this));
		}
		myChats = new HashMap<String, Chat>();
	}
	
	public void initiateChat(Chat chat){
    	myChats.put(chat.getId(), chat);
    }
    
    public void closeChat(Chat chat){
    	myChats.remove(chat.getId());
    }
    
    public void createChat(List<Broker> participants){
    	Chat newChat = new Chat(participants);
    	myChats.put(newChat.getId(), newChat);
    }
	
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

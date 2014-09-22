package br.ufrj.tp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;

public class Database {

	private HashMap<String, Chat> myChats;
	private ArrayList<Client> clients;
	private ArrayList<Broker> brokers;
	
	public Database(){
		myChats = new HashMap<String, Chat>();
		clients = new ArrayList<Client>(); 
		brokers = new ArrayList<Broker>();
	}
	
	public synchronized void addClient(Client cli){
		clients.add(cli);
	}
	
	public synchronized void removeClient(Client cli){
		clients.remove(cli);
	}
	
	public synchronized ArrayList<Client> getClientList(){
		return clients;
	}
	
	public synchronized boolean existeCliente(String nome){
		
		for(Client a: clients){
			System.out.println(nome + " " + a.getUsername());
			if (a.getUsername().equals(nome)){
				System.out.println(a.getUsername().equals(nome));
				return true;
			}
			System.out.println(a.getUsername().equals(nome));
		}
		return false;
	}
	
	public synchronized boolean existeChat(String nome){
		
		for(Chat a: myChats.values()){
			if ((a.getUser1() == nome) || (a.getUser2() == nome)){
				return true;
			}
		}
		return false;
	}
	
	public synchronized void addBroker(Broker b){
		brokers.add(b);
	}
	
	public Broker findBroker(String nome){
		for (Broker a: brokers){
			if (a.getClientname() == nome) return a;
		}
		return null;
		
	}
	
	public synchronized Chat getChat(String nome){
		for(Chat a: myChats.values()){
			if ((a.getUser1() == nome) || (a.getUser2() == nome)){
				return a;
			}
		}
		return null;
	}

	public HashMap<String, Chat> getChats() {
		return myChats;
	}

	public void setChats(HashMap<String, Chat> myChats) {
		this.myChats = myChats;
	}
	
	public ArrayList<Client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}
	
	public Broker getBroker(String name){
		for (Broker b: brokers){
			System.out.println("ccc" + name);
			System.out.println("ddd" + b.getClientname());
			if (b.getClientname().compareTo(name) == 0) return b;
		}
		return null;
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
	
}

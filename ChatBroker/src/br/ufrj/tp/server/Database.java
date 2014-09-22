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
	
	public Database(){
		myChats = new HashMap<String, Chat>();
		clients = new ArrayList<Client>(); 
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

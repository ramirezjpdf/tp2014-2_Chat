package br.ufrj.tp.broker;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;
import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable, Observer, Comparable<Broker>{
    private SockConnection sockConn;
    private Client client;
    private Map<String, Chat> myChats;
    
    
    public Broker(SockConnection sockConn) {
        this(sockConn, null);
    }
    
    public Broker(SockConnection sockConn, Client client){
    	this.sockConn = sockConn;
    	this.client = client;
    	this.myChats = new HashMap<String, Chat>();
    }

    public void sendMsgToClient(byte[] msg){
    	try{
    		sockConn.send(msg);
    	} catch (IOException e) {
            System.out.println("ERROR - Could not send message from Broker to client");
            e.printStackTrace();
        }
    	
    }
    
    public void initiateChatWithMe(Chat chat){
    	myChats.put(chat.getId(), chat);
    }
    
    public void closeChatWithMe(Chat chat){
    	myChats.remove(chat.getId());
    }
    
    public void createChat(List<Broker> participants){
    	participants.add(this);
    	Chat newChat = new Chat(participants);
    	for(Broker broker : participants){
    		if (broker.equals(this)) continue;
    		broker.initiateChatWithMe(newChat);
    	}
    	myChats.put(newChat.getId(), newChat);
    }

    @Override
    public void run() {
        System.out.println("Broker instantiated");
        try {
            sockConn.send("OK".getBytes());
            String msg = new String(sockConn.recv());
            while (!msg.trim().equals("END")){
                sockConn.send(msg.toUpperCase().getBytes());
                msg = new String(sockConn.recv());
                System.out.println("Received from Client: " + msg + " => " + msg.equals("END"));
                System.out.println("msg.equals(\"END\"): " + msg.trim().equals("END"));
            }
            System.out.println("Closing broker");
        } catch (IOException e) {
            System.out.println("ERROR - Could not send message from Broker to client");
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Observable obs, Object arg){
        //TODO update client with the new client added in the server's client list
    }
    
    @Override
	public int compareTo(Broker anotherBroker) {
		return client.getUsername().compareTo(anotherBroker.getClient().getUsername());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Broker other = (Broker) obj;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		return true;
	} 
	
	public Client getClient() {
		return client;
	}
}

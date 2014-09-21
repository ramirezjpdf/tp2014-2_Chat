package br.ufrj.tp.broker;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;
import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable, Observer, Comparable<Broker>{
    private SockConnection sockConn;
    private ArrayList<Client> clients;
    private Server server;
    
    public Broker(SockConnection sockConn) {
    	this.sockConn = sockConn;
        clients = new ArrayList<Client>(); 
    }
    
    public Broker(SockConnection sockConn, Server serv) {
    	this.sockConn = sockConn;
        clients = new ArrayList<Client>(); 
        server = serv;
    }
    
    public Broker(SockConnection sockConn, Server serv, Client client){
    	this.sockConn = sockConn;
    	clients = new ArrayList<Client>();
    	clients.add(client);
    	server = serv;
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
    	server.initiateChat(chat);
    }
    
    public void closeChatWithMe(Chat chat){
    	server.closeChat(chat);
    }
    
    public void createChat(List<Broker> participants){
    	participants.add(this);
    	server.createChat(participants);
    }

    @Override
    public void run() {
        System.out.println("Broker instantiated");
        try {
            sockConn.send("Bem vindo ao Chat de Teleprocessamento!".getBytes());
            sockConn.send("\nPrecisamos do seu nome, pode enviá-lo?".getBytes());
            String msg = new String(sockConn.recv());
            clients.add(new Client(msg));
            sockConn.send("Lista de usuarios online.\n".toUpperCase().getBytes());
            for(Client c: clients){
            	String mensagem = c.getUsername() + "\n"; 
            	sockConn.send(mensagem.toUpperCase().getBytes());
            }
            msg = new String(sockConn.recv());
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
    
    
    //return client.getUsername().compareTo(anotherBroker.getClient().getUsername());
    @Override
	public int compareTo(Broker anotherBroker) {
		return 1;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return false;
	} 
	
}

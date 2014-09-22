package br.ufrj.tp.broker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;
import br.ufrj.tp.server.Database;
import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable, Observer, Comparable<Broker>{
    private SockConnection sockConn;
    private Database db;
    
    public Broker(SockConnection sockConn) {
    	this.sockConn = sockConn;
    }
    
    public Broker(SockConnection sockConn, Database db) {
    	this.sockConn = sockConn;
        this.db = db;
    }
    
    public Broker(SockConnection sockConn, Server serv, Client client){
    	this.sockConn = sockConn;
    	this.db = db;
    	db.addClient(client);
    }

    public void sendMsgToClient(byte[] msg){
    	try{
    		sockConn.send(msg);
    	} catch (IOException e) {
            System.out.println("ERROR - Could not send message from Broker to client");
            e.printStackTrace();
        }
    	
    }
    
    public synchronized Database getDatabase(){
    	return db;
    }
    
    public void initiateChatWithMe(Chat chat){
    	db.initiateChat(chat);
    }
    
    public void closeChatWithMe(Chat chat){
    	db.closeChat(chat);
    }
    
    public void createChat(List<Broker> participants){
    	participants.add(this);
    	db.createChat(participants);
    }

    @Override
    public void run() {
        System.out.println("Broker instantiated");
        String clientname = "";
        try {
            sockConn.send("Bem vindo ao Chat de Teleprocessamento!".getBytes());
            sockConn.send("\nPrecisamos do seu nome, pode enviá-lo?".getBytes());
            String msg = new String(sockConn.recv());
            Client cli = new Client(msg);
            clientname = msg;
            db.addClient(cli);
            sockConn.send("Lista de usuarios online.\n".getBytes());
            for(Client c: db.getClientList()){
            	String mensagem = c.getUsername(); 
            	sockConn.send(mensagem.getBytes());
            }
            sockConn.send("Deseja iniciar conversa?\n".getBytes());
            sockConn.send("Digite o nome de quem deseja conversar\n".getBytes());
            sockConn.send("ou digite WAIT para esperar alguem que queira conversar com voce.\n".getBytes());
            msg = new String(sockConn.recv());
            while (!(db.existeCliente(msg)) && (msg != "WAIT")){ 
            	System.out.println(msg);
            	sockConn.send("Nome nao consta na lista.\n".toUpperCase().getBytes());
            	sockConn.send("Deseja iniciar conversa?\n".toUpperCase().getBytes());
                sockConn.send("Digite o nome de quem deseja conversar\n".toUpperCase().getBytes());
                sockConn.send("ou digite WAIT para esperar alguem que queira conversar com voce.\n".toUpperCase().getBytes());
            	msg = new String(sockConn.recv());
            }
            /*if (msg == "WAIT"){
            	
            }*/
            
            
            
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

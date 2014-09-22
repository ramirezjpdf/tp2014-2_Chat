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
    private Client client;
    private Chat chatemandamento;
   
    public Broker(SockConnection sockConn) {
    	this.sockConn = sockConn;
    }
    
    public Broker(SockConnection sockConn, Database db) {
    	this.sockConn = sockConn;
        this.db = db;
    }
    
    public Broker(SockConnection sockConn, Database db, Server serv, Client client){
    	this.sockConn = sockConn;
    	this.db = db;
    	db.addClient(client);
    	this.client = client;
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

	public String getClientname() {
		return client.getUsername();
	}

	public void setClientname(String clientname) {
		client.setUsername(clientname);
	} 
    
    @Override
    public void run() {
   
        System.out.println("Broker instantiated");
        try {
        	String msg;
        	
            sockConn.send("Bem vindo ao Chat de Teleprocessamento!".getBytes());
            
            if (client == null){
	            sockConn.send("\nPrecisamos do seu nome, pode envi�-lo?".getBytes());
	            msg = new String(sockConn.recv());	            
	            client = new Client(msg);
	            db.addClient(client);
            }
            
            sockConn.send("Lista de usuarios online.\n".getBytes());
            String mensagem = "";
            
            /* Talvez o concat n�o seja necess�rio para substituir
             * o sockConn.send. Fiz isso porqu� aparentemente todos os nomes n�o apareciam
             * mas pode ser bug do SocketTest 3.0.0.
             */
            for(Client c: db.getClientList()){
            	mensagem = mensagem.concat(c.getUsername()); 
            }
            
            sockConn.send(mensagem.getBytes());
            sockConn.send("Deseja iniciar conversa?\n".getBytes());
            sockConn.send("Digite o nome de quem deseja conversar,\n".getBytes());
            sockConn.send(("digite WAIT para esperar alguem que queira conversar com voce, " +
            		 "ou END para encerrar o Chat de Teleprocessamento.\n").getBytes());
            msg = new String(sockConn.recv());
            
            //loop principal do chat
            while (!msg.startsWith("END")){
	            //TODO = Retirar gambiarra para funcionar com SocketTest v3.0.0
	            while (!(db.existeCliente(msg)) && (!msg.startsWith("WAIT"))){ 
	            	if (msg.equals("WAIT")) break;
	            	sockConn.send("Nome nao consta na lista.\n".toUpperCase().getBytes());
	            	sockConn.send("Deseja iniciar conversa?\n".toUpperCase().getBytes());
	                sockConn.send("Digite o nome de quem deseja conversar\n".toUpperCase().getBytes());
	                sockConn.send("ou digite WAIT para esperar alguem que queira conversar com voce.\n".toUpperCase().getBytes());
	            	msg = new String(sockConn.recv());
	            }
	            if (msg.startsWith("WAIT")){
	            	while(!db.existeChat(getClientname())){}
	            } else {
	            	ArrayList<Broker> listadechat = new ArrayList<Broker>();
	            	listadechat.add(db.getBroker(msg));
	            	System.out.println("aaa" + listadechat.get(0).getClientname());
	            	createChat(listadechat);
	            	
	            }
	            chatemandamento = db.getChat(getClientname());
	            
	            while (!msg.trim().equals("END")){
	            	msg = new String(sockConn.recv());
	            	chatemandamento.sendMsg(msg.getBytes());
	            }
	            
	            sockConn.send("Lista de usuarios online.\n".getBytes());
	            mensagem = "";
	            
	            for(Client c: db.getClientList()){
	            	mensagem = mensagem.concat(c.getUsername()); 
	            }
	            
	            sockConn.send(mensagem.getBytes());
	            sockConn.send("Deseja iniciar conversa?\n".getBytes());
	            sockConn.send("Digite o nome de quem deseja conversar,\n".getBytes());
	            sockConn.send(("digite WAIT para esperar alguem que queira conversar com voce, " +
	            		 "ou END para encerrar o Chat de Teleprocessamento.\n").getBytes());
	            msg = new String(sockConn.recv());
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
		return client.getUsername().compareTo(anotherBroker.getClientname());
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

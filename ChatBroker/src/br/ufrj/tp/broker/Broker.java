package br.ufrj.tp.broker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import java.util.TreeSet;

import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;
import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable, Observer, Comparable<Broker>{
    private SockConnection sockConn;
    private BrokerFactory bf;
    private Client client;
    private Chat chatemandamento;
    
   
    public Broker(SockConnection sockConn) {
    	this.sockConn = sockConn;
    	this.client = new Client("");
    }
    
    public Broker(SockConnection sockConn, BrokerFactory bf) {
    	this.sockConn = sockConn;
    	this.client = new Client("");
    	this.bf = bf;
    }
    
    public Broker(SockConnection sockConn, BrokerFactory bf, Client client){
    	this.sockConn = sockConn;
    	this.client = new Client("");
    	this.bf = bf;
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
    
    public void createChat(String nome){
    	TreeSet<Broker> treeset = new TreeSet<Broker>();
    	treeset.add(this);
    	treeset.add(bf.getBrokerForChat(nome));
    	chatemandamento = new Chat(treeset);
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
            
            if ((client == null) || (client.getUsername() == null) || 
            		(client.getUsername() == "")){
	            sockConn.send("\nPrecisamos do seu nome, pode enviá-lo?".getBytes());
	            msg = new String(sockConn.recv());	            
	            client = new Client(msg);
            }
            
            sockConn.send("Lista de usuarios online.\n".getBytes());
            String mensagem = bf.getUserList();
            
            sockConn.send(mensagem.getBytes());
            sockConn.send("Deseja iniciar conversa?\n".getBytes());
            sockConn.send("Digite o nome de quem deseja conversar,\n".getBytes());
            sockConn.send(("digite WAIT para esperar alguem que queira conversar com voce, " +
            		 "ou END para encerrar o Chat de Teleprocessamento.\n").getBytes());
            msg = new String(sockConn.recv());
            
            //loop principal do chat
            while (!msg.startsWith("END")){
	            //TODO = Retirar gambiarra para funcionar com SocketTest v3.0.0
	            while (!(bf.existeCliente(msg)) && (!msg.startsWith("WAIT"))){ 
	            	if (msg.equals("WAIT")) break;
	            	sockConn.send("Nome nao consta na lista.\n".toUpperCase().getBytes());
	            	sockConn.send("Deseja iniciar conversa?\n".toUpperCase().getBytes());
	                sockConn.send("Digite o nome de quem deseja conversar\n".toUpperCase().getBytes());
	                sockConn.send("ou digite WAIT para esperar alguem que queira conversar com voce.\n".toUpperCase().getBytes());
	            	msg = new String(sockConn.recv());
	            }
	            if (msg.startsWith("WAIT")){
	            	msg = new String(sockConn.recv());
	            	while(!msg.startsWith("[CHATCREATED]"));
	            } else {
	            	createChat(msg);
	            }
	            
	            while (!msg.trim().equals("END")){
	            	msg = new String(sockConn.recv());
	            	chatemandamento.sendMsg(msg.getBytes());
	            }
	            
	            sockConn.send("Lista de usuarios online.\n".getBytes());
	            mensagem = bf.getUserList();
	            
	            
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
    	if (client.getUsername() == null){
    		return 1;
    	}else if (anotherBroker.getClientname() == null) return -1;
    	else return client.getUsername().compareTo(anotherBroker.getClientname());
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

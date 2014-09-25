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
import br.ufrj.tp.protocol.ProtocolAction;
import br.ufrj.tp.protocol.ProtocolManager;
import br.ufrj.tp.protocol.ProtocolMsgParsedObj;
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

	public String getClientName() {
		return client.getUsername();
	}
	

	@Override
	public void run() {
		System.out.println("Broker instantiated");

		ProtocolManager protocolManager = new ProtocolManager();
		ProtocolMsgParsedObj po;
		handshake(protocolManager);

		byte[] msg = null;
		while(true){
			try{
				msg = sockConn.recv();
			}catch(IOException e){
				//TODO Handle
			}
			
			po = protocolManager.parseWrappedMsg(msg);
			switch(po.getAction()){
				case CHAT:
					handleCaseChat(protocolManager, po);
					break;
				case CHATEND:
					handleCaseChatEnd(protocolManager, po);
					break;
				case CHATCREATED:
					handleCaseChatCreated(protocolManager, po);
					break;
				case CHATASKPERMISSION:
					handleCaseChatAskPermission(protocolManager, po);
					break;
				case CHATGIVESPERMISSION:
					handleCaseChatGivesPermission(protocolManager, po);
					break;
				case CHATDENIESPERMISSION:
					handleCaseChatDeniesPermission(protocolManager, po);
					break;
				default:
					break;
			}
		}
	}

	private void handshake(ProtocolManager protocolManager) {
		sendMsgToClient(protocolManager.wrapConnectionStatusMsg("OK"));
		
		byte[] msg = null;
		try {
			msg = sockConn.recv();
		} catch (IOException e) {
			System.out.println("ERROR - Could not receive login message from Client");
			//TODO Handle
		}
		
		ProtocolMsgParsedObj po = protocolManager.parseWrappedMsg(msg);
		if (po.getAction() == ProtocolAction.CHATLOGIN) {
			handleCaseChatLogin(po);
		}else {
			//TODO throws Exception 
		}
	}

	private void handleCaseChatDeniesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj po) {
		// TODO Auto-generated method stub
		
	}

	private void handleCaseChatGivesPermission(ProtocolManager protocolManager,	ProtocolMsgParsedObj po) {
		// TODO Auto-generated method stub
		
	}

	private void handleCaseChatAskPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj po) {
		// TODO Auto-generated method stub
		
	}

	private void handleCaseChatCreated(ProtocolManager protocolManager, ProtocolMsgParsedObj po) {
		// TODO Auto-generated method stub
		
	}

	private void handleCaseChatEnd(ProtocolManager protocolManager, ProtocolMsgParsedObj po) {
		// TODO Auto-generated method stub
		
	}

	private void handleCaseChat(ProtocolManager protocolManager, ProtocolMsgParsedObj po) {
		// TODO Auto-generated method stub
		
	}

	private void handleCaseChatLogin(ProtocolMsgParsedObj po) {
		client.setUsername(po.getArgs().get(0));
		
	}

	@Override
	public void update(Observable obs, Object arg){
		//TODO update client with the new client added in the server's client list


	}
	

	@Override
	public int compareTo(Broker anotherBroker) {
		if (client.getUsername() == null){
			return 1;
		}else if (anotherBroker.getClientName() == null) return -1;
		else return client.getUsername().compareTo(anotherBroker.getClientName());
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

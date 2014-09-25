package br.ufrj.tp.broker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

import br.ufrj.tp.chat.Chat;
import br.ufrj.tp.client.Client;
import br.ufrj.tp.protocol.ProtocolAction;
import br.ufrj.tp.protocol.ProtocolManager;
import br.ufrj.tp.protocol.ProtocolMsgParsedObj;
import br.ufrj.tp.sockConnection.SockConnection;
import br.ufrj.tp.utils.ObservableSet;

public class Broker implements Runnable, Observer, Comparable<Broker>{
	private SockConnection sockConn;
	private BrokerFactory bf;
	private Client client;
	private Chat chatemandamento;
	private Set<Client> onlineClients;


	public Broker(SockConnection sockConn) {
		this.sockConn = sockConn;
		this.client = new Client("");
		this.onlineClients = new HashSet<Client>();
	}

	public void sendMsgToClient(byte[] msg){
		try{
			sockConn.send(msg);
		} catch (IOException e) {
			System.out.println("ERROR - Could not send message from Broker to client");
			e.printStackTrace();
		}

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
		sendListOfOnlineClients();
		
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
		
		System.out.println(msg + "SGAGASGASKGKASJG");
		ProtocolMsgParsedObj po = protocolManager.parseWrappedMsg(msg);
		if (po.getAction() == ProtocolAction.CHATLOGIN) {
			handleCaseChatLogin(po);
		}else {
			System.out.println(msg);
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
	
	private void sendListOfOnlineClients() {
		ProtocolManager po = new ProtocolManager();
		System.out.println("Sending online clients: " + onlineClients);
		byte[] msg = po.wrapListMsg(onlineClients);
		sendMsgToClient(msg);
	}

	@Override
	public void update(Observable obs, Object arg){
		//FIXME Do a safe cast here!
		ObservableSet<Broker> onlineBrokers = (ObservableSet<Broker>)obs;
		System.out.println("SERVER ONLINE BROKERS SIZE: " + onlineBrokers.size());
		onlineClients = new HashSet<Client>();

		for (Iterator<Broker> iter = onlineBrokers.iterator(); iter.hasNext();) {
			Broker broker = (Broker)iter.next();
			onlineClients.add(broker.client);
		}
		
		if (!client.getUsername().isEmpty()) sendListOfOnlineClients();		
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

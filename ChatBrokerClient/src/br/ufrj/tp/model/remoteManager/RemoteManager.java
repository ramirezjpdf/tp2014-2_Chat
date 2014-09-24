package br.ufrj.tp.model.remoteManager;

import java.io.IOException;

import br.ufrj.tp.client.Client;
import br.ufrj.tp.model.chat.ChatManager;
import br.ufrj.tp.protocol.ProtocolChatMsg;
import br.ufrj.tp.protocol.ProtocolManager;
import br.ufrj.tp.protocol.ProtocolMsgParsedObj;
import br.ufrj.tp.sockConnection.SockConnection;

public class RemoteManager implements Runnable{
	private SockConnection sockConn;
	private ChatManager chatManager;
	
	public RemoteManager(SockConnection sockConn, ChatManager chatManager){
		this.sockConn = sockConn;
		this.chatManager = chatManager;
	}
	
	
	private void getClientInitialListFromRemote(ProtocolManager protocolManager) throws IOException{
		byte[] listMsg = sockConn.recv();
		ProtocolMsgParsedObj parsedObj = protocolManager.parseWrappedMsg(listMsg);
		try{
			chatManager.setClientSet(protocolManager.makeClientSetFromListMsg(parsedObj));
		}catch(IllegalArgumentException e){
			throw new IOException("The server could not send a list of the current online clients");
		}
	}

	@Override
	public void run() {
		ProtocolManager protocolManager = new ProtocolManager();
		try{
			getClientInitialListFromRemote(protocolManager);
		}catch(IOException e){
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		byte[] msg = null;
		while(true){
			try{
				msg = sockConn.recv();
			}catch(IOException e){
				//TODO HAndle
			}
			
			ProtocolMsgParsedObj po = protocolManager.parseWrappedMsg(msg);
			switch(po.getAction()){
				case LIST:
					handleCaseList(protocolManager, po);
					break;
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
			}
		}
	}
	
	private void handleCaseList(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		try{
			chatManager.setClientSet(protocolManager.makeClientSetFromListMsg(parsedObject));
		}catch(IllegalArgumentException e){
			System.out.println("Error when trying to get a list of the current online clients");
		}
	}
	
	private void handleCaseChat(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		try{
			ProtocolChatMsg protocolChatMsgObject = protocolManager.makeProtocolChatMsgObj(parsedObject);
		}catch(IllegalArgumentException e){
			System.out.println("Error when trying to get a list of the current online clients");
		}
	}
	
	private void handleCaseChatEnd(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		
	}
	
	private void handleCaseChatCreated(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		
	}
	
	private void handleCaseChatAskPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		
	}
	
	private void handleCaseChatGivesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		
	}
	
	private void handleCaseChatDeniesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		
	}

}

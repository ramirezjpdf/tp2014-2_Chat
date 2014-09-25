package br.ufrj.tp.model.remoteManager;

import java.io.IOException;

import br.ufrj.tp.model.chat.ChatManager;
import br.ufrj.tp.protocol.ProtocolAction;
import br.ufrj.tp.protocol.ProtocolChatAskPermission;
import br.ufrj.tp.protocol.ProtocolChatCreatedMsg;
import br.ufrj.tp.protocol.ProtocolChatDeniesPermission;
import br.ufrj.tp.protocol.ProtocolChatEndMsg;
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
	
	
	public void handshake(ProtocolManager protocolManager) throws IOException{
		byte[] statusMsg = sockConn.recv();
		ProtocolMsgParsedObj parsedObj = protocolManager.parseWrappedMsg(statusMsg);
		if(!(parsedObj.getAction().equals(ProtocolAction.CONNECTION) || !parsedObj.getArgs().get(0).equals("OK"))){
			throw new IOException("ERROR on estabilishing connection with server");
		}
		
		byte[] chatLoginMsg = protocolManager.wrapChatLoginMsg(chatManager.getClient());
		sockConn.send(chatLoginMsg);
		
		byte[] listMsg = sockConn.recv();
		parsedObj = protocolManager.parseWrappedMsg(listMsg);
		try{
			chatManager.setClientSet(protocolManager.makeClientSetFromListMsg(parsedObj));
		}catch(IllegalArgumentException e){
			throw new IOException("The server could not send a list of the current online clients");
		}
	}

	@Override
	public void run() {
		ProtocolManager protocolManager = new ProtocolManager();		
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
			chatManager.forwardMsgoClientSideChat(protocolChatMsgObject);
		}catch(IllegalArgumentException e){
			System.out.println("Error when trying to receive message");
		}
	}
	
	private void handleCaseChatEnd(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		try{
			ProtocolChatEndMsg protocolChatEndMsgObject = protocolManager.makeProtocolChatEndMsgObj(parsedObject);
			chatManager.alertEndChat(protocolChatEndMsgObject);
		}catch(IllegalArgumentException e){
			System.out.println("Error when trying to end a chat");
		}
	}
	
	private void handleCaseChatCreated(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		try{
			ProtocolChatCreatedMsg protocolChatCreatedMsgObject = protocolManager.makeProtocolChatCreatedMsgObj(parsedObject);
			chatManager.createClientSideChat(protocolChatCreatedMsgObject);
			//TODO
		}catch(IllegalArgumentException e){
			System.out.println("Error when trying to create a chat");
		}
	}
	
	private void handleCaseChatAskPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		ProtocolChatAskPermission protocolAskChatPermissionObject = protocolManager.makeProtocolChatAskPermission(parsedObject);
		//TODO Tomar acoes para solicitar permissao dos clientes solicitados para o chat.
	}
	
	private void handleCaseChatGivesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		//TODO
	}
	
	private void handleCaseChatDeniesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		ProtocolChatDeniesPermission protocolDeniesChatPermissionObject = protocolManager.makeProtocolChatDeniesPermission(parsedObject);
		//TODO Tomar acoes para avisar cliente solicitante sobre recusa de iniciar chat do cliente solicitado.
	}

}

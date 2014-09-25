package br.ufrj.tp.model.remoteManager;

import java.io.IOException;

import br.ufrj.tp.model.chat.ChatManager;
import br.ufrj.tp.protocol.ProtocolChatCreatedMsg;
import br.ufrj.tp.protocol.ProtocolChatEndMsg;
import br.ufrj.tp.protocol.ProtocolAskChatPermission;
import br.ufrj.tp.protocol.ProtocolChatMsg;
import br.ufrj.tp.protocol.ProtocolDeniesChatPermission;
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
		ProtocolAskChatPermission protocolAskChatPermissionObject = protocolManager.makeProtocolAskChatPermission(parsedObject);
		//TODO Tomar acoes para solicitar permissao dos clientes solicitados para o chat.
	}
	
	private void handleCaseChatGivesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		//TODO
	}
	
	private void handleCaseChatDeniesPermission(ProtocolManager protocolManager, ProtocolMsgParsedObj parsedObject){
		ProtocolDeniesChatPermission protocolDeniesChatPermissionObject = protocolManager.makeProtocolDeniesChatPermission(parsedObject);
		//TODO Tomar acoes para avisar cliente solicitante sobre recusa de iniciar chat do cliente solicitado.
	}

}

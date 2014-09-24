package br.ufrj.tp.protocol;

import java.util.Collection;

import br.ufrj.tp.client.Client;

public class ProtocolManager {
	private byte[] wrap(ProtocolAction action, String ... args){
		StringBuilder builder = new StringBuilder("[" + action.name() + "]");
		for (String s : args){
			builder.append(s + ";");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString().getBytes();
	}
	
	public byte[] wrapChatMsg(Client sender, Client receiver, String msg){
		return wrap(ProtocolAction.CHAT, sender.getUsername(), receiver.getUsername(), msg);
	}
	
	public byte[] wrapListMsg(Collection<? extends Client> clients){
		String [] clientUsernames = new String[clients.size()];
		int i = 0;
		for (Client client : clients){
			clientUsernames[i] = client.getUsername();
			i++;
		}
		return wrap(ProtocolAction.LIST, clientUsernames);
	}
	
	public byte[] wrapChatAskPermissionMsg(Client asker, Client asked){
		return wrap(ProtocolAction.CHATASKPERMISSION, asker.getUsername(), asked.getUsername());
	}
	
	public byte[] wrapChatGivesPermissionMsg(Client asked, Client asker){
		return wrap(ProtocolAction.CHATGIVESPERMISSION, asked.getUsername(), asker.getUsername());
	}
	
	public byte[] wrapChatDeniesPermissionMsg(Client asked, Client asker){
		return wrap(ProtocolAction.CHATDENIESPERMISSION, asked.getUsername(), asker.getUsername());
	}
	
	public byte[] wrapChatCreatedMSg(Client asker, Client asked){
		return wrap(ProtocolAction.CHATCREATED, asker.getUsername(), asked.getUsername());
	}
	
	public byte[] wrapChatEndMsg(Client sender, String chatId){
		return wrap(ProtocolAction.CHATEND, sender.getUsername(), chatId);
	}
}

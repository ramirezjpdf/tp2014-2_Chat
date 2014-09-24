package br.ufrj.tp.protocol;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public byte[] wrapChatCreatedMsg(Client asker, Client asked){
		return wrap(ProtocolAction.CHATCREATED, asker.getUsername(), asked.getUsername());
	}
	
	public byte[] wrapChatEndMsg(Client sender, String chatId){
		return wrap(ProtocolAction.CHATEND, sender.getUsername(), chatId);
	}
	
	public ProtocolMsgParsedObj parseWrappedMsg(byte[] wrappedMsg) throws IllegalArgumentException{
		String msg = new String(wrappedMsg).trim();
		ProtocolAction action;
		
		StringBuilder builder = new StringBuilder();
		for(ProtocolAction a : ProtocolAction.values()){
			builder.append(a + "|");
		}
		builder.deleteCharAt(builder.length() - 1);
		
		String pattern = "^\\[(" + builder.toString() + ")\\]";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(msg);
		if(m.find()){
			action = ProtocolAction.valueOf(m.group(1));
		}
		else{
			throw new IllegalArgumentException("The wrappedMSg passed as argument does not have a correspondent ProtocolAction");
		}
		
		String arg;
		try{
			arg = msg.split(pattern)[1];
		}catch(IndexOutOfBoundsException e){
			throw new IllegalArgumentException("The wrappedMSg passed as argument does not have arguments");
		}
		
		List<String> argList = Arrays.asList(arg.split(";"));
		return new ProtocolMsgParsedObj(action, argList);
		
	}
}

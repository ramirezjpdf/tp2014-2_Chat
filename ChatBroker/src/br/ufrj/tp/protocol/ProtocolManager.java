package br.ufrj.tp.protocol;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	
	public byte[] wrapChatMsg(Client sender, String chatId, String msg){
		return wrap(ProtocolAction.CHAT, sender.getUsername(), chatId, msg);
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
	
	public byte[] wrapChatCreatedMsg(String chatId, Collection<? extends Client> clients){
		String [] arguments = new String[clients.size() + 1];
		arguments[0] = chatId; 
		int i = 1;
		for (Client client : clients){
			arguments[i] = client.getUsername();
			i++;
		}
		return wrap(ProtocolAction.CHATCREATED, arguments);
	}
	
	public byte[] wrapChatEndMsg(Client sender, String chatId){
		return wrap(ProtocolAction.CHATEND, sender.getUsername(), chatId);
	}
	
	public ProtocolMsgParsedObj parseWrappedMsg(byte[] wrappedMsg) throws IllegalArgumentException{
		String msg = new String(wrappedMsg).trim();
		ProtocolAction action;
		
		StringBuilder builder = new StringBuilder();
		for(ProtocolAction a : ProtocolAction.values()){
			builder.append(a.name() + "|");
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
	
	public Set<Client> makeClientSetFromListMsg(ProtocolMsgParsedObj po) throws IllegalArgumentException{
		if(!po.getAction().equals(ProtocolAction.LIST)){
			throw new IllegalArgumentException("This parsed object does not correspond to a " + ProtocolAction.LIST + " Msg");
		}
		
		Set<Client> clientSet = new HashSet<Client>();
		for (String s : po.getArgs()){
			Client client = new Client(s);
			clientSet.add(client);
		}
		return clientSet;
	}
	
	public ProtocolChatMsg makeProtocolChatMsgObj(ProtocolMsgParsedObj po){
		if(!po.getAction().equals(ProtocolAction.CHAT)){
			throw new IllegalArgumentException("This parsed object does not correspond to a " + ProtocolAction.CHAT + " Msg");
		}
		
		return new ProtocolChatMsg(new Client(po.getArgs().get(0)), po.getArgs().get(1), po.getArgs().get(2));
	}
	
	public ProtocolChatEndMsg makeProtocolChatEndMsgObj(ProtocolMsgParsedObj po){
		if(!po.getAction().equals(ProtocolAction.CHATEND)){
			throw new IllegalArgumentException("This parsed object does not correspond to a " + ProtocolAction.CHATEND + " Msg");
		}
		
		return new ProtocolChatEndMsg(new Client(po.getArgs().get(0)), po.getArgs().get(1));
	}
	
	public ProtocolChatCreatedMsg makeProtocolChatCreatedMsgObj(ProtocolMsgParsedObj po) throws IllegalArgumentException{
		if(!po.getAction().equals(ProtocolAction.CHATCREATED)){
			throw new IllegalArgumentException("This parsed object does not correspond to a " + ProtocolAction.CHATCREATED + " Msg");
		}
		
		String chatId = po.getArgs().get(0);
		Set<Client> clientSet = new HashSet<Client>();
		for (int i = 1; i < po.getArgs().size(); i++){
			Client client = new Client(po.getArgs().get(i));
			clientSet.add(client);
		}
		return new ProtocolChatCreatedMsg(chatId, clientSet);
	}
}

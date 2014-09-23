package br.ufrj.tp.chat;

import java.util.Set;

import br.ufrj.tp.broker.Broker;

public class ChatIdUtil {
	public static String generateChatId(Set<Broker> participants){
		//TODO: resolver bug de NullPointerException
		//Collections.sort(participants);
		String id = "";
		for (Broker b : participants){
			id += b.getClientname();
		}
		return id;
	}
}

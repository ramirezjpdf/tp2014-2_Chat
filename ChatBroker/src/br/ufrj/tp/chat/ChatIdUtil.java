package br.ufrj.tp.chat;

import java.util.Collections;
import java.util.List;

import br.ufrj.tp.broker.Broker;

public class ChatIdUtil {
	public static String generateChatId(List<Broker> participants){
		//Collections.sort(participants);
		String id = "";
		for (Broker b : participants){
			id += b.getClientname();
		}
		return id;
	}
}

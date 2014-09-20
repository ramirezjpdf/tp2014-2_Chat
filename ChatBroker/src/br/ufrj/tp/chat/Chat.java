package br.ufrj.tp.chat;

import java.util.List;

import br.ufrj.tp.broker.Broker;

public class Chat {
	private List<Broker> brokerList;
	private String id;

	public Chat(List<Broker> brokerList){
		this.brokerList = brokerList;
		this.id = ChatIdUtil.generateChatId(brokerList);
	}
	
	public void sendMsg(byte[] msg, Broker senderBroker){
		for(Broker broker : brokerList){
			if (broker.equals(senderBroker)) continue;
			broker.sendMsgToClient(msg);
		}
	}
	
	public String getId() {
		return id;
	}

}

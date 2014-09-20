package br.ufrj.tp.chat;

import java.util.List;

import br.ufrj.tp.broker.Broker;

public class Chat {
	private List<Broker> brokerList;
	
	public Chat(List<Broker> brokerList){
		this.brokerList = brokerList;
	}
	
	public void sendMsg(byte[] msg, Broker senderBroker){
		for(Broker broker : brokerList){
			if (broker.equals(senderBroker)) continue;
			broker.sendMsgToClient(msg);
		}
	}
}

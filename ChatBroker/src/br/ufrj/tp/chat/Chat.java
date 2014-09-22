package br.ufrj.tp.chat;

import java.util.List;

import br.ufrj.tp.broker.Broker;

public class Chat {
	private List<Broker> brokerList;
	private String id;
	private String user1;
	private String user2;

	public Chat(List<Broker> brokerList){
		this.brokerList = brokerList;
		this.id = ChatIdUtil.generateChatId(brokerList);
		user1 = brokerList.get(0).getClientname();
		user2 = brokerList.get(1).getClientname();
	}
	
	public void sendMsg(byte[] msg){
		for(Broker broker : brokerList){
			broker.sendMsgToClient(msg);
		}
	}
	
	public String getId() {
		return id;
	}
	
	public String getUser1(){
		return user1;
	}
	
	public String getUser2(){
		return user2;
	}
}

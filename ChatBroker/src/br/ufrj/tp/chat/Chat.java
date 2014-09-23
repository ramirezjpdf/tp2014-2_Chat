package br.ufrj.tp.chat;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import br.ufrj.tp.broker.Broker;

public class Chat {
	private TreeSet<Broker> brokerset;
	private String id;
	private String user1;
	private String user2;

	public Chat(TreeSet<Broker> brokerset){
		this.brokerset = brokerset;
		this.id = ChatIdUtil.generateChatId(brokerset);
	}
	
	public void sendMsg(byte[] msg){
		//No caso da interface própria, limitar envio para com quem se fala ao invés de
		//ambos os usuários.
		for(Broker broker : brokerset){
			broker.sendMsgToClient(msg);
		}
	}
	
	public String getId() {
		return id;
	}
}

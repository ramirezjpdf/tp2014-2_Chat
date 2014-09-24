package br.ufrj.tp.test;

import java.util.HashSet;
import java.util.Set;

import br.ufrj.tp.client.Client;
import br.ufrj.tp.protocol.ProtocolManager;

public class ProtocolManagerTest {
	public static void main(String[] args) {
		Client sender   = new Client("Sender");
		Client receiver = new Client("Receiver");
		Client asker    = new Client("Asker");
		Client asked	= new Client("Asked");
		
		Set<Client> clientSet = new HashSet<Client>();
		clientSet.add(sender);
		clientSet.add(receiver);
		clientSet.add(asker);
		clientSet.add(asked);
		
		ProtocolManager pm = new ProtocolManager();
		
		byte[] chatMSg;
		System.out.println("### Wrap Chat Msg ###");
		System.out.println(new String(chatMSg = pm.wrapChatMsg(sender, sender.getUsername() + receiver.getUsername(), "Hello Receiver")));
		System.out.println("#####################");
		
		System.out.println("");
		
		byte[] listMsg;
		System.out.println("### Wrap List Msg ###");
		System.out.println(new String(listMsg = pm.wrapListMsg(clientSet)));
		System.out.println("#####################");
		
		System.out.println("");
		
		byte[] chatAskPermissionMsg;
		System.out.println("### Wrap Chat Ask Permission Msg ###");
		System.out.println(new String(chatAskPermissionMsg = pm.wrapChatAskPermissionMsg(asker, asked)));
		System.out.println("####################################");
		
		System.out.println("");
		
		byte[] chatDeniesPermissionMsg;
		System.out.println("### Wrap Chat Denies Permission Msg ###");
		System.out.println(new String(chatDeniesPermissionMsg = pm.wrapChatDeniesPermissionMsg(asked, asker)));
		System.out.println("#######################################");
		
		System.out.println("");
		
		byte[] chatGivesPermissionMsg;
		System.out.println("### Wrap Chat Gives Permission Msg ###");
		System.out.println(new String(chatGivesPermissionMsg = pm.wrapChatGivesPermissionMsg(asked, asker)));
		System.out.println("#######################################");
		
		System.out.println("");
		
		byte[] chatCreatedMsg;
		System.out.println("### Wrap Chat Created Msg ###");
		System.out.println(new String(chatCreatedMsg = pm.wrapChatCreatedMsg(asker, asked)));
		System.out.println("#############################");
		
		System.out.println("");
		
		byte[] chatEndMsg;
		System.out.println("### Wrap Chat End Msg ###");
		System.out.println(new String(chatEndMsg = pm.wrapChatEndMsg(sender, sender.getUsername() + receiver.getUsername())));
		System.out.println("#########################");
		
		
		System.out.println("");
		System.out.println("===== Parsing msg! =====");
		
		System.out.println("=== Parse Chat Msg ===");
		System.out.println(pm.parseWrappedMsg(chatMSg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Lis Msg ===");
		System.out.println(pm.parseWrappedMsg(listMsg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Chat Ask Permission Msg ===");
		System.out.println(pm.parseWrappedMsg(chatAskPermissionMsg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Chat Denies Permission Msg ===");
		System.out.println(pm.parseWrappedMsg(chatDeniesPermissionMsg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Chat Gives Permission Msg ===");
		System.out.println(pm.parseWrappedMsg(chatGivesPermissionMsg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Chat Created Msg ===");
		System.out.println(pm.parseWrappedMsg(chatCreatedMsg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Chat End Msg ===");
		System.out.println(pm.parseWrappedMsg(chatEndMsg));
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Error! No Valid Action! ===");
		try{
			pm.parseWrappedMsg(new String("[CHET]aaa;l;l").getBytes());
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		System.out.println("======================");
		
		System.out.println("");
		
		System.out.println("=== Parse Error! No Arguments! ===");
		try{
			pm.parseWrappedMsg(new String("[CHAT]").getBytes());
		}catch(IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
		System.out.println("======================");
	}
}

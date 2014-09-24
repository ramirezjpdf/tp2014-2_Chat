package br.ufrj.tp.test;

import java.util.HashSet;
import java.util.Set;

import br.ufrj.tp.chat.ChatIdUtil;
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
		
		System.out.println("### Wrap Chat Msg ###");
		System.out.println(new String(pm.wrapChatMsg(sender, receiver, "Hello Receiver")));
		System.out.println("#####################");
		
		System.out.println("");
		
		System.out.println("### Wrap List Msg ###");
		System.out.println(new String(pm.wrapListMsg(clientSet)));
		System.out.println("#####################");
		
		System.out.println("");
		
		System.out.println("### Wrap Chat Ask Permission Msg ###");
		System.out.println(new String(pm.wrapChatAskPermissionMsg(asker, asked)));
		System.out.println("####################################");
		
		System.out.println("");
		
		System.out.println("### Wrap Chat Denies Permission Msg ###");
		System.out.println(new String(pm.wrapChatDeniesPermissionMsg(asked, asker)));
		System.out.println("#######################################");
		
		System.out.println("");
		
		System.out.println("### Wrap Chat Gives Permission Msg ###");
		System.out.println(new String(pm.wrapChatGivesPermissionMsg(asked, asker)));
		System.out.println("#######################################");
		
		System.out.println("");
		
		System.out.println("### Wrap Chat Created Msg ###");
		System.out.println(new String(pm.wrapChatCreatedMsg(asker, asked)));
		System.out.println("#############################");
		
		System.out.println("");
		
		System.out.println("### Wrap Chat End Msg ###");
		System.out.println(new String(pm.wrapChatEndMsg(sender, sender.getUsername() + receiver.getUsername())));
		System.out.println("#########################");
		
	}
}

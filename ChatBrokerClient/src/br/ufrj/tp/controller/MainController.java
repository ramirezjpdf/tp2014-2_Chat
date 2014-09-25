package br.ufrj.tp.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.tp.client.Client;
import br.ufrj.tp.model.chat.ChatManager;
import br.ufrj.tp.model.remoteManager.RemoteManager;
import br.ufrj.tp.protocol.ProtocolManager;
import br.ufrj.tp.sockConnection.TCPSockConnection;
import br.ufrj.tp.sockConnection.UDPSockConnection;
import br.ufrj.tp.sockListener.SockListenerConst;
import br.ufrj.tp.utils.SocketUtils;

public class MainController {
	private static final String SERVER_HOST = "localhost";
	
	private ChatManager chatManager;
	private RemoteManager remoteManager;
	
	public void initiateConnection(String username, SocketUtils.TransportProtocol transpProtocol){
		chatManager = new ChatManager(new Client(username));
		try {
			switch(transpProtocol){
				case TCP:
					remoteManager = new RemoteManager(new TCPSockConnection(new Socket(SERVER_HOST, SockListenerConst.PORT)), chatManager);
					break;
				case UDP:
					remoteManager = new RemoteManager(new UDPSockConnection(InetAddress.getByName(SERVER_HOST), SockListenerConst.PORT), chatManager);
					break;
			}
		} catch (UnknownHostException e) {
			System.out.println("Could not resolve server hostname");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error on initializing SockConnection");
			System.exit(1);
		}
		
		try{
			remoteManager.handshake(new ProtocolManager());
		}catch(IOException e){
			System.out.println("Error during handshake");
			System.exit(1);
		}
		chatManager.setRemoteManager(remoteManager);
		new Thread(remoteManager).start();
	}

	public List<String> getClientsUsernames() {
		List<String> clientUsernames = new ArrayList<String>();
		for (Client c : chatManager.getClientSet()){
			clientUsernames.add(c.getUsername());
		}
		return clientUsernames;
	}

	public void iniciateChatWith(String string) {
		// TODO Auto-generated method stub
		
	}
}

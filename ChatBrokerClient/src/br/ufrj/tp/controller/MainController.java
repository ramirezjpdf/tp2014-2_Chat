package br.ufrj.tp.controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufrj.tp.client.Client;
import br.ufrj.tp.model.chat.ChatManager;
import br.ufrj.tp.model.remoteManager.RemoteManager;
import br.ufrj.tp.sockConnection.TCPSockConnection;
import br.ufrj.tp.sockListener.SockListenerConst;

public class MainController {
	private static final String SERVER_HOST = "localhost";
	
	private ChatManager chatManager;
	private RemoteManager remoteManager;
	
	public void initiateConnection(String username){
		chatManager = new ChatManager(new Client(username));
		try {
			remoteManager = new RemoteManager(new TCPSockConnection(new Socket(SERVER_HOST, SockListenerConst.PORT)), chatManager);
		} catch (UnknownHostException e) {
			System.out.println("Could not resolve server hostname");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error on initializing SockConnection");
			System.exit(1);
		}
	}
}

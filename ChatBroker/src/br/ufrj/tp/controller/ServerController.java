package br.ufrj.tp.controller;

import java.io.IOException;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockListener.SockListener;
import br.ufrj.tp.sockListener.TCPSockListener;
import br.ufrj.tp.sockListener.UDPSockListener;

public class ServerController {
	
	private Server server;
	private String msg;
	
	public boolean startServer(int connectionType) {
		if (isConnTypeValid(connectionType)) {
			SockListener sockListener = getSocketListener(connectionType);
			server = new Server(sockListener);
			
			try {
				//A mensagem pode começar como "Server Chat is running!..."
				//Porquê em caso de qualquer erro, ela será mudada;
				//Caso nada de errado aconteça, então o servidor está funcionando.
				msg = "Server Chat is running! Waiting for Clients connections...";
				server.start();
			} catch (IOException e) {
				msg = "ERROR: An I/O error occurred when waiting for a connection.";
				return false;
			}
			return true;
		}
		
		msg = "ERROR: You've typed an incorrect option. Please, try again! " +
			  "Reminding: 1 for TCP, 2 for UDP.";
		return false;
	}
	
	public String getMsg() {
		return msg;
	}

	private SockListener getSocketListener(int connectionType) {
		if (connectionType == 1) {
			return new TCPSockListener();
		}
		
		if (connectionType == 2){
			return new UDPSockListener();
		}
		
		return null;
	}

	private boolean isConnTypeValid(int connectionType) {
		return connectionType == 1 || connectionType == 2;
	}
	
}

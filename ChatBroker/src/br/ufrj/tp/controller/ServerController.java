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
				server.start();
			} catch (IOException e) {
				msg = "ERROR: I/O operation failed during connection establishment via Socket.";
			}
			
			//FIXME: Como a função server.start() inicia o servidor, que fica em loop contínuo, a linha de execução não chega até esse ponto.
			msg = "Server Chat is running! Waiting for Clients connections...";
			return true;
		}
		
		msg = "ERROR: You've typed an incorrect option. Please, try again!";
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

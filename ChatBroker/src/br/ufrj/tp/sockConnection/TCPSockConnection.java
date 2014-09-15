package br.ufrj.tp.sockConnection;

import java.io.IOException;
import java.net.Socket;

public class TCPSockConnection implements SockConnection {
	private Socket clientSocket;
	
	public TCPSockConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void send(String msg) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public String recv() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}

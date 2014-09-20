package br.ufrj.tp.sockConnection;

import java.io.IOException;

import java.net.Socket;

import br.ufrj.tp.sockListener.SockListenerConst;

public class TCPSockConnection implements SockConnection {
	private Socket clientSocket;
	
	public TCPSockConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void send(byte[] msg) throws IOException {
		clientSocket.getOutputStream().write(msg);

	}

	@Override
	public byte[] recv() throws IOException {
		byte[] recvData = new byte[SockListenerConst.MSG_LEN];
		clientSocket.getInputStream().read(recvData);
		return recvData;
	}

}

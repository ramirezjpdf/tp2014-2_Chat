package br.ufrj.tp.sockConnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSockConnection implements SockConnection{
	private static int PORT = 1024;
	private static final int MSG_LEN = 1024;
	private static int getPort(){
		return PORT++;
	}
	
	private class UDPConnection{
		private InetAddress adress;
		private int port;
		
		public UDPConnection(InetAddress adress, int port) {
			this.adress = adress;
			this.port = port;
		}

		public InetAddress getAdress() {
			return adress;
		}

		public int getPort() {
			return port;
		}
	}
	
	private UDPConnection udpClient;
	private DatagramSocket udpSocket;
	
	public UDPSockConnection(DatagramPacket pkg) throws SocketException{
		this.udpClient = new UDPConnection(pkg.getAddress(), pkg.getPort());
		this.udpSocket = new DatagramSocket(getPort());
	}
	@Override
	public void send(String msg) throws IOException {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, udpClient.getAdress(), udpClient.getPort());
		udpSocket.send(sendPacket);
	}

	@Override
	public String recv() throws IOException {
		byte[] recvData = new byte[MSG_LEN];
		DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
		udpSocket.receive(recvPacket);
		return new String(recvPacket.getData());
	}

}

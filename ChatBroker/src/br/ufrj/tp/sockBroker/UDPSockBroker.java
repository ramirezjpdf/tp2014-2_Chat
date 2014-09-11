package br.ufrj.tp.sockBroker;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSockBroker implements SockBroker{
	private static int PORT = 1024;
	private static int getPort(){
		return PORT++;
	}
	
	private class UDPClient{
		private InetAddress adress;
		private int port;
		
		public UDPClient(InetAddress adress, int port) {
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
	
	private UDPClient udpClient;
	private DatagramSocket udpSocket;
	
	public UDPSockBroker(DatagramPacket pkg) throws SocketException{
		this.udpClient = new UDPClient(pkg.getAddress(), pkg.getPort());
		this.udpSocket = new DatagramSocket(getPort());
	}
	@Override
	public void send(String msg) throws IOException {
		byte[] sendData = msg.getBytes();
		DatagramPacket sendPkg = new DatagramPacket(sendData, sendData.length, udpClient.getAdress(), udpClient.getPort());
		udpSocket.send(sendPkg);
	}

	@Override
	public String recv() {
		// TODO Auto-generated method stub
		return null;
	}

}

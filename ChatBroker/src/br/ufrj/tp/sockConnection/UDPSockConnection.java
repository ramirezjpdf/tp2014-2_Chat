package br.ufrj.tp.sockConnection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.ufrj.tp.utils.SocketUtils;

public class UDPSockConnection implements SockConnection{
	private static final int MSG_LEN = 1024;
	
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

		public void setAdress(InetAddress adress) {
			this.adress = adress;
		}

		public void setPort(int port) {
			this.port = port;
		}
		
		
	}
	
	private UDPConnection udpClient;
	private DatagramSocket udpSocket;
	
	public UDPSockConnection(InetAddress address, int port) throws IOException{
		this.udpClient = new UDPConnection(address, port);
		this.udpSocket = new DatagramSocket(SocketUtils.getAvailablePort());
	}
	public UDPSockConnection(DatagramPacket pkg) throws IOException{
		this.udpClient = new UDPConnection(pkg.getAddress(), pkg.getPort());
		this.udpSocket = new DatagramSocket(SocketUtils.getAvailablePort());
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
		refreshUDPClient(recvPacket);
		return new String(recvPacket.getData());
	}
	
	private void refreshUDPClient(DatagramPacket newPacket){
		if(!newPacket.getAddress().equals(udpClient.getAdress())){
			udpClient.setAdress(newPacket.getAddress());
		}
		if(newPacket.getPort() != udpClient.getPort()){
			udpClient.setPort(newPacket.getPort());
		}
	}

}

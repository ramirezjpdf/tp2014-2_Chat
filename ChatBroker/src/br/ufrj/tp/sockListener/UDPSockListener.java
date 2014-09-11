package br.ufrj.tp.sockListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockBroker.UDPSockBroker;

public class UDPSockListener implements SockListener{
	private static final int port = 1023;
	private static final int MSG_LEN = 1024;
	
	@Override
	public void listen(BrokerFactory factory) throws IOException {
		DatagramSocket serverSocket = new DatagramSocket(port);
		byte[] recvData = new byte[MSG_LEN];
		
		try{
			while(true){
				DatagramPacket recvPkg = new DatagramPacket(recvData, recvData.length);
				serverSocket.receive(recvPkg);
				factory.getBroker(new UDPSockBroker(recvPkg)).run();
			}
		}finally{
			serverSocket.close();
		}
	}

}

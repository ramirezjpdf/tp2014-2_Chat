package br.ufrj.tp.sockListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockConnection.UDPSockConnection;

//TODO: Implementar listen(BrokenFactory factory, int port).
public class UDPSockListener implements SockListener{
	@Override
	public void listen(BrokerFactory factory) throws IOException {
		DatagramSocket serverSocket = new DatagramSocket(SockListenerConst.PORT);
		byte[] recvData = new byte[SockListenerConst.MSG_LEN];
		
		try{
			while(true){
				DatagramPacket recvPkg = new DatagramPacket(recvData, recvData.length);
				serverSocket.receive(recvPkg);
				factory.getBroker(new UDPSockConnection(recvPkg)).run();
				//TODO Como add novo Broker na lista de Brokers do Server???
			}
		}finally{
			serverSocket.close();
		}
	}

	public void listen(BrokerFactory factory, int port) throws IOException {
		
	}

}

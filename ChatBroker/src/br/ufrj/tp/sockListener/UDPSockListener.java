package br.ufrj.tp.sockListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockConnection.UDPSockConnection;
import br.ufrj.tp.utils.ObservableSet;

//TODO: Implementar listen(BrokenFactory factory, int port).
public class UDPSockListener implements SockListener{
	@Override
	public void listen(BrokerFactory factory, ObservableSet<Broker> onlineBrokers) throws IOException {
		DatagramSocket serverSocket = new DatagramSocket(SockListenerConst.PORT);
		byte[] recvData = new byte[SockListenerConst.MSG_LEN];
		
		try{
			while(true){
				DatagramPacket recvPkg = new DatagramPacket(recvData, recvData.length);
				serverSocket.receive(recvPkg);
				Broker broker = factory.getBroker(new UDPSockConnection(recvPkg));
				onlineBrokers.add(broker);
				new Thread(broker).start();
			}
		}finally{
			serverSocket.close();
		}
	}

	public void listen(BrokerFactory factory, int port) throws IOException {
		
	}

}

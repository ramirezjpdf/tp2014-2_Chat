package br.ufrj.tp.sockListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockConnection.TCPSockConnection;
import br.ufrj.tp.utils.ObservableSet;

public class TCPSockListener implements SockListener{

	@Override
	public void listen(BrokerFactory factory, ObservableSet<Broker> onlineBrokers) throws IOException {
		ServerSocket serverSocket = new ServerSocket(SockListenerConst.PORT);
		System.out.println("Connection Socket do Lado Servidor estabelecido.");
		
		try{
			while(true){
				Socket clientSocket = serverSocket.accept();
				Broker broker = factory.getBroker(new TCPSockConnection(clientSocket));
				onlineBrokers.add(broker);
				new Thread(broker).start();
			}
		}finally{
			serverSocket.close();
		}
	}
}

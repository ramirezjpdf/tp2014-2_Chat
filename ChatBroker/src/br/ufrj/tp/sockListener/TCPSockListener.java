package br.ufrj.tp.sockListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockConnection.TCPSockConnection;

public class TCPSockListener implements SockListener{

	@Override
	public void listen(BrokerFactory factory) throws IOException {
		ServerSocket serverSocket = new ServerSocket(SockListenerConst.PORT);
		System.out.println("Connection Socket do Lado Servidor estabelecido.");
		
		try{
			while(true){
				Socket clientSocket = serverSocket.accept();
				new Thread(factory.getBroker(new TCPSockConnection(clientSocket))).start();
			}
		}finally{
			serverSocket.close();
		}
	}
	
	@Override
	public void listen(BrokerFactory factory, int port) throws IOException {
		
		if (port < 0){
			System.out.println("Invalid port number. Please try again.");
			return;
		}
		
		ServerSocket serverSocket = new ServerSocket(SockListenerConst.PORT + port);
		System.out.println("Connection Socket do Lado Servidor estabelecido.");
		
		try{
			while(true){
				Socket clientSocket = serverSocket.accept();
				factory.getBroker(new TCPSockConnection(clientSocket)).run();
				//TODO Como add novo Broker na lista de Brokers do Server???
			}
		}finally{
			serverSocket.close();
		}
	}
}

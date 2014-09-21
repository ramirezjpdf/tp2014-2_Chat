package br.ufrj.tp.server;

import java.io.IOException;

import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockListener.SockListener;

public class Server {

	private SockListener welcomeSocket;
	private BrokerFactory brokerFactory;
	
	public Server(SockListener listener) {
		this.welcomeSocket = listener;
		this.brokerFactory = new BrokerFactory();
	}
	
	public void start() throws IOException {
		welcomeSocket.listen(brokerFactory);
	}
}

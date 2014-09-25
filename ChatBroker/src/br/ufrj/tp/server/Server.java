package br.ufrj.tp.server;

import java.io.IOException;
import java.util.HashSet;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockListener.SockListener;
import br.ufrj.tp.utils.ObservableSet;

public class Server{

	private SockListener welcomeSocket;
	private BrokerFactory brokerFactory;
	private ObservableSet<Broker> onlineBrokers;
	
	public Server(SockListener listener) {
		this.welcomeSocket = listener;
		this.brokerFactory = new BrokerFactory(this);
		this.onlineBrokers = new ObservableSet<Broker>(new HashSet<Broker>());
	}
    
    public synchronized void addBroker(Broker broker){
		onlineBrokers.add(broker);
	}
	
	public synchronized void removeBroker(Broker broker){
		onlineBrokers.remove(broker);
	}
	
	public synchronized ObservableSet<Broker> getBrokers(){
		return onlineBrokers;
	}
    
	public void start()  throws IOException {
		welcomeSocket.listen(brokerFactory, onlineBrokers);	
	}
}

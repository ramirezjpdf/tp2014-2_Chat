package br.ufrj.tp.server;

import java.io.IOException;
import java.util.HashSet;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockListener.SockListener;
import br.ufrj.tp.utils.ObservableSet;

public class Server{

	//TODO = Tratar exceções
	private SockListener welcomeSocket;
	private BrokerFactory brokerFactory;
	private ObservableSet<Broker> brokers;
	
	public Server(SockListener listener) {
		this.welcomeSocket = listener;
		this.brokerFactory = new BrokerFactory(this);
		this.brokers = new ObservableSet<Broker>(new HashSet<Broker>());
	}
	
//	public Broker getBroker(String name){
//		for (Broker b: brokers){
//			if (b.getClientName().compareTo(name) == 0) return b;
//		}
//		return null;
//	}
    
    public synchronized void addBroker(Broker broker){
		brokers.add(broker);
	}
	
	public synchronized void removeBroker(Broker broker){
		brokers.remove(broker);
	}
	
	public synchronized ObservableSet<Broker> getBrokers(){
		return brokers;
	}
    
//	public synchronized String getUserList(){
//		String a = "[LISTA]";
//		for (Broker b: brokers){
//			a = a.concat(b.getClientName() + ";");
//		}
//		return a;
//		
//	}
	
//	public synchronized boolean existeCliente(String nome){
//		
//		for(Broker a: brokers){
//			//Versão para quando não usarmos mais SocketTest
//			//if (a.getClientname().equals(nome)){
//			
//			//Versão gambiarra
//			if ((a == null) || (a.getClientName() == null)) return false;
//			if (a.getClientName().startsWith(nome)){
//				return true;
//			}
//		}
//		return false;
//	}
//	
	public void start()  throws IOException {
		
		welcomeSocket.listen(brokerFactory);
		
	}
}

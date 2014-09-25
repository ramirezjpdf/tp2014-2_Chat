package br.ufrj.tp.sockListener;

import java.io.IOException;

import br.ufrj.tp.broker.Broker;
import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.utils.ObservableSet;

public interface SockListener {
	public void listen(BrokerFactory factory, ObservableSet<Broker> brokers) throws IOException;
}

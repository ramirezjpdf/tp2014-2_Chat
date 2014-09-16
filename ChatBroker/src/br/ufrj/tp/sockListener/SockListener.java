package br.ufrj.tp.sockListener;

import java.io.IOException;

import br.ufrj.tp.broker.BrokerFactory;

public interface SockListener {
	public void listen(BrokerFactory factory) throws IOException;
}

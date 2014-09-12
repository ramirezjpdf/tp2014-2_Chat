package br.ufrj.tp.test;

import java.io.IOException;

import br.ufrj.tp.broker.BrokerFactory;
import br.ufrj.tp.sockListener.SockListener;
import br.ufrj.tp.sockListener.UDPSockListener;

public class MainTest {
	public static void main(String[] args) {
		SockListener listener = new UDPSockListener();
		System.out.println("Listening for incoming clients...");
		try {
			listener.listen(new BrokerFactory());
		} catch (IOException e) {
			System.out.println("Error with socket when listening. Exiting program.");
			e.printStackTrace();
			return;
		}
	}
}

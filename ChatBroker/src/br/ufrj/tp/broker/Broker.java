package br.ufrj.tp.broker;


import java.io.IOException;

import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable{
	private SockConnection sockConn;
	
	
	public Broker(SockConnection sockConn) {
		this.sockConn = sockConn;
	}


	@Override
	public void run() {
		System.out.println("Broker instantiated");
		try {
			sockConn.send("OK");
			String msg = sockConn.recv();
			while (!msg.trim().equals("END")){
				sockConn.send(msg.toUpperCase());
				msg = sockConn.recv();
				System.out.println("Received from Client: " + msg + " => " + msg.equals("END"));
				System.out.println("msg.equals(\"END\"): " + msg.trim().equals("END"));
			}
			System.out.println("Closing broker");
		} catch (IOException e) {
			System.out.println("ERROR - Could not send message from Borker to client");
			e.printStackTrace();
		}
	}
	
}

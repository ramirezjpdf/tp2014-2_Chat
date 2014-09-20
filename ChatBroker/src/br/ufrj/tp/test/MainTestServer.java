package br.ufrj.tp.test;

import java.io.IOException;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockListener.TCPSockListener;

public class MainTestServer {

	public static void main(String[] args) {
		
		TCPSockListener tcp = new TCPSockListener();
		Server server = new Server(tcp);
		
		try {
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}

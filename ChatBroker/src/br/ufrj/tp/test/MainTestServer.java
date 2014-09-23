package br.ufrj.tp.test;

import java.io.IOException;
import java.util.ArrayList;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockListener.SockListener;
import br.ufrj.tp.sockListener.TCPSockListener;

public class MainTestServer {

	public static void main(String[] args) throws IOException {
		
		TCPSockListener tcp = new TCPSockListener();
		
		Server server = new Server(tcp);
		
		server.start();
		
	}

}

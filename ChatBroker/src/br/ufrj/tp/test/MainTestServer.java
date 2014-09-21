package br.ufrj.tp.test;

import java.io.IOException;
import java.util.ArrayList;

import br.ufrj.tp.server.Server;
import br.ufrj.tp.sockListener.SockListener;
import br.ufrj.tp.sockListener.TCPSockListener;

public class MainTestServer {

	public static void main(String[] args) throws IOException {
		
		TCPSockListener tcp1 = new TCPSockListener();
		TCPSockListener tcp2 = new TCPSockListener();
		TCPSockListener tcp3 = new TCPSockListener();
		TCPSockListener tcp4 = new TCPSockListener();
		
		ArrayList<SockListener> tcp = new ArrayList<SockListener>();
		tcp.add(tcp1); tcp.add(tcp2);tcp.add(tcp3);tcp.add(tcp4);
		
		Server server = new Server(tcp);
		
		server.start();
		
		
		
		
	}

}

package br.ufrj.tp.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketUtils {
	public static enum TransportProtocol{
		UDP,
		TCP
	}
	
	public static int getAvailablePort() throws IOException{
		ServerSocket sock = null;
		try{
			sock = new ServerSocket(0);
			return sock.getLocalPort();
		}finally{
			sock.close();
		}
	}
}

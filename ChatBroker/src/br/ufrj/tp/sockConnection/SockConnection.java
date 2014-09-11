package br.ufrj.tp.sockConnection;

import java.io.IOException;

public interface SockConnection {
	public void send(String msg) throws IOException;
	public String recv();
}

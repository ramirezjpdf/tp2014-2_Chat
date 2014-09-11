package br.ufrj.tp.sockBroker;

import java.io.IOException;

public interface SockBroker {
	public void send(String msg) throws IOException;
	public String recv();
}

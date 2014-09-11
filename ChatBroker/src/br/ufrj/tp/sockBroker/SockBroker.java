package br.ufrj.tp.sockBroker;

public interface SockBroker {
	public void send(String msg);
	public String recv();
}

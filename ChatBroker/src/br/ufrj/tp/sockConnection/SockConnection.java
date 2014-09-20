package br.ufrj.tp.sockConnection;

import java.io.IOException;

public interface SockConnection {
	public void send(byte[] msg) throws IOException;
	public byte[] recv() throws IOException;
}

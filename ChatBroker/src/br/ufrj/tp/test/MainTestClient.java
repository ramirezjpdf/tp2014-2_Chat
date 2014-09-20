package br.ufrj.tp.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import br.ufrj.tp.sockListener.SockListenerConst;


public class MainTestClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket clientSocket = new Socket("192.168.1.3", SockListenerConst.PORT, InetAddress.getByName("192.168.1.3"), 1025);
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String sentence = "Mensagem de Teste do Cliente";
		outToServer.writeBytes(sentence + '\n');
		
		System.out.println(sentence);
		
		String modifiedSentence = inFromServer.readLine();
		System.out.println("FROM SERVER: " + modifiedSentence);
		
		clientSocket.close();
	}

}

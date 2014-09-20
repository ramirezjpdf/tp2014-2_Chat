package br.ufrj.tp.broker;


import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import br.ufrj.tp.sockConnection.SockConnection;

public class Broker implements Runnable, Observer{
    private SockConnection sockConn;
    
    
    public Broker(SockConnection sockConn) {
        this.sockConn = sockConn;
    }

    public void sendMsgToClient(byte[] msg){
    	
    }

    @Override
    public void run() {
        System.out.println("Broker instantiated");
        try {
            sockConn.send("OK".getBytes());
            String msg = new String(sockConn.recv());
            while (!msg.trim().equals("END")){
                sockConn.send(msg.toUpperCase().getBytes());
                msg = new String(sockConn.recv());
                System.out.println("Received from Client: " + msg + " => " + msg.equals("END"));
                System.out.println("msg.equals(\"END\"): " + msg.trim().equals("END"));
            }
            System.out.println("Closing broker");
        } catch (IOException e) {
            System.out.println("ERROR - Could not send message from Borker to client");
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Observable obs, Object arg){
        //TODO
    }
}

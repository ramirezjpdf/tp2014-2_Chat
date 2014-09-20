package br.ufrj.tp.test;

import java.io.IOException;
import br.ufrj.tp.controller.*;

public class MainTestServerController {
	public static void main(String[] args) throws IOException {
		
		ServerController servcontrol;
		
		servcontrol = new ServerController();
		
		servcontrol.startServer(1);
		
		
		
	}

}

package br.ufrj.tp.protocol;

import java.util.List;

public class ProtocolMsgParsedObj {
	private ProtocolAction action;
	private List<String> args;
	
	public ProtocolMsgParsedObj(ProtocolAction action, List<String> args) {
		this.action = action;
		this.args = args;
	}

	public ProtocolAction getAction() {
		return action;
	}

	public List<String> getArgs() {
		return args;
	}
	
	@Override
	public String toString(){
		return "ProtocolAction: " + action.name() + ".\n Arguments: " + args.toString();
	}
}

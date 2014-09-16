package br.ufrj.tp.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import br.ufrj.tp.utils.ObservableSet;
import br.ufrj.tp.utils.ObservableSet.ObservableSetChange;

public class ObserverTest {
	private static class TestObserver implements Observer{
		private String name;
		
		public TestObserver(String name) {
			this.name = name;
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			@SuppressWarnings("unchecked")
			ObservableSetChange<String> change = (ObservableSetChange<String>) arg1;
			if (change.getStatus().equals(ObservableSetChange.ADDED_ELEMENT)){
				System.out.println("Observer: " + name + ". Element: " + "'" + change.getElemChanged() + "' added" );
			}
			else if(change.getStatus().equals(ObservableSetChange.ADDED_ELEMENT_COLLECTION)){
				for(String s : change.getElemChangedCollection()){
					System.out.println("Observer: " + name + ". Elements: " + "'" + s + "' added" );
				}
			}
			else if(change.getStatus().equals(ObservableSetChange.REMOVED_ELEMENT)){
				System.out.println("Observer: " + name + ". Element: " + "'" + change.getElemChanged() + "' removed" );
			}
			else if(change.getStatus().equals(ObservableSetChange.REMOVED_ELEMENT_COLLECTION)){
				for(String s : change.getElemChangedCollection()){
					System.out.println("Observer: " + name + ". Elements: " + "'" + s + "' removed" );
				}
			}
		}	
	}
	
	public static void main(String[] args) {
		ObservableSet<String> obsStr = new ObservableSet<String>(new HashSet<String>());
		for (int i = 0; i < 4; i++){
			TestObserver o = new TestObserver("o" + i);
			obsStr.addObserver(o);
		}
		
		System.out.println("######ADDING ELEMENT######");
		obsStr.add("String 1");
		
		System.out.println("######REMOVING ELEMENT######");
		obsStr.remove("String 1");
		
		System.out.println("######ADDING ELEMENT COLLECTION");
		String[]strs = {"s2", "s3", "s4", "s5", "s6"};
		obsStr.addAll(Arrays.asList(strs));
		
		System.out.println("######REMOVING ELEMENT COLLECTION");
		String[]strsRem = {"s2", "s3"};
		obsStr.removeAll(Arrays.asList(strsRem));
		
		System.out.println("######RETAIN ELEMENT COLLECTION");
		String[]strsRetain = {"s4"};
		obsStr.retainAll(Arrays.asList(strsRetain));
		
		System.out.println("obsStr.size() = " + obsStr.size());
		
		System.out.println("######ITERATOR REMOVE######");
		obsStr.addAll(Arrays.asList("s7", "s8"));
		Iterator<String>it = obsStr.iterator();
		while(it.hasNext()){
			String n = it.next();
			if(n.equals("s7")){
				it.remove();
			}
		}
	}
}

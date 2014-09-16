package br.ufrj.tp.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

public class ObservableSet<T> extends Observable {
	
	public static class ObservableSetChange<E>{
		public static final String ADDED_ELEMENT    = "add";
		public static final String REMOVED_ELEMENT  = "rem";
		public static final String ADDED_ELEMENT_COLLECTION   = "addAll";
		public static final String REMOVED_ELEMENT_COLLECTION = "remAll";
		
		private String status;
		private E elemChanged;
		private Collection<? extends E> elemChangedCollection;
		
		public ObservableSetChange(String status, E elemChanged,
				Collection<? extends E> elemChangedCollection) {
			super();
			this.status = status;
			this.elemChanged = elemChanged;
			this.elemChangedCollection = elemChangedCollection;
		}
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public E getElemChanged() {
			return elemChanged;
		}
		public void setElemChanged(E elemChanged) {
			this.elemChanged = elemChanged;
		}
		public Collection<? extends E> getElemChangedCollection() {
			return elemChangedCollection;
		}
		public void setElemChangedCollection(Collection<? extends E> elemChangedCollection) {
			this.elemChangedCollection = elemChangedCollection;
		}		
	}
	
	private class ObservableSetIterator<E> implements Iterator<E>{
		private Iterator<E> it;
		private E currentElem;

		public ObservableSetIterator(Iterator<E> it) {
			this.it = it;
			this.currentElem = null;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public E next() {
			currentElem =  it.next();
			return currentElem;
		}

		@Override
		public void remove() {
			it.remove();
			setChanged();
			notifyObservers(new ObservableSetChange<E>(ObservableSetChange.REMOVED_ELEMENT, currentElem, null));
		}
		
		
	}
	
    private Set<T> set;
    
    
    public boolean add(T arg0) {
		boolean b = set.add(arg0);
		if(b){
			setChanged();
			notifyObservers(new ObservableSetChange<T>(ObservableSetChange.ADDED_ELEMENT, arg0, null));
		}
		return b;
	}

	public boolean addAll(Collection<? extends T> arg0) {
		boolean b = set.addAll(arg0);
		if(b){
			setChanged();
			notifyObservers(new ObservableSetChange<T>(ObservableSetChange.ADDED_ELEMENT_COLLECTION, null, arg0));
		}
		return b;
	}

//	public void clear() {
//		set.clear();
//	}

	public boolean contains(Object arg0) {
		return set.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return set.containsAll(arg0);
	}

	public boolean equals(Object arg0) {
		return set.equals(arg0);
	}

	public int hashCode() {
		return set.hashCode();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public Iterator<T> iterator() {
		return new ObservableSetIterator<T>(set.iterator());
	}

	public boolean remove(T arg0) {
		boolean b = set.remove(arg0);
		if(b){
			setChanged();
			notifyObservers(new ObservableSetChange<T>(ObservableSetChange.REMOVED_ELEMENT, arg0, null));
		}
		return b;
	}

	public boolean removeAll(Collection<? extends T> arg0) {
		boolean b = set.removeAll(arg0);
		if(b){
			setChanged();
			notifyObservers(new ObservableSetChange<T>(ObservableSetChange.REMOVED_ELEMENT_COLLECTION, null, arg0));
		}
		return b;
	}

	public boolean retainAll(Collection<? extends T> arg0) {
		Collection<T> removed = new ArrayList<T>();
		for (T t : set){
			if(!arg0.contains(t)) removed.add(t);
		}
		boolean b = set.retainAll(arg0);
		if(b){
			setChanged();
			notifyObservers(new ObservableSetChange<T>(ObservableSetChange.REMOVED_ELEMENT_COLLECTION, null, removed));
		}
		return b;
	}

	public int size() {
		return set.size();
	}

	public Object[] toArray() {
		return set.toArray();
	}

	public <E extends T> E[] toArray(E[] arg0) {
		return set.toArray(arg0);
	}

//	public ObservableSet(Class<? extends Set<T>> setClass) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//        Constructor<? extends Set<T>> constr = null;
//        Constructor<? extends Set<T>>[] constructors = (Constructor<? extends Set<T>>[])setClass.getDeclaredConstructors();
//        for(Constructor<? extends Set<T>> c : constructors){
//            if(c.getGenericParameterTypes().length == 0){
//                constr = c;
//                break;
//            }
//        }
//        constr.setAccessible(true);
//        this.set = constr.newInstance();
//    }
	
	public ObservableSet(Set<T> set){
		this.set = set;
	}

}

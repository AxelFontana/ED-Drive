package TDAMapeo;

import TDALista.ListaDobleEnlace;
import TDALista.PositionList;

public class mapeoConHashCerrado<K, V> implements Map<K, V> {

	private int size,N;
	private Entrada<K,V> []A;
	private Entrada<K,V> disponible;
	
	@SuppressWarnings("unchecked")
	public mapeoConHashCerrado() {
		N = 7;
		size = 0;
		A = (Entrada<K,V>[]) new Entrada[N];
		disponible = new Entrada<K,V>(null,null);
		}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		int p = findEntry(key);
		V mValue = null;
		if(p>=0) 
		mValue = A[p].getValue();
		return mValue;	
	}
	
	private int findEntry(K key) {
		int p = funcionHash(key);
		boolean found = false;
		int cont=1;
		while(A[p]!=null && !found && cont<=A.length){
			if(A[p]!=disponible && A[p].getKey().equals(key)) 
					found = true;
			else {
				p = (p+1) % N;
				cont++;
			}
		}
		if(A[p]!=null)
			return p;
		else
			return -1;
	}
	
	private void checkKey(K key)throws InvalidKeyException{
		if(key==null)
			throw new InvalidKeyException("La clave es nula.");
	}

	private int funcionHash(K k) {
		int i = Math.abs(k.hashCode());
		return (i % N);
	}
	
	private boolean factorCarga() {
		float i = size/N;
		return i<0.9;
	}
	
	private void rehash() {
		Iterable<Entry<K,V>> entradas= entries();
		N=(A.length*2+1);
		A=(Entrada<K,V> [])new Entrada[N];
		size=0;
		try {
		for(Entry<K,V> e:entradas) 
			put(e.getKey(),e.getValue());
		}catch(InvalidKeyException f) {
			System.out.println(f.getMessage());
		}
	}
	
	private int findAvailableEntry(K k) {
		int p = funcionHash(k);
		while(A[p]!=null && A[p]!=disponible && (!A[p].getKey().equals(k)) ) {
			p= (p+1) % N;
		}
		return p;
	}
	
	@Override
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		if(!factorCarga())
			rehash();
		int p = findAvailableEntry(key);
		V mValue = null;
		if(p>=0) {
			if(A[p]!=null && A[p]!=disponible) {
				mValue = A[p].getValue();
				A[p].setValue(value);
			}
		else {
			size++;
			A[p] = new Entrada<K,V>(key,value);
			}
		}
		return mValue;
	}
	
	@Override
	public V remove(K key) throws InvalidKeyException{
		checkKey(key);
		V mValue=null;
		int p=funcionHash(key);
		int cont=1;
		while(cont<=A.length && A[p]!=null && mValue==null) {
			if(A[p]!=disponible && A[p].getKey().equals(key)) {
				mValue=A[p].getValue();
				A[p]=disponible;
				size--;
			}else {
				p=(p+1)%A.length;
			}	
			cont++;
		}
		return mValue;		
	}

	@Override
	public Iterable<K> keys(){
		PositionList<K> l=new ListaDobleEnlace<K>();
		for(Entrada<K,V> e:A)
			if(e!=null && e!=disponible)
			    l.addLast(e.getKey());
		return l;
	}
	
	@Override
	public Iterable<V> values(){
		PositionList<V> l=new ListaDobleEnlace<V>();
		for(Entrada<K,V> e:A)
			if(e!=null && e!=disponible)
			    l.addLast(e.getValue());
		return l;
	}
	
	@Override
	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> l=new ListaDobleEnlace<Entry<K,V>>();
		for(Entrada<K,V> e:A)
			if(e!=null && e!=disponible)
			    l.addLast(e);
		return l;
	}
}	

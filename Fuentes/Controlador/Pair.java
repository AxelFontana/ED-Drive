package Controlador;

import TDAMapeo.Entry;

public class Pair<K, V> implements Entry<K, V> {

	protected K key;
	protected V value;
	
	
	public Pair(K key,V value) {
		this.key=key;
		this.value=value;
	}
	public Pair() {
		this(null,null);
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V v) {
		value = v;
	}

	public void setKey(K k) {
		key = k;
	}

}

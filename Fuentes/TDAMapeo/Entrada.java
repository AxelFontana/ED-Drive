package TDAMapeo;

public class Entrada<K, V> implements Entry<K, V>{
	private K key;
	private V value;
	
	public Entrada(K k, V v) {
		key=k; value=v;
	}
	public Entrada() {
		key=null; value=null;
	}
	public K getKey() { 
		return key; 
	}
	public V getValue() { 
		return value; 
	}
	public void setKey( K k ) {
		key = k; 
	}
	public void setValue(V v) {
		value = v; 
	}
	public String toString( ) {
		return "(" + getKey() + "," + getValue() + ")" ;
	}
}

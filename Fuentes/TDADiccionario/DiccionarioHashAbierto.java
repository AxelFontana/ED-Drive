package TDADiccionario;

import java.util.Iterator;

import TDALista.InvalidPositionException;
import TDALista.ListaDobleEnlace;
import TDALista.Position;
import TDALista.PositionList;
import TDAMapeo.Entrada;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;


/**
 * TDA Diccionario con hash abierto
 */
public class DiccionarioHashAbierto<K,V> implements Dictionary<K,V>{
	protected PositionList<Entry<K,V>> [] array;
	protected int size;
	protected int initialSize;
	protected final double factorDeCarga=(0.9);
	
	/**
	 * Constructor para la estructura diccionario con Hash Abierto
	 * @param tamanio Cantidad de buckets que tendra la tabla hash
	 */
	public DiccionarioHashAbierto(int tamanio) {
		array=(ListaDobleEnlace<Entry<K,V>> [])new ListaDobleEnlace [tamanio];
		for(int i=0; i<array.length; i++) {
			array[i]= new ListaDobleEnlace<Entry<K,V>>();
		}
		initialSize=tamanio;
		size=0;
	}
	
	public DiccionarioHashAbierto() {
		this(13);
	}
	
	/**
	 * Consulta el n�mero de entradas del diccionario.
	 * @return N�mero de entradas del diccionario.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Consulta si el diccionario est� vac�o.
	 * @return Verdadero si el diccionario est� vac�o, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	public int hash(K key) {
		return ( key.hashCode() % array.length );
	}
	
	/**
	 * Busca una entrada con clave igual a una clave dada y la devuelve, si no existe retorna nulo.
	 * @param key Clave a buscar.
	 * @return Entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
	 */
	public Entry<K, V> find(K key) throws InvalidKeyException {
		if(key==null) {
			throw new InvalidKeyException("Invalid key.");
		}
		
		Entry<K,V> toRet=null;
		int hKey= hash(key);
		if(!array[hKey].isEmpty()) {//Si la lista del bucket donde estoy buscando posee entradas entonces busco
			
			Iterator<Entry<K,V>> it= (array[hKey]).iterator();
			boolean encontre=false; Entry<K,V> pos=null;
			while(!encontre && it.hasNext()) {
				pos=it.next();
				if(pos.getKey().equals(key)) {
					encontre=true;
					toRet=pos;
				}
			}//Fin while
		}
		return toRet;
	}
	
	/**
	 * Retorna una colecci�n iterable que contiene todas las entradas con clave igual a una clave dada.
	 * @param key Clave de las entradas a buscar.
	 * @return Colecci�n iterable de las entradas encontradas.
	 * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
	 */
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		if(key==null) {
			throw new InvalidKeyException("Invalid key.");
		}
		
		PositionList<Entry<K,V>> toRet= new ListaDobleEnlace<Entry<K,V>>();
		int hKey=hash(key);
		if(!array[hKey].isEmpty()) {//Si la lista del bucket seleccionado posee entradas entonces busco
			
			for(Entry<K,V> e: array[hKey]) {
				if(e.getKey().equals(key)) {
					toRet.addLast(e);
				}
			}
			
		}
		return toRet;
	}
	
	/**
	 * Se encarga de redimensionar la tabla hash
	 */
	private void reHash() {
		PositionList<Entry<K,V>> [] viejo= array;
		initialSize=ProximoPrimo(initialSize*2);
		array=(ListaDobleEnlace<Entry<K,V>> [])new ListaDobleEnlace [initialSize];
		for(int i=0; i<array.length; i++) {//Inicializo todos los buckets con listas vacias
			array[i]= new ListaDobleEnlace<Entry<K,V>>();
		}
		int hKey=-1;
		
		for(int i=0; i<viejo.length; i++) {//Recorro la tabla hash 
			for(Entry<K,V> e: viejo[i]) {//Recorro la lista
				hKey= hash(e.getKey());
				array[hKey].addLast(e);
			}
		}
	}
	
	/**
	 * Consulta el numero primo mas cercano al pasado por parametro
	 * @param num Numero a verificar
	 * @return Un numero primo mayor o igual al pasado por parametro
	 */
	private int ProximoPrimo(int num) {
		boolean encontre =false;
		while(!encontre) {
			if(EsPrimo(num)) {
				encontre=true;
			}
			else {
				num++;
			}
		}
		return num;
	}
	
	/**
	 * Consulta si un numero pasado por parametro es primo
	 * @param num Numero a verificar
	 * @return Verdadero si el numero es primo
	 */
	private boolean EsPrimo(int num) {
		int it=2; boolean es=true;
		while(it<num && !es) {
			if(num % it == 0) {
				es=false;
			}
			else {
				it++;
			}
		}
		return es;
	}
	
	/**
	 * Inserta una entrada con una clave y un valor dado en el diccionario y retorna la entrada creada.
	 * @param key Clave de la entrada a crear.
	 * @return value Valor de la entrada a crear.
	 * @throws InvalidKeyException si la clave pasada por par�metro es inv�lida.
	 */
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if(key==null) {
			throw new InvalidKeyException("Invalid key.");
		}
		
		Entry<K,V> toRet= new Entrada<K,V>(key,value);
		int hKey= hash(key); boolean encontre=false;
		if(!array[hKey].isEmpty()) {//Si la lista del bucket donde estoy buscando posee entradas entonces busco si la entrada ya existe
			
			Iterator<Entry<K,V>> it= array[hKey].iterator();
			 Entry<K,V> pos=null;
			while(it.hasNext() && !encontre) {
				pos=it.next();
				if(pos.getKey().equals(key) && pos.getValue().equals(value)) {
					encontre=true;
				}
			}
			
		}
		if(!encontre) {
			array[hKey].addLast(toRet);
			size++;
		}
		if(size/initialSize > factorDeCarga) {
			reHash();
		}
		return toRet;
	}
	
	/**
	 * Remueve una entrada dada en el diccionario y devuelve la entrada removida.
	 * @param e Entrada a remover.
	 * @return Entrada removida.
	 * @throws InvalidEntryException si la entrada no est� en el diccionario o es inv�lida.
	 */
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		if(e==null) {
			throw new InvalidEntryException("Invalid entry.");
		}
		
		int hKey= hash(e.getKey());
		boolean esta=false;
		
		if(!array[hKey].isEmpty()) {//Si la lista del bucket donde estoy buscando posee entradas entonces busco la entrada
			
			Iterator<Position<Entry<K,V>>> it= (array[hKey].positions()).iterator();
			Position<Entry<K,V>> pos=null;
			while(!esta && it.hasNext()) {
				pos=it.next();
				if( pos.element().equals(e) ) {//Si la entrada de la lista es igual a la entrada pasada por parametro
					try {
						esta=true;
						array[hKey].remove(pos);
						size--;
					} catch(InvalidPositionException f) {
						f.printStackTrace();
					}
				}
			}//Fin while
		}
		if(!esta) {
			throw new InvalidEntryException("Entry doesn't belong to this dictionary.");
		}
		
		return e;
	}
	
	/**
	 * Retorna una colecci�n iterable con todas las entradas en el diccionario.
	 * @return Colecci�n iterable de todas las entradas.
	 */
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> toRet= new ListaDobleEnlace<Entry<K,V>>();
		
		for(int i=0; i<array.length; i++) {
			
			if(!array[i].isEmpty()) {
				for(Entry<K,V> e: array[i]) {
					toRet.addLast(e);
				}
			}
		}
		return toRet;
	}
}

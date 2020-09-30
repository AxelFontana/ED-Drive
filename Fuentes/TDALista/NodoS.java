package TDALista;
/**
 * Clase NodoSimpleEnlace
 * @author jacttis
 *
 * 
 */
public class NodoS<E> implements Position<E>{
	private E dato;
	private NodoS<E> siguiente;
	
	/**
	 * Crea un objeto NodoS de tipo E con un valor interno y una referencia a un sgte nodo
	 * @param item valor a  almacenar dentro del nodo
	 * @param sig referencia a el nodo sgte
	 */
	public NodoS(E item, NodoS<E> sig) {
		dato=item;
		siguiente=sig;
	}
	
	/**
	 * Crea un objeto NodoS de tipo E con un valor interno y con referencia nula hacia su sgte nodo
	 * @param item valor a almacenar dentro del nodo
	 */
	public NodoS(E item) {
		this(item,null);
	}

	//Setters
	/**
	 * Reemplaza el valor interno del nodo
	 * @param elemento nuevo valor del nodo
	 */
	public void setElemento(E elemento){
		dato=elemento;
	}
	/**
	 * Establece una referencia hacia un nodo sgte
	 * @param siguiente nueva referencia hacia un nodo sgte
	 */
	public void setSiguiente(NodoS<E> siguiente){
		this.siguiente=siguiente;
	}
	
	//Getters
	/**
	 * Consulta la referencia al Nodo siguiente
	 * @return El nodo siguiente 
	 */
	public NodoS<E> getSiguiente(){	
		return siguiente;
	}
	
	/**
	 * Consulta el valor interno del nodo
	 * @return el valor interno del nodo
	 */
	public E element(){
		return dato;
	}
	
}

package TDAArbol;

import TDALista.ListaDobleEnlace;
import TDALista.Position;
import TDALista.PositionList;

/**
 * Implementacion de la clase TNodo<E> que se utilizara para el TDAArbol.
 * @author jacttis
 *
 */
public class TNodo<E> implements Position<E>{
	private E elem;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;
	
	/**
	 * Crea un TNodo donde le asocia un valor a su rotulo, hace una referencia a su nodo padre e inicializa la lista de hijos vacia
	 * @param elemento Valor a asociar en el rotulo del nodo
	 * @param padre Referencia del padre del nodo, nula en caso de que el nodo sea la raiz del arbol
	 */
	public TNodo(E elemento, TNodo<E> padre) {
		elem=elemento;
		this.padre=padre;
		hijos= new ListaDobleEnlace<TNodo<E>>();
	}
	/**
	 * Crea un TNodo donde le asocia un valor a su rotulo. Se utiliza para la creacion de la raiz del arbol.
	 * @param elemento Valor a asociar en el rotulo del nodo
	 */
	public TNodo(E elemento) {
		this(elemento,null);
	}
	
	/**
	 * Consulta el valor del rotulo
	 */
	public E element() { 
		return elem; 
	}
	public PositionList<TNodo<E>> getHijos() { 
		return hijos; 
	}
	public void setElemento( E elemento ) { 
		this.elem = elemento; 
	}
	public TNodo<E> getPadre() { 
		return padre; 
	}
	public void setPadre( TNodo<E> padre ) { 
		this.padre = padre; 
	}
	
}

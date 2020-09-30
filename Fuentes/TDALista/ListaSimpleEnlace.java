package TDALista;

import java.util.Iterator;

/**
 * TDA Lista simple enlace
 * @author jacttis
 *
 */
public class ListaSimpleEnlace<E>  implements PositionList<E>{
	protected NodoS<E> head;
	protected int size;

	/**
	 * Crea un objeto de ListaSimpleEnlace vacia
	 */
	public ListaSimpleEnlace() {
		head=null;
		size=0;
	}
	/**
	 * Consulta la cantidad de elementos de la lista.
	 * @return Cantidad de elementos de la lista.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Consulta si la lista est� vac�a.
	 * @return Verdadero si la lista est� vac�a, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return size==0;
	}
	
	/**
	 * Devuelve la posici�n del primer elemento de la lista. 
	 * @return Posici�n del primer elemento de la lista.
	 * @throws EmptyListException si la lista est� vac�a.
	 */
	public Position<E> first() throws EmptyListException{
		if(isEmpty()) {
			throw new EmptyListException("Lista vacia.");
		}else {
			return head;
		}
	}
	
	/**
	 * Devuelve la posici�n del �ltimo elemento de la lista. 
	 * @return Posici�n del �ltimo elemento de la lista.
	 * @throws EmptyListException si la lista est� vac�a.
	 * 
	 */
	public Position<E> last() throws EmptyListException{
		if(isEmpty()) {
			throw new EmptyListException("Lista vacia.");
		}else {
			NodoS<E> aux=head;
			while(aux.getSiguiente()!=null) {
				aux=aux.getSiguiente();
			}
			return aux;
		}
	}
	
	/**
	 * Devuelve la posici�n del elemento siguiente a la posici�n pasada por par�metro.
	 * @param p Posici�n a obtener su elemento siguiente.
	 * @return Posici�n del elemento siguiente a la posici�n pasada por par�metro.
	 * @throws InvalidPositionException si el posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
	 * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al �ltimo elemento de la lista.
	 */
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		NodoS<E> n= checkPosition(p);
		if(n.getSiguiente()==null) {
			throw new BoundaryViolationException("Next: sgte de ultimo.");
		}
		return n.getSiguiente();
	}
	
	/**
	 * Consulta si la Position p no es nula y que pertenezca a la lista, de ser asi retorna el nodo el cual la implementa
	 * @param p Posicion a verificar
	 * @return Nodo el cual implementa la Position p
	 * @throws InvalidPositionException si la posicion pasada por parametro es nula o si la posicion no pertenece a la lista
	 */
	private NodoS<E> checkPosition( Position<E> p ) throws InvalidPositionException {
		try {
			if( p == null )
				throw new InvalidPositionException("Posición nula");
			return (NodoS<E>) p;
		} catch( ClassCastException e ) {
			throw new InvalidPositionException( "...." );
		}
    }
	
	/**
	 * Devuelve la posici�n del elemento anterior a la posici�n pasada por par�metro.
	 * @param p Posici�n a obtener su elemento anterior.
	 * @return Posici�n del elemento anterior a la posici�n pasada por par�metro.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
	 * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al primer elemento de la lista.
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		NodoS<E> n= checkPosition(p);
		if(n==head)
			throw new BoundaryViolationException("Prev: Anterior del primero");
		NodoS<E> aux = head;
		while( aux.getSiguiente() != n && aux.getSiguiente() != null )
			aux = aux.getSiguiente();
		return aux;
	}
	
	/**
	 * Inserta un elemento al principio de la lista.
	 * @param element Elemento a insertar al principio de la lista.
	 */
	public void addFirst(E element) {
		NodoS<E> n= new NodoS<E>(element);
		if(isEmpty()) {
			head=n;
		}else {
			n.setSiguiente(head);
			head=n;
		}	
		size++;
	}
	
	/**
	 * Inserta un elemento al final de la lista.
	 * @param element Elemento a insertar al final de la lista.
	 */
	public void addLast(E element) {
		NodoS<E> n= new NodoS<E>(element);
		if(isEmpty()) {
			head=n;
		}else {
			NodoS<E> aux=head;
			while(aux.getSiguiente() != null) {
				aux=aux.getSiguiente();
			}
			aux.setSiguiente(n);
		}
		size++;
	}
	
	/**
	 * Inserta un elemento luego de la posici�n pasada por par�matro.
	 * @param p Posici�n en cuya posici�n siguiente se insertar� el elemento pasado por par�metro.
	 * @param element Elemento a insertar luego de la posici�n pasada como par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		NodoS<E> n= checkPosition(p);
		NodoS<E> nuevo= new NodoS<E>(element);
		nuevo.setSiguiente( n.getSiguiente() );
		n.setSiguiente( nuevo );
		size++;
	}
	
	/**
	 * Inserta un elemento antes de la posici�n pasada como par�metro.
	 * @param p Posici�n en cuya posici�n anterior se insertar� el elemento pasado por par�metro. 
	 * @param element Elemento a insertar antes de la posici�n pasada como par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		NodoS<E> n= checkPosition(p);
		if(n==head) {
			addFirst(element);
		}else {
			try {
				NodoS<E> anterior = (NodoS<E>) prev(p);	
				NodoS<E> nuevo = new NodoS<E>( element, n );
				anterior.setSiguiente( nuevo );
				size++;
			} catch(BoundaryViolationException e) {
				System.out.println(e.getStackTrace());
			}
		}	
	}
	
	/**
	 * Remueve el elemento que se encuentra en la posici�n pasada por par�metro.
	 * @param p Posici�n del elemento a eliminar.
	 * @return element Elemento removido.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */	
	public E remove(Position<E> p) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("Lista vacia.");
		NodoS<E> n= checkPosition(p);
		E item=p.element();
		if(n==head) {
			head= n.getSiguiente();
		}else {
			try {
				NodoS<E> anterior=(NodoS<E>) prev(p);
				anterior.setSiguiente(n.getSiguiente());
			}catch(BoundaryViolationException e) {
				System.out.println(e.getStackTrace());
			}
		}
		n.setSiguiente(null);
		size--;
		return item;
	}
	
	/**
	 * Establece el elemento en la posici�n pasados por par�metro. Reemplaza el elemento que se encontraba anteriormente en esa posici�n y devuelve el elemento anterior.
	 * @param p Posici�n a establecer el elemento pasado por par�metro.
	 * @param element Elemento a establecer en la posici�n pasada por par�metro.
	 * @return Elemento anterior.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.	 
	 */
	public E set(Position<E> p, E element) throws InvalidPositionException{
		if(isEmpty())
			throw new InvalidPositionException("Lista vacia.");
		NodoS<E> n= checkPosition(p);
		E item= n.element();
		n.setElemento(element);
		return item;
	}
	
	/**
	 * Devuelve un iterador de todos los elementos de la lista.
	 * @return Un iterador de todos los elementos de la lista.
	 */
	public Iterator<E> iterator(){
		// Creo un elementIterator sobre la lista this a iterar
		return new ElementIterator<E>( this );
	}
	
	/**
	 * Devuelve una colecci�n iterable de posiciones.
	 * @return Una colecci�n iterable de posiciones.
	 */
	public Iterable<Position<E>> positions(){
		PositionList<Position<E>> list= new ListaSimpleEnlace<Position<E>>();
		if(!this.isEmpty()) {
			NodoS<E> n= head;
			while(n.getSiguiente() != null) {
				list.addLast(n);
				n = n.getSiguiente();
			}
			list.addLast(n);
		}
		return list;
	}
	
	
}

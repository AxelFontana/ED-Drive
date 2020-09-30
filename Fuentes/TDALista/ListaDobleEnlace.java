package TDALista;

import java.util.Iterator;


/**
 * TDA Lista de doble enlace
 * @author jacttis
 *
 */
public class ListaDobleEnlace<E> implements PositionList<E>{
	protected Nodo<E> Inicial,Final;
	protected int size;
	
	public ListaDobleEnlace() {
		Inicial=new Nodo<E>(null);
		Final=new Nodo<E>(null);
		Inicial.setSiguiente(Final);
		Final.setAnterior(Inicial);
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
	 * Devuelve la posicion del primer elemento de la lista. 
	 * @return Posicion del primer elemento de la lista.
	 * @throws EmptyListException si la lista esta vacia.
	 */
	public Position<E> first() throws EmptyListException{
		if(size==0) {
			throw new EmptyListException("Lista vacia.");
		}else {
			return Inicial.getSiguiente();
		}
	}
	
	/**
	 * Devuelve la posici�n del �ltimo elemento de la lista. 
	 * @return Posici�n del �ltimo elemento de la lista.
	 * @throws EmptyListException si la lista est� vac�a.
	 * 
	 */
	public Position<E> last() throws EmptyListException{
		if(size==0) {
			throw new EmptyListException("Lista vacia.");
		}else {
			return Final.getAnterior();
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
		Nodo<E> n=checkPosition(p);
		if(n==Final.getAnterior())
			throw new BoundaryViolationException("La posicion ingresada corresponde al ultimo elem de la lista.");
		if(size==0)
			throw new InvalidPositionException("La lista esta vacia");
		return n.getSiguiente();
	}

	/**
	 * Devuelve la posici�n del elemento anterior a la posici�n pasada por par�metro.
	 * @param p Posici�n a obtener su elemento anterior.
	 * @return Posici�n del elemento anterior a la posici�n pasada por par�metro.
	 * @throws InvalidPositionException si la posici�n pasada por par�metro es inv�lida o la lista est� vac�a.
	 * @throws BoundaryViolationException si la posici�n pasada por par�metro corresponde al primer elemento de la lista.
	 */
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException{
		Nodo<E> n=checkPosition(p);
		if(p==Inicial.getSiguiente())
			throw new BoundaryViolationException("La posicion corresponde al primer elem de la lista.");
		if(size==0)
			throw new InvalidPositionException("La lista esta vacia");
		return n.getAnterior();
	}
	
	/**
	 * Inserta un elemento al principio de la lista.
	 * @param element Elemento a insertar al principio de la lista.
	 */
	public void addFirst(E element) {
		Nodo<E> n=new Nodo<E>(element);
		Nodo<E> aux= Inicial.getSiguiente();
		Inicial.setSiguiente(n);
		aux.setAnterior(n);
		n.setAnterior(Inicial);
		n.setSiguiente(aux);	
		size++;
	}
	
	/**
	 * Inserta un elemento al final de la lista.
	 * @param element Elemento a insertar al final de la lista.
	 */
	public void addLast(E element) {
		Nodo<E> n=new Nodo<E>(element);
		Nodo<E> aux= Final.getAnterior();
		Final.setAnterior(n);
		aux.setSiguiente(n);
		n.setAnterior(aux);
		n.setSiguiente(Final);
		size++;
	}
	
	/**
	 * Inserta un elemento luego de la posici�n pasada por par�matro.
	 * @param p Posici�n en cuya posici�n siguiente se insertar� el elemento pasado por par�metro.
	 * @param element Elemento a insertar luego de la posici�n pasada como par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */
	public void addAfter(Position<E> p, E element) throws InvalidPositionException{
		if(size==0)
			throw new InvalidPositionException("La lista esta vacia");
		Nodo<E> n=checkPosition(p);
		Nodo<E> aux= new Nodo<E>(element); 
		aux.setSiguiente(n.getSiguiente());
		(n.getSiguiente()).setAnterior(aux);
		n.setSiguiente(aux);
		aux.setAnterior(n);
		size++;
	}
	
	/**
	 * Inserta un elemento antes de la posici�n pasada como par�metro.
	 * @param p Posici�n en cuya posici�n anterior se insertar� el elemento pasado por par�metro. 
	 * @param element Elemento a insertar antes de la posici�n pasada como par�metro.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */
	public void addBefore(Position<E> p, E element) throws InvalidPositionException{
		Nodo<E> Pos= checkPosition(p);
		Nodo<E> Ins= new Nodo<E>(element);
		if(size==0)
			throw new InvalidPositionException("La lista esta vacia.");
		Ins.setAnterior(Pos.getAnterior());
		(Pos.getAnterior()).setSiguiente(Ins);
		Pos.setAnterior(Ins);
		Ins.setSiguiente(Pos);
		size++;
	}
	
	/**
	 * Remueve el elemento que se encuentra en la posici�n pasada por par�metro.
	 * @param p Posici�n del elemento a eliminar.
	 * @return element Elemento removido.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.
	 */	
	public E remove(Position<E> p) throws InvalidPositionException{
		Nodo<E> Pos= checkPosition(p);
		if(size==0)
			throw new InvalidPositionException("Lista vacia");
		Nodo<E>	aux;
		aux=Pos.getAnterior();
		Pos.setAnterior(null);
		aux.setSiguiente(Pos.getSiguiente());
		(Pos.getSiguiente()).setAnterior(aux);
		Pos.setSiguiente(null);
		size--;
		return Pos.element();
	}
	
	/**
	 * Establece el elemento en la posici�n pasados por par�metro. Reemplaza el elemento que se encontraba anteriormente en esa posici�n y devuelve el elemento anterior.
	 * @param p Posici�n a establecer el elemento pasado por par�metro.
	 * @param element Elemento a establecer en la posici�n pasada por par�metro.
	 * @return Elemento anterior.
	 * @throws InvalidPositionException si la posici�n es inv�lida o la lista est� vac�a.	 
	 */
	public E set(Position<E> p, E element) throws InvalidPositionException{
		Nodo<E> Pos=checkPosition(p);
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		E aux;
		aux=Pos.element();
		Pos.setElemento(element);
		return aux;
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
	 * Devuelve una coleccion iterable de posiciones.
	 * @return Una coleccion iterable de posiciones.
	 */
	public Iterable<Position<E>> positions(){
		PositionList<Position<E>> list= new ListaDobleEnlace<Position<E>>();
		if(!this.isEmpty()) {
			Nodo<E> n=Inicial.getSiguiente();
			while(n!=Final) {
				list.addLast(n);
				n = n.getSiguiente();
			}
		}
		return list;	
	}
	
	private Nodo<E> checkPosition( Position<E> p ) throws InvalidPositionException {
			try {
				if( p == null || p==Inicial || p==Final)
					throw new InvalidPositionException("Posición nula");
				return (Nodo<E>) p;
			} catch( ClassCastException e ) {
				throw new InvalidPositionException( "...." );
			}
	}
	
}


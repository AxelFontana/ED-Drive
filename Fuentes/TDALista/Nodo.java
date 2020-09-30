package TDALista;

public class Nodo<E> implements Position<E>{
	private E elemento;
	private Nodo<E> siguiente;
	private Nodo<E> anterior;
	
	//Constructores
	public Nodo(E item, Nodo<E> sig, Nodo<E> ant){
		elemento=item;
		siguiente=sig;
		anterior=ant;
	}
	public Nodo(E item){
		this(item,null,null);
	}
	
	//Setters
	public void setElemento(E elemento){
		this.elemento=elemento;
	}
	public void setSiguiente(Nodo<E> siguiente){
		this.siguiente=siguiente;
	}
	public void setAnterior(Nodo<E> anterior) {
		this.anterior=anterior;
	}
	//Getters
	public Nodo<E> getSiguiente(){
		return siguiente;
	}
	public Nodo<E> getAnterior(){
		return anterior;
	}
	public E element(){
		return elemento;
	}
}

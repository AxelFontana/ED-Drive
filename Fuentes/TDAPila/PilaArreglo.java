package TDAPila;
public class PilaArreglo<E>implements Stack<E> {
	
	protected int cant;
	protected E [] arre;
	
	
	
	public PilaArreglo(int max){
		arre=(E[])new Object[max];
		cant=0;
	}
	
	public PilaArreglo() {
		this(25);
	}
	/**
	 * Consulta la cantidad de elementos de la pila.
	 * @return Cantidad de elementos de la pila.
	 */
	public int size() {
		return cant;
	}
	/**
	 * Consulta si la pila está vacía.
	 * @return Verdadero si la pila está vacía, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return cant==0;
	}

	/**
	 * Examina el elemento que se encuentra en el tope de la pila.
	 * @return Elemento que se encuentra en el tope de la pila.
	 * @throws EmptyStackException si la pila está vacía. 
	 */
	public E top()throws EmptyStackException{
		if(isEmpty()) {
			throw new EmptyStackException("Pila Vacia");
		}
	return arre[cant-1];
	}
	private E[] Copiar(){
		E[]aux= (E[])new Object[2*arre.length];
		for(int i=0;i<size();i++) {
			aux[i]=arre[i];
		}
		return aux;	
	}
	/**
	 * Inserta un elemento en el tope de la pila.
	 * @param element Elemento a insertar.
	 */
	public void push(E element) {
		if(arre.length==size()) {
			E[] ArreAux=Copiar();
			arre=ArreAux;
		}
		arre[cant]=element;
		cant++;
	}
	/**
	 * Remueve el elemento que se encuentra en el tope de la pila.
	 * @return Elemento removido.
	 * @throws EmptyStackException si la pila está vacía. 
	 */
	public E pop() throws EmptyStackException{
		if(isEmpty()) {
			throw new EmptyStackException("Pila Vacia");
		}
		E ret=arre[cant-1];
		arre[cant-1]=null;
		cant--;
		return ret;
	}
}

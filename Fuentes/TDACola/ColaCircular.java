package TDACola;

public class ColaCircular<E> implements Queue<E>{
	protected E cola[];
	protected int Inicial,Final;
	
	public ColaCircular() {
		cola=(E[])new Object[10];
		Inicial=0; Final=0;
	}
	
	public int size() {
		return (((cola.length)-Inicial+Final) % cola.length);
	}
	
	public boolean isEmpty() {
		return Inicial==Final;
	}
	
	public E front() throws EmptyQueueException{
		if(isEmpty())
			throw new EmptyQueueException("Cola vacia");
		return cola[Inicial];
	}
	
	public void enqueue(E elemento) {
		if(size()==(cola.length -1)){
			E[] aux= copiar(Inicial);
			Final=size();
			Inicial=0;
			cola=aux;
		}
		cola[Final]=elemento;
		Final=((Final+1)% cola.length);
		
	}
	private E[] copiar(int n) {
		E[] aux=(E[]) new Object[2*cola.length];
		for(int i=0; i<size(); i++) {
			aux[i]=cola[n];
			n=(n+1)%cola.length;
		}
		return aux;
	}
	
	public E dequeue() throws EmptyQueueException{
		if(isEmpty())
			throw new EmptyQueueException("Cola vacia.");
		E temp=cola[Inicial];
		cola[Inicial]=null;
		Inicial=(Inicial +1)% cola.length;
		return temp;
	}
}

package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;

public class Node<T extends Comparable<T>> implements INode<T> {

	private int index ;
	private T value ;
	private ArrayList<INode<T>>arr ;
	private IHeap<?> heap ;
	public Node(){
		index = 0 ;
		value = null ;
		arr = null ;
		heap = null ;
	}
	public Node(IHeap<T>heap,int index ,T value ,ArrayList<INode<T>>arr){
		this.index = index ;
		this.value = value ;
		this.arr = arr ;
		this.heap = heap ;
	}
	public INode<T> getLeftChild() {
		if(this.index*2+1 >= heap.size())return null ;
		return arr.get(this.index*2+1) ;
	}

	@Override
	public INode<T> getRightChild() {
		if(this.index*2+2 >= heap.size())return null ;
		return arr.get(this.index*2+2) ;
	}

	public INode<T> getParent() {
		return arr.get(index/2) ;
	}

	@Override
	public T getValue() {
		return value ;
	}

	@Override
	public void setValue(T value) {
		this.value = value ;
	}
	public void setArr(ArrayList<INode<T>> arr){
		this.arr = arr;
	}
	public int getIndex(){
		return index ;
	}
	public String toString(){
		return value.toString();
	}
}

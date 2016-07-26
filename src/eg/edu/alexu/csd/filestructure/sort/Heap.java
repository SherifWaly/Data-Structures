package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collection;

public class Heap<T extends Comparable<T>> implements IHeap<T>{
	
	private ArrayList<INode<T>>arr = new ArrayList<INode<T>>() ;
	private int size = 0 ;
	private boolean f = false ;
	public Heap(){
		
	}
	public INode<T> getRoot() {
		if(size() == 0)throw new RuntimeException() ;
		return arr.get(0) ;
	}

	public int size() {
		if(!f)return arr.size() ;
		return size ;
	}

	@Override
	public void heapify(INode<T> node) {
		INode<T> largest = node ;
		Node<T> l = (Node<T>) node.getLeftChild() ;
		Node<T> r = (Node<T>) node.getRightChild() ;
		if(l != null){
			if(l.getValue().compareTo(node.getValue()) > 0)largest = l ;
		}
		if(r != null){
			if(r.getValue().compareTo(largest.getValue()) > 0)largest = r ;
		}
		if(largest != node){
			T val = node.getValue() ;
			node.setValue(largest.getValue()) ;
			largest.setValue(val) ;
			heapify(largest);
		}
	}

	@Override
	public T extract() {
		if(size() == 0){
			throw new RuntimeException() ;
		}
		T val = arr.get(0).getValue() ;
		INode<T> node = arr.get(size()-1) ;
		arr.get(0).setValue(node.getValue());
		arr.remove(size()-1) ;
		if(size() != 0){
			heapify(arr.get(0)) ;
		}
		return val ;
	}

	@Override
	public void insert(T element) {
		int size = size() ;
		arr.add(new Node<T>(this,size,element,arr)) ;
		INode<T>node = (Node<T>)arr.get(size()-1) ;
		while(node != node.getParent()){
			if(node.getParent().getValue().compareTo(node.getValue()) < 0){
				T val = node.getValue() ;
				node.setValue(node.getParent().getValue()) ;
				node.getParent().setValue(val) ;
				node = node.getParent() ;
			}
			else {
				break ;
			}
		}
	}

	@Override
	public void build(Collection<T> unordered) {
		arr = new ArrayList<INode<T>>() ;
		if(unordered == null)return ;
		if(unordered.size() == 0)return ;
		for(T node : unordered){
			int sizee = size() ;
			arr.add(new Node<T>(this,sizee,node,arr)) ;
		}
		for(int i = size()/2 ; i >= 0 ; i--){
			heapify(arr.get(i)) ;
		}
	}
	public void heapBuild(Collection<T> unordered){
		if(unordered == null)return ;
		size = size() ;
		f = true ;
		while(size > 0){
			T t = arr.get(size-1).getValue() ;
			arr.get(size-1).setValue(arr.get(0).getValue()) ;
			arr.get(0).setValue(t) ;
			size -- ;
			heapify(arr.get(0)) ;
		}
		f = false ;
		size = unordered.size() ;
	}
}

package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;
import java.util.LinkedList;

public class Chaining<K,V> implements IHashChaining ,IHash<K,V>{
	private int size = 0;
	private int collisions = 0;
	ArrayList<LinkedList<Node<K, V>>>list;
	public Chaining(){
		list = new ArrayList<LinkedList<Node<K, V>>>();
		for(int i = 0 ; i < 1200 ; i++){
			list.add(new LinkedList<Node<K,V>>());
		}
	}
	public void put(K key, V value) {
		int x = key.hashCode()%list.size();
		boolean found = false;
		LinkedList<Node<K, V>>arr = list.get(x);
		for(Node<K, V>node : arr){
			if(node.key.equals(key)){
				node.value = value;
				found = true;
				break;
			}
			collisions++;
		}
		if(!found){
			arr.add(new Node<K, V>(key,value));
			size++;
		}
	}

	@Override
	public String get(K key) {
		int x = key.hashCode()%list.size();
		LinkedList<Node<K, V>>arr = list.get(x);
		for(Node<K, V>node : arr){
			if(node.key.equals(key)){
				return (String)node.value;
			}
			collisions++;
		}
		return null;
	}

	@Override
	public void delete(K key) {
		int x = key.hashCode()%list.size();
		LinkedList<Node<K, V>>arr = list.get(x);
		for(Node<K, V>node : arr){
			if(node.key.equals(key)){
				arr.remove(node);
				size--;
				break;
			}
			collisions++;
		}
		
	}

	@Override
	public boolean contains(K key) {
		int x = key.hashCode()%list.size();
		LinkedList<Node<K, V>>arr = list.get(x);
		for(Node<K, V>node : arr){
			if(node.key.equals(key)){
				return true;
			}
			collisions++;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		if(size == 0){
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int capacity() {
		return list.size();
	}

	@Override
	public int collisions() {
		return collisions;
	}

	@Override
	public Iterable<K> keys() {
		LinkedList<K>key = new LinkedList<K>();
		for(LinkedList<Node<K,V>>arr : list){
			for(Node<K,V>node : arr){
				key.add(node.key);
			}
		}
		return key;
	}

}

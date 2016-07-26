package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class LinearProbing<K, V> implements IHashLinearProbing,IHash<K,V> {
	private ArrayList<Node<K,V>>tarteeb;
	private Node<K,V>arr[];
	private int size = 0;
	private int collisions = 0;
	public LinearProbing(){
		arr = new Node[1200];
		tarteeb = new ArrayList<Node<K,V>>();
		collisions = 0;
		size = 0;
	}
	public void put(K key, V value) {
		if(!putt(key,value,true)){
			size = 0;
			rehash();
			putt(key,value,true);
		}
	}

	private boolean putt(K key, V value ,boolean f) {
		int x = key.hashCode()%arr.length;
		int c = 0;
		for(c = 0 ; c < arr.length ; c++){
			if(arr[x] == null){
				arr[x] = new Node<K, V>(key,value);
				if(f)tarteeb.add(arr[x]);
				size++;
				if(c > 0){
					collisions++;
				}
				return true;
			}
			collisions++;
			x = (x+1)%arr.length;
		}
		collisions++;
		return false;
	}
	private void rehash() {
		Node<K,V>aux[] = arr;
		int len = aux.length;
		arr = new Node[len*2];
		for(Node<K,V>node : tarteeb){
			putt(node.key,node.value,false);
		}
	}
	@Override
	public String get(K key) {
		int x = key.hashCode() % arr.length;
		while(true){
			if(arr[x] == null){
				collisions++;
				return null;
			}
			else if(arr[x].key.equals(key)){
				return (String)arr[x].value;
			}
			collisions++;
			x = (x+1)%arr.length;
		}
	}

	@Override
	public void delete(K key) {
		int x = key.hashCode() % arr.length;
		while(true){
			if(arr[x] == null){
				collisions++;
				return;
			}
			else if(arr[x].key.equals(key)){
				arr[x] = null;
				size--;
				return;
			}
			collisions++;
			x = (x+1)%arr.length;
		}

	}

	@Override
	public boolean contains(K key) {
		int x = key.hashCode() % arr.length;
		while(true){
			if(arr[x] == null){
				collisions++;
				return false;
			}
			else if(arr[x].key.equals(key)){
				return true;
			}
			collisions++;
			x = (x+1)%arr.length;
		}
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
		return arr.length;
	}

	@Override
	public int collisions() {
		return collisions;
	}

	@Override
	public Iterable<K> keys() {
		ArrayList<K>list = new ArrayList<K>();
		for(Node<K,V>node : tarteeb){
			if(node == null)continue;
			list.add(node.key);
		}
		return list;
	}

}

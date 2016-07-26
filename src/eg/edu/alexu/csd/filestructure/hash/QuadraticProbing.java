package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class QuadraticProbing<K, V> implements IHashQuadraticProbing,IHash<K,V> {

	private Node<K,V>arr[];
	private int size = 0;
	private int collisions = 0;
	private String string = "";
	public QuadraticProbing(){
		arr = new Node[1200];
		collisions = 0;
		size = 0;
	}
	public void put(K key, V value) {
		if(!putt(key,value)){
			size = 0;
			rehash();
			putt(key,value);
		}
		if(size == arr.length){
			size = 0;
			rehash();
		}
	}

	private boolean putt(K key, V value) {
		int x = key.hashCode()%arr.length;
		int c = 0;
		for(c = 0 ; c < arr.length ; c++){
			int y = (x+(int)(1L*c*c%arr.length))%arr.length;		
			if(arr[y] == null){
				arr[y] = new Node<K, V>(key,value);
				string += key.toString()+"\n";
				size++;
				if(c > 0){
					collisions++;
				}
				return true;
			}
			collisions++;
		}
		collisions++;
		System.out.println("hamada" + " " + size);
		return false;
	}
	private void rehash() {
		Node<K,V>aux[] = arr;
		int len = aux.length;
		arr = new Node[len*2];
		for(Node<K,V>node : aux){
			if(node == null)continue;
			putt(node.key,node.value);
		}
	}
	@Override
	public String get(K key) {
		int x = key.hashCode() % arr.length;
		int c = 0;
		while(true){
			int y = (x+(int)(1L*c*c%arr.length))%arr.length;		
			if(arr[y] == null){
				collisions++;
				return null;
			}
			else if(arr[y].key.equals(key)){
				collisions++;
				return (String)arr[x].value;
			}
			collisions++;
			c++;
		}
	}

	@Override
	public void delete(K key) {
		int x = key.hashCode() % arr.length;
		int c = 0;
		while(true){
			int y = (x+(int)(1L*c*c%arr.length))%arr.length;		
			if(arr[y] == null){
				collisions++;
				return;
			}
			else if(arr[y].key.equals(key)){
				collisions++;
				arr[y] = null;
				size--;
				return;
			}
			collisions++;
			c++;
		}
	}

	@Override
	public boolean contains(K key) {
		int x = key.hashCode() % arr.length;
		int c = 0;
		while(true){
			int y = (x+(int)(1L*c*c%arr.length))%arr.length;		
			if(arr[y] == null){
				collisions++;
				return false;
			}
			else if(arr[y].key.equals(key)){
				collisions++;
				return true;
			}
			collisions++;
			c++;
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
		for(Node<K,V>node : arr){
			if(node == null)continue;
			list.add(node.key);
		}
		return list;
	}

}

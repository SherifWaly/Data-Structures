package eg.edu.alexu.csd.filestructure.hash;

import java.util.ArrayList;

public class DoubleHash<K, V> implements IHashDouble,IHash<K,V> {

	private Node<K,V>arr[];
	private int size = 0;
	private int collisions = 0;
	private int prime = 0;
	private ArrayList<Node<K,V>>tarteeb;
	public DoubleHash(){
		arr = new Node[1200];
		prime = getPrime(arr.length);
		collisions = 0;
		size = 0;
		tarteeb = new ArrayList<Node<K,V>>();
		System.out.println(prime);
	}

	public void put(K key, V value) {
		/*if(size == arr.length){
			size = 0;
			rehash();
		}*/
		if(!putt(key,value,true)){
			size = 0;
			rehash();
			System.out.println(prime);
			putt(key,value,true);
		}
		if(size == arr.length){
			size = 0;
			rehash();
		}
	}

	private boolean putt(K key, V value,boolean f) {
		int x = key.hashCode()%arr.length;
		int h2 = prime - (Integer)key%prime;
		int c = 0;
		for(c = 0 ; c < arr.length ; c++){
			int y = (x+(int)(1L*h2*c%arr.length))%arr.length;	
			if(arr[y] == null){
				arr[y] = new Node<K, V>(key,value);
				if(f)tarteeb.add(arr[y]);
				size++;
				return true;
			}
			collisions++;
		}
		System.out.println("hamada");
		collisions++;
		return false;
	}
	private void rehash() {
		Node<K,V>aux[] = arr;
		int len = aux.length;
		//prime = getPrime(len*2);
		arr = new Node[len*2];
		for(Node<K,V>node : tarteeb){
			if(node == null)continue;
			putt(node.key,node.value,false);
		}
	}
	@Override
	public String get(K key) {
		int x = key.hashCode() % arr.length;
		int h2 = prime - (Integer)key%prime;
		int c = 0;
		while(true){
			if(arr[x] == null){
				return null;
			}
			else if(arr[x].key.equals(key)){
				return (String)arr[x].value;
			}
			collisions++;
			c++;
			x = (x+h2)%arr.length;
		}
	}

	@Override
	public void delete(K key) {
		int x = key.hashCode() % arr.length;
		int h2 = prime - (Integer)key%prime;
		int c = 0;
		while(true){
			if(arr[x] == null){
				return;
			}
			else if(arr[x].key.equals(key)){
				arr[x] = null;
				size--;
				return;
			}
			collisions++;
			c++;
			x = (x+h2)%arr.length;
		}
	}

	@Override
	public boolean contains(K key) {
		int x = key.hashCode() % arr.length;
		int h2 = prime - (Integer)key%prime;
		int c = 0;
		while(true){
			if(arr[x] == null){
				return false;
			}
			else if(arr[x].key.equals(key)){
				return true;
			}
			collisions++;
			c++;
			x = (x+h2)%arr.length;
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
	private int getPrime(int length) {
		for(int i = length-1 ; i >= 0 ; i-=2){
			if(check(i))return i;
		}
		return 0;
	}

	private boolean check(int i) {
		for(int j = 2 ; j*j <= i ; j++){
			if(i % j == 0)return false;
		}
		return true;
	}
	
}

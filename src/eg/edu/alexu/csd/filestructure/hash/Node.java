package eg.edu.alexu.csd.filestructure.hash;

public class Node<K, V> {
	public K key;
	public V value;
	public Node(K key , V value){
		this.key = key;
		this.value = value;
	}
}

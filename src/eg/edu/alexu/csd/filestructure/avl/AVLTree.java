package eg.edu.alexu.csd.filestructure.avl;

public class AVLTree<T extends Comparable<T>> implements IAVLTree<T> {

	private Node<T>root = null;
	@Override
	public void insert(T key) {
		root = insert(root,key);
	}

	private Node<T> insert(Node<T> node, T key) {
		if(node == null){
			return new Node<T>(key);
		}
		if(node.getValue().compareTo(key) > 0){
			node.left = insert(node.left,key);
		}
		else{
			node.right = insert(node.right,key);
		}
		updateHeight(node);
		int balance = getBalance(node);
		if(balance > 1 && node.left.getValue().compareTo(key) > 0){  //LL
			node = rightRotate(node);
		}
		else if(balance < -1 && node.right.getValue().compareTo(key) <= 0){    //RR
			node = leftRotate(node);
		}
		else if(balance > 1){    //LR
			node.left = leftRotate(node.left);
			node = rightRotate(node);
		}
		else if(balance < -1){   //RL
			node.right = rightRotate(node.right);
			node = leftRotate(node);
		}
		return node;
	}

	private Node<T> leftRotate(Node<T> node) {
		Node<T>right = node.right;
		Node<T>subtree = right.left;
		right.left = node;
		node.right = subtree;
		updateHeight(node);
		updateHeight(right);
		return right;
	}

	private Node<T> rightRotate(Node<T> node) {
		Node<T>left = node.left;
		Node<T>subtree = left.right;
		left.right = node;
		node.left = subtree;
		updateHeight(node);
		updateHeight(left);
		return left;
	}

	private void updateHeight(Node<T> node) {
		node.height = 1+Math.max(height(node.left), height(node.right));
	}
	@Override
	public boolean delete(T key) {
		if(!search(key)){
			return false;
		}
		root = delete(root,key);
		return true;
	}

	private Node<T> delete(Node<T> node, T key) {
		if(node == null){
			return null;
		}
		if(node.getValue().compareTo(key) > 0){
			node.left = delete(node.left,key);
		}
		else if(node.getValue().compareTo(key) < 0){
			node.right = delete(node.right,key);
		}
		else{
			if(node.left == null && node.right == null){
				return null;
			}
			else if(node.left == null){
				node = node.right;
			}
			else if(node.right == null){
				node = node.left;
			}
			else{
				Node<T>successor = getSuccessor(node);
				node.setValue(successor.getValue());
				node.right = delete(node.right,successor.getValue());
			}
		}
		updateHeight(node);
		int balance = getBalance(node);
		if(balance > 1 && getBalance(node.left) > -1){       //LL
			node = rightRotate(node);
		}
		else if(balance > 1){   //LR
			node.left = leftRotate(node.left);
			node = rightRotate(node);
		}
		else if(balance < -1 && getBalance(node.right) < 1){ //RR
			node = leftRotate(node);
		}
		else if(balance < -1){  //RL
			node.right = rightRotate(node.right);
			node = leftRotate(node);
		}
		return node;
	}

	private int getBalance(Node<T> node) {
		return height(node.left)-height(node.right);
	}

	private Node<T> getSuccessor(Node<T> node) {
		node = node.right;  //if it was null the node would be removed from before
		while(node.left != null){
			node = node.left;
		}
		return node;
	}

	@Override
	public boolean search(T key) {
		INode<T> node = root;
		while(node != null){
			if(node.getValue().compareTo(key) == 0){
				return true;
			}
			if(node.getValue().compareTo(key) > 0){
				node = node.getLeftChild();
			}
			else{
				node = node.getRightChild();
			}
		}
		return false;
	}

	@Override
	public int height() {
		return height(root);
	}

	@Override
	public INode<T> getTree() {
		return root;
	}
	
	private int height(Node<T>node){
		if(node == null){
			return 0;
		}
		return node.height;
	}
}
class Node<T extends Comparable<T>> implements INode<T>{
	
	Node<T>right = null;
	Node<T>left = null;
	private T value = null;
	int height = 1;
	
	public Node(T value){
		this.value = value;
	}
	@Override
	public INode<T> getLeftChild() {
		return left; 
	}

	@Override
	public INode<T> getRightChild() {
		return right;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
	
}

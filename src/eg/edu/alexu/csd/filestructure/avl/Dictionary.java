package eg.edu.alexu.csd.filestructure.avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Dictionary implements IDictionary{

	private Scanner reader;
	
	private IAVLTree<String>tree = new AVLTree<String>();
	
	private int size = 0;
	
	
	public void load(File file) {
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			return;
		}
		while(reader.hasNextLine()){
			String input = reader.nextLine();
			if(input == null)continue;
			if(input.equals(""))continue;
			if(!tree.search(input)){
				tree.insert(input);
				size++;
			}
		}
		reader.close();
	}

	@Override
	public boolean insert(String word) {
		if(!tree.search(word)){
			tree.insert(word);
			size++;
			return true;
		}
		return false;
	}

	@Override
	public boolean exists(String word) {
		return tree.search(word);
	}

	@Override
	public boolean delete(String word) {
		if(tree.delete(word)){
			size--;
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int height() {
		return tree.height();
	}
	
}

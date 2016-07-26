package eg.edu.alexu.csd.filestructure.sort;

import java.util.ArrayList;
import java.util.Collections;

public class Sorter<T extends Comparable<T>> implements ISort<T> {

	@Override
	public IHeap<T> heapSort(ArrayList<T> unordered) {
		IHeap<T>heap = new Heap<T>() ;
		heap.build(unordered);
		((Heap<T>) heap).heapBuild(unordered) ;
		return heap;
	}

	@Override
	public void sortSlow(ArrayList<T> unordered) {
		for (int i = 0; i < unordered.size(); i++) {
			for (int j = 0 ; j < unordered.size()-1 ; j++) {
				if(comp(unordered.get(j),unordered.get(j+1)))swap(j, j + 1, unordered);
			}
		}
	}


	private void swap(int i, int j, ArrayList<T> unordered) {
		T tmp = unordered.get(i);
		unordered.set(i, unordered.get(j));
		unordered.set(j, tmp);
	}

	@Override
	public void sortFast(ArrayList<T> unordered) {
		int sz = unordered.size() ;
		mergeSort(0,sz-1,unordered);
	}

	private void mergeSort(int i, int j, ArrayList<T> unordered) {
		if(i > j)return ;
		if(i == j)return ;
		int mid = (i+j)/2 ;
		mergeSort(i,mid,unordered) ;
		mergeSort(mid+1,j,unordered) ;
		ArrayList<T>t = new ArrayList<T>();
		int x = i , y = mid+1 ;
		while(x <= mid+1 || y <= j+1){
			if(x == mid+1 && y == j+1)break ;
			if(x == mid+1){
				t.add(unordered.get(y)) ;
				y++ ;
			}
			else if(y == j+1){
				t.add(unordered.get(x)) ;
				x++ ;
			}
			else {
				if(comp(unordered.get(y),unordered.get(x))){
					t.add(unordered.get(x)) ;
					x++ ;
				}
				else {
					t.add(unordered.get(y)) ;
					y++ ;
				}
			}
		}
		for(x = 0 ; x < t.size() ; x++){
			unordered.set(x+i, t.get(x)) ;
		}
	}

	private boolean comp(T x , T y) {
		return x.compareTo(y) > 0;
	}

}

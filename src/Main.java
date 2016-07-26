import java.util.Random;
import java.util.Scanner;

import eg.edu.alexu.csd.filestructure.hash.Chaining;
import eg.edu.alexu.csd.filestructure.hash.DoubleHash;
import eg.edu.alexu.csd.filestructure.hash.IHash;
import eg.edu.alexu.csd.filestructure.hash.LinearProbing;
import eg.edu.alexu.csd.filestructure.hash.QuadraticProbing;


public class Main {
	public static void main(String []args){
		IHash table = new QuadraticProbing();
		System.out.println(table.capacity());
		Random x = new Random();
		Scanner in = new Scanner(System.in);
		for(int i = 0 ; i < 1000 ; i++){
			int z = in.nextInt();
			table.put(z, "45");
		}
		System.out.println(table.collisions());
		//table.put(1,"SFS");
	}
}

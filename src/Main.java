import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;


public class Main extends JFrame {
	
	
	

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter size of individuals");
		int n = Integer.parseInt(sc.nextLine());
		System.out.println("Enter size of population");
		int l = Integer.parseInt(sc.nextLine());
		System.out.println("Enter tournament size");
		int s = Integer.parseInt(sc.nextLine());
		System.out.println("Enter mutation probability");
		double mut = Double.parseDouble(sc.nextLine());
		System.out.println("Enter crossover probability");
		double xover = Double.parseDouble(sc.nextLine());
		System.out.println("Enter replacement %");
		double replace = Double.parseDouble(sc.nextLine());
		System.out.println("Enter number of generations");
		int g = Integer.parseInt(sc.nextLine());
		System.out.println("Stop after first solution? y for yes, n for no");
		char stop = sc.nextLine().charAt(0);
		sc.close();
		Generation ga = new Generation();
		ga.mainCycle(n, l, s, mut, xover,replace, g, stop);
		
		/*
		List<String> st = ga.getStats();
		for(String stat : st)
			System.out.println(stat);
		ArrayList<Individual> indies= new ArrayList<Individual>();
		for(Individual p: ga.genPop.pop)
		{
			if(p.fitness == 0.0 && !indies.contains(p)) {
	    indies.add(p);
		System.out.println(p + " " + p.fitness);}
		}
		*/
		
	}
}

import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author HT and JT
 *
 */
public class Generation {

	List<String> stats;
	Population genPop;
	BoardGraphics bg;
	private int b_width = 1024;
	private int b_height = 720;
	
	public Generation()
	{
		stats = new ArrayList<String>();
		 genPop = new Population();
	}
	
	/**
	 *  The genetic Algorithm cycle. 
	 * @param n the size of the individuals
	 * @param l the size of the population
	 * @param s the size of the tournaments
	 * @param mut the mutation probability
	 * @param xover the crossover probability
	 * @param replace the replacement percentage
	 * @param g the number of generations 
	 */
	public void mainCycle(int n, int l, int s, double mut, double xover,double replace, int g, char stop )
	{
		List<Individual> permut, selected, offspring;
		selected = new ArrayList<Individual>();
		String genStats;
		genPop.populate(n, l);
		startGraphicBoard(n);
		genPop.setPopulationStats();
		String firstGenStats = genPop.gen + ": " + genPop.stats.toString();
		stats.add(firstGenStats);
		int tourns = genPop.pop.size()/s;
		// Selection 
		for(int j = 0 ; j < g; j ++) {
		for(int i = 0; i < s ; i++)
		{
			permut = genPop.permutatePopulation();
			selected.addAll(genPop.TournamentSelection(permut, tourns, s));	
		}
	    // CrossOver
		offspring = genPop.crossOverPopulation(selected, xover);	
		//Replacement
		genPop.replacePopulation(offspring, replace);
		//Mutation
		genPop.mutatePopulation(mut);
		genPop.setPopulationStats();
		
		genStats = genPop.gen + ": " + genPop.stats.toString();
		stats.add(genStats);
		Collections.sort(genPop.pop);
		updateGraphicBoard();
		if(genPop.stats.min == 0 && stop == 'y')
			break;
		}
	}
	
	public List<String> getStats()
	{
		return this.stats;
	}
	
	
	public void startGraphicBoard(int n)
	{
		
		this.bg = new BoardGraphics(n, b_width, b_height);
		bg.setSize(b_width, b_height);
		bg.setVisible(true);
		bg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bg.setResizable(true);	
		bg.placeQueens(bg.getGraphics(), genPop.pop.get(0)  );
		bg.showStats(bg.getGraphics(), genPop.gen, (int) genPop.pop.get(0).fitness, genPop.stats.avg, b_width, b_height );
	}
	
	
	public void updateGraphicBoard()
	{
	   Graphics boardGraphics = bg.getGraphics();
	   bg.clearBoard(boardGraphics);
	   bg.drawBoard(boardGraphics);
	   bg.placeQueens(boardGraphics, genPop.pop.get(0));
	   bg.showStats(bg.getGraphics(), genPop.gen,(int) genPop.pop.get(0).fitness ,genPop.stats.avg , b_width, b_height);
	}
	
}
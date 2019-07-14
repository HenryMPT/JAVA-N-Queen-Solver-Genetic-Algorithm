import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * @author HT and JT
 *
 */
public class Population  {

	
	Random generator;
	ArrayList<Individual> pop ;
	Stats stats;
	int gen;
	
	
	public class Stats
	{
		double max, avg, min;

		public Stats()
		{
			max = -1;
			avg = 0;
			min = 99999999;
		}
		
		@Override
		public String toString()
		{
		
			DecimalFormatSymbols uS = new DecimalFormatSymbols();
			uS.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("0.00", uS);
			return df.format(max) + " "  + df.format(avg) + " " + df.format(min) ;
		}
		
	}
	
	public void setPopulationStats()
	{
		double fit;
		this.stats = new Stats();
		for(Individual ind : pop)
		{
			fit = ind.getFitness();
			this.stats.avg += fit;
			if( fit > this.stats.max)
				this.stats.max  = fit;
			if(fit < this.stats.min )
				this.stats.min = fit;
		}
		this.stats.avg = this.stats.avg / pop.size();
	}
	
	public Population()
	{
		generator = new Random(0);
		pop = new ArrayList<Individual>();
		stats = new Stats();
		gen = 0;
	}
	

	/**
	 *  Gets a random index between 0 and n-1 
	 * @param u the random number
	 * @param n size of the list
	 * @return a random index
	 */
	public int getRandomIndex(double u, int n)  
	{

		return (int) Math.round( u * (n-1)) ;
	}
	
	/**
	 *  Gets a random index between a and b
	 * @param u the random number
	 * @param a the minimum index
	 * @param b the maximum index
	 * @return a random index
	 */
	public int getRandomIndexInInterval(double u, int a, int b)  
	{

		return  a + (int) Math.round( u * (b-a)) ;
	}
	
	
	
	/**
	 *  Creates a population by creating a sequential Individual of size 
	 *  n, and then creates l permutations of this Individual and adds them to the population.
	 * @param n size of the individuals
	 * @param l size of the population
	 */
	public void populate(int n, int l) {
	Individual sequential = new Individual(n);
	Individual tmp;
	Double u;
	ArrayList<Integer> chromos;
	int index;
	for(int i = 0; i < l ; i ++)
	{
		tmp = sequential.clone();
		chromos = new ArrayList<Integer>();
		for(int j = 0 ; j < n ; j ++) {
		u = generator.nextDouble();
		index = getRandomIndex(u,tmp.Chromo.size() );
		chromos.add(tmp.Chromo.remove(index));
		}
		pop.add(new Individual(chromos));
	}	
	}

	

	/**
	 *  Cycles through the population and based on the crossover probability does one of the following:
	 *  - add two crossOver offsprings 
	 *  - add the parents 
	 * @param selected the selected list of individuals
	 * @param mut the crossover probability
	 * @return the new list of individuals
	 */
	public List<Individual> crossOverPopulation(List<Individual> selected, double mut) {
		List<Individual> offspring = new ArrayList<Individual>();
		Individual p1, p2;
		double u;
		int n = selected.size()/2;
		for(int i = 0; i < n ; i++)
		{
			p1 = selected.remove(0);
			p2 = selected.remove(0);
			u = generator.nextDouble();

			if( u < mut)
			{
				offspring.add(p1.crossOver(p1, p2, generator));
				offspring.add(p1.crossOver(p2, p1, generator));
			}
			else
			{
				offspring.add(p1);
				offspring.add(p2);
			}
		}
		return offspring;
	}


	/**
	 *  Replaces a certain percentage of the population with the offspring
	 * @param offspring the list of generated offspring
	 * @param percent the percentage to be replaced
	 */
	public void replacePopulation(List<Individual> offspring, double percent) {
		int n = (int) Math.round(percent * pop.size());
		Collections.sort(pop);
		Collections.sort(offspring);
		int size = pop.size()-1;
		int i = 0;
		while(i < n)
		{
			pop.set(size - i, offspring.remove(0));
			i++;
		}
		gen++;
		
	}

	
	


/**
 *  Mutates the whole population, using the getMutated method from the Individual class.
 * @param mut the mutation probability
 * @return the list of mutated individuals
 */
	public List<Individual> mutatePopulation(double mut) {
		Individual mutIndividual;
		double rand;
		List<Individual> mutPop = this.cloneIndividuals(this.pop);
		for(int i = 0 ; i < this.pop.size() ; i++) {
			rand = generator.nextDouble();
			if( rand < mut) {
			mutIndividual = this.pop.get(i).getMutated(generator);
			mutPop.set(i, mutIndividual);
		}}
		return mutPop;
		
	}
	
	
	/**
	 * 
	 * @param original the original list of Individuals to be cloned
	 * @return a deep copy of the Individual List
	 */
	public List<Individual> cloneIndividuals(List<Individual> original)
	{
		List<Individual> clone = new ArrayList<Individual>();
		for( Individual ind : original)
			clone.add(ind);
		return clone;
	}

/**
 *  Randomly permutates individuals within the population
 * @return the permutated list of Individuals
 */
	public List<Individual> permutatePopulation() {
		List<Individual> perm = cloneIndividuals(this.pop); 
		double r;
		int index;
		int n = this.pop.size();
		for(int i = 0; i < n-1; i++)
		{
			r = generator.nextDouble();
			index = getRandomIndexInInterval(r, i, n-1);
	//		System.out.println("replacing: " + perm.get(i) + " with " + perm.get(index));
			Individual tmp = perm.get(i);
			perm.set(i, perm.get(index));
			perm.set(index, tmp );
		}
		return perm;
	}



 /** Performs a tournament a given list of competitors and returns a list of winners. 
  * 
  * @param competitors the Individual list of competitors
  * @param n the size of the list
  * @param r the size of the tournament
  * @return the list of tournament winners
  */
	public List<Individual> TournamentSelection(List<Individual> competitors, int n, int r) {
		List<Individual> winners = new ArrayList<Individual>();
		List<Individual> round = new ArrayList<Individual>(r);
		for(int i = 0; i < n; i++)
		{
			round.clear();
			for(int j = 0 ; j < r; j++) {
				round.add(competitors.remove(0));
			}
			Collections.sort(round);
			winners.add(round.get(0));
		}
		return winners;
	}
	

	
	
}

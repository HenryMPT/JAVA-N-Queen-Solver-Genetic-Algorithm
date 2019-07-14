import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author HT and JT
 *
 */
public class Individual implements Comparable<Object>, Generational {

	
	public ArrayList<Integer> Chromo;
	public double fitness;
	
	public Individual( ArrayList<Integer> Ch)
	{
		this.Chromo = Ch;
		this.fitness = setFitness();
	}
	
	public Individual( )
	{
		this.Chromo = new ArrayList<Integer>();
	}
	
	public Individual( int n)
	{
		this.Chromo = sequentialIntegers(n);
		this.fitness = setFitness();
	}
	
	@Override
	/**
	 *  Gets the fitness value of the individual. In this context, the fitness is the number of conflicts
	 *  ( how many queens can attack each other).
	 * @return the number of conflicts
	 */
	public double getFitness() {
		return this.fitness;
	}
	
	
	
	
	/**
	 *  Sets the fitness value of the individual. In this context, the fitness is the number of conflicts
	 *  ( how many queens can attack each other). So the lower the fitness, the better. 
	 * @return the number of conflicts
	 */
	public int setFitness()
	{
		int conflicts = 0;
		for(int i = 0; i < this.Chromo.size(); i++)
		{
			conflicts += checkLeftDiagonal(i, this.Chromo.get(i));
			conflicts += checkRightDiagonal(i, this.Chromo.get(i));
		}
		return conflicts;
	}
	
	/**
	 *  Gets a random index between 0 and n-1 
	 * @param u the random number
	 * @param n size of the list
	 * @return a random index
	 */
	public static int getRandomIndex(double u, int n)  
	{

		return (int) Math.round( u * (n-1)) ;
	}
	/**
	 * 
	 * @param i the starting line 
	 * @param j the starting column
	 * @return number of conflicts on the left diagonal
	 */
	public int checkLeftDiagonal(int i, int j)
	{
		int result = 0;
		int next;
		while(j > 0 && i < this.Chromo.size() - 1 )
		{
			i++;
			j--;
			next = this.Chromo.get(i);
			if(next == j )
				result+=1;
		}
		return result;
	}
	
	/**
	 * 
	 * @param i the starting line 
	 * @param j the starting column
	 * @return number of conflicts on the right diagonal
	 */
	public int checkRightDiagonal(int i, int j)
	{
		int result = 0;
		int next;
		while(j < this.Chromo.size() && i < this.Chromo.size() - 1 )
		{
			i++;
			j++;
			next = this.Chromo.get(i);
			if(next == j )
				result+=1;
		}
		return result;
	}	
	
	/**
	 *  Initializes a non sequential genome made by the integers from 0 to n. 
	 *  The genome is valid for the N Queen problem. 
	 * @param n the size N
	 * @return Individual's arraylist
	 */
	public ArrayList<Integer> sequentialIntegers(int n)
	{
		ArrayList<Integer> seq = new ArrayList<Integer>(n);
		for(int i = 0; i < n; i++)
		seq.add(i);
		return seq;
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for(Integer i : this.Chromo)
		{
			s += i + " ";
		}
		return s;
	}
	
	@Override
	public boolean equals(Object o)
	{
		Individual cmp = (Individual) o;
		int i = 0;
		for(Integer num : this.Chromo)
			if( num != cmp.Chromo.get(i++))
				return false;
		return true;
	}
	
	/** Adds a + sign to commond edges. 
	 * 
	 * @param ind The individual
	 * @param adjMap The adjancy map
	 * @param i the index of the node
	 * @param n the index of the edge
	 */
	public static void addCommonEdge(Individual ind , HashMap<Integer, ArrayList<String>> adjMap, int i, int n) {
		int p;
		ArrayList<String> tmp;
		p = adjMap.get(ind.Chromo.get(i)).indexOf("" + ind.Chromo.get(n));
		tmp = adjMap.get(ind.Chromo.get(i));
		tmp.set(p, tmp.get(p) + "+") ;
	}
	
	
	/**
	 * 
	 * Part of the Edge Recombination Method. Given an Individual and its initialized adjancencys map, 
	 * adds all adjacencys.
	 * 
	 * @param ind The Individual
	 * @param adjMap The initialized adjacency map
	 */
	public static void mapAdjacencys(Individual ind, HashMap<Integer, ArrayList<String>> adjMap)
	{
		ArrayList<String> tmp;
		int n = ind.Chromo.size();
		for(int i = 0 ; i < n ; i++)
		{
			if(i == 0)
			{
				if(adjMap.get(ind.Chromo.get(i)).contains("" + ind.Chromo.get(n-1)))
				{
					addCommonEdge(ind,adjMap,i, n-1);
				}
				else
				{
					adjMap.get(ind.Chromo.get(i)).add("" + ind.Chromo.get(n-1));	
				}
				if(adjMap.get(ind.Chromo.get(i)).contains("" + ind.Chromo.get(i+1)))
				{
					addCommonEdge(ind,adjMap,i, i+1);
				}
				else
				{
					adjMap.get(ind.Chromo.get(i)).add("" + ind.Chromo.get(i+1));	
				}
				
			}
			else if(i==n-1)
			{
				if(adjMap.get(ind.Chromo.get(i)).contains("" + ind.Chromo.get(0)))
				{
					addCommonEdge(ind,adjMap,i, 0);
				}
				else
				{
					adjMap.get(ind.Chromo.get(i)).add("" + ind.Chromo.get(0));	
				}
				
				if(adjMap.get(ind.Chromo.get(i)).contains("" + ind.Chromo.get(i-1)))
				{
					addCommonEdge(ind,adjMap,i, i-1);
				}
				else
				{
					adjMap.get(ind.Chromo.get(i)).add("" + ind.Chromo.get(i-1));	
				}
			}
			else
			{
				if(adjMap.get(ind.Chromo.get(i)).contains("" + ind.Chromo.get(i+1)))
				{
					addCommonEdge(ind,adjMap,i, i+1);
				}
				
				else
				{
					adjMap.get(ind.Chromo.get(i)).add("" + ind.Chromo.get(i+1));	
				}
				
				if(adjMap.get(ind.Chromo.get(i)).contains("" + ind.Chromo.get(i-1)))
				{
					addCommonEdge(ind,adjMap,i, i-1);
				}
				else
				{
					adjMap.get(ind.Chromo.get(i)).add("" + ind.Chromo.get(i-1));	
				}
				
			}
			
	}} 
		
	/**
	 * Initializes the adjacency map, mapping each number to an empty list
	 * 
	 * @param ind The Individual whose genome is going to be mapped
	 * @param adjMap The Adjacency map 
	 */
		public static void initializeMap(Individual ind, HashMap<Integer,ArrayList<String>> adjMap)
		{
			for(Integer num : ind.Chromo)
			{
				adjMap.put(num, new ArrayList<String>());
			}
		}
	
		
		/**
		 * Creates a new individual, reorganizing the genome of the parents 
		 * with the Edge Recombination operator. 
		 * 
		 * 
		 * @param parent1 The first parent
		 * @param parent2 The second parent
		 * @param rand The random generator
		 * @return newly generated Individual
		 */
	public static Individual edgeRecombination(Individual parent1, Individual parent2 , Random rand)
	{
		int n = parent1.Chromo.size();
		HashMap<Integer, ArrayList<String>> adjMap = new HashMap<Integer, ArrayList<String>>();
		initializeMap(parent1, adjMap);
		mapAdjacencys(parent1, adjMap );
		mapAdjacencys(parent2, adjMap );
		ArrayList<Integer> result = new ArrayList<Integer>();
		int node = parent1.Chromo.get(getRandomIndex(rand.nextDouble(),n));
		while(result.size() < n) {
			result.add(node);
			removeAllAdjacent(node, adjMap);
			if(!adjMap.get(node).isEmpty()) {
				
				node = getAdjacentEdge(adjMap,node,rand);
			}
			else
			{	if(result.size() == n)
				break;
				node = getAvailableNode(parent1, result, rand);
			}
		}
		return new Individual(result);
	}
	
	@Override
	/**
	 * Returns an Individual generator by crossing over parent1 and parent2
	 */
	public   Individual crossOver(Generational parent1, Generational parent2 , Random rand) {
		return edgeRecombination((Individual)parent1,  (Individual)parent2 ,rand);
		
	}

	/**
	 * 
	 * @param u the randomly generated double 
	 * @param a the first possible index
	 * @param b the last possible index
	 * @return the random index
	 */
	public int getRandomIndexInInterval(double u, int a, int b)  
	{

		return  a + (int) Math.round( u * (b-a)) ;
	}	
	

/**
 *  Cycles through the genome of the individual and swaps , or not, each allel with a random one, depending on the probability,
 * @param mut the mutation probability
 * @param rand the Random generator
 * @return the mutated Individual
 */
	public Individual swapMutation(double mut, Random rand)
{
	Individual mutated = this.clone();
	int n = this.Chromo.size();
	double p, u;
	int tmp, index;
	for(int i = 0; i < n-1; i++)
	{
		p = rand.nextDouble();
		if( p < mut)
		{
			u = rand.nextDouble();
			index = getRandomIndexInInterval(u, i, n-1 );
			tmp = mutated.Chromo.get(i);
			mutated.Chromo.set(i, mutated.Chromo.get(index));
			mutated.Chromo.set(index, tmp);	
		}
	}
	return mutated;
	
	
}

@Override
/**
 * Returns a mutated individual. The mutation is swapping two chromossomes.
 */
public Individual getMutated(Random rand)
{
	int index1 = getRandomIndex(rand.nextDouble(),this.Chromo.size());
	int index2 = getRandomIndex(rand.nextDouble(),this.Chromo.size());
	while( index2 == index1)
		index2 = getRandomIndex(rand.nextDouble(),this.Chromo.size());		
	Individual mutated = this.clone();
	int n = this.Chromo.size();
	int tmp = mutated.Chromo.get(index1);
	mutated.Chromo.set(index1, mutated.Chromo.get(index2));
	mutated.Chromo.set(index2, tmp);	
	return mutated;	
	}

/** 
 *  Selects the next edge node to add to map prioritizing shortest adjacency list and common edges. Otherwise it's chosen at random. 
 * @param adjMap adjancy map
 * @param node current node
 * @param rand random generator
 * @return the next edge to add the the path. 
 */
public static int getAdjacentEdge(HashMap<Integer, ArrayList<String>> adjMap, int node, Random rand)
{

		int min_size = Integer.MAX_VALUE;
		boolean repeatedmin =  false;
		int best_node = 0;
		int doubledge = -1;
		for(String p : adjMap.get(node)) {
	
			if(p.length() > 1 && p.charAt(p.length()-1) == '+') {
				p = p.substring(0,p.length()-1);
				doubledge = Integer.parseInt(p);
			}
			int v = Integer.parseInt(p);
			
			if(adjMap.get(v).size() < min_size) {
				repeatedmin = false;
				best_node = v;
				min_size = adjMap.get(v).size();
				}
			else if(adjMap.get(v).size() == min_size)
				repeatedmin = true;
			
		}
		if(repeatedmin)
		{
			if(doubledge !=-1) {
			if(adjMap.get(doubledge).size() <= min_size)
				best_node = doubledge; 
		}
			else {
				
				best_node = Integer.parseInt(adjMap.get(node).get(getRandomIndex(rand.nextDouble(), adjMap.get(node).size())));
			}
		}
		return best_node;
}



/**
 *  Returns a random node from the list of available nodes. To get this list the original list will all nodes is cloned,
 *  and the used nodes are removed from it. 
 * 
 * @param parent The Individual parent
 * @param used The list of used nodes 
 * @param rand The random generator
 * @return the chosen available node
 */
private static int getAvailableNode(Individual parent, ArrayList<Integer> used, Random rand)
{
	Individual clone = parent.clone();
	
	for(Integer num : used)
	{
		clone.Chromo.remove(clone.Chromo.indexOf(num));
	}
	return clone.Chromo.get(getRandomIndex(rand.nextDouble(), clone.Chromo.size()));
}


   /**
    * This method is used by the ERO operator. 
    * @param node The node to remove from the lists
    * @param adjMap The map of adjacencys
    */
	private static void removeAllAdjacent(int node, HashMap<Integer, ArrayList<String>> adjMap) {
		//adjMap.remove(node);
		for(Integer i : adjMap.keySet()) {
			adjMap.get(i).remove(""+node);
			adjMap.get(i).remove(node+"+");	
		}
	}

	@Override
	public int compareTo(Object o) {
		Individual cmp = (Individual) o;
		return (int)( this.fitness - cmp.fitness);
	}
	
	@Override
	public Individual clone()
	{
		Individual cl = new Individual();
		for(Integer num : this.Chromo)
		{
			cl.Chromo.add(num);
		}
		return cl;
	}
	
	
}

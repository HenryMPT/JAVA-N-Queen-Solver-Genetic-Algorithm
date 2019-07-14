import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class TestNQueens {

	@Test
	public void testClone() {
		Individual original = new Individual(8);
		Individual clone = original.clone();
		clone.Chromo.remove(0);
		Assert.assertNotEquals(original.Chromo, clone.Chromo);
	}
	
	public boolean checkRepeatedNumber(Individual ind)
	{
		boolean repeated = false;
		int cmp;
		int n = ind.Chromo.size();
		for(int i = 0; i < n-1; i++) {
			if(repeated)
				break;
			cmp = ind.Chromo.get(i);
			for(int j = i+1 ; j < n ; j++)
			{
				if(ind.Chromo.get(j) == cmp) {
					repeated = true;
					break;}
			}	
			}
		return repeated;
	}
	
	@Test
	public void testEqualIndividuals()
	{
		Individual original = new Individual(8);
		Individual original2 = new Individual(8);
		Individual original3= new Individual(8);
		original3.Chromo.set(3, 99);
		Assert.assertTrue(original.equals(original2));
		Assert.assertFalse(original.equals(original3));
	}
	

	
	public void testUniquePermutations()
	{
		Individual ind = new Individual(100);
		Assert.assertFalse(checkRepeatedNumber(ind));
	}
	
	@Test
	public void testConflicts()
	{
		  ArrayList<Integer> solution = new ArrayList<Integer>();
		  solution.add(3);
		  solution.add(6);
		  solution.add(2);
		  solution.add(7);
		  solution.add(1);
		  solution.add(4);
		  solution.add(0);
		  solution.add(5);
		  Individual ind = new Individual(solution);
		  ArrayList<Integer> solution2 = new ArrayList<Integer>();
		  solution2.add(1);
		  solution2.add(7);
		  solution2.add(5);
		  solution2.add(0);
		  solution2.add(2);
		  solution2.add(4);
		  solution2.add(6);
		  solution2.add(3);
		  Individual ind2 = new Individual(solution2);
		  ArrayList<Integer> solution3 = new ArrayList<Integer>();
		  solution3.add(3);
		  solution3.add(5);
		  solution3.add(0);
		  solution3.add(2);
		  solution3.add(6);
		  solution3.add(7);
		  solution3.add(1);
		  solution3.add(4);
		  Individual ind3 = new Individual(solution3);
		  Assert.assertEquals(ind.getFitness(), 0, 0.001);
		  Assert.assertEquals(ind2.getFitness(), 0, 0.001);
		  Assert.assertEquals(ind3.getFitness(), 1, 0.001);
	}
	
	@Test
	public void testTournamentSelection()
	{
		Population p = new Population();
		int n = 320;
		p.populate(100, n);
		List<Individual> perm1 = p.permutatePopulation();
		double avg = 0;
		double size = perm1.size();
		for(Individual ind : perm1) 
			avg += ind.fitness;
		List<Individual> winners = 	p.TournamentSelection(perm1, 8, 8);
	
	}

	
	@Test
	public void testPopulate() {
		Population p = new Population();
		int n = 10000;
		p.populate(20, n);
		for(Individual ind : p.pop) {
			Assert.assertFalse(checkRepeatedNumber(ind));
		}
		Assert.assertEquals(p.pop.size(), n);
		
	}
	
	@Test
	public void testAdjacencyList()
	{
		  ArrayList<Integer> solution = new ArrayList<Integer>();
		  solution.add(1);
		  solution.add(2);
		  solution.add(3);
		  solution.add(4);
		  solution.add(5);
		  solution.add(6);
		  solution.add(7);
		  solution.add(8);
		  solution.add(9);
		  Individual ind = new Individual(solution);
		  ArrayList<Integer> solution2 = new ArrayList<Integer>();
		  solution2.add(9);
		  solution2.add(3);
		  solution2.add(7);
		  solution2.add(8);
		  solution2.add(2);
		  solution2.add(6);
		  solution2.add(5);
		  solution2.add(1);
		  solution2.add(4);
		  Individual ind2 = new Individual(solution2);
		  HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();//Individual.edgeRecombination(ind, ind2);
			Individual.initializeMap(ind, map);
			Individual.mapAdjacencys(ind, map );
			Individual.mapAdjacencys(ind2, map );
		  Assert.assertEquals(map.toString(),"{1=[9, 2, 4, 5], 2=[3, 1, 6, 8], 3=[4, 2, 7, 9], 4=[5, 3, 9, 1], 5=[6+, 4, 1], 6=[7, 5+, 2], 7=[8+, 6, 3], 8=[9, 7+, 2], 9=[1, 8, 4, 3]}");
	}
	
	@Test
	public void testSwapMutation()
	{
		Individual ind = new Individual(8);
		Individual mutated = ind.swapMutation(1, new Random(0));
		Individual notMutated = ind.swapMutation(0, new Random(0));
		Assert.assertNotEquals(ind, mutated);
		Assert.assertEquals(ind, notMutated);
	}
	
	@Test
	public void testSwapPopulation()
	{
		Population p = new Population();
		p.populate(8, 10);
		List<Individual> mutated = p.mutatePopulation(0.25);
		Assert.assertNotEquals(mutated, p.pop);
	}
	
	
	@Test
	public void testEdgeRecombinationOperator()
	{
		  ArrayList<Integer> solution = new ArrayList<Integer>();
		  solution.add(1);
		  solution.add(2);
		  solution.add(3);
		  solution.add(4);
		  solution.add(5);
		  solution.add(6);
		  solution.add(7);
		  solution.add(8);
		  solution.add(9);
		  Individual ind = new Individual(solution);
		  ArrayList<Integer> solution2 = new ArrayList<Integer>();
		  solution2.add(9);
		  solution2.add(3);
		  solution2.add(7);
		  solution2.add(8);
		  solution2.add(2);
		  solution2.add(6);
		  solution2.add(5);
		  solution2.add(1);
		  solution2.add(4);
		  Individual ind2 = new Individual(solution2);
		  Random r = new Random(0);
		  Individual firstborn = Individual.edgeRecombination(ind, ind2,r);
		  Assert.assertNotEquals(firstborn, ind);
		  Assert.assertNotEquals(firstborn, ind2);
	}
	
	
	@Test
	public void testPermutatePopulation() {
		Population p = new Population();
		int n = 8;
		p.populate(8, n);

		List<Individual> perm = p.permutatePopulation(); 
		Assert.assertEquals(p.pop.size(), perm.size());
		Assert.assertNotEquals(p.pop, perm);
		for(Individual c : p.pop)
		{
			Assert.assertTrue(perm.contains(c));
		}
	
	}
	
	
}

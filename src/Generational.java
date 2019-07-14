import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author HT and JT
 *
 */
public interface Generational {

	

	
	public double getFitness();
	
	public Generational getMutated(Random rand);
	
	public Generational crossOver(Generational parent1, Generational parent2 , Random rand);
	
	
	
	
}

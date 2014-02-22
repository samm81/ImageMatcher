import java.math.BigInteger;
import java.util.Random;

public class Breeder {

	Random r;
	
	public Breeder() {
		r = new Random();
	}
	
	public BigInteger breed(BigInteger parent1, BigInteger parent2) {
		int bitLength = parent1.bitCount();
		int splitPoint = r.nextInt(bitLength);
		
		parent1 = parent1.shiftRight(splitPoint).shiftLeft(splitPoint);
		BigInteger mask = BigInteger.valueOf(2).pow(splitPoint).subtract(BigInteger.valueOf(1));
		parent2 = parent2.and(mask);
		
		return parent1.or(parent2);
	}
	
	public BigInteger mutate(BigInteger gene){
		return gene.flipBit(r.nextInt(gene.bitCount()));
	}
	
}

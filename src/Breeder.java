import java.math.BigInteger;
import java.util.Random;

public class Breeder {

	Random r;
	
	public Breeder() {
		r = new Random();
	}
	
	public BigInteger breed(BigInteger parent1, BigInteger parent2) {
		int bitLength = parent1.bitLength();
		
		int splitPoint = r.nextInt(bitLength);
		
		parent1 = parent1.shiftRight(splitPoint).shiftLeft(splitPoint);
		BigInteger mask = BigInteger.valueOf(2).pow(splitPoint).subtract(BigInteger.valueOf(1));
		parent2 = parent2.and(mask);
		
		return parent1.or(parent2);
	}
	
}

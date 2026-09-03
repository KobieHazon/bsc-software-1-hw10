package riddles;

public class C extends B {
	
	private int i;
	private int j;

	public C(int i, int j) {
		super(i,j);
		
	}

	@Override
	public int compareTo(A other) {
		if (other instanceof C == false) {
			return -other.compareTo(new A(this.j, this.i));
		}
		return (new A(j, i)).compareTo(new A(other.j, other.i));
	}



}
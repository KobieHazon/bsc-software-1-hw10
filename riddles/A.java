package riddles;


public class A implements Comparable<A> {
	
	protected int i;
	protected int j;

	public A(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public int hashCode() {
		return this.i;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof A) {
			return obj.equals(this.i);
		}
		else if (obj instanceof B) {
			return obj.equals(this.j);
		}
		return obj.equals(this.i);
	}

	@Override
	public int compareTo(A o) {
		if (this instanceof B) {
			if (o instanceof B) {
				return this.j - o.j;
			}
			else {
				return this.j - o.i;
			}
		}
		else {
			if (o instanceof B) {
				return this.i - o.j;
			}
			else {
				return this.i - o.i;
			}
		}
	}
	
	
	public String toString() {return "("+this.i+" "+this.j+")";}



}

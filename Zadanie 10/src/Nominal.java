
public enum Nominal {
	Zł1(1), Zł2(2), Zł5(5), Zł10(10), Zł20(20), Zł50(50), Zł100(100), Zł200(200), Zł500(500);
	
	private int wartość;
	
	private Nominal( int wartość ) {
		this.wartość = wartość;
	}
	
	public int wartość() {
		return wartość;
	}
}

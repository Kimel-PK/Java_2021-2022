import java.io.Serializable;


public class DaneDoPrzechowania implements Serializable {
	private int i;
	
	public DaneDoPrzechowania( int i ) {
		this.i = i;
	}
	
	public void compare( int j ) {
		if ( i == j ) System.out.println( "Odtworzono prawidłowo" );
		else System.out.println( "BLAD !!!!" );
	}
}

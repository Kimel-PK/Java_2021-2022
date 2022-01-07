
public class Start {

	/**
	 * @param args Argumenty uruchomienia programu - trzy katalogi, do ktorych maja trafic obiekty
	 */
	public static void main(String[] args) {
		PrzechowywaczI p = new PrzechowywaczObiektow();
		
		DaneDoPrzechowania d1 = new DaneDoPrzechowania( 1 );
		DaneDoPrzechowania d2 = new DaneDoPrzechowania( 2 );
		DaneDoPrzechowania d3 = new DaneDoPrzechowania( 3 );
		DaneDoPrzechowania d4 = new DaneDoPrzechowania( 4 );
		DaneDoPrzechowania d5;
		
		int d1_id = p.save( args[ 0 ], d1 );
		int d2_id = p.save( args[ 1 ], d2 );
		int d3_id = p.save( args[ 2 ], d3 );
		int d4_id = p.save( args[ 2 ], d4 );
		
		d1 = ( DaneDoPrzechowania )p.read( d1_id );
		d2 = ( DaneDoPrzechowania )p.read( d2_id );
		d3 = ( DaneDoPrzechowania )p.read( d3_id );
		d4 = ( DaneDoPrzechowania )p.read( d4_id );
		d5 = ( DaneDoPrzechowania )p.read( d1_id );
		
		d1.compare( 1 );
		d2.compare( 2 );
		d3.compare( 3 );
		d4.compare( 4 );
		d5.compare( 1 );
		
		System.out.println( "Zapis niezapisywalnego " );
		if ( p.save( args[ 0 ], new Start() ) == -1 ) {
			System.out.println( " Poprawnie wykryto, ze obiektu nie mozna zapisac" );
		}
	}
}

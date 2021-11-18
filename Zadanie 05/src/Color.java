
/**
 * Typ wyliczeniowy reprezentujący kolory
 */
public enum Color {
	BLACK, WHITE, GREEN, YELLOW, RED, ORANGE, PINK,  ;
	
	@Override
	public String toString() {
		return name().substring(0, 1);
	}
	
	/**
	 * Metoda main pozwalająca zobaczyć jak prezentowane są
	 * poszczególne kolory.
	 */
	public static void main(String[] args) {
		for ( Color color : Color.values() ) {
			System.out.println( color.name() + " -> " + color );
		}
	}
}

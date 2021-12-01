/**
 * Dostawca skrytek
 *
 */
public interface HidingPlaceSupplier {
	/**
	 * Interfejs pojedynczej skrytki
	 *
	 */
	public interface HidingPlace {
		/**
		 * Zwraca true jeśli skrytka coś zawiera. W przeciwnym wypadku false.
		 * 
		 * @return czy skrytka posiada zawartość
		 */
		public boolean isPresent();

		/**
		 * Otwiera skrytkę i zwraca jej wartość.
		 * 
		 * @return wartość zawartości skrytki
		 */
		public double openAndGetValue();
	}

	/**
	 * Zwraca pojedynczą skrytkę, brak skrytek sygnalizuje za pomocą null. Metoda
	 * może być wykonywana współbieżnie.
	 * 
	 * @return obiekt skrytki
	 */
	public HidingPlace get();

	/**
	 * Liczba wątków, które mają obsługiwać przeszukiwanie skrytek.
	 * Wartość jest stała dla danego obiektu.
	 * 
	 * @return liczba wątków jakie mają przeszukiwać skrytki
	 */
	public int threads();
}

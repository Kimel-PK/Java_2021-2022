
/**
 * Interfejs dostawcy obiektów zgodnych z HidingPlaceSupplier
 */
public interface HidingPlaceSupplierSupplier {
	/**
	 * Metoda zwraca obiekty dostarczające skrytek aż do wyczerpania ich liczby.
	 * Brak kolejnych obiektów sygnalizowane jest poprzez zwrócenie null.
	 * 
	 * @param totalValueOfPreviousObject suma wartości przedmiotów w wyciągniętych
	 *                                   ze skrytek dostrczonych przez poprzedni
	 *                                   obiekt HidingPlaceSupplier.
	 * @return obiekt dostarcy skrytek
	 */
	public HidingPlaceSupplier get(double totalValueOfPreviousObject);
}

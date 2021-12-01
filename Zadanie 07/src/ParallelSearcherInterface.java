
/**
 * Interfejs współbieżnego systemu przeszukiwania skrytek.
 */
public interface ParallelSearcherInterface {
	/**
	 * Metoda ustawiająca dostęp do obiektu dostarczającego obiekty zawierające
	 * skrytki.
	 * 
	 * @param supplier obiekt dostarczający obiekty zawierające skrytki
	 */
	public void set(HidingPlaceSupplierSupplier supplier);
}

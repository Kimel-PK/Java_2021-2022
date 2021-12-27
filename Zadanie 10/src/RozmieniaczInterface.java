import java.util.List;

/**
 * Interfejs serwisu rozmieniajacego pieniadze.
 */
@FunctionalInterface
public interface RozmieniaczInterface {
	/**
	 * Metoda rozmienia (o ile jest to mozliwe) pieniadze na drobniejsze.
	 * Rozmieniać można wyłącznie pieniądze rozmienialne.
	 * W wyniku uzyskuje się pieniądze rozmienialne.
	 * Suma nominałów zwróconych pieniędzy zgadza się z nominałem
	 * pieniądza przekazanego. Rozmiana może być wykonana dowolnie.
	 * Np.
	 * <pre>
	 * 100 -> 50 + 50
	 * 100 -> 20 + 10 + 10 + 10 + 50
	 * </pre>
	 * Najniższy nominał, który podlega rozmianie to 2 zł.
	 * 
	 * @param pieniadzDoRozmienienia pieniadz rozmieniany
	 * @return uzyskane drobne
	 */
	public List<Pieniadz> rozmien( Pieniadz pieniadzDoRozmienienia );
}

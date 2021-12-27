import java.util.List;
import java.util.function.Supplier;

/**
 * Interfejs kasjera rozliczającego zakupy.
 */
public interface KasjerInterface {
	/**
	 * Rozliczenia zakupów polega na pobraniu z przekazanych pieniędzy ceny i
	 * zwróceniu reszty. Wyliczając resztę należy uwzględnić prawa matematyki,
	 * nierozmienialnych złotówek oraz chęć minimalizacji traconych środków (kasjer
	 * zwraca nierozmienialne złotówki tylko jeśli musi).
	 * 
	 * @param cena      kwota do zapłaty za towar
	 * @param pieniadze przekazane kasjerowi pieniądze
	 * @return reszta
	 */
	public List<Pieniadz> rozlicz(int cena, List<Pieniadz> pieniadze);

	/**
	 * Metoda zwraca aktualny stan kasy.
	 * 
	 * @return stan kasy czyli lista posiadanych pieniedzy.
	 */
	public List<Pieniadz> stanKasy();

	/**
	 * Metoda pozwala na przekazanie dostepu do serwisu, który pozwoli na
	 * rozmienienie pieniedzy.
	 * 
	 * @param rozmieniacz serwis rozmieniajacy pieniadze
	 */
	public void dostępDoRozmieniacza(RozmieniaczInterface rozmieniacz);

	/**
	 * Metoda ustawia dostep do dostawcy poczatkowego stanu kasy. Przekazanego
	 * dostawce należy odpytać o pieniądze w kasie i ponawiać pytanie aż do
	 * uzyskania null.
	 * 
	 * @param dostawca dostarcza poczatkowego stanu pieniedzy w kasie
	 */
	public void dostępDoPoczątkowegoStanuKasy(Supplier<Pieniadz> dostawca);
}

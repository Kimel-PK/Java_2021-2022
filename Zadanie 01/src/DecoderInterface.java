
/**
 * Abstrakcyjna klasa definiująca interfejs pozwalający na dekodowanie protokołu
 * szeregowego opisanego w zadaniu 01.
 * 
 * @author oramus
 *
 */
public abstract class DecoderInterface {
	/**
	 * Metoda pozwala na dostarczanie danych do zdekodowania. Pojedyncze wywołanie
	 * metody dostarcza jeden bit.
	 * 
	 * @param bit Argumentem wywołania jest dekodowany bit. Argument może przybrać
	 *            wartości wyłącznie 0 i 1.
	 */
	public abstract void input(int bit);

	/**
	 * Metoda zwraca odkodowane dane. Metoda nigdy nie zwraca null. Jeśli jeszcze
	 * żadna liczba nie została odkodowana metoda zwraca "" (pusty ciąg znaków,
	 * czyli ciąg znaków o długości równej 0).
	 * 
	 * @return Ciąg znaków reprezentujący sekwencję odkodowanych danych.
	 */
	public abstract String output();

	/**
	 * Metoda przywraca stan początkowy. Proces odkodowywania danych zaczyna się od
	 * początku.
	 */
	public abstract void reset();
}

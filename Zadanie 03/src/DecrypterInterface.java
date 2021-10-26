import java.util.Map;

/**
 * Interfejs systemu dekodującego zaszyfrowane wiadomości.
 */
public interface DecrypterInterface {
	/**
	 * Metoda do przekazania tekstu zakodowanej wiadomości.
	 * 
	 * @param encryptedDocument zaszyfrowana wiadomość.
	 */
	public void setInputText(String encryptedDocument);

	/**
	 * Metoda zwraca mapę ujawniającą sposób kodowania. Kluczem tej mapy jest
	 * kodowany znak. Wartością znak, na który klucz został zamieniony w
	 * zaszyfrowanej wiadomości.
	 * 
	 * @return Sposób zakodowania wiadomości czyli mapa prowadząca od znaku do jego
	 *         kodu. W przypadku braku możliwości rozkodowania wiadomości lub jej
	 *         braku (do setInputText przekazano null, lub metody nie wywołano)
	 *         zwracana jest mapa o rozmiarze 0.
	 */
	public Map<Character, Character> getCode();

	/**
	 * Metoda zwraca mapę ujawniającą sposób możliwiający zdekowanie
	 * zaszyfrowanej wiadomości Kluczem tej mapy jest kod. Wartością znak, na który
	 * klucz należy zamienić aby odtworzyć oryginalną wiadomość.
	 * 
	 * @return Sposób zdekodowania wiadomości czyli mapa prowadząca od kodu do
	 *         oryginalnego znaku. W przypadku braku możliwości rozkodowania
	 *         wiadomości lub jej braku (do setInputText przekazano null, lub metody
	 *         nie wywołano) zwracana jest mapa o rozmiarze 0.
	 */
	public Map<Character, Character> getDecode();
}

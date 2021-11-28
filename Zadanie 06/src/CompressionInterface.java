import java.util.Map;

public interface CompressionInterface {
	/**
	 * Metoda dodaje pojedyncze słowo. Wszystkie słowa będą mieć taką samą długość i
	 * składać się wyłącznie z kombinacji zer i jedynek.
	 * 
	 * @param word słowo z ciągu danych przeznaczonych do kompresji.
	 */
	public void addWord(String word);

	/**
	 * Metoda zleca wykonanie kompresji przekazanych danych.
	 */
	public void compress();

	/**
	 * Metoda zwraca nagłówek. Mapa zawiera jako klucz ciąg, który koduje słowo i
	 * słowo, które jest nim kodowane. W nagłówku umieszczane są wyłącznie
	 * informacje o słowach, które kodowane będą za pomocą mniejszej niż oryginalna
	 * ilości bitów. Jeśli przekazanej sekwencji nie można skompresować podaną
	 * metodą metoda zwraca mapę o rozmiarze 0.
	 * 
	 * @return mapa kodowania słów
	 */
	public Map<String, String> getHeader();

	/**
	 * Metoda zwraca kolejne elementy skompresowanej sekwencji.
	 * 
	 * @return pojedyncze słowo ze skompresowanej sekwencji.
	 */
	public String getWord();

}

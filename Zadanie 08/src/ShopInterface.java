import java.util.Map;

/**
 * Interfejs sklepu. Wszyskie metody mogą być wywoływane współbieżnie. Metoda
 * purchase jako jedyna może doprowadzić to zablokowania pracy wywołującego ją
 * wątku (stan wątku WAITING). Pozostałe metody muszą dać się wywołać pomimo
 * istniejących zablokowanych wątków oczekujących na zakończenie metody
 * purchase.
 */
public interface ShopInterface {
	/**
	 * Dostawa dóbr do sklepu. Dostawa opisana jest w postaci mapy. Kluczem jest
	 * nazwa dostarczonego produktu, wartością ilość dostarczonych sztuk. Efektem
	 * ubocznym metody jest zakończenie oczekiwania wszystkich tych wątków, które
	 * wywołały metodę purchase dla towaru, którego nie było w sklepie w
	 * wystarczającej ilości, a został on dostarczony. Dostawa nie kończy
	 * oczekiwania wątków, które oczekują na dostawę innego towaru.
	 * 
	 * @param goods spis dostarczonych do sklepu dóbr. Klucz nazwa towaru, wartość
	 *              ilość dostarczonych sztuk.
	 */
	public void delivery(Map<String, Integer> goods);

	/**
	 * Zakup towaru o podanej nazwie (productName) w liczbie quantity sztuk. W
	 * przypadku braku takiego towaru lub braku odpowiedniej ilości sztuk towaru
	 * wątek, który wywołał metodę jest blokowany do czasu dostawy zawierającej ten
	 * produkt. Jeśli sklep posiada na stanie odpowiednią ilość sztuk towaru zakup
	 * jest realizowany powodując odpowiednie zmniejszenie stanu magazynu.
	 * 
	 * @param productName nazwa towaru
	 * @param quantity    ilość sztuk
	 * @return true - zakup zrealizowany, false - zakup niezrealizowany.
	 */
	public boolean purchase(String productName, int quantity);

	/**
	 * Aktualny stan magazynu. Mapa zawiera informacje o wszystkich towarach, które
	 * zostały dostarczone do sklepu, nawet jeśli w magazynie nie ma ani jednej
	 * sztuki danego towaru (wszystkie zostały sprzedane). Kluczem jest nazwa
	 * towaru, wartością aktualna liczba szuk towaru w magazynie sklepu.
	 * 
	 * @return stan magazynu sklepu
	 */
	public Map<String, Integer> stock();
}

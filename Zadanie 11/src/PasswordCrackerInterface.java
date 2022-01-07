
public interface PasswordCrackerInterface {
	/**
	 * Metoda zwraca hasło do serwisu znajdującego się na komputerze host i
	 * oczekującego na połączenia na porcie o numerze port.
	 * 
	 * @param host adres serwera
	 * @param port numer portu
	 * @return hasło
	 */
	public String getPassword(String host, int port);
}

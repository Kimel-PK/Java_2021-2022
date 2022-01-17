import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

class PrzechowywaczObiektow implements PrzechowywaczI {
	
	Connection con;
	
	/**
	 * Metoda ustawia połączenie do bazy danych typu SQLite.
	 * 
	 * @param connection referencja do utworzonego połączenia do bazy danych.
	 */
	public void setConnection(Connection connection) {
		con = connection;
	}

	/**
	 * Zleca pisanie obiektu na dysku w podanym katalogu.
	 * 
	 * @param path           Identyfikator katalogu, w którym ma zostać zapisany
	 *                       obiekt.
	 * @param obiektDoZapisu Referencja do obiektu, ktory ma zostac zapisany na
	 *                       dysku.
	 * @return Identyfikator obiektu. Podanie tego identyfikatora w metodzie read ma
	 *         pozwolic na odzyskanie obiektu.
	 * @throws IllegalArgumentException błędny identyfikator ścieżki (brak takiej w
	 *                                  bazie) lub problem z przekazaną referencją
	 *                                  obiektDoZapisu.
	 */
	public int save(int path, Object obiektDoZapisu) throws IllegalArgumentException {
		
		// dodaj wpis do bazy
		
		Statement zapytanie;
		
		try {
			zapytanie = con.createStatement();
			zapytanie.executeQuery("");
		} catch (SQLException e) {}
		
		
		// zapisz plik na dysku
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("objects.bin"))) {
			outputStream.writeObject(obiektDoZapisu);
		} catch (IOException e) {}
		
		return 0;
	}

	/**
	 * Zleca odczyt obiektu o podanym id.
	 * 
	 * @param obiektDoOdczytu Identyfikator obiektu, ktory chcemy odzyskac.
	 * @return Obiekt typu Optional zawierający (o ile istnieje) obiekt o podanym
	 *         obiektDoOdczytu. W przypadku podania błędnego idektyfikatora
	 *         obiektDoOdczytu metoda zwraca pusty obiekt Optional. Metoda
	 *         <b>nigdy</b> nie zwraca null.
	 */
	public Optional<Object> read(int obiektDoOdczytu) {
		
		// odczytaj wpis z bazy
		
		
		// odczytaj plik z dysku
		Optional<Object> obiekt = null;
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("objects.bin"))) {
			obiekt = Optional.of((Object) inputStream.readObject());
		} catch (IOException e) {} catch (ClassNotFoundException e) {}
		
		return obiekt;
	}
	
}
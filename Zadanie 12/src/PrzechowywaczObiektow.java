import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Random;

class PrzechowywaczObiektow implements PrzechowywaczI {
	
	Connection con;
	
	public void setConnection(Connection connection) {
		con = connection;
	}
	
	public int save(int path, Object obiektDoZapisu) throws IllegalArgumentException {
		
		try {
			
			Random rand = new Random();
			String nazwaPliku = Long.toString(System.currentTimeMillis()) + "-" + rand.nextInt(99) + ".plik";
			
			// odczytaj sciezke z bazy
			
			Statement zapytanie = con.createStatement();
			ResultSet wynik = zapytanie.executeQuery("SELECT katalog FROM Katalogi WHERE idKatalogu = " + path);
            wynik.next();
			
            String sciezka = wynik.getString("katalog") + "/";
            
			// zapisz plik na dysku
            
            File plik = new File(sciezka + nazwaPliku);
            System.out.println(plik.getAbsolutePath());
            plik.createNewFile();
			ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(plik));
			outputStream.writeObject(obiektDoZapisu);
			
			outputStream.close();
			
			// dodaj wpis do bazy
			
			zapytanie = con.createStatement();
			zapytanie.executeUpdate("INSERT INTO Pliki VALUES (null, " + path + ", '" + nazwaPliku + "')");
			
			wynik = zapytanie.getGeneratedKeys();
            wynik.next();
			
			int id = wynik.getInt(1);
			System.out.println(id);
			
			return id;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public Optional<Object> read(int obiektDoOdczytu) {
		
		try {
			
			// odczytaj wpis z bazy
			
			Statement zapytanie = con.createStatement();
			ResultSet wyniki = zapytanie.executeQuery("SELECT Katalogi.katalog, Pliki.plik FROM Pliki INNER JOIN Katalogi ON Katalogi.idKatalogu = Pliki.idKatalogu WHERE idPliku = " + obiektDoOdczytu);
			
			if (!wyniki.next()) {
				return Optional.empty();
			}
			
			String sciezka = wyniki.getString("katalog");
			String nazwaPliku = wyniki.getString("plik");
			
			// odczytaj plik z dysku
			Optional<Object> obiekt = null;
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(sciezka + "/" + nazwaPliku));
			obiekt = Optional.of((Object) inputStream.readObject());
			
			inputStream.close();
			return obiekt;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
	 	} catch (ClassNotFoundException e) {
	 		e.printStackTrace();
	 	}
		
		return null;
	}
}
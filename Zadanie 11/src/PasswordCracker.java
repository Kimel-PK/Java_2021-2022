import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

class PasswordCracker implements PasswordCrackerInterface {
	
	List<Boolean> blokady = new ArrayList<>();
	List<Integer> haslo = new ArrayList<>();
	String schemat;
	int odgadnieteZnaki = 0;
	
	public String getPassword(String host, int port) {
		
		Socket socket = null;
		BufferedReader reader = null;
		try {
			socket = new Socket (host, port); // tworzymy gniazdo sieciowe
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			PrintWriter writer = new PrintWriter (socket.getOutputStream());
			writer.println ("Program");
			writer.flush();
			
			reader.readLine(); // Powitac!
			reader.readLine(); // timeout: 2000 msec
			schemat = reader.readLine().substring(9); // schema : ?????
			reader.readLine(); // Zaczynamy! Zadnij haslo
			
			// System.out.println("Schemat to: " + schemat);
			
			StworzPusteHaslo ();
			
			for (int i = 0; i < 99999; i++) {
				
				// System.out.println("Zgaduj: " + ZbudujHaslo());
				// System.out.println("Blokad: " + WypiszBlokady());
				
				writer.println(ZbudujHaslo());
				writer.flush();
				
				int iloscTrafien = CzytajIloscTrafien(reader);
				// System.out.println(odgadnieteZnaki + " => " + iloscTrafien);
				
				if (iloscTrafien == schemat.length())
					return ZbudujHaslo();
				
				if (iloscTrafien > odgadnieteZnaki) {
					odgadnieteZnaki = iloscTrafien;
					Dostroj(reader, writer);
				} else {
					KrokWPrzod();
				}
				
			}
			
		} catch (UnknownHostException e) {
			System.out.println ("Nie znam takiego hosta " + host);
		} catch (ConnectException e) {
			System.out.println ("Blad polaczenia z serwerem " + host);
		} catch (Exception e) {
			e.printStackTrace ();
		}
		
		return null;
	}
	
	void StworzPusteHaslo () {
		
		haslo.clear();
		blokady.clear();
		
		odgadnieteZnaki = 0;
		
		for (int i = 0; i < schemat.length(); i++) {
			haslo.add(0);
			blokady.add(false);
		}
	}
	
	String ZbudujHaslo () {
		String _haslo = "";
		
		for (int i = 0; i < schemat.length(); i++) {
			_haslo += PasswordComponents.passwordComponents.get(schemat.charAt(i)).get(haslo.get(i));
		}
		
		return _haslo;
	}
	
	void KrokWPrzod () {
		// System.out.println("Krok");
		for (int i = 0; i < schemat.length(); i++) {
			InkrementujZnak(i);
		}
	}
	
	void Dostroj (BufferedReader reader, PrintWriter writer) {
		// System.out.println("Dostrajanie");
		for (int i = 0; i < schemat.length(); i++) {
			
			// System.out.println("Pozycja: " + i);
			
			if (!InkrementujZnak(i))
				continue;
			
			writer.println(ZbudujHaslo());
			writer.flush();
			
			// System.out.println("Zgaduj: " + ZbudujHaslo());
			// System.out.println("Blokad: " + WypiszBlokady());
			
			int iloscTrafien = CzytajIloscTrafien(reader);
			// System.out.println(odgadnieteZnaki + " => " + iloscTrafien);
			
			if (iloscTrafien < odgadnieteZnaki) {
				// System.out.println("Ilosc trafien spadla");
				haslo.set(i, haslo.get(i) - 1);
				blokady.set(i, true);
			} else if (iloscTrafien > odgadnieteZnaki) {
				// System.out.println("Ilosc trafien wzrosla");
				blokady.set(i, true);
				odgadnieteZnaki++;
				
				if (iloscTrafien == schemat.length())
					return;
				
			} else {
				// System.out.println("Ilosc trafien nie zmienila sie");
			}
		}
	}
	
	int CzytajIloscTrafien (BufferedReader reader) {
		try {
			String trafienia = reader.readLine().replaceAll("\\D+", "");
			if ("".equals(trafienia)) {
				return schemat.length();
			} else {
				return Integer.parseInt(trafienia);
			}
		} catch (IOException e) {}
		return -1;
	}
	
	String WypiszBlokady () {
		String _blokady = "";
		for (int i = 0; i < schemat.length(); i++) {
			if (blokady.get(i)) {
				_blokady += "X";
			} else {
				_blokady += "O";
			}
		}
		return _blokady;
	}
	
	boolean InkrementujZnak (int pozycja) {
		if (blokady.get(pozycja) == false) {
			haslo.set(pozycja, haslo.get(pozycja) + 1);
			if (haslo.get(pozycja) + 1 == PasswordComponents.passwordComponents.get(schemat.charAt(pozycja)).size()) {
				blokady.set(pozycja, true);
			}
			return true;
		}
		return true;
	}
	
}
import java.util.LinkedList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

class Kasjer implements KasjerInterface {
	
	List<Pieniadz> kasa = new LinkedList<>();
	RozmieniaczInterface rozmieniacz;
	
	public Kasjer () {
		System.out.println("Nowy Kasjer");
	}
	
	public List<Pieniadz> rozlicz(int cena, List<Pieniadz> _pieniadze) {
		
		List<Pieniadz> pieniadze = new LinkedList<>(_pieniadze);
		List<Pieniadz> zebrane = new LinkedList<>();
		List<Pieniadz> reszta = new LinkedList<>(pieniadze);
		
		// trzeba zebrać najpierw wszystkie pieniądze zaczynając od nierozmienialnych które nie przekroczą ceny
		// potem tyle rozmienialnych żeby przekroczyć cene i wydać
		
		// jeśli nie ma jak dobrać rozmienialnych to wtedy dopiero zajmujemy się nierozmienialnymi
		
		// sortuj otzymane pieniadze od najwiekszego
		sortujPieniadzeMalejaco(pieniadze);
		
		// zbierz najwieksza mozliwa kwote bez rozmieniania
		zebrane = zbierzPieniadze(cena, pieniadze, false);
		odejmijPieniadze(reszta, zebrane);
		
		// nie trzeba bylo rozmieniac zadnych pieniedzy
		if (policzPieniadze(zebrane) == cena) {
			System.out.println("Nie trzeba nic rozmieniac ani wydawac");
			kasa.addAll(zebrane);
			return reszta;
		}
		
		// szukaj pierwszej rozmienialnej monety pozwalajacej na rozliczenie
		Pieniadz rozmienialnaMoneta = szukajNominaluWiekszegoLubRownego(cena - policzPieniadze(zebrane), reszta, true);
		
		// jeśli znaleziono odpowiednia monete to rozmien i koniec
		if (rozmienialnaMoneta != null) {
			
			System.out.println("Rozmienianie pieniedzy klienta");
			
			reszta.remove(rozmienialnaMoneta);
			List<Pieniadz> rozmienionaMoneta = rozmieniacz.rozmien(rozmienialnaMoneta);
			sortujPieniadzeMalejaco(rozmienionaMoneta);
			
			while (policzPieniadze (zbierzPieniadze(cena - policzPieniadze(zebrane), rozmienionaMoneta, false)) < cena - policzPieniadze(zebrane)) {
				// rozmieniaj az bedzie sie dalo zebrac brakujaca kwote
				rozmienNajmniejszyNominal(rozmienionaMoneta);
			}
			
			// rozdziel wynik pomiedzy zebrane a reszte i zwroc reszte
			zebrane.addAll(zbierzPieniadze(cena - policzPieniadze(zebrane), rozmienionaMoneta, false));
			odejmijPieniadze(rozmienionaMoneta, zebrane);
			reszta.addAll(rozmienionaMoneta);
			
			kasa.addAll (zebrane);
			
			return reszta;
			
		}
		
		// uporaj sie z nierozmienialna N-zlotowka
		System.out.println("Trzeba się zabrać za nierozmienialne");
		
		// szukaj najmniejszej nierozmienialnej N-zlotowki
		
		Pieniadz nierozmienialnaMoneta = szukajNominaluWiekszegoLubRownego(cena - policzPieniadze(zebrane), reszta, false);
		
		int resztaNierozmienialnej = nierozmienialnaMoneta.wartosc() - (cena - policzPieniadze(zebrane));
		
		// szukaj w kasie nierozmienialnaMoneta - cena - zebrane
		List<Pieniadz> pozostaleDrobne = zbierzPieniadze(resztaNierozmienialnej, kasa, true);
		if (policzPieniadze(pozostaleDrobne) + policzPieniadze(zebrane) == cena) {
			
			reszta.addAll(pozostaleDrobne);
			kasa.removeAll(pozostaleDrobne);
			
			return reszta;
			
		} else {
			// jeśli nie to szukaj większej rozmienialnej i rozmień
			
			System.out.println("Poszukiwanie rozmienialnej monety większej lub równej nierozmienialnej");
			
			Pieniadz prawieOstatecznyRatunek = szukajNominaluWiekszegoLubRownego(nierozmienialnaMoneta.wartosc(), kasa, true);
			if (prawieOstatecznyRatunek != null) {
				
				kasa.remove(prawieOstatecznyRatunek);
				
				List<Pieniadz> rozmienionaMoneta = rozmieniacz.rozmien(prawieOstatecznyRatunek);
				sortujPieniadzeMalejaco(rozmienionaMoneta);
				
				while (policzPieniadze (zbierzPieniadze(resztaNierozmienialnej, rozmienionaMoneta, false)) < resztaNierozmienialnej) {
					// rozmieniaj az bedzie sie dalo zebrac brakujaca kwote
					rozmienNajmniejszyNominal(rozmienionaMoneta);
				}
				
				List<Pieniadz> wydanaReszta = zbierzPieniadze(resztaNierozmienialnej, rozmienionaMoneta, false);
				reszta.addAll(wydanaReszta);
				odejmijPieniadze(rozmienionaMoneta, wydanaReszta);
				kasa.addAll(rozmienionaMoneta);
				
				return reszta;
				
			} else {
				// jeśli nie to szukaj w kasie dowolnych monet sumarycznie dających pozostałą kwotę
				
				System.out.println("Ostateczność - wydawanie nierozmienialnych monet");
				
				List<Pieniadz> ostatniaNadzieja = zbierzPieniadze(resztaNierozmienialnej, kasa, false);
				
				if (policzPieniadze(ostatniaNadzieja) == resztaNierozmienialnej) {
					
					reszta.addAll(ostatniaNadzieja);
					odejmijPieniadze(kasa, ostatniaNadzieja);
					
					return reszta;
				}
				
				return null;
			}
		}
	}
	
	public List<Pieniadz> zbierzPieniadze (int wartosc, List<Pieniadz> pieniadze, Boolean tylkoRozmienialne) {
		
		sortujPieniadzeMalejaco (pieniadze);
		
		List<Pieniadz> zebrane = new LinkedList<>();
		int zebraneWartosc = 0;
		
		for (int i = 0; i < pieniadze.size() && zebraneWartosc != wartosc; i++) {
			if (pieniadze.get(i).wartosc() + zebraneWartosc <= wartosc) {
				if (tylkoRozmienialne) {
					if (pieniadze.get(i).czyMozeBycRozmieniony()) {
						zebraneWartosc += pieniadze.get(i).wartosc();
						zebrane.add(pieniadze.get(i));
					}
				} else {
					zebraneWartosc += pieniadze.get(i).wartosc();
					zebrane.add(pieniadze.get(i));
				}
			}
		}
		
		return zebrane;
	}
	
	public int policzPieniadze (List<Pieniadz> pieniadze) {
		
		int suma = 0;
		
		for (Pieniadz pieniadz : pieniadze) {
			suma += pieniadz.wartosc();
		}
		
		return suma;
	}
	
	public Pieniadz szukajNominaluWiekszegoLubRownego (int liczba, List<Pieniadz> pieniadze, Boolean rozmienialnosc) {
		
		if (pieniadze.size() == 0) {
			return null;
		}
		
		sortujPieniadzeMalejaco(pieniadze);
		
		for (int i = pieniadze.size() - 1; i >= 0; i--) {
			if (pieniadze.get(i).wartosc() >= liczba && pieniadze.get(i).czyMozeBycRozmieniony() == rozmienialnosc) {
				return pieniadze.get(i);
			}
		}
		
		return null;
	}
	
	public void sortujPieniadzeMalejaco (List<Pieniadz> pieniadze) {
		// rozmienialne pieniadze sa uznawane za wieksze ze wzgledu na priorytet
		// Collections.sort(pieniadze, (a, b) -> a.wartosc() < b.wartosc() ? 1 : a.wartosc() == b.wartosc() ? a.czyMozeBycRozmieniony() == false ? -1 : 1 : -1);
		Collections.sort(pieniadze, (a, b) -> a.czyMozeBycRozmieniony() == false ? -1 : a.wartosc() < b.wartosc() ? 1 : a.wartosc() == b.wartosc() ? 0 : 1);
	}
	
	public Boolean rozmienNajmniejszyNominal (List<Pieniadz> pieniadze) {
		
		for (int i = pieniadze.size() - 1; i >= 0; i--) {
			if (pieniadze.get(i).czyMozeBycRozmieniony() && pieniadze.get(i).wartosc() > 1) {
				
				List<Pieniadz> rozmienione = rozmieniacz.rozmien(pieniadze.get(i));
				pieniadze.remove(i);
				pieniadze.addAll(rozmienione);
				sortujPieniadzeMalejaco (pieniadze);
				
				return true;
			}
		}
		
		return false;
	}
	
	public void odejmijPieniadze (List<Pieniadz> odjemna, List<Pieniadz> odejmnik) {
		
		for (int i = 0; i < odejmnik.size(); i++) {
			odjemna.remove(odejmnik.get(i));
		}
	}
	
	public List<Pieniadz> stanKasy() {
		return kasa;
	}
	
	public void dostępDoRozmieniacza(RozmieniaczInterface _rozmieniacz) {
		rozmieniacz = _rozmieniacz;
	}
	
	public void dostępDoPoczątkowegoStanuKasy(Supplier<Pieniadz> dostawca) {
		Pieniadz pieniadz = dostawca.get();
		for (; pieniadz != null; pieniadz = dostawca.get()) {
			kasa.add(pieniadz);
		}
	}
	
}
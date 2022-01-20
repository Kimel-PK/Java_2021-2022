import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

class Kasjer implements KasjerInterface {
	
	List<Pieniadz> kasa = new LinkedList<>();
	RozmieniaczInterface rozmieniacz;
	
	public List<Pieniadz> rozlicz(int cena, List<Pieniadz> pieniadze) {
		
		// reszta do wydania
		List<Pieniadz> resztaPieniadze = new LinkedList<>();
		// wartosc reszty do wydania
		int resztaWartosc = 0;
		
		int rozmienialnePieniadzeKlienta = 0;
		
		for (int i = 0; i < pieniadze.size(); i++) {
			if (pieniadze.get(i).czyMozeBycRozmieniony()) {
				rozmienialnePieniadzeKlienta += pieniadze.get(i).wartosc();
			}
			resztaWartosc += pieniadze.get(i).wartosc();
		}
		
		resztaWartosc -= cena;
		
		// sprawdz w jaki sposob bedziemy sie rozliczac
		if (rozmienialnePieniadzeKlienta >= resztaWartosc) {
			
			// da się rozliczyc normalnie
			
			// wez wszystkie wszystkie pieniadze od klienta
			for (int i = 0; i < pieniadze.size(); i++) {
				kasa.add(pieniadze.get(i));
			}
			
			// wydaj reszte
			return WydajReszte(resztaWartosc);
			
		} else {
			
			// trzeba skorzystac z mechaniki NR
			
			// znajdz najmniejsza NR monete ktora pozwoli nam sie rozliczyc
			int najmniejszaNR = 0;
			for (int i = 1 ; i < pieniadze.size(); i++) {
				if (pieniadze.get(najmniejszaNR).wartosc() > pieniadze.get(i).wartosc() && pieniadze.get(i).wartosc() >= cena) {
					najmniejszaNR = i;
				}
			}
			
			resztaPieniadze.add(pieniadze.get(najmniejszaNR));
			
			// umieszczanie w kasie wszystkich pieniedzy poza najmniejsza NR potrzebna do rozmiany
			for (int i = 0 ; i < pieniadze.size(); i++) {
				if (i != najmniejszaNR) {
					kasa.add(pieniadze.get(i));
				}
			}
			
			// wydawanie reszty
			resztaPieniadze.addAll(WydajReszte(resztaWartosc));
			
			return resztaPieniadze;
		}
	}
	
	List<Pieniadz> WydajReszte (int resztaWartosc) {
		
		int zebranePieniadze = 0;
		boolean wszystkoRozmienione = false;
		List<Pieniadz> reszta = new LinkedList<>();
		
		while (!wszystkoRozmienione) {
			for (int i = 0; i < kasa.size(); i++) {
				
				// zbieramy pieniadze
				if (kasa.get(i).czyMozeBycRozmieniony() && kasa.get(i).wartosc() + zebranePieniadze <= resztaWartosc) {
					zebranePieniadze += kasa.get(i).wartosc();
					reszta.add(kasa.remove(i));
					i--;
				}
				
				// zebralismy potrzebne pieniadze
				if (zebranePieniadze == resztaWartosc) {
					return reszta;
				}
			}
			
			// nie udalo nam sie zebrac pieniedzy
			// musimy jakis rozmienic
			for (int i = 0; i < kasa.size(); i++) {
				if (kasa.get(i).wartosc() > 1 && kasa.get(i).czyMozeBycRozmieniony()) {
					List<Pieniadz> rozmienionaMoneta = rozmieniacz.rozmien(kasa.remove(i));
					while (rozmienionaMoneta.size() > 0) {
						kasa.add(rozmienionaMoneta.remove(0));
					}
					break;
				}
				if (i == kasa.size() - 1) {
					// rozmienilismy juz wszystkie monety
					wszystkoRozmienione = true;
				}
			}
		}
		
		// wydajemy z kasy nie bez sprawdzania czy to moneta R czy NR
		for (int i = 0; i < kasa.size(); i++) {
			if (kasa.get(i).wartosc() + zebranePieniadze <= resztaWartosc) {
				zebranePieniadze += kasa.get(i).wartosc();
				reszta.add(kasa.remove(i));
				i--;
			}
			
			if (zebranePieniadze == resztaWartosc) {
				return reszta;
			}
		}
		
		return new LinkedList<>();
	}
	
	public List<Pieniadz> stanKasy() {
		return kasa;
	}
	
	public void dostępDoRozmieniacza(RozmieniaczInterface _rozmieniacz) {
		rozmieniacz = _rozmieniacz;
	}
	
	public void dostępDoPoczątkowegoStanuKasy(Supplier<Pieniadz> dostawca) {
		kasa.clear();
		Pieniadz pieniadz = dostawca.get();
		while (pieniadz != null) {
			kasa.add(pieniadz);
			pieniadz = dostawca.get();
		}
	}
}
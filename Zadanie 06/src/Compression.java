import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

class Compression implements CompressionInterface{
	
	int dlugoscWejscia;
	List<String> wejscie = new LinkedList<>();
	Map<String, Integer> liczbaSlow = new HashMap<>();
	Map<String, String> naglowek = new HashMap<>();
	Map<String, String> naglowekOdwrotny = new HashMap<>();
	int wyjscie;
	
	public void addWord(String word) {
		
		wejscie.add(word);
		
		dlugoscWejscia += word.length();
		
		if (liczbaSlow.get(word) != null) {
			liczbaSlow.put(word, liczbaSlow.get(word) + 1);
		} else {
			liczbaSlow.put(word, 1);
		}
		
	}
	
	public void compress() {
		
		int optI = 0; // optymalna dlugosc klucza
		int optJ = 0; // optymalna ilosc kodowanych slow
		
		// posortuj mape liczby slow
		Map<String, Integer> liczbaSlowMalejaco = liczbaSlow.entrySet().stream()
		.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
		.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		
		int optymalny = dlugoscWejscia;
		int skompresowany;
		
		// dla kazdego rozmiaru klucza krotszego niz dlugosc slowa
		for (int i = 1; i < liczbaSlowMalejaco.keySet().toArray()[0].toString().length(); i++) {
			for (int j = 1; j <= Math.pow(2, i - 1); j++) {
				
				skompresowany = dlugoscWejscia;
				
				for (int k = 0; k < liczbaSlowMalejaco.size(); k++) {
					
					String slowo = liczbaSlowMalejaco.keySet().toArray()[k].toString();
					int wystapieniaSlowa = liczbaSlowMalejaco.get (liczbaSlowMalejaco.keySet().toArray()[k]);
					
					if (k < j) {
						skompresowany -= slowo.length() * wystapieniaSlowa; // zysk
						skompresowany += (i + slowo.length()) + wystapieniaSlowa * i; // straty
					} else {
						skompresowany += wystapieniaSlowa; // straty
					}
					
				}
				
				if (skompresowany < optymalny) { // znaleziono wydajniejsza kompresje
					optymalny = skompresowany;
					optI = i;
					optJ = j;
				}
				
			}
		}
		
		// czy kompresja ma sens
		if (optymalny < dlugoscWejscia) {
			
			for (int i = 0; i < optJ; i++) {
				naglowek.put(String.format("%" + optI + "s", Integer.toBinaryString(i)).replaceAll(" ", "0"), liczbaSlowMalejaco.keySet().toArray()[i].toString());
			}
			
			naglowekOdwrotny = naglowek.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
		}
		
	}
	
	public Map<String, String> getHeader() {
		return naglowek;
	}
	
	public String getWord() {
		
		if (naglowekOdwrotny.get(wejscie.get(wyjscie)) == null) {
			return "1" + wejscie.get(wyjscie++);
		} else {
			return naglowekOdwrotny.get(wejscie.get(wyjscie++));
		}
		
	}
	
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Loops implements GeneralLoops {
	
	private List<Integer> dolneLimity;
	private List<Integer> temp; // obecnie przetwarzany zestaw
	private List<Integer> gorneLimity;
	private List<List<Integer>> wynik;
	
	public void setLowerLimits(List<Integer> limits) {
		dolneLimity = new ArrayList<>(limits);
	}
	
	public void setUpperLimits(List<Integer> limits) {
		gorneLimity = new ArrayList<>(limits);
	}
	
	public List<List<Integer>> getResult() {
		
		// ustaw odpowiednio listy limitow jesli nie zostaly ustawione przez uzytkownika
		if (dolneLimity == null) {
			if (gorneLimity == null) {
				dolneLimity = Arrays.asList(0);
				gorneLimity = Arrays.asList(0);
			} else {
				dolneLimity = new ArrayList<>();
				for (int i = 0; i < gorneLimity.size(); i++) {
					dolneLimity.add(0);
				}
			}
		}
		
		wynik = new ArrayList<List<Integer>> ();
		temp = new ArrayList<Integer> (dolneLimity);
		
		// iterujemy od "prawej" strony
		int licznik = temp.size() - 1;
		wynik.add(new ArrayList<Integer>(temp));
		
		while (licznik >= 0) {
			
			if (temp.get(licznik) == gorneLimity.get(licznik)) { // jesli obecna pozycja osiagnela gorny limit
				temp.set(licznik, dolneLimity.get(licznik)); // ustaw pozycje z powrotem na dolny limit
				licznik--;
			} else {
				temp.set(licznik, temp.get(licznik) + 1); // dodajemy 1 na obecnej pozycji
				licznik = temp.size() - 1; // ustawiamy pozycje na poczatku, po prawej stronie
				wynik.add(new ArrayList<Integer>(temp)); // dodajemy rezultat na wyjscie
			}
			
		}
		
		return wynik;
		
	}
	
}
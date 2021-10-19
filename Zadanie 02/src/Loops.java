import java.util.ArrayList;
import java.util.List;

public class Loops implements GeneralLoops {
	
	private List<Integer> dolneLimity = null;
	private List<Integer> temp;
	private List<Integer> gorneLimity;
	
	int masaKrytyczna;
	
	public void setLowerLimits(List<Integer> limits) {
		dolneLimity = limits;
	}
	
	public void setUpperLimits(List<Integer> limits) {
		gorneLimity = limits;
	}
	
	public List<List<Integer>> getResult() {
		
		if (dolneLimity == null) {
			dolneLimity = new ArrayList<>();
			for (int i = 0; i < gorneLimity.size(); i++) {
				dolneLimity.add(0);
			}
		}
		
		temp = new ArrayList<Integer> (dolneLimity);
		
		int licznik = 0;
		
		System.out.println("dolne: ");
		for (Integer liczba : dolneLimity) {
			System.out.print(liczba);
		}
		System.out.println(" ");
		
		System.out.println("gorne: ");
		for (Integer liczba : gorneLimity) {
			System.out.print(liczba);
		}
		System.out.println(" ");
		System.out.println("===============");
		System.out.println(" ");
		
		// dopoki nie nastapi "przepelnienie"
		do {
			
			WypiszListe(temp);
			
			licznik = 0;
			
			// dodajmy 1 do calosci
			
			if (temp.get(licznik) + 1 > gorneLimity.get(licznik)) { // przepelnienie w danym polu
				
				temp.set(licznik, dolneLimity.get(licznik)); // ustaw z powrotem wartosc poczatkowa
				temp.set (licznik + 1, temp.get(licznik + 1) + 1);
				licznik++;
				
				// dodawaj kolejne liczby az trafisz na "wolne" miejsce
				while (licznik < temp.size()) {
					
					masaKrytyczna++;
					if (masaKrytyczna > 100) {
						break;
					}
					
					if (temp.get(licznik) > gorneLimity.get(licznik)) { // przepelnienie jeszcze raz
						temp.set (licznik, dolneLimity.get(licznik));
						temp.set (licznik + 1, temp.get(licznik + 1) + 1);
						licznik++;
						continue;
					}
					
					break;
					
				}
				
			} else { // w danym polu nie ma przepelnienia
				temp.set(licznik, temp.get(licznik) + 1);
			}
			
			masaKrytyczna++;
			if (masaKrytyczna > 100) {
				break;
			}
			
			// System.out.println(licznik + " < " + temp.size());
		} while (licznik < temp.size());
		
		return null;
		
	}
	
	private void WypiszListe (List<Integer> lista) {
		
		for (Integer liczba : lista) {
			System.out.print(liczba);
		}
		System.out.print("\n");
		
	}
	
}
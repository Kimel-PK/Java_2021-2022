import java.util.ArrayList;
import java.util.List;

public class Loops implements GeneralLoops {
	
	private List<Integer> dolneLimity;
	private List<Integer> temp;
	private List<Integer> gorneLimity;
	private List<List<Integer>> wynik;
	
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
		
		wynik = new ArrayList<List<Integer>> ();
		temp = new ArrayList<Integer> (dolneLimity);
		
		int licznik = temp.size() - 1;
		wynik.add(new ArrayList<Integer>(temp));
		
		while (licznik >= 0) {
			
			if (temp.get(licznik) == gorneLimity.get(licznik)) {
				temp.set(licznik, dolneLimity.get(licznik));
				licznik--;
			} else {
				temp.set(licznik, temp.get(licznik) + 1);
				licznik = temp.size() - 1;
				wynik.add(new ArrayList<Integer>(temp));
			}
		}
		
		return wynik;
		
	}
	
}
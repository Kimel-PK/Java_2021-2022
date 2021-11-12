import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BusLine implements BusLineInterface {
	
	Map<String, List<PunktTrasy>> linieAutobusowe;
	Map<String, List<Position>> skrzyzowania;
	Map<String, List<String>> skrzyzowaniaNazwy;
	
	{
		linieAutobusowe = new HashMap<String, List<PunktTrasy>>();
	}
	
	class LinesPair implements BusLineInterface.LinesPair {
		
		public String getFirstLineName() {
			return "";
		}
		
		public String getSecondLineName() {
			return "";
		}
		
	}
	
	// Metoda dodaje linie autobusowa o podanej nazwie.
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint) {
		List<PunktTrasy> punkty = new LinkedList<>();
		punkty.add(new PunktTrasy (firstPoint, PunktTrasy.Kierunek.Nieokreslony, true, null));
		punkty.add(new PunktTrasy (lastPoint, PunktTrasy.Kierunek.Nieokreslony, true, null));
		linieAutobusowe.put(busLineName, punkty);
	}
	
	// Metoda dodaje odcinek lineSegment do linii autobusowej o nazwie busLineName.
	public void addLineSegment(String busLineName, LineSegment lineSegment) {
		
		List<PunktTrasy> punkty = linieAutobusowe.get(busLineName);
		
		PunktTrasy.Kierunek kierunek = PunktTrasy.OkreslKierunek(lineSegment.getFirstPosition(), lineSegment.getLastPosition());
		System.out.println("Określono kierunek na " + kierunek);
		
		PunktTrasy nastepny = null;
		
		Boolean koncowy = false;
		
		// sprawdź czy w liście istnieje już punkt końcowy
		for (PunktTrasy punkt : punkty) {
			if (punkt.wspolrzedne.hashCode() == lineSegment.getLastPosition().hashCode()) {
				System.out.println("Odnaleziono punkt koncowy zgodny z dodawanym segmentem " + punkt.wspolrzedne);
				punkt.UstawPlaszczyzne(kierunek);
				punkt.wezel = true;
				nastepny = punkt;
				koncowy = true;
				break;
			}
		}
		
		if (!koncowy) {
			punkty.add (new PunktTrasy(lineSegment.getLastPosition(), kierunek, true, null));
		}
		
		// dodawaj kolejne punkty
		Position temp = new Position2D (lineSegment.getLastPosition().getCol(), lineSegment.getLastPosition().getRow());
		System.out.println("Rozpocznij z wspl " + temp);
		while (temp.hashCode() != lineSegment.getFirstPosition().hashCode()) {
			
			if (nastepny != null) {
				nastepny = new PunktTrasy (temp, kierunek, nastepny);
			} else {
				nastepny = new PunktTrasy (temp, kierunek, null);
			}
			punkty.add (nastepny);
			System.out.println("Dodano punkt " + nastepny.wspolrzedne);
			
			temp = PunktTrasy.PrzesunPunkt(temp, PunktTrasy.OdwrocKierunek(kierunek));
			System.out.println("Przesunieto punkt " + temp);
			
		}
		
		Boolean poczatkowy = false;
		
		// sprawdź czy istnieje już punkt początkowy
		for (PunktTrasy punkt : punkty) {
			if (punkt.wspolrzedne.hashCode() == temp.hashCode()) {
				System.out.println("Odnaleziono punkt startowy zgodny z dodawanym segmentem" + punkt.wspolrzedne);
				punkt.nastepny = nastepny;
				poczatkowy = true;
				break;
			}
		}
		
		if (!poczatkowy) {
			punkty.add (new PunktTrasy(lineSegment.getFirstPosition(), kierunek, true, null));
		}
		
	}
	
	// Metoda zleca rozpoczecie poszukiwania skrzyzowan.
	public void findIntersections() {
		
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej zas wartoscia lista polozen wszystkich punktow nalezacych do danej linii.
	public Map<String, List<Position>> getLines() {
		Map<String, List<Position>> linieAutobusoweWyjscie = new HashMap<>();
		for (String busLineName : linieAutobusowe.keySet()) {
			List<Position> punkty = new ArrayList<>();
			for (PunktTrasy punktTrasy : linieAutobusowe.get(busLineName)) {
				punkty.add(punktTrasy.wspolrzedne);
			}
			linieAutobusoweWyjscie.put(busLineName, punkty);
		}
		return linieAutobusoweWyjscie;
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej a wartoscia lista kolejnych skrzyzowan na trasie linii.
	public Map<String, List<Position>> getIntersectionPositions() {
		return skrzyzowania;
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej a wartoscia lista nazw kolejnych linii, z ktorymi linia ta ma skrzyzowania.
	public Map<String, List<String>> getIntersectionsWithLines() {
		return skrzyzowaniaNazwy;
	}
	
	// Metoda zwraca mape, której kluczem jest para nazw linii a wartoscia zbior polozen, w ktorych para linii ma skrzyzowania.
	public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() {
		return null;
	}
	
	// =======================================================
	// =======================================================
	// =======================================================
	
	public void WypiszLinie (String busLineName) {
		for (PunktTrasy punkt : linieAutobusowe.get(busLineName)) {
			if (punkt.nastepny != null)
				System.out.println(punkt.wspolrzedne + " - " + punkt.hashCode() + " => " + punkt.nastepny.hashCode() + " " + punkt.plaszczyzna + " " + punkt.wezel);
			else
				System.out.println(punkt.wspolrzedne + " - " + punkt.hashCode() + " => null " + punkt.plaszczyzna + " " + punkt.wezel);
		}
	}
	
}

class PunktTrasy {
		
	Boolean wezel = false;
	PunktTrasy nastepny;
	Plaszczyzna plaszczyzna;
	Position wspolrzedne;
	
	public PunktTrasy (Position _wspolrzedne, Kierunek _kierunek, PunktTrasy _nastepny) {
		wspolrzedne = _wspolrzedne;
		UstawPlaszczyzne(_kierunek);
		nastepny = _nastepny;
	}
	
	public PunktTrasy (Position _wspolrzedne, Kierunek _kierunek, Boolean _wezel, PunktTrasy _nastepny) {
		wspolrzedne = _wspolrzedne;
		UstawPlaszczyzne(_kierunek);
		wezel = _wezel;
		nastepny = _nastepny;
	}
	
	public void UstawPlaszczyzne (Kierunek _kierunek) {
		if (_kierunek == Kierunek.Gora)
			plaszczyzna = Plaszczyzna.Pion;
		else if (_kierunek == Kierunek.PrawaGora)
			plaszczyzna = Plaszczyzna.PrawySkos;
		else if (_kierunek == Kierunek.Prawo)
			plaszczyzna = Plaszczyzna.Poziom;
		else if (_kierunek == Kierunek.PrawyDol)
			plaszczyzna = Plaszczyzna.LewySkos;
		else if (_kierunek == Kierunek.Dol)
			plaszczyzna = Plaszczyzna.Pion;
		else if (_kierunek == Kierunek.LewyDol)
			plaszczyzna = Plaszczyzna.PrawySkos;
		else if (_kierunek == Kierunek.Lewo)
			plaszczyzna = Plaszczyzna.Poziom;
		else if (_kierunek == Kierunek.LewaGora)
			plaszczyzna = Plaszczyzna.LewySkos;
		else
			plaszczyzna = Plaszczyzna.Nieokreslona;
	}
	
	enum Kierunek {
		Gora, PrawaGora, Prawo, PrawyDol, Dol, LewyDol, Lewo, LewaGora, Nieokreslony
	}
	
	enum Plaszczyzna {
		Pion, PrawySkos, Poziom, LewySkos, Nieokreslona
	}
	
	public static Position PrzesunPunkt (Position punkt, Kierunek kierunek) {
		if (kierunek == Kierunek.Gora)
			return new Position2D(punkt.getCol(), punkt.getRow() + 1);
		else if (kierunek == Kierunek.PrawaGora)
			return new Position2D(punkt.getCol() + 1, punkt.getRow() + 1);
		else if (kierunek == Kierunek.Prawo)
			return new Position2D(punkt.getCol() + 1, punkt.getRow());
		else if (kierunek == Kierunek.PrawyDol)
			return new Position2D(punkt.getCol() + 1, punkt.getRow() - 1);
		else if (kierunek == Kierunek.Dol)
			return new Position2D(punkt.getCol(), punkt.getRow() - 1);
		else if (kierunek == Kierunek.LewyDol)
			return new Position2D(punkt.getCol() - 1, punkt.getRow() - 1);
		else if (kierunek == Kierunek.Lewo)
			return new Position2D(punkt.getCol() - 1, punkt.getRow());
		else // lewa góra
			return new Position2D(punkt.getCol() - 1, punkt.getRow() + 1);
	}
	
	public static Kierunek OdwrocKierunek (Kierunek kierunek) {
		if (kierunek == Kierunek.Gora)
			return Kierunek.Dol;
		else if (kierunek == Kierunek.PrawaGora)
			return Kierunek.LewyDol;
		else if (kierunek == Kierunek.Prawo)
			return Kierunek.Lewo;
		else if (kierunek == Kierunek.PrawyDol)
			return Kierunek.LewaGora;
		else if (kierunek == Kierunek.Dol)
			return Kierunek.Gora;
		else if (kierunek == Kierunek.LewyDol)
			return Kierunek.PrawaGora;
		else if (kierunek == Kierunek.Lewo)
			return Kierunek.Prawo;
		else if (kierunek == Kierunek.LewaGora)
			return Kierunek.PrawyDol;
		else
			return Kierunek.Nieokreslony;
	}
	
	public static Kierunek OkreslKierunek (Position punkt1, Position punkt2) {
		if (punkt2.getCol() == punkt1.getCol() && punkt2.getRow() > punkt1.getRow())
			return Kierunek.Gora;
		else if (punkt2.getCol() > punkt1.getCol() && punkt2.getRow() > punkt1.getRow())
			return Kierunek.PrawaGora;
		else if (punkt2.getCol() > punkt1.getCol() && punkt2.getRow() == punkt1.getRow())
			return Kierunek.Prawo;
		else if (punkt2.getCol() > punkt1.getCol() && punkt2.getRow() < punkt1.getRow())
			return Kierunek.PrawyDol;
		else if (punkt2.getCol() == punkt1.getCol() && punkt2.getRow() < punkt1.getRow())
			return Kierunek.Dol;
		else if (punkt2.getCol() < punkt1.getCol() && punkt2.getRow() < punkt1.getRow())
			return Kierunek.LewyDol;
		else if (punkt2.getCol() < punkt1.getCol() && punkt2.getRow() == punkt1.getRow())
			return Kierunek.Lewo;
		else if (punkt2.getCol() < punkt1.getCol() && punkt2.getRow() > punkt1.getRow())
			return Kierunek.LewaGora;
		else
			return Kierunek.Nieokreslony;
	}
	
}
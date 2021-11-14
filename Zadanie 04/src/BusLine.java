import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

class BusLine implements BusLineInterface {
	
	// mapy wyjściowe
	Map<String, List<Position>> lines;
	Map<String, List<Position>> intersectionPositions;
	Map<String, List<String>> intersectionsWithLines;
	Map<BusLineInterface.LinesPair, Set<Position>> intersectionOfLinesPair;
	
	// mapy pomocnicze
	Map<String, List<PunktTrasy>> linieAutobusowePT;
	Map<String, Map<Integer, Position>> skrzyzowaniaPozycje;
	Map<String, Map<Integer, String>> skrzyzowaniaNazwy;
	
	{
		lines = new HashMap<>();
		
		linieAutobusowePT = new HashMap<>();
		skrzyzowaniaPozycje = new HashMap<>();
		skrzyzowaniaNazwy = new HashMap<>();
	}
	
	class LinesPair implements BusLineInterface.LinesPair {
		
		String linia1;
		String linia2;
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LinesPair other = (LinesPair) obj;
			return getFirstLineName() == other.getFirstLineName() && getSecondLineName() == other.getSecondLineName();
		}
		
		LinesPair (String _linia1, String _linia2) {
			linia1 = _linia1;
			linia2 = _linia2;
		}
		
		public String getFirstLineName() {
			return linia1;
		}
		
		public String getSecondLineName() {
			return linia2;
		}
		
	}
	
	// Metoda dodaje linie autobusowa o podanej nazwie.
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint) {
		List<PunktTrasy> punkty = new LinkedList<>();
		punkty.add(new PunktTrasy (firstPoint, PunktTrasy.Kierunek.Nieokreslony, null));
		punkty.add(new PunktTrasy (lastPoint, PunktTrasy.Kierunek.Nieokreslony, null));
		linieAutobusowePT.put(busLineName, punkty);
	}
	
	// Metoda dodaje odcinek lineSegment do linii autobusowej o nazwie busLineName.
	public void addLineSegment(String busLineName, LineSegment lineSegment) {
		
		// System.out.println("Dodaj segment");
		
		List<PunktTrasy> punkty = linieAutobusowePT.get(busLineName);
		
		PunktTrasy.Kierunek kierunek = PunktTrasy.OkreslKierunek(lineSegment.getFirstPosition(), lineSegment.getLastPosition());
		// System.out.println("Określono kierunek na " + kierunek);
		
		PunktTrasy nastepny = null;
		
		Boolean koncowy = false;
		
		// sprawdź czy w liście istnieje już punkt końcowy
		for (PunktTrasy punkt : punkty) {
			if (punkt.wspolrzedne.hashCode() == lineSegment.getLastPosition().hashCode()) {
				// System.out.println("Odnaleziono punkt koncowy zgodny z dodawanym segmentem " + punkt.wspolrzedne);
				punkt.UstawPlaszczyzne(PunktTrasy.Kierunek.Nieokreslony);
				nastepny = punkt;
				koncowy = true;
				break;
			}
		}
		
		if (!koncowy) {
			nastepny = new PunktTrasy(lineSegment.getLastPosition(), PunktTrasy.Kierunek.Nieokreslony, null);
			punkty.add (nastepny);
		}
		
		// dodawaj kolejne punkty
		Position temp = new Position2D (lineSegment.getLastPosition().getCol(), lineSegment.getLastPosition().getRow());
		temp = PunktTrasy.PrzesunPunkt(temp, PunktTrasy.OdwrocKierunek(kierunek));
		while (temp.hashCode() != lineSegment.getFirstPosition().hashCode()) {
			
			if (nastepny != null) {
				// System.out.println("Następny to " + nastepny.wspolrzedne);
				nastepny = new PunktTrasy (temp, kierunek, nastepny);
			} else {
				nastepny = new PunktTrasy (temp, kierunek, null);
			}
			punkty.add (nastepny);
			// System.out.println("Dodano punkt " + nastepny.wspolrzedne);
			
			temp = PunktTrasy.PrzesunPunkt(temp, PunktTrasy.OdwrocKierunek(kierunek));
			
		}
		
		Boolean poczatkowy = false;
		
		// sprawdź czy istnieje już punkt początkowy
		for (PunktTrasy punkt : punkty) {
			if (punkt.wspolrzedne.hashCode() == temp.hashCode()) {
				// System.out.println("Odnaleziono punkt startowy zgodny z dodawanym segmentem" + punkt.wspolrzedne);
				punkt.UstawPlaszczyzne(PunktTrasy.Kierunek.Nieokreslony);
				punkt.nastepny = nastepny;
				poczatkowy = true;
				break;
			}
		}
		
		if (!poczatkowy) {
			punkty.add (new PunktTrasy(lineSegment.getFirstPosition(), PunktTrasy.Kierunek.Nieokreslony, nastepny));
		}
		
	}
	
	// Metoda zleca rozpoczecie poszukiwania skrzyzowan.
	public void findIntersections() {
		
		for (String nazwaLinii1 : linieAutobusowePT.keySet()) {
			List<PunktTrasy> linia1 = linieAutobusowePT.get(nazwaLinii1);
			
			for (String nazwaLinii2 : linieAutobusowePT.keySet()) {
				List<PunktTrasy> linia2 = linieAutobusowePT.get(nazwaLinii2);
				
				// System.out.println("Trasy " + nazwaLinii1 + " - " + nazwaLinii2);
				
				int i = 0;
				PunktTrasy punkt1P = linia1.get(0);
				PunktTrasy punkt1 = punkt1P.nastepny;
				PunktTrasy punkt1N = punkt1P.nastepny.nastepny;
				while (punkt1N != null) {
					
					PunktTrasy punkt2P = linia2.get(0);
					PunktTrasy punkt2 = punkt2P.nastepny;
					PunktTrasy punkt2N = punkt2P.nastepny.nastepny;
					while (punkt2N != null) {
						
						if (punkt1.wspolrzedne.equals(punkt2.wspolrzedne)) {
							// System.out.println("Podejrzenie o skrzyżowanie na " + punkt1.wspolrzedne);
							// punkt podejrzany o skrzyżowanie
							if (PunktTrasy.CzyProstopadlePlaszczyzny (punkt1P.plaszczyzna, punkt2P.plaszczyzna)) {
								// System.out.println("Prostopadłość");
								// miejsce stykania się jest prostopadłe
								if (punkt1P.plaszczyzna == punkt1N.plaszczyzna &&
									punkt2P.plaszczyzna == punkt2N.plaszczyzna) {
									
									// System.out.println("Dodanie punktu");
									
									if (skrzyzowaniaPozycje.get(nazwaLinii1) == null) {
										skrzyzowaniaPozycje.put(nazwaLinii1, new TreeMap<>());
									}
									skrzyzowaniaPozycje.get(nazwaLinii1).put(i, new Position2D (punkt1.wspolrzedne.getCol(), punkt1.wspolrzedne.getRow()));
									if (skrzyzowaniaNazwy.get(nazwaLinii1) == null) {
										skrzyzowaniaNazwy.put(nazwaLinii1, new TreeMap<>());
									}
									skrzyzowaniaNazwy.get(nazwaLinii1).put(i, nazwaLinii2);
								}
							}
						}
						
						punkt2P = punkt2P.nastepny;
						punkt2 = punkt2.nastepny;
						punkt2N = punkt2N.nastepny;
					}
					
					i++;
					punkt1P = punkt1P.nastepny;
					punkt1 = punkt1.nastepny;
					punkt1N = punkt1N.nastepny;
				}
				
			}
			
			if (skrzyzowaniaPozycje.get(nazwaLinii1) != null) {
				// System.out.println("Dodawanie linii " + nazwaLinii1);
				lines.put(nazwaLinii1, new ArrayList<>(linia1.size()));
				PunktTrasy punkt = linia1.get(0);
				do {
					
					lines.get(nazwaLinii1).add(new Position2D(punkt.wspolrzedne.getCol(), punkt.wspolrzedne.getRow()));
					punkt = punkt.nastepny;
					
				} while (punkt != null);
			}
			
		}
		
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej zas wartoscia lista polozen wszystkich punktow nalezacych do danej linii.
	public Map<String, List<Position>> getLines() {
		return lines;
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej a wartoscia lista kolejnych skrzyzowan na trasie linii.
	public Map<String, List<Position>> getIntersectionPositions() {
		
		intersectionPositions = new HashMap<>();
		
		for (String nazwaTrasy : skrzyzowaniaPozycje.keySet()) {
			Map<Integer, Position> trasa = skrzyzowaniaPozycje.get(nazwaTrasy);
			List<Position> punkty = new LinkedList<>();
			intersectionPositions.put(nazwaTrasy, punkty);
			for (Integer numerPunktu : trasa.keySet()) {
				punkty.add(trasa.get(numerPunktu));
			}
		}
		
		return intersectionPositions;
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej a wartoscia lista nazw kolejnych linii, z ktorymi linia ta ma skrzyzowania.
	public Map<String, List<String>> getIntersectionsWithLines() {
		
		intersectionsWithLines = new HashMap<>();
		
		for (String nazwaTrasy : skrzyzowaniaNazwy.keySet()) {
			Map<Integer, String> trasa = skrzyzowaniaNazwy.get(nazwaTrasy);
			List<String> nazwy = new LinkedList<>();
			intersectionsWithLines.put(nazwaTrasy, nazwy);
			for (Integer numerPunktu : trasa.keySet()) {
				nazwy.add(trasa.get(numerPunktu));
			}
		}
		
		return intersectionsWithLines;
	}
	
	// Metoda zwraca mape, której kluczem jest para nazw linii a wartoscia zbior polozen, w ktorych para linii ma skrzyzowania.
	public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() {
		
		intersectionOfLinesPair = new HashMap<>();
		
		Map<String, List<Position>> interPositions;
		if (intersectionPositions != null)
			interPositions = intersectionPositions;
		else
			interPositions = getIntersectionPositions();
		
		Map<String, List<String>> interNames;
		if (intersectionOfLinesPair != null)
			interNames = intersectionsWithLines;
		else
			interNames = getIntersectionsWithLines();
		
		for (String nazwaLinii1 : interPositions.keySet()) {
			List<Position> linia1 = interPositions.get(nazwaLinii1);
			
			for (String nazwaLinii2 : interPositions.keySet()) {
				
				LinesPair para = new LinesPair(nazwaLinii1, nazwaLinii2);
				intersectionOfLinesPair.put(para, new HashSet<>());
				
				for (int i = 0; i < linia1.size(); i++) {
					
					if (interNames.get(nazwaLinii1).get(i) == nazwaLinii2) {
						intersectionOfLinesPair.get(para).add(linia1.get(i));
					}
					
				}
				
			}
			
		}
		
		for (String nazwaLinii1 : linieAutobusowePT.keySet()) {
			
			for (String nazwaLinii2 : linieAutobusowePT.keySet()) {
				
				boolean znaleziono = false;
				LinesPair para = new LinesPair(nazwaLinii1, nazwaLinii2);
				
				for (BusLineInterface.LinesPair para2 : intersectionOfLinesPair.keySet()) {
					if (para.equals(para2)) {
						znaleziono = true;
						break;
					}
				}
				
				if (!znaleziono)
					intersectionOfLinesPair.put(para, new HashSet<>());
				
			}
			
		}
		
		return intersectionOfLinesPair;
	}
	
}

class PunktTrasy {
		
	PunktTrasy nastepny;
	Plaszczyzna plaszczyzna;
	Position wspolrzedne;
	
	public PunktTrasy (Position _wspolrzedne, Kierunek _kierunek, PunktTrasy _nastepny) {
		wspolrzedne = _wspolrzedne;
		UstawPlaszczyzne(_kierunek);
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
	
	public static boolean CzyProstopadlePlaszczyzny (Plaszczyzna pl1, Plaszczyzna pl2) {
		
		if (pl1 == Plaszczyzna.Poziom && pl2 == Plaszczyzna.Pion)
			return true;
		if (pl1 == Plaszczyzna.Pion && pl2 == Plaszczyzna.Poziom)
			return true;
		if (pl1 == Plaszczyzna.LewySkos && pl2 == Plaszczyzna.PrawySkos)
			return true;
		if (pl1 == Plaszczyzna.PrawySkos && pl2 == Plaszczyzna.LewySkos)
			return true;
		else
			return false;
	}
	
}
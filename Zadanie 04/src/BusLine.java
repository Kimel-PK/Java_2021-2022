import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BusLine implements BusLineInterface {
	
	Map<String, List<Position>> linieAutobusowe;
	Map<String, List<Position>> skrzyzowania;
	Map<String, List<String>> skrzyzowaniaNazwy;
	
	{
		linieAutobusowe = new HashMap<String, List<Position>>();
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
		List<Position> punkty = new LinkedList<>();
		punkty.add(firstPoint);
		punkty.add(lastPoint);
		linieAutobusowe.put(busLineName, punkty);
	}
	
	// Metoda dodaje odcinek lineSegment do linii autobusowej o nazwie busLineName.
	public void addLineSegment(String busLineName, LineSegment lineSegment) {
		
		List<Position> punkty = linieAutobusowe.get(busLineName);
		
		for (int i = 0; i < punkty.size(); i++) {
			if (punkty.get(i).equals(lineSegment.getFirstPosition())) {
				punkty.add(i + 1, lineSegment.getLastPosition());
				break;
			} else if (punkty.get(i) == lineSegment.getLastPosition()) {
				punkty.add(i, lineSegment.getFirstPosition());
				break;
			}
		}
		
	}
	
	// Metoda zleca rozpoczecie poszukiwania skrzyzowan.
	public void findIntersections() {
		
		skrzyzowania = new HashMap<>();
		skrzyzowaniaNazwy = new HashMap<>();
		
		for (String nazwa1 : linieAutobusowe.keySet()) {
			List<Position> linia1 = linieAutobusowe.get(nazwa1);
			
			for (String nazwa2 : linieAutobusowe.keySet()) {
				List<Position> linia2 = linieAutobusowe.get(nazwa2);
				
				for (int i = 0; i < linieAutobusowe.get(nazwa2).size() - 1; i++) {
					
					
					
				}
			}
			
		}
		
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej zas wartoscia lista polozen wszystkich punktow nalezacych do danej linii.
	public Map<String, List<Position>> getLines() {
		return linieAutobusowe;
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej a wartoscia lista kolejnych skrzyzowan na trasie linii.
	public Map<String, List<Position>> getIntersectionPositions() {
		return null;
	}
	
	// Metoda zwraca mape, ktorej kluczem jest nazwa linii autobusowej a wartoscia lista nazw kolejnych linii, z ktorymi linia ta ma skrzyzowania.
	public Map<String, List<String>> getIntersectionsWithLines() {
		return null;
	}
	
	// Metoda zwraca mape, której kluczem jest para nazw linii a wartoscia zbior polozen, w ktorych para linii ma skrzyzowania.
	public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() {
		return null;
	}
	
}
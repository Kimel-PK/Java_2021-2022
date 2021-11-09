import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interfejs systemu obsługującego wyszukiwanie skrzyżowań linii autobusowych.
 */
public interface BusLineInterface {

	/**
	 * Interfejs reprezentujący parę dwóch linii autobusowych.
	 */
	public interface LinesPair {
		/**
		 * Metoda zwraca nazwę pierwszej z zapamiętanych linii.
		 * 
		 * @return nazwa pierwszej z linii
		 */
		public String getFirstLineName();

		/**
		 * Metoda zwraca nazwię drugiej z zapamiętanych linii.
		 * 
		 * @return nazwa drugiej z linii
		 */
		public String getSecondLineName();
	}

	/**
	 * Metoda dodaje linię autobusową o podanej nazwie. Wraz z nazwą linii
	 * przekazywane są informacje o pierwszym i ostatnim punkcie na trasie.
	 * 
	 * @param busLineName nazwa linii
	 * @param firstPoint  pierwszy punkt na trasie
	 * @param lastPoint   ostatni punkt na trasie
	 */
	public void addBusLine(String busLineName, Position firstPoint, Position lastPoint);

	/**
	 * Metoda dodaje odcinek lineSegment do linii autobusowej o nazwie busLineName.
	 * Odcinki nie muszą być dodawane w jakiejkolwiek kolejności. Można z nich
	 * utworzyć całą trasę poprzez uwzględnienie położenia punktów krańcowych.
	 * 
	 * @param busLineName nazwa linii autobusowej
	 * @param lineSegment odcinek trasy
	 */
	public void addLineSegment(String busLineName, LineSegment lineSegment);

	/**
	 * Metoda zleca rozpoczęcie poszukiwania skrzyżowań.
	 */
	public void findIntersections();

	/**
	 * Metoda zwraca mapę, której kluczem jest nazwa linii autobusowej zaś wartością
	 * lista położeń wszystkich punktów należących do danej linii. Mapa nie zawiera
	 * wśród kluczy nazw linii, które nie mają żadnego skrzyżowania.
	 * 
	 * @return mapa z przebiegiem tras linii autobusowych
	 */
	public Map<String, List<Position>> getLines();

	/**
	 * Metoda zwraca mapę, której kluczem jest nazwa linii autobusowej a wartością
	 * lista kolejnych skrzyżowań na trasie linii. Mapa nie zawiera wśród kluczy
	 * nazw linii, które nie mają żadnego skrzyżowania.
	 * 
	 * @return mapa skrzyżowań dla poszczególnych linii.
	 */
	public Map<String, List<Position>> getIntersectionPositions();

	/**
	 * Metoda zwraca mapę, której kluczem jest nazwa linii autobusowej a wartością
	 * lista nazw kolejnych linii, z którymi linia ta ma skrzyżowania. Zbiór kluczy
	 * nie zawiera linii, które nie mają skrzyżowania.
	 * 
	 * @return mapa skrzyżowań pomiędzy liniami
	 */
	public Map<String, List<String>> getIntersectionsWithLines();

	/**
	 * Metoda zwraca mapę, której kluczem jest para nazw linii a wartością zbiór
	 * położeń, w których para linii ma skrzyżowania. Jeśli linie nie mają żadnego
	 * skrzyżowania, to mapa zawiera pusty zbiór pozycji skrzyżowań
	 * 
	 * @return mapa skrzyżowań dla par linii autobusowych
	 */
	public Map<LinesPair, Set<Position>> getIntersectionOfLinesPair();
}

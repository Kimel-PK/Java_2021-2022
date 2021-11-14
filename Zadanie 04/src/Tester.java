public class Tester {
	
	public static void main(String[] args) {
		
		BusLine busLine = new BusLine();
		
		busLine.addBusLine("a", new Position2D(1, 1), new Position2D(2, 6));
		busLine.addLineSegment("a", new LineSegment(new Position2D(1, 1), new Position2D(8, 8)));
		busLine.addLineSegment("a", new LineSegment(new Position2D(8, 0), new Position2D(2, 6)));
		busLine.addLineSegment("a", new LineSegment(new Position2D(8, 8), new Position2D(8, 0)));
		
		busLine.addBusLine("b", new Position2D(5, 8), new Position2D(8, 8));
		busLine.addLineSegment("b", new LineSegment(new Position2D(5, 8), new Position2D(8, 8)));
		
		busLine.addBusLine("c", new Position2D(1, 1), new Position2D(4, 2));
		busLine.addLineSegment("c", new LineSegment(new Position2D(1, 1), new Position2D(1, 4)));
		busLine.addLineSegment("c", new LineSegment(new Position2D(1, 4), new Position2D(4, 4)));
		busLine.addLineSegment("c", new LineSegment(new Position2D(4, 4), new Position2D(10, 4)));
		busLine.addLineSegment("c", new LineSegment(new Position2D(10, 4), new Position2D(10, 2)));
		busLine.addLineSegment("c", new LineSegment(new Position2D(10, 2), new Position2D(4, 2)));
		
		busLine.findIntersections();
		
		System.out.println("Linie autobusowe");
		System.out.println(busLine.getLines() + "\n");
		System.out.println("Pozycje skrzyżowań");
		System.out.println(busLine.getIntersectionPositions() + "\n");
		System.out.println("Nazwy z którymi istnieją skrzyżowania");
		System.out.println(busLine.getIntersectionsWithLines() + "\n");
		System.out.println("Pary tras");
		System.out.println(busLine.getIntersectionOfLinesPair() + "\n");
		
	}
	
}
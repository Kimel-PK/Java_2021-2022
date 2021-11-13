public class Tester {
	
	public static void main(String[] args) {
		
		BusLine busLine = new BusLine();
		
		busLine.addBusLine("173", new Position2D(1, 1), new Position2D(1, 5));
		
		busLine.addLineSegment("173", new LineSegment(new Position2D(1, 1), new Position2D(5, 5))); // 1
		busLine.addLineSegment("173", new LineSegment(new Position2D(5, 1), new Position2D(1, 5))); // 3
		busLine.addLineSegment("173", new LineSegment(new Position2D(5, 5), new Position2D(5, 1))); // 2
		
		busLine.addBusLine("144", new Position2D(4, 3), new Position2D(6, 3));
		
		busLine.addLineSegment("144", new LineSegment(new Position2D(4, 3), new Position2D(6, 3)));
		
		busLine.findIntersections();
		
		System.out.println(busLine.getLines() + "\n");
		
		System.out.println("Pozycje skrzyżowań");
		System.out.println(busLine.getIntersectionPositions() + "\n");
		System.out.println("Nazwy z którymi istnieją skrzyżowania");
		System.out.println(busLine.getIntersectionsWithLines() + "\n");
		System.out.println("Pary tras");
		System.out.println(busLine.getIntersectionOfLinesPair() + "\n");
		
	}
	
}
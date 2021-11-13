import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test04 {
	static int errorCount = 0;
	
	public static void main(String[] args) {
		var b = new BusLine();
		b.addBusLine("a", new Position2D(1, 1), new Position2D(2, 6));
		b.addLineSegment("a", new LineSegment(new Position2D(1, 1), new Position2D(8, 8))); // 1
		b.addLineSegment("a", new LineSegment(new Position2D(8, 0), new Position2D(2, 6))); // 3
		b.addLineSegment("a", new LineSegment(new Position2D(8, 8), new Position2D(8, 0))); // 2
		
		b.addBusLine("b", new Position2D(5, 8), new Position2D(8, 8));
		b.addLineSegment("b", new LineSegment(new Position2D(5, 8), new Position2D(8, 8)));
		
		b.addBusLine("c", new Position2D(1, 1), new Position2D(4, 2));
		b.addLineSegment("c", new LineSegment(new Position2D(1, 1), new Position2D(1, 4)));
		b.addLineSegment("c", new LineSegment(new Position2D(1, 4), new Position2D(4, 4)));
		b.addLineSegment("c", new LineSegment(new Position2D(4, 4), new Position2D(10, 4)));
		b.addLineSegment("c", new LineSegment(new Position2D(10, 4), new Position2D(10, 2)));
		b.addLineSegment("c", new LineSegment(new Position2D(10, 2), new Position2D(4, 2)));
		
		b.findIntersections();
		
		// getLines
		Map<String, List<Position>> expLines = new HashMap<String, List<Position>>();
		expLines.put("a", List.of(
				new Position2D(1, 1), new Position2D(2, 2), new Position2D(3, 3), new Position2D(4, 4), new Position2D(5, 5), 
				new Position2D(6, 6), new Position2D(7, 7), new Position2D(8, 8), new Position2D(8, 7),	new Position2D(8, 6), 
				new Position2D(8, 5), new Position2D(8, 4), new Position2D(8, 3), new Position2D(8, 2),	new Position2D(8, 1), 
				new Position2D(8, 0), new Position2D(7, 1), new Position2D(6, 2), new Position2D(5, 3), new Position2D(4, 4), 
				new Position2D(3, 5), new Position2D(2, 6)));
		expLines.put("c", List.of(
				new Position2D(1, 1), new Position2D(1, 2), new Position2D(1, 3), new Position2D(1, 4), new Position2D(2, 4),
				new Position2D(3, 4), new Position2D(4, 4), new Position2D(5, 4), new Position2D(6, 4), new Position2D(7, 4),
				new Position2D(8, 4), new Position2D(9, 4), new Position2D(10, 4), new Position2D(10, 3), new Position2D(10, 2),
				new Position2D(9, 2), new Position2D(8, 2), new Position2D(7, 2), new Position2D(6, 2), new Position2D(5, 2), 
				new Position2D(4, 2)));
		if (!expLines.equals(b.getLines())) {
			System.err.println("error: getLines invalid");
			System.out.println("Oczekiwano:");
			System.out.println(expLines);
			System.out.println("Otrzymano:");
			System.out.println(b.getLines());
			errorCount++;
		}

		// getIntersectionPositions
		Map<String, List<Position>> expPositions = new HashMap<String, List<Position>>();
		expPositions.put("a", List.of(
					new Position2D(4, 4), new Position2D(8, 4), new Position2D(8, 2), new Position2D(4, 4)));
		expPositions.put("c", List.of(
					new Position2D(8, 4), new Position2D(8, 2)));
		if (!expPositions.equals(b.getIntersectionPositions())) {
			System.err.println("error: getIntersectionPositions invalid");
			System.out.println("Oczekiwano:");
			System.out.println(expPositions);
			System.out.println("Otrzymano:");
			System.out.println(b.getIntersectionPositions());
			errorCount++;
		}

		// getIntersectionsWithLines
		Map<String, List<String>> expInter = new HashMap<String, List<String>>();
		expInter.put("a", List.of("a", "c", "c", "a"));
		expInter.put("c", List.of("a", "a"));
		if (!expInter.equals(b.getIntersectionsWithLines())) {
			System.err.println("error: getIntersectionsWithLines invalid");
			System.out.println("Oczekiwano:");
			System.out.println(expInter);
			System.out.println("Otrzymano:");
			System.out.println(b.getIntersectionsWithLines());
			errorCount++;
		}

		// getIntersectionOfLinesPair
		Map<BusLine.LinesPair, Set<Position>> expPair = new HashMap<BusLine.LinesPair, Set<Position>>();
		Map<BusLineInterface.LinesPair, Set<Position>> resPair = b.getIntersectionOfLinesPair();
		expPair.put(b.new LinesPair("a", "a"), new HashSet<Position>(List.of(new Position2D(4, 4))));
		expPair.put(b.new LinesPair("a", "b"), new HashSet<Position>(List.of()));
		expPair.put(b.new LinesPair("a", "c"), new HashSet<Position>(List.of(new Position2D(7, 2), new Position2D(7, 4))));
		expPair.put(b.new LinesPair("b", "a"), new HashSet<Position>(List.of()));
		expPair.put(b.new LinesPair("b", "b"), new HashSet<Position>(List.of()));
		expPair.put(b.new LinesPair("b", "c"), new HashSet<Position>(List.of()));
		expPair.put(b.new LinesPair("c", "a"), new HashSet<Position>(List.of(new Position2D(7, 2), new Position2D(7, 4))));
		expPair.put(b.new LinesPair("c", "b"), new HashSet<Position>(List.of()));
		expPair.put(b.new LinesPair("c", "c"), new HashSet<Position>(List.of()));

		if (expPair.size() != resPair.size()) {
			System.err.println("error: getIntersectionOfLinesPair invalid");
			errorCount++;
		} else {
			for (var expEntry : expPair.entrySet()) {
				BusLine.LinesPair expLinesPair = expEntry.getKey();
				var match = resPair.entrySet().stream()
						.filter(entry -> entry.getKey().getFirstLineName().equals(expLinesPair.getFirstLineName())
								&& entry.getKey().getSecondLineName().equals(expLinesPair.getSecondLineName()))
						.findFirst();
				if (match.isEmpty() || !match.get().getValue().equals(expEntry.getValue())) {
					System.err.println("error: getIntersectionOfLinesPair invalid");
					errorCount++;
					break;
				}
			}
		}

		if (errorCount == 0)
			System.out.println("all tests passed");
	}
}
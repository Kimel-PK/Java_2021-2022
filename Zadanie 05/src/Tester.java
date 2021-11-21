// autorzy: ignis, bestemic, wmakos

public class Tester {
	
	public static class Canvas implements CanvasInterface {
		
		public Color[][] array;
		
		Canvas(Color[][] array) {
			this.array = array;
		}
		
		public void setColor(Position position, Color color) throws CanvasInterface.CanvasBorderException, CanvasInterface.BorderColorException {
			
			try {
				if (array[position.getRow()][position.getCol()] == null);
			} catch (ArrayIndexOutOfBoundsException ex) {
				throw new CanvasInterface.CanvasBorderException();
			}
			
			if (array[position.getRow()][position.getCol()].equals(Color.RED)) {
				array[position.getRow()][position.getCol()] = color;
				throw new CanvasInterface.BorderColorException(Color.RED);
			} else {
				array[position.getRow()][position.getCol()] = color;
			}
			
		}
	
	}
	
	public static void main(String[] args) throws GraphicsInterface.WrongStartingPosition, GraphicsInterface.NoCanvasException {
		Canvas canvas = new Canvas(new Color[][] {
			{ Color.WHITE, Color.RED, Color.RED, Color.WHITE, Color.RED, Color.WHITE, Color.RED, Color.WHITE, Color.WHITE,
				Color.WHITE, },
			{ Color.WHITE, Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.RED, Color.WHITE, Color.WHITE,
				Color.WHITE, },
			{ Color.WHITE, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.RED, Color.WHITE, },
			{ Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.RED, Color.WHITE, },
			{ Color.WHITE, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.RED, Color.WHITE, },
			{ Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.RED, Color.WHITE, },
			{ Color.WHITE, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.WHITE, Color.RED, Color.RED, Color.WHITE,
				Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, Color.RED, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, },
			{ Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
				Color.WHITE, Color.WHITE, }, });
	
		Graphics gp = new Graphics();
		boolean threwEx = false;
		
		// test 1 - no canvas
		try {
			gp.fillWithColor(new Position2D(90, 90), Color.GREEN);
		} catch (GraphicsInterface.NoCanvasException ex) {
			threwEx = true;
		}
		System.out.println("test 1 " + (threwEx ? "passed" : "failed"));
		
		// test 2 - invalid pos (out od canvas)
		threwEx = false;
		gp.setCanvas(canvas);
		try {
			gp.fillWithColor(new Position2D(90, 90), Color.GREEN);
		} catch (GraphicsInterface.WrongStartingPosition ex) {
			threwEx = true;
		}
		System.out.println("test 2 " + (threwEx ? "passed" : "failed"));
		
		// test 3 - invalid pos (pos in border)
		threwEx = false;
		gp.setCanvas(canvas);
		try {
			gp.fillWithColor(new Position2D(1, 1), Color.GREEN);
		} catch (GraphicsInterface.WrongStartingPosition ex) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < canvas.array.length; i++) {
				for (int j = 0; j < canvas.array[0].length; j++) {
					sb.append(canvas.array[i][j]);
				}
				sb.append("\n");
			}
			String expectedStr = "WRRWRWRWWW\nWRRRRWRWWW\nWRWWWWWRWW\nWRWWWWWWRW\nRWWWWWWWRW\nWRWWWWWWRW\nRWWWWWWWRW\nWRRRWWRRWW\nWWWWRRWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\n";
			threwEx = expectedStr.equals(sb.toString()) ? true: false;
		}
		System.out.println("test 3 " + (threwEx ? "passed" : "failed"));
		
		// test 4
		gp.fillWithColor(new Position2D(5, 5), Color.GREEN);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < canvas.array.length; i++) {
			for (int j = 0; j < canvas.array[0].length; j++) {
				sb.append(canvas.array[i][j]);
			}
			sb.append("\n");
		}
		// System.out.println(sb.toString());
		String expectedStr = "WRRWRGRWWW\nWRRRRGRWWW\nWRGGGGGRWW\nWRGGGGGGRW\nRGGGGGGGRW\nWRGGGGGGRW\nRGGGGGGGRW\nWRRRGGRRWW\nWWWWRRWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\n";
		System.out.println("test 4 " + (expectedStr.equals(sb.toString()) ? "passed" : "failed"));
		
		// test 5
		gp.fillWithColor(new Position2D(5, 5), Color.ORANGE);
		sb = new StringBuilder();
		for (int i = 0; i < canvas.array.length; i++) {
			for (int j = 0; j < canvas.array[0].length; j++) {
				sb.append(canvas.array[i][j]);
			}
			sb.append("\n");
		}
		// System.out.println(sb.toString());
		expectedStr = "WRRWRORWWW\nWRRRRORWWW\nWROOOOORWW\nWROOOOOORW\nROOOOOOORW\nWROOOOOORW\nROOOOOOORW\nWRRROORRWW\nWWWWRRWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\nWWWWWWWWWW\n";
		System.out.println("test 5 " + (expectedStr.equals(sb.toString()) ? "passed" : "failed"));
		
		// test 6
		canvas = new Canvas(new Color[][] {
		{ Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, },
		{ Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, },
		{ Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.RED, },
		{ Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, },
		{ Color.RED, Color.WHITE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, },
		{ Color.RED, Color.WHITE, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, },
		{ Color.RED, Color.WHITE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.RED, },
		{ Color.RED, Color.WHITE, Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, Color.WHITE, Color.RED, },
		{ Color.RED, Color.WHITE, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.WHITE, Color.RED, },
		{ Color.RED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.RED, },
		{ Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, },});
		
		gp.setCanvas(canvas);
		gp.fillWithColor(new Position2D(1, 1), Color.GREEN);
		sb = new StringBuilder();
		for (int i = 0; i < canvas.array.length; i++) {
			for (int j = 0; j < canvas.array[0].length; j++) {
				sb.append(canvas.array[i][j]);
			}
			sb.append("\n");
		}
		// System.out.println(sb.toString());
		expectedStr = "RRRRRRRRRRR\nRGGGGGGGGGR\nRRRRRRRRRGR\nRGGGGGGGGGR\nRGRRRRRRRRR\nRGRGGGGGGGR\nRGRRRRRRRGR\nRGRWWWWWRGR\nRGRRRRRRRGR\nRGGGGGGGGGR\nRRRRRRRRRRR\n";
		System.out.println("test 6 " + (expectedStr.equals(sb.toString()) ? "passed" : "failed"));
	}
}
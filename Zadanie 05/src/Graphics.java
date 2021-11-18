import java.util.Objects;
import java.util.Queue;

class Graphics implements GraphicsInterface {
	
	CanvasInterface canvas;
	
	public void fillWithColor(Position startingPosition, Color color) throws GraphicsInterface.WrongStartingPosition, GraphicsInterface.NoCanvasException {
		
		if (canvas == null)
			throw new NoCanvasException();
			
		try {
			canvas.setColor(startingPosition, color);
		} catch (CanvasBorderException | BorderColorException e) {
			throw new WrongStartingPosition();
		}
		
		Queue<Position2D> kolejka = new Queue<>();
		kolejka.add((Position2D)startingPosition);
		
		while (kolejka.size() != 0) {
			
			Position2D obecnaPozycja = kolejka.poll();
			
			try {
				canvas.setColor(obecnaPozycja, color);
			} catch (CanvasBorderException | BorderColorException e) {
				
			}
			
			kolejka.add(new Position2D(obecnaPozycja.getCol() + 1, obecnaPozycja.getRow()));
			kolejka.add(new Position2D(obecnaPozycja.getCol(), obecnaPozycja.getRow() + 1));
			kolejka.add(new Position2D(obecnaPozycja.getCol() - 1, obecnaPozycja.getRow()));
			kolejka.add(new Position2D(obecnaPozycja.getCol(), obecnaPozycja.getRow() - 1));
			
		}
		
	}
	
	public void setCanvas(CanvasInterface _canvas) {
		canvas = _canvas;
	}
	
}

class Position2D implements Position {

	private final int col;
	private final int row;

	public Position2D(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
}
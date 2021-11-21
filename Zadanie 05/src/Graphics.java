import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Graphics implements GraphicsInterface {
	
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
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position2D other = (Position2D) obj;
			return col == other.col && row == other.row;
		}
		
	}
	
	CanvasInterface canvas;
	List<Position> piksele;
	
	public void fillWithColor(Position startingPosition, Color color) throws GraphicsInterface.WrongStartingPosition, GraphicsInterface.NoCanvasException {
		
		if (canvas == null)
			throw new NoCanvasException();
			
		try {
			canvas.setColor(startingPosition, color);
		} catch (CanvasInterface.CanvasBorderException e) {
			throw new WrongStartingPosition();
		} catch (CanvasInterface.BorderColorException e) {
			try {
				canvas.setColor(startingPosition, e.previousColor);
			} catch (Exception e2) {
				
			}
			throw new WrongStartingPosition();
		}
		
		piksele = new LinkedList<>();
		
		Queue<Position> kolejka = new LinkedList<>();
		kolejka.add(startingPosition);
		
		while (kolejka.size() > 0) {
			
			Position obecnaPozycja = kolejka.poll();
			
			if (piksele.contains(obecnaPozycja)) {
				continue;
			} else {
				piksele.add(obecnaPozycja);
			}
			
			try {
				canvas.setColor(obecnaPozycja, color);
			} catch (CanvasInterface.CanvasBorderException e) {
				continue;
			} catch (CanvasInterface.BorderColorException e) {
				try {
					canvas.setColor(obecnaPozycja, e.previousColor);
					continue;
				} catch (Exception e2) {
					
				}
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
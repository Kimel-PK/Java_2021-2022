import java.util.Objects;

public class Position2D implements Position {

	private final int col;
	private final int row;

	public Position2D(int col, int row) {
		this.col = col;
		this.row = row;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}

	@Override
	public String toString() {
		return "Position2D [col=" + col + ", row=" + row + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(col, row);
	}

	@Override
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

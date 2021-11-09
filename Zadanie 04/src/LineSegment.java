
public class LineSegment {
	private final Position firstPosition;
	private final Position lastPosition;

	public LineSegment(Position firstPosition, Position lastPosition) {
		this.firstPosition = firstPosition;
		this.lastPosition = lastPosition;
	}

	@Override
	public String toString() {
		return "LineSegment [firstPosition=" + firstPosition + ", lastPosition=" + lastPosition + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstPosition == null) ? 0 : firstPosition.hashCode());
		result = prime * result + ((lastPosition == null) ? 0 : lastPosition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LineSegment))
			return false;
		LineSegment other = (LineSegment) obj;
		if (firstPosition == null) {
			if (other.firstPosition != null)
				return false;
		} else if (!firstPosition.equals(other.firstPosition))
			return false;
		if (lastPosition == null) {
			if (other.lastPosition != null)
				return false;
		} else if (!lastPosition.equals(other.lastPosition))
			return false;
		return true;
	}

	public Position getFirstPosition() {
		return firstPosition;
	}

	public Position getLastPosition() {
		return lastPosition;
	}

}

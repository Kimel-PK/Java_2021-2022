/**
 * Interfejs płótna
 *
 */
public interface CanvasInterface {

	/**
	 * Wyjątek zgłaszany, gdy zlecana jest operacja poza obszarem płótna.
	 */
	public class CanvasBorderException extends Exception {
		private static final long serialVersionUID = 4759606029757073905L;
	}

	/**
	 * Wyjątek zgłaszany, gdy piksel zmienia kolor ze stanowiącego kolor graniczny
	 * na nowy kolor.
	 */
	public class BorderColorException extends Exception {
		private static final long serialVersionUID = -4752159948902473254L;
		public final Color previousColor;

		public BorderColorException(Color color) {
			previousColor = color;
		}
	}

	/**
	 * Metoda pozwalająca na zmianę koloru piksela o położeniu position na color.
	 * 
	 * @param position położenie piksela
	 * @param color    nowy kolor piksela
	 * @throws CanvasBorderException położenie wypada poza obszarem płótna
	 * @throws BorderColorException  zmieniono kolor piksela granicznego
	 */
	public void setColor(Position position, Color color) throws CanvasBorderException, BorderColorException;
}

/**
 * Interfejs narzędzia graficznego.
 *
 */
public interface GraphicsInterface {

	/**
	 * Wyjątek zgłaszany w przypadku braku dostępu do płótna.
	 */
	public class NoCanvasException extends Exception {
		private static final long serialVersionUID = 8263666547167500356L;
	}

	/**
	 * Wyjątek zgłaszany, gdy początkowe położenie, od którego wypełniany jest
	 * obszar jest nieprawidłowe (poza obszarem płótna) lub powodujące zgłoszenie
	 * wyjątku {@link CanvasInterface.BorderColorException}.
	 *
	 */
	public class WrongStartingPosition extends Exception {
		private static final long serialVersionUID = -8582620817646059440L;
	}

	/**
	 * Metoda, za pomocą której użytkownik dostarcza płótno, na którym wykonywane
	 * będą operacje graficzne.
	 * 
	 * @param canvas referencja do płótna
	 */
	public void setCanvas(CanvasInterface canvas);

	/**
	 * Metoda wypełniająca obszar kolorem color. Wypełnianie obszaru rozpoczynane
	 * jest od pozycji startingPosition.
	 * 
	 * @param startingPosition położenie początkowe
	 * @param color            kolor, którym wypełniany jest obszar
	 * @throws WrongStartingPosition położenie początkowe jest niepoprawne
	 * @throws NoCanvasException     referencja do płótna jest niepoprawna
	 */
	public void fillWithColor(Position startingPosition, Color color) throws WrongStartingPosition, NoCanvasException;
}

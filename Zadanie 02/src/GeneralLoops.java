import java.util.List;

/**
 * Interfejs ogólnego systemu pozwalającego wyznaczać stany liczników
 * zagnieżdżonych pętli typu for.
 */
public interface GeneralLoops {
	/**
	 * Metoda ustalająca dolne limity pętli. Metoda wywoływana opcjonalnie. Jeśli
	 * metody nie wywołano pętle rozpoczynają iteracje od 0. Limit na pozycji 0
	 * odpowiada pętli zewnętrznej. Ostatni - najbardziej zagnieżdzonej pętli
	 * wewnętrznej.
	 * 
	 * @param limits lista wartości, od których rozpoczynane są iteracje w kolejnych
	 *               pętlach.
	 */
	public void setLowerLimits(List<Integer> limits);

	/**
	 * Metoda ustalająca górny limit pętli. Metoda wywoływana opcjonalnie. Jeśli
	 * metody nie wywołano ostatnią górnym limitem pętli jest 0. Limit na pozycji 0
	 * odpowiada pętli zewnętrznej. Ostatni - najbardziej zagnieżdzonej pętli
	 * wewnętrznej.
	 * 
	 * @param limits lista wartości, na których kończy się wykonywanie iteracji w
	 *               kolejnych pętlach.
	 */
	public void setUpperLimits(List<Integer> limits);

	/**
	 * Metoda zwraca listę list stanów pętli - zewnętrzna lista to kolejne iteracje,
	 * wewnętrzna stan liczników w danej iteracji. Np. przy limitach wynoszących
	 * {0,0,1} i {1,2,2} wynikiem powinno być:
	 * 
	 * <pre>
	 * {0,0,1},
	 * {0,0,2},
	 * {0,1,1},
	 * {0,1,2},
	 * {0,2,1},
	 * {0,2,2},
	 * {1,0,1},
	 * {1,0,2},
	 * {1,1,1},
	 * {1,1,2},
	 * {1,2,1},
	 * {1,2,2}
	 * </pre>
	 * 
	 * czyli 12 (2x3x2) list o rozmiarze 3. Kolejność danych ma znaczenie i
	 * odpowiada działaniu zagnieżdżonych pętli for.
	 * 
	 * @return lista zawierająca listy stanów liczników poszczególnych pętli.
	 */
	public List<List<Integer>> getResult();
}

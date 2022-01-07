public interface PrzechowywaczI {
/** Zleca pisanie obiektu na dysku w podanym katalogu.
   @param path Katalog do zapisania obiektu. Kazdy obiekt moze byc
     zapisany w innym katalogu.
   @param obiektDoZapisu Referencja do obiektu, ktory ma zostac
     zapisany na dysku.
   @return Identyfikator obiektu. Podanie tego identyfikatora ma pozwolic
     na odzyskanie obiektu. Jesli zapis nie jest mozliwy nalezy
     zwrocic -1, za poprawne identyfikatory uwazamy liczby > 0
*/
   public int save( String path, Object obiektDoZapisu );

/** Zleca odczyt obiektu o podanym id.
   @param obiektDoDoczytu Identyfikator obiektu, ktory chcemy odzyskac.
   @return Referencja od odczytanego obiektu. Jesli podano bledny identyfikator
     ma zostac zwrocony null.
*/
   public Object read( int obiektDoOdczytu );
}

# Zadanie 12

## Bardzo, bardzo stare zadanie…

Idea zadania pojawiła się w trakcie kursu Java z roku akademickiego 2011/12.

## Idea zadania

Zadaniem klasy będzie zapisywanie i odczytywanie z dysku zserializowanych obiektów. Powierzony klasie obiekt ma zostać zapisany na dysku i uzyskać identyfikator liczbowy pozwalający na jego odzyskanie. Identyfikator będzie zwracany użytkownikowi klasy. Aby zadanie dostosować do nowych realiów dodana została obsługa bazy danych, wyjątku oraz typu `Optional`.

## Baza danych

Baza danych ma pozwolić na przechowanie informacji o zapisanych obiektach pomiędzy uruchomieniami programu. Czyli, ma być możliwe zapisanie obiektów na dysku (a w bazie informacji o miejscu ich zapisania) i po ponownym uruchomieniu programu odzyskanie zapisanych obiektów.

Sama baza danych w momencie dostarczenia Państwu dostępu do niej (a stanie się to na początku pracy programu), będzie zawierać dwie tabele (Katalogi i Pliki) o następującej postaci:

```text
|        Katalogi      |            |            Pliki            |
| idKatalogu | katalog |            | idPliku | idKatalogu | plik |
|            |         |            |         |            |      |
```

Tabela `Katalogi` będzie wypełniona danymi, których nie należy modyfikować. Tabela `Pliki` będzie pusta i do Państwa dyspozycji - to w niej należy zachować informacje o zapisywanych na dysku plikach.

> Uwaga: pola `idKatalogu` i `idPliku` będą typu `Integer`. Pola `katalog` i `plik` będą typu `String`.

## Przykład użycia bazy danych

Załóżmy, że tabela `Katalogi` zawiera następujące wpisy:

```text
|        Katalogi      | 
| idKatalogu | katalog | 
|      1     | /tmp/A1 | 
|      2     | /tmp/A2 | 
```

Jeśli na dysku trzeba zapisać obiekt w katalogu o identyfikatorze równym 2 to znaczy, że zapisujemy go w katalogu `/tmp/A2`.

Aby odtworzenie obiektu było możliwe, w tabeli `Pliki` trzeba zapisać informacje o nadanym mu identyfikatorze i następnie użytym katalogu. Niech plik ma identyfikator równy `12345` i nazywa się np. `plim`. W tabeli pliki powinien pojawić się więc następujący rekord:

```text
|            Pliki            |
| idPliku | idKatalogu | plik |
| 12345   |     2      | plim |
```

> UWAGA: W tabeli `Pliki` w kolumnie `plik` nie wolno zapisywać nazwy katalogu (w sensie ciągu znaków)! Informacja o katalogu ma być zapisana w postaci odpowiedniego `idKatalogu`.

## Optional

To narzędzie, które wprowadzono w Java 8 dla tych metod, które nie zawsze mogą zwrócić referencję do obiektu-wyniku. Nie mając co zwrócić typowo zwracały one `null`, a to powodowało znany efekt w postaci `NullPointerException`. `Optional` to opakowanie na wynik. Dzięki `Optional` metoda zawsze może zwrócić poprawny obiekt - ot, czasami nie będzie w nim spodziewanego wyniku, ale cóż, c'est la vie...

## Dostarczenie rozwiązania

Proszę o dostarczenie kodu klasy o nazwie `PrzechowywaczObiektow` implementującej interfejs `PrzechowywaczI`.

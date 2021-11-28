# Zadanie 06

## Idea

Do programu docierają dane. Są to n-bitowe słowa (n < 9). Wiele z nich będzie się potwarzać. Chcemy użyć tej informacji aby dokonać bezstratnej kompresji danych.
Kompresja

Pomysł na skompresowanie danych jest następujący:

1. badamy ile razy powtarza się dana sekwencja bitów (słowo)
2. następnie staramy się zakodować dane tak, że najczęściej występujące słowa kodujemy używając mniejszej niż n liczby bitów, a pozostałe kodujemy używajac n+1 bitów.

Dlaczego pozostałe słowa wymagają n+1 bitów? Bo musimy użyć jednego bitu do rozróżnienia pomiędzy sekwencjami kodującymi czesto powtarzające się słowa i resztą, która nie jest kodowana.

Umawiamy się, że sekwencje kodujące powtarzające się słowa zaczynają się zawsze od `0`, a pozostałe od `1`.

## Przykłady

### Przykład bardzo prosty

```text
słowa 3-y bitowe

Wejście:
001 001 001 010 111 011 001 001 110 000 001 001 001 001

Statystyka słów:
001 9x
000 1x
010 1x
011 1x
110 1x
111 1x

Kodowanie:
Słowo `001` kodujemy poprzez samo `0`, 
pozostałe słowa stają się 4-ro bitowe.

Skompresowana sekwencja:
[0->001] 0 0 0 1010 1111 1011 0 0 1110 1000 0 0 0 0
```

W części oznaczonej w przykładzie [] (nagłówku) podana została informacja o sposobie kodowania. Podany nagłówek zajmuje 4 bity.

### Teraz trochę trudniej

```text
000 001 000 001 000 001 000 001 011 001 000 110 001 000 111 001 001 000 000 000 001
```

Jak widać bardzo często powtarzają się dwie sekwencje bitów `000` i `001`. Zamieńmy je odpowiednio na sekwencje o rozmiarze dwóch bitów (bit `0` oznaczający zakodowane dane i jeden bit służący do rozróżnienia, która sekwencja jest zakodowana) `00` i `01`, a pozostałe dostaną z przodu dodatkowy bit `1` (staną się 4-ro bitowe).

```text
[00->000 01->001] 00 01 00 01 00 01 00 01 1011 01 00 1110 01 00 1111 01 01 00 00 00 01
```

Rozmiar nagłówka wynosi 10 bitów

## Co zyskujemy/co tracimy?

W sumie: zyskujemy zmiejszając liczbę bitów potrzebnych do zakodowania często powtarzających się słów, tracimy zaś na wszystkich pozostałych, które musimy wydłużyć o jeden bit.

## Na czym ma polegać zadanie?

Zadanie polega na dokonaniu optymalnej kompresji przekazanego ciągu bitów. Przez "optymalna kompresja" rozumiem tu, że Państwa program sprawdzi jakie sekwencje najlepiej zakodować i tak dobierze ich ilość, aby uwzględniając długość nagłówka oraz konieczność dodania dodatkowego bitu do sekwencji nie kodowanych ilość bitów potrzebnych do zapisania informacji będzie możliwie najmniejsza.

## Wersja obiektowa

Aby nie męczyć się z bitami słowa będą dostarczane w postaci ciągu znaków o stałej długości. Wynik kompresji będzie odbierany w postaci nagłówka (mapa informująca o sposobie kompresji) i ciągów znaków. Jak widać z przykładów odbierane ciągi będą zazwyczaj (nie zawsze kompresja będzie mieć sens i lepiej jej nie robić) posiadać długość inną niż te przekazywane do kompresji.

> Uwaga: nie należy przekazywać spacji i znaków `[` oraz `]`. Znaczki te użyte zostały tylko w celu ułatwienia prezentacji idei kompresji.

## Dostarczenie rozwiązania

Proszę o dostarczenie kodu klasy o nazwie Compression implementującej interfejs CompressonInterface.

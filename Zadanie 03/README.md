# Zadanie 03

## Idea

Pewien hipotetyczny "Wydział Fizyki, Astronomii i Informatyki Stosowanej" prowadzi wewnętrzną korespondencję w formie zaszyfrowanej. Szyfr jest prosty - jedna litera alfabetu zamieniana jest pojedynczym znakiem (niekoniecznie będzie to litera). Aby złamać szyfr (przynajmniej częściowo) otrzymujecie Państwo zaszyfrowaną wiadomość, w której (prawie) na pewno znajduje się pełna nazwa Wydziału pisana tak, jak powyżej (bez znaków cudzysłowu). Ludzie są jednak tylko ludźmi i mogą się mylić - w przekazanej wiadomości nazwy Wydziału może jednak nie być.

### UWAGA

> W zaszyfrowanym tekście mogą znajdować się nadmiarowe spacje, tabulatory czy znaki nowej linii. Oznacza to, że nazwa Wydziału może być zapisana przed zaszyfrowaniem np. tak:

```text
Wydział   Fizyki, 	Astronomii 
i
Informatyki 			Stosowanej
```

## Umowa

Do zaszyfrowania wiadomości zastosowane zostaną wyłącznie znaki: małe litery, duże litery, liczby oraz znaki `"!.@#$%^&*()<>?;:{[]}\|"`

## Wejście/wyjście

Wejście jest zaszyfrowany dokument.

Wyjście dwie mapy:

- Pierwsza, której kluczem jest litera alfabetu, a wartością znak, w który została w zamieniona (wynik getCode).
- Druga. Kluczem jest znak, a wartością zamieniana litera alfabetu (wynik getDecode).

## Przykład

Niech słowo Wydział zaszyfrowane jest jako `1234567`

code (czyli jak zaszyfrowano)

```text
W -> 1
y -> 2
d -> 3
z -> 4
i -> 5
a -> 6
ł -> 7
```

decode (czyli jak odkodować)

```text
1 -> W
2 -> y
3 -> d
4 -> z
5 -> i
6 -> a
7 -> ł
```

Dostarczenie rozwiązania.

Proszę o dostarczenie kodu klasy o nazwie `Decrypter` implementującej interfejs `DecrypterInterface`.

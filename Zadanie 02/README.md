# Zadanie 02
Do zdobycia maksymalnie: 3pkt.

## Idea

Zadanie sprowadza się do napisania programu, który będzie realizować działanie przypominające pracę wielokrotnie zagnieżdżonych pętli for. Smaczkiem w tym zadaniu jest to, że liczba poziomów zagnieżdżenia nie będzie znana i może być dowolna (czytaj: dowolnie duża).

Program znając liczbę poziomów zagnieżdżenia (a dowie się o nim dopiero w tracie wykonania) ma wygenerować listę stanów wszystkich liczników w trakcie realizacji iteracji.

Stan początkowy i końcowy dla każdej z pętli może być inny. Limity określane są za pomocą wywołań metod należących do interfejsu. Uwaga: podane w limitach wartości traktujemy "włącznie z nimi", czyli odpowiadać będzie to następującemu działaniu pętli:

```java
for
    for
        [...]
            for (int licznik = limitDolny; licznik <= limitGórny; licznik++)
                [...]
                    for
                        for
```

## Podpowiedź

May the Recursion be with you... [link](https://en.wikipedia.org/wiki/Recursion_(computer_science))

![Lightsaber](https://zti.if.uj.edu.pl/Piotr.Oramus/dydaktyka/Java/Zadania/Zadanie02/lightsaber.png)

## Założenia

Zakładamy, że limit górny będzie nie mniejszy od dolnego.

Zakładamy, że jeśli określone zostaną limity dolny i górny to rozmiar przekazanych list będzie identyczny.

## Dostarczenie rozwiązania.

Proszę o dostarczenie kodu klasy o nazwie `Loops` implementującej interfejs `GeneralLoops`. 
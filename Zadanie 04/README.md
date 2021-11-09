# Zadanie 04

## Idea

Na pewnej 2-wymiarowej płaszczyźnie umieszczono trasy linii autobusowych. Pojedyncza trasa autobusowa składa się z odcinków. Każdy odcinek ma podany punkt początkowy i końcowy. W skład odcinka wchodzą wszystkie punkty od punktu początkowego do końcowego wraz punktami krańcowymi. Punkt końcowy jednego odcinka jest zarazem punktem początkowym kolejnego odcinka wchodzącego w skład danej trasy. Współrzędne punktów są całkowite. Umawiamy się, że odcinek może przyjmować jeden z czterech dozwolonych kierunków:

```text
        *    *         *
*****   *     *       *
        *      *     *
```

Zadanie polega na odnalezieniu tych punktów (oznaczonych "x"), w których dwie trasy (oznaczone tu poprzez A i B) przecinają się pod kątem prostym czyli tak:

```text
   A      A   B
   A       A B 
BBBxBBB     x
   A       B A
   A      B   A
```

Uwaga: należy również uwzględnić scenariusz, w którym w miejscu przecięcia (lub w jego pobliżu) trasa autobusu opisana jest np. dwoma odcinkami (a i A to dwa odcinki jednej trasy, b i B to odcinki należące do drugiej trasy). Np. tak:

```text
   a      a   B
   a       a B 
bbBxBBB     x
   A       b A
   A      b   A
```

Umawiamy się, że prezentowanie poniżej przykładowe obrazki nie przedstawiają interesujących nas skrzyżowań:

```text
   1               1
    1              1
22222?2222      222?222
      1             1 
       1             1 
```

> UWAGA: zakładamy, że trasa może mieć skrzyżowanie sama ze sobą.

## Wynik pracy programu

Wynikiem mają być:

- Mapa trasa -> lista kolejnych punktów wchodzących w skład trasy.
- Mapa trasa -> lista kolejnych punktów, w których ta trasa ma skrzyżowania
- Mapa trasa -> lista kolejnych tras, z którymi trasa ma skrzyżowanie
- Mapa para tras -> zbiór punktów, w których te dwie trasy mają skrzyżowania 

## Dostarczenie rozwiązania

Proszę o dostarczenie kodu klasy o nazwie `BusLine` implementującej interfejs `BusLineInterface`. Do szczęścia potrzebna jest jeszcze implementacja interfejsu reprezentującego parę tras - proszę ją także dostarczyć. Najlepiej jako klasę wewnętrzną.

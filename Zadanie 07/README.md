# Zadanie 07

## Wątki

W tym zadaniu należy w rozwiązaniu posłużyć się wątkami.

## Problem

Otrzymujecie Państwo ode mnie obiekt `HidingPlaceSupplierSupplier`, z którego należy pobierać (get) obiekty dostarczające skrytki. Każdorazowo odbieracie Państwo pojedynczy obiekt typu `HidingPlaceSupplier` zawierający dużą liczbę skrytek (`HidingPlace`). Tylko część z nich coś zawiera (`isPresent`). Te trzeba opróżnić (`openAndGetValue`). Po przeglądnięciu i opróżnieniu wszystkich skrytek odbieracie Państwo kolejny obiekt ze skrytkami `HidingPlaceSupplier`.

HidingPlaceSupplierSupplier - dostarcza -> HidingPlaceSupplier - dostarcza -> HidingPlace - może-zawiera -> coś cennego

Skrytki zawierają cenne przedmioty. Ich wartość trzeba podliczyć. Aby uzyskać kolejny obiekt ze skrytkami trzeba podać prawidłową wartość przedmiotów ukrytych w skrytkach w poprzednim obiekcie (uwaga: pierwszy obiekt można pobrać podając wartość 0).

## Cel

Zadanie sprawdza się do napisania kodu, który dzięki użyciu wątków będzie możliwie często odbierał obiekty ze skrytkami.

## Warunki pracy

- do poszukiwania skrytek zawierających zawartość (`isPresent`) należy posłużyć się wieloma wątkami
- wolno opróżniać (`openAndGetValue`) tylko jedną skrytkę w danej chwili
- wolno poprosić o przyznanie nowego obiektu ze skrytkami tylko po przeglądnięciu poprzedniego i po pobraniu oraz przeglądnięciu wszystkich jego skrytek. Oczywiście - nie dotyczy to pobrania pierwszego obiektu ze skrytkami.
- Do zarządzania wątkami w programie wolno używać wyłącznie metod z klasy Thread i Object. Używanie innych narzędzi (np. klas z pakietu Concurrent, klas Atomowych, Egzekutorów etc.) jest zabronione.
- Uzyskanie null zamiast obiektu zawierającego skrytki kończy poszukiwania.

## Błędy krytyczne

- jednoczesne otwarcie więcej niż jednej skrytki (chodzi o użycie metody `openAndGetValue`)
- wielokrotne otwieranie tej samej skrytki (chodzi o użycie metody `openAndGetValue` na jednej skrytce)
- zbyt wczesne poproszenie o kolejny obiekt ze skrytkami tj. przed zakończeniem analizy poprzedniego
- podanie błędnej wartości przedmiotów
- sekwencyjne wyszukiwanie skrytek z zawartością
- użycie do przeglądania większej/mniejszej liczby wątków niż limit (threads) podany przez obiekt ze skrytkami.

## Dostarczenie rozwiązania

Proszę o dostarczenie kodu klasy o nazwie `ParallelSearcher` implementującej interfejs `ParallelSearcherInterface`.

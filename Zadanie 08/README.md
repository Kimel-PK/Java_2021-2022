# Zadanie 08

## Idea zadania

Tym razem zadanie to obsługa sklepu za pomocą wątków.

Towary dostarczane do pewnego sklepu mają swoje unikalne nazwy (String). Dostawa obejmuje pewną liczbę towarów. Towary mogą być dostarczane w różnej liczbie sztuk (zakładamy, że każda rzecz obsługiwana przez sklep jest łatwo policzalna np. kartony mleka, groszek w puszkach itp.)

Dostawy (delivery) mogą być realizowane w dowolnej chwili. Klienci (wątki) także mogą pojawiać się w dowolnym momencie i zgłaszać chęć zakupu (purchase).

Zakładamy, że klient, którego zakupu nie można zrealizować gotowy jest poczekać w sklepie na najbliższą dostawę towaru, który chce nabyć. Klient zaczeka nawet jeśli okaże się, że dostarczona liczba sztuk nie wystarczy aby spełnić jego potrzeby.

## Zakupy

Klient, który zgłasza się do sklepu po towar zawsze podaje jego nazwę oraz liczbę sztuk. Jeśli towar jest znany i jest go w wystarczającej ilości, klient obsługiwany jest standardowo (metoda kończy się zwracając true). Jeśli jednak zamówienia klienta nie można obsłużyć, wątek klienta ma zostać zablokowany do chwili najbliższej dostawy towaru o tej samej nazwie.

Może zdarzyć się tak, że na ten sam towar czekać będzie wielu klientów. Dostawa tego towaru ma doprowadzić do uwolnienia ich wątków (zakończenia blokowania metody purchase).

## Przykład

Załóżmy, że towar X(5) chcą kupić trzej klienci A(10), B(20) i C(35). W nawiasach podano liczbę sztuk jaką posiada sklep i żądania klientów. Każdy z wątków A, B, C jest zatrzymywany. Niech teraz pojawi się dostawa 30 sztuk towaru X. Powinna ona doprowadzić do odblokowania wszystkich wątków (A, B, C). Możliwe są następujące scenariusze (wszystkie są traktowane jako poprawne):

- Wątki A i B otrzymują towar, wątek C nie. Stan magazynu po operacji 5.
- Wątek C otrzymuje towar, wątki A i B nie. Stan magazynu po operacji 0.

## Niepoprawne scenariusze to przykładowo

- Jeden z wątków np. A nie zostaje odblokowany
- Obudzono wątek, który nie czekał na dostawę towaru X
- Zrealizowano zakup dla wątków A, B i C (3x true).
- Zrealizowano zakup tylko dla wątku A. Wątki B i C obudzono, ale zakup w nich zakończył się false. Sklep zarabia na sprzedaży! Nie wolno nie sprzedawać posiadanego towaru!
- itd. itd.

## Uwagi

- Żaden z kupujących nie ma wyższego priorytetu niż inni. Dlatego w przedstawionym przykładnie oba poprawne rozwiązania są równie dobre.
- Aby program działał poprawnie blokowanie wątków-klientów nie może doprowadzić do zablokowania możliwości realizacji dostaw i sprawdzenia stanu magazynu.
- Do realizacji programu nie wolno używać odpytywania (ciągłego/wielokrotnego sprawdzania stanu np. magazynu). Obciążenie CPU wątkami, które reprezentują klientów czekających na dostawę powinno wynosić praktycznie 0.
- Wątki nie mogą nigdy przejść w stan TIMED_WAITING. Zabronione jest więc używanie metod sleep() i wait() (lub odpowiednika) w wersji z timeout-em.

## Dostarczenie rozwiązania

Proszę o dostarczenie kodu klasy o nazwie `Shop` implementującej interfejs `ShopInterface`.

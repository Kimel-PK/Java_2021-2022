# Zadanie 10

## Nierozmienialna N-złotówka

W książce `Poniedziałek zaczyna się w sobotę` pojawia się nierozmienialna pięciokopiejkówka. Płacąc nią za towar tańszy od pięciu kopiejek zawsze powracała do płacącego. Co ciekawe, reszta z tych pięciu kopiejek zachowywała się normalnie i także trafiała do płacącego. W sumie: czysty zysk! Nie dość, że mamy towar i resztę, to moneta, którą zapłaciliśmy, do nas wraca. Trzeba tylko pamiętać o jednym: aby moneta wróciła musiała być rozmieniona na drobne.

Przy okazji spotkamy się z Interfejsami Funkcyjnymi w Java.

## Zadanie

W tym zadaniu trzeba będzie zmierzyć się z nierozmienialną N-złotówką. Będzie to nierozmienialna moneta/banknot (generalnie `Pieniądz`), który będzie w przypadku konieczności rozmienienia powracał wraz z resztą do płacącego.

Środki płatnicze (zakładamy dla uproszczenia, że interesują nas wyłącznie złotówki i groszami się nie przejmujemy) reprezentuje klasa `Pieniadz`. Ma ona prostą formę - dwa pola, które reprezentują wartość (nominał) oraz flagę, która określa czy egzemplarz (obiekt) posiada własność nierozmienialności.

## Co trzeba zrobić?

Proszę zaimplementować w klasie `Kasjer` interfejs `KasjerInterface`.

### Obsługa zakupów

Aby obliczyć co zwraca metoda `rozlicz` posługujemy się zwykłą matematyką i wiedzą o mechanice nierozmienialnych N-złotówek. Przyświeca nam podstawowa zasada: minimalizacja traconej przez sprzedawcę (kasjera) gotówki. Chodzi o to, aby nierozmienialny pieniądz był użyty w zakupie tak, aby w miarę możliwości nie powrócił do kupującego. To od kasjera zależy jak z przekazanych pieniędzy poskłada kwotę potrzebną do opłacenia cely towarów.

Dla uproszczenia:

- Zakładamy, że obsługiwane są wyłącznie nominały: 1, 2, 5, 10, 20, 50, 100, 200 i 500zł.
- Zakładamy również, że kasjer dysponuje pewną ilością gotówki na starcie (trzeba ją pobrać z przekazanego obiektu) i ma możliwość rozmieniania pieniędzy w odpowiednim serwisie (uwaga: ten obsługuje wyłącznie zwykłe, rozmienialne pieniądze)
- Kolejność zwracanych pieniędzy nie ma znaczenia.
- W przypadku braku reszty proszę zwrócić pustą listę (taką o rozmiarze 0).
- Jeśli zwracana jest nierozmienialna N-złotówka, to musi zostać wrócony dokładnie ten sam obiekt, który został dostarczony do metody rozlicz.
- Nigdy nie pojawi się zamiast obiektu `NULL` - tego samego oczekuje się od wyniku.
- Kasjer nie może samodzielnie tworzyć obiektów reprezentujących `Pieniądz`. Wystarczającą ilość gotówki uzyska z zakupów i początkowo od `Supplier`-a.

## Rozmienianie

W zadaniu pojawia się termin "Rozmienianie" pieniędzy. Są jednak dwa konteksty jego zastosowania. W pierwszym chodzi o fizyczne rozmienienie zwykłego pieniądza w serwisie `Rozmieniacz`-a. W drugim chodzi o logiczne rozmienianie pieniądza nierozmienialnego. Pieniądza nierozmienialnego nie wolno rozmienić w `Rozmieniaczu`, ale można zamienić go "logicznie" na mniejsze nominały. Jeśli kasjer zrealizuje drugą z czynności w celu pokrycia ceny, musi nierozmienialny pieniądz zwrócić kupującemu.

### Przykłady

- Płacę 10zł + 10złNR, cena 15zł - kasjer wydaje 5zł reszty, bo uznał, że 5zł pochodzi ze zwykłego 10zł.
- Płacę 20zł + 10złNR, cena 25zł - wydaje 5zł reszty, bo zwykłymi pieniędzmi można posłużyć się tak, że 10złNR nie trzeba rozmieniać.
- Płacę 20złNR + 10złNR, cena 25zł - wydaje 5zł + nierozmienialną 10zł - tak tracę najmniej pieniędzy z kasy.
  - w tym przypadku nie uda się rozliczyć zakupu bez logicznego rozmienienia jednego z NR pieniędzy.

## Dostarczenie rozwiązania

Proszę o dostarczenie kodu klasy o nazwie `Kasjer` implementującej interfejs `KasjerInterface`.

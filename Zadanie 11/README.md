
# Zadanie 11

## Zadanie

Zadanie polega na napisaniu aplikacji, która połączy się za pomocą protokołu TCP/IP z serwerem i odgadnie hasło, które serwer wylosuje dla zestawionego połączenia (każde połączenie z serwerem powoduje utworzenie nowego hasła).

## Jak złamać hasło?

Generalnie, da się to zrobić i to w rozsądnym czasie. Pomogą w tym następujące czynniki:

- Znany będzie schemat hasła. Wiadomo będzie jak długie jest hasło i z jakich składników (cyfry, znaki specjalne, litery małe, litery duże) się ono składa.
- Wszystkie znaki, które mogą być użyte do stworzenia hasła są znane i podzielone na odpowiednie kategorie.
- Serwer informuje o ilości znaków, które zostały odgadnięte w haśle i pozwala na przeprowadzenie kolejnej próby.

## Schemat hasła

Schemat hasła to ciąg znaków, który składa się z pewnej kombinacji liter 'l', 'u', 'n' i 's'. Każda litera wskazuje na listę znaków, które mogą być zastosowane na danej pozycji hasła. I tak:

- 'l' to małe litery (lowercase)
- 'u' to duże litery (uppercase)
- 'n' to liczby (numbers)
- 's' to znaki specjalne (symbols)

Jedna litera schematu hasła odpowiada jednej literze w haśle.

## Przykład

```text
luns    aB2*
snul    +9Cd
```

## Klasa `PasswordComponents`

Klasa ta udostępnia listy znaków, które są podstawą do wygenerowania hasła. Dodatkowo zawiera użyteczną mapę, której kluczem jest znak ze schematu hasła a wartością lista znaków, które odpowiadają danemu znakowi schematu.

Metoda decodePasswordSchema
tworzy z ciągu znaków (String) listę pojedynczych znaków składających się na schemat hasła (Character).

## Niepowodzenie w łamaniu hasła

Serwer automatycznie zakończy (zamknie) połączenie, gdy klient nie dostarczy propozycji hasła w określonym czasie (serwer o nim informuje) lub propozycja nie będzie zgodna ze schematem hasła (np. propozycja hasła `1234` dla schematu `lllll`).
Dostarczenie rozwiązania.

Proszę o dostarczenie kodu klasy o nazwie PasswordCracker implementującej interfejs PasswordCrackerInterface.

## Test

Serwer jest udostępniony i czeka na Państwa połączenia pod adresem:

```text
IPv4: 172.30.24.15
port: 8080
```

Serwer pracuje w dwóch trybach - `Człowiek` i `Program`. Tryb `Człowiek` pozwala na zapoznanie się z działaniem serwera interaktywnie np. za pomocą programu telnet. Oczywiście, rozwiązaniem zadania będzie kod, który odgadnie hasło w trybie `Program`.

Połączenie za pomocą telnet można uzyskać w sposób następujący

```text
telnet 172.30.24.15 8080
```

> UWAGA: z uwagi na obecność naszego Wspaniałego, Wydziałowego firewall-a z serwerem można połączyć się tylko z pracowni komputerowych a zdalnie za pomocą spk-ssh.if.uj.edu.pl. Z domu ja też nie mogę się z nim połączyć...

Przykładowe połączenie z serwisem może wyglądać tak:

```text
spk-ssh:~$ telnet 172.30.24.15 8080
Trying 172.30.24.15...
Connected to 172.30.24.15.
Escape character is '^]'.
Wersja 1.0. Powitać! Z kim rozmawiam: Człowiek czy Program? 20 sekund na odpowiedz
Człowiek
Zdradzam password: Js4jg2,
timeout: 20000 msec.
schema : ulnllns
Zaczynamy! Zgadnij hasło.
Js4jg3,
To nie hasło, odgadnięto 6 znaków
Js4jg2,
+OK
Access granted - hasło zostało odgadnięte
```

/*

Zadanie 01 termin I. Do zdobycia maksymalnie: 3pkt.

Proszę napisać kod klasy Decode rozszerzającej klasę abstrakcyjną DecoderInterface. Zadaniem klasy Decode jest dekodowanie danych, które będą do niej przekazywane poprzez pewien 
szeregowy protokół komunikacyjny (podobny protokół jest używany w komunikacji za pomocą podczerwieni pomiędzy pilotem a np. telewizorem). Do zaimplementowania przez Państwa są 
tylko trzy metody. Oczywiście, można dla własnej wygody dodać do kodu dodatkowe metody np. prywatne, ale ja z nich korzystać nie będę, bo Państwa klasa zostanie użyta wyłącznie 
za pomocą metod znajdujących się w DecoderInterface.

Idea działania protokołu wygląda tak:

X0 - koduje 0
XX0 - koduje 1
XXX0 - koduje 2
XXXX0 - koduje 3

i tyle zakodowanych liczb nam wystarczy (czyli od 0 do 3 włącznie).

Co to są te X-y? To są jedynki.
Dlaczego więc zamiast X nie wpisane zostały jedynki? Bo w miejscu X może to być jedna jedynka albo więcej. Mam nadzieję, że tą rzecz wyjaśni przykład poniżej.
Skąd będzie wiadomo ile jedynek ma X? Bo każdy strumień danych zaczynać się będzie zakodowanym zerem.

UWAGA: zwacam uwagę na to, że same, kolejne powtórzenia 0 lub 1 - nic nie kodują. Istotny jest moment zakończenia jedynek i pojawienie się na wejściu zera.

Dane wejściowe przekazywane są za pomocą liczb całkowitych. Wyjście to ciąg znaków. Sposób generowania ciągu (zwykła konkatenacja ciągów czy zastosowanie klas typu StringBuilder) 
nie ma znaczenia. Ważne jest tylko to, aby wynik był poprawny.

UWAGA: w ciągu wynikowym nie mogą występować inne znaki niż: "0", "1", "2" i "3".

Kolejny przykład

000010000110010111010 -> 01020         (X to 1)
11011110000000011011111100110 -> 01020 (X to 11)

Żeby nie było wątpliwości. Wejście w postaci np. 000111000 będzie zrealizowane w kodzie w postaci:

referencja.input(0);
referencja.input(0);
referencja.input(0);
referencja.input(1);
referencja.input(1);
referencja.input(1);
referencja.input(0);
referencja.input(0);
referencja.input(0);

Gwarancja.

Użytkownik Państwa kodu będzie używać go zgodnie z dokumentacją. Nigdy nie pojawi się w wywołaniu input coś innego niż liczby 0 i 1.
Dostarczenie rozwiązania.

Proszę o dostarczenie kodu klasy o nazwie Decoder rozszerzającej klasę DecoderInterface. 

*/

public class Decode extends DecoderInterface {
	
	int X = -1;
	int licznik = -1;
	String wynik = "";
	
	public static void main (String[] args) {
		
		Decode referencja = new Decode();
		
		referencja.input(0);
		referencja.input(1);
		referencja.input(1);
		referencja.input(0);
		referencja.input(0);
		referencja.input(1);
		referencja.input(1);
		referencja.input(0);
		
		referencja.output();
		
	}
	
	public void input (int bit) {
		
		if (bit == 1) {
			licznik++;
		} else {
			if (licznik > -1) {
				if (X == -1) {
					X = licznik + 1;
				}
				wynik += Integer.toString(licznik / X);
				licznik = -1;
			}
		}
		
	}
	
	public String output () {
		return wynik;
	}
	
	public void reset () {
		X = -1;
		licznik = -1;
		wynik = "";
	}
	
}
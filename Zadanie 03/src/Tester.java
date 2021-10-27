public class Tester {
	
	public static void main (String[] args) {
		
		/*
		Dobry Wieczór                        Dxb80 1*9c3ó8
		Wydział Fizyki, Astronomii           1023*4Ł f*30{*, 5678x!xW**           
		i                                    *
		Informatyki Stosowanej               }!fx8W470{* s7x6xw4!9i  
		*/
		
		// pobierz string
		String before = "Dxb80 1*9c3ó8\n1023*4Ł f*30{*, 5678x!xW**    \t    \t   \n*\n}!fx8W470{* s7x6xw4!9i  ";
		
		// wyczyść nadmiarowe spacje, tabulatory i znaki nowej linii
		String after = before.trim().replaceAll("[\n\t]", " ").replaceAll("[ ]+", " ");
		
		System.out.println(after);
		
		// podziel wiadomość na słowa
		String[] parts = after.split(" ");
		String dlugosciSlow = "";
		
		// konwertuj wiadomość na listę długości słów do poszukiwania wzoru
		for (String part : parts) {
			dlugosciSlow += part.length() + "-";
		}
		
		if (dlugosciSlow.indexOf("7-7-10-1-11-10") != -1) {
			
			// od początku do znalezionego elementu
			// zbieraj i dodawaj do siebie liczby żeby określić miejsce w ciągu źródłowym
			
			
			// weź znak
			// sprawdź czy dany znak można zakodować czy nie zgadza się ze wzorcem
			
			// powtarzaj dla wszystkich znaków
			
			// jeśli udało się dotrzeć do końca wzorca bez błędów koniec programu
			
			// jeśli nie udało się dotrzeć do końca wzorca to przerwij
			
		} else {
			// nie znaleziono nic co by chociaż przypominało naszą wiadomość
		}
		
    }
	
}

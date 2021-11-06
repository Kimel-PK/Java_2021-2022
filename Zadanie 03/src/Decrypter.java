import java.util.HashMap;
import java.util.Map;

class Decrypter implements DecrypterInterface {
    
    private Map<Character, Character> szyfr;
    private String zaszyfrowanaWiadomosc;
    
    private final String wzorzec = "Wydział Fizyki, Astronomii i Informatyki Stosowanej";
    
	public void setInputText(String encryptedDocument) {
        
        if (encryptedDocument == null)
            encryptedDocument = "";
        
		// wyczyść nadmiarowe spacje, tabulatory i znaki nowej linii
        zaszyfrowanaWiadomosc = encryptedDocument.trim().replaceAll("[\n\t]", " ").replaceAll("[ ]+", " ");
		
        Analyze ();
    }
    
	public Map<Character, Character> getDecode() {
        return szyfr;
    }
    
	public Map<Character, Character> getCode() {
        
        Map<Character, Character> odwroconySzyfr = new HashMap<>();
        for(Map.Entry<Character, Character> entry : szyfr.entrySet()){
            odwroconySzyfr.put(entry.getValue(), entry.getKey());
        }
        
        return odwroconySzyfr;
    }
    
    private void Analyze () {
        
        szyfr = new HashMap<>();
        
        // podziel wiadomość na słowa
        String[] slowa = zaszyfrowanaWiadomosc.split(" ");
        int[] dlugosciSlow = new int[slowa.length];
        
        // konwertuj wiadomość na listę długości słów do poszukiwania wzoru
        for (int i = 0; i < slowa.length; i++) {
            dlugosciSlow[i] = slowa[i].length();
        }
        
        for (int i = 0; i < dlugosciSlow.length - 5; i++) {
            if (dlugosciSlow[i] == 7 && dlugosciSlow[i + 1] == 7 && dlugosciSlow[i + 2] == 10 && dlugosciSlow[i + 3] == 1 && dlugosciSlow[i + 4] == 11 && dlugosciSlow[i + 5] == 10) {
                
                // znaleziono podejrzany ciąg
                
                int pozycjaWCiagu = 0;
                
                // od początku do znalezionego elementu
                // zbieraj i dodawaj do siebie liczby żeby określić miejsce w ciągu źródłowym
                for (int j = 0; j < i; j++) {
                    pozycjaWCiagu += dlugosciSlow[j] + 1;
                }
                
                // powtarzaj dla wszystkich znaków
                for (int n = 0; n < wzorzec.length(); n++, pozycjaWCiagu++) {
                    
                    // weź znak
                    if (zaszyfrowanaWiadomosc.charAt(pozycjaWCiagu) == ' ' || zaszyfrowanaWiadomosc.charAt(pozycjaWCiagu) == ',')
                        continue; // pomiń spacje i przecinki
                    
                    // sprawdź czy dany znak można zakodować i czy zgadza się ze wzorcem
                    if (szyfr.containsKey(zaszyfrowanaWiadomosc.charAt(pozycjaWCiagu))) {
                        if (szyfr.get(zaszyfrowanaWiadomosc.charAt(pozycjaWCiagu)) != wzorzec.charAt(n))
                            break;
                    } else {
                        szyfr.put(zaszyfrowanaWiadomosc.charAt(pozycjaWCiagu), wzorzec.charAt(n));
                    }
                    
                }
                
                if (szyfr.size() == 22) {
                    // jeśli udało się dotrzeć do końca wzorca bez błędów koniec programu
                    return;
                } else {
                    // jeśli nie udało się dotrzeć do końca wzorca
                    szyfr = new HashMap<>();
                }
                
            }
        }
        
    }
    
}
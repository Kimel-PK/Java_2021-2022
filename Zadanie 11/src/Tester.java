public class Tester {
	
	public static void main(String[] args) {
		
		PasswordCracker PC = new PasswordCracker();
		
		for (int i = 0; i < 100; i++) {
			// niestety nie mam plików serwera więc nie da się tego przetestować w repo
			String haslo = PC.getPassword ("localhost", 8080);
			System.out.println("Odgadnięte hasło: " + haslo);
		}
		
	}
}

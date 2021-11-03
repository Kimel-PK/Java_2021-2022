public class Tester {
	
	public static void main (String[] args) {
		
		Decrypter decrypter = new Decrypter();
		
		decrypter.setInputText("Dxb80 1*9c3ó8\n1023*4Ł f*30{*, 5678x!xW**    \t    \t   \n*\n}!#x8W470{* s7x6xw4!9i  ");
		
		System.out.println("szyfr:");
		System.out.println (decrypter.getCode());
		
		System.out.println("odwrocony szyfr:");
		System.out.println (decrypter.getDecode());
		
    }
	
}

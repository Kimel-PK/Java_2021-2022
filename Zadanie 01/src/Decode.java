class Decode extends DecoderInterface {
	
	int X = -1;
	int licznik = -1;
	String wynik = "";
	
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
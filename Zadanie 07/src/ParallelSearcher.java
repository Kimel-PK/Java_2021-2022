class ParallelSearcher implements ParallelSearcherInterface {
	
	double sumaWartosci;
	
	public void set(HidingPlaceSupplierSupplier supplier) {
		
		HidingPlaceSupplier zestawSkrytek = supplier.get(0);
		
		while (zestawSkrytek != null) {
			
			Thread[] watki = new Thread[zestawSkrytek.threads()];
			
			// utworz nowe watki przeszukujace skrytki
			for (int i = 0; i < watki.length; i++) {
				watki[i] = new Lurker(zestawSkrytek, this);
				watki[i].start();
			}
			
			// poczekaj na zakonczenie wszystkich watkow
			for (int i = 0; i < watki.length; i++) {
				try {
					watki[i].join();
				} catch (Exception e) {}
			}
			
			// wyslij wynik i odbierz nowy zestaw skrytek
			zestawSkrytek = supplier.get(sumaWartosci);
			sumaWartosci = 0;
			
		}
	}
	
	class Lurker extends Thread {
	
		HidingPlaceSupplier zestawSkrytek;
		Object blokada;
		
		public Lurker (HidingPlaceSupplier _zestawSkrytek, Object _blokada) {
			zestawSkrytek = _zestawSkrytek;
			blokada = _blokada;
		}
		
		public void run () {
			
			HidingPlaceSupplier.HidingPlace skrytka = zestawSkrytek.get();
			while (skrytka != null) {
				
				synchronized(blokada) {
					if (skrytka.isPresent()) {
						sumaWartosci += skrytka.openAndGetValue();
					}
				}
				
				skrytka = zestawSkrytek.get();
				
			}
			
		}
		
	}
	
}
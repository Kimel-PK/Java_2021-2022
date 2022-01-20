// Autor: ignis

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class Sup implements Supplier<Pieniadz> {
	Pieniadz[] array;
	int i = 0;

	public Sup(Pieniadz[] array) {
		this.array = array;
	}

	@Override
	public Pieniadz get() {
		try {
			return array[i++];
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

}

class Rozmieniacz implements RozmieniaczInterface {
	// list of serial numbers processed by rozmieniacz - they should not appear
	// anywhere or the Kasjer duped money
	public List<Integer> serialNumbers = new LinkedList<Integer>();
	List<Integer> nominaly = Arrays.stream(Nominal.values()).map(n -> n.wartość()).collect(Collectors.toList());
	Random rand = new Random(2137);

	/**
	 * Maps pieniadze to serial numbers
	 * 
	 * @param pieniadze
	 * @return
	 */
	public static List<Integer> MapPtoSN(List<Pieniadz> pieniadze) {
		return pieniadze.stream().map(Pieniadz::numerSeryjny).collect(Collectors.toList());
	}

	// same as below but automatic mapping Pieniadz::numerSeryjny
	public void serialNumberCheck2(List<Pieniadz> serials, List<Pieniadz> anyExtraSerials) {
		serialNumberCheck(MapPtoSN(serials), MapPtoSN(anyExtraSerials));
	}

	/**
	 * Checks list of serial numbers for duplicates and also against second list and
	 * internal list.
	 * 
	 * @param serials				 list of serial numbers to check
	 * @param anyExtraSerials extra list to check against
	 */
	public void serialNumberCheck(List<Integer> serials, List<Integer> anyExtraSerials) {
		if (serials.stream().collect(Collectors.toSet()).size() < serials.size())
			throw new RuntimeException("Found duplicate serial numbers in the list");

		List<Integer> common = new ArrayList<Integer>(this.serialNumbers.size() + anyExtraSerials.size());
		Collections.copy(common, this.serialNumbers);
		if (anyExtraSerials != null)
			common.addAll(anyExtraSerials);

		common.retainAll(serials);
		if (common.size() > 0)
			throw new RuntimeException("Found " + common.size() + " duplicate serial numbers in the list");

		// System.out.println("No duplicate serial numbers found");
	}

	@Override
	public List<Pieniadz> rozmien(Pieniadz coin) {
		if (serialNumbers.contains(coin.numerSeryjny()))
			throw new RuntimeException("Serial number " + coin.numerSeryjny() + " appeared twice - money was duped");

		if (coin.wartosc() == 1)
			throw new RuntimeException("Spróbowano rozmienić pieniądz ZŁ1");

		if (!coin.czyMozeBycRozmieniony())
			throw new RuntimeException("Spróbowano rozmienić pieniądz nierozmienialny");

		List<Pieniadz> result = new LinkedList<Pieniadz>();

		int bound = nominaly.indexOf(coin.wartosc());
		int remainingValue = coin.wartosc();

		// randomly generate smaller nominals
		while (remainingValue > 0) {
			int index = rand.nextInt(bound);
			int val = nominaly.get(index);
			// if nominal to high, set it as limit for next random
			if (val > remainingValue) {
				bound = index;
			} else {
				result.add(new Pieniadz(Nominal.values()[index], Rozmienialnosc.TAK));
				remainingValue -= val;
			}
		}

		return result;
	}

}

public class Tester {
	public static void main(String[] args) {
		
		var r = new Rozmieniacz();
		var k = new Kasjer();
		k.dostępDoRozmieniacza(r);
		
		// test 1:
		System.out.println("Test nr. 1");
		k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł2, Rozmienialnosc.TAK) }));
		List<Pieniadz> pieniadze = List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE), new Pieniadz (Nominal.Zł10, Rozmienialnosc.TAK));
		var reszta = k.rozlicz(19, pieniadze);
		var kasa = k.stanKasy();
		r.serialNumberCheck2(kasa, reszta);
		if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 1)
			throw new RuntimeException("Nieprawidlowa reszta");
		if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 21)
			throw new RuntimeException("Nieprawidlowa kasa");
		
		// test 2:
		System.out.println("Test nr. 2");
		k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE) }));
		pieniadze = List.of(new Pieniadz(Nominal.Zł50, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł20, Rozmienialnosc.TAK));
		reszta = k.rozlicz(65, pieniadze);
		kasa = k.stanKasy();
		r.serialNumberCheck2(kasa, reszta);
		if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 5)
			throw new RuntimeException("Nieprawidlowa reszta");
		if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 70)
			throw new RuntimeException("Nieprawidlowa kasa");
		
		// test 3:
		System.out.println("Test nr. 3");
		k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł1, Rozmienialnosc.TAK) }));
		pieniadze = List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.TAK));
		reszta = k.rozlicz(9, pieniadze);
		kasa = k.stanKasy();
		r.serialNumberCheck2(kasa, reszta);
		if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 1)
			throw new RuntimeException("Nieprawidlowa reszta");
		if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 10)
			throw new RuntimeException("Nieprawidlowa kasa");
		
		// test 4:
		System.out.println("Test nr. 4");
		k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł1, Rozmienialnosc.TAK) }));
		pieniadze = List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.TAK));
		reszta = k.rozlicz(10, pieniadze);
		kasa = k.stanKasy();
		r.serialNumberCheck2(kasa, reszta);
		if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 0)
			throw new RuntimeException("Nieprawidlowa reszta");
		if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 11)
			throw new RuntimeException("Nieprawidlowa kasa");
		
		// test 5:
		System.out.println("Test nr. 5");
		k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł2, Rozmienialnosc.TAK) }));
		pieniadze = List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.TAK));
		reszta = k.rozlicz(9, pieniadze);
		kasa = k.stanKasy();
		r.serialNumberCheck2(kasa, reszta);
		if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 1)
			throw new RuntimeException("Nieprawidlowa reszta");
		if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 11)
			throw new RuntimeException("Nieprawidlowa kasa");
		
		// test 6:
		System.out.println("Test nr. 6");
		k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł2, Rozmienialnosc.TAK) }));
		pieniadze = List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE));
		reszta = k.rozlicz(9, pieniadze);
		kasa = k.stanKasy();
		r.serialNumberCheck2(kasa, reszta);
		if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 11)
			throw new RuntimeException("Nieprawidlowa reszta");
		if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 1)
			throw new RuntimeException("Nieprawidlowa kasa");
			
		System.out.println("All tests passed");
	}
}

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
   * @param serials         list of serial numbers to check
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

    // test 1: 35R + 35NR | cena 69
    List<Pieniadz> pieniadze = List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.TAK), new Pieniadz(Nominal.Zł20, Rozmienialnosc.TAK),
        new Pieniadz(Nominal.Zł5, Rozmienialnosc.TAK), new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł20, Rozmienialnosc.NIE),
        new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE));
    var reszta = k.rozlicz(69, pieniadze);
    var kasa = k.stanKasy();
    
    r.serialNumberCheck2(kasa, reszta);
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 1)
      throw new RuntimeException("Nieprawidlowa reszta");
    if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 69)
      throw new RuntimeException("Nieprawidlowa kasa");

    // test 2: 500NR | cena 1
    k = new Kasjer();
    k.dostępDoRozmieniacza(r);
    k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł500, Rozmienialnosc.TAK) }));
    pieniadze = List.of(new Pieniadz(Nominal.Zł500, Rozmienialnosc.NIE));
    reszta = k.rozlicz(1, pieniadze);
    kasa = k.stanKasy();
    r.serialNumberCheck2(kasa, reszta);
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 499 + 500)
      throw new RuntimeException("Nieprawidlowa reszta");
    if (reszta.stream().filter(p -> !p.czyMozeBycRozmieniony()).findAny().orElseThrow().numerSeryjny() != pieniadze.get(0).numerSeryjny())
      throw new RuntimeException("Zwrocony pieniadz NR ma inny numer seryjny");
    if (kasa.size() != 1 || !kasa.get(0).equals(new Pieniadz(Nominal.Zł1, Rozmienialnosc.TAK)))
      throw new RuntimeException("Nieprawidlowa kasa");

    // test 3: te 3 testy z przykładów
    List<Pieniadz> razem = new LinkedList<Pieniadz>();
    reszta = k.rozlicz(15, List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.TAK), new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE)));
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 5)
      throw new RuntimeException("Nieprawidlowa reszta");
    razem.addAll(reszta);
    reszta = k.rozlicz(25, List.of(new Pieniadz(Nominal.Zł20, Rozmienialnosc.TAK), new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE)));
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 5)
      throw new RuntimeException("Nieprawidlowa reszta");
    razem.addAll(reszta);
    var nroz = new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE);
    reszta = k.rozlicz(25, List.of(new Pieniadz(Nominal.Zł20, Rozmienialnosc.NIE), nroz));
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 5 + 10)
      throw new RuntimeException("Nieprawidlowa reszta");
    if (reszta.stream().filter(p -> !p.czyMozeBycRozmieniony()).findAny().orElseThrow().numerSeryjny() != nroz.numerSeryjny())
      throw new RuntimeException("Zwrocony pieniadz NR ma inny numer seryjny");
    razem.addAll(reszta);
    r.serialNumberCheck2(k.stanKasy(), razem);

    // test 4: wydawanie NR
    k = new Kasjer();
    k.dostępDoRozmieniacza(r);
    k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE),
        new Pieniadz(Nominal.Zł5, Rozmienialnosc.TAK), new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE) }));
    reszta = k.rozlicz(5, List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE)));
    kasa = k.stanKasy();
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 15)
      throw new RuntimeException("Nieprawidlowa reszta");
    if (!kasa.stream().allMatch(p -> !p.czyMozeBycRozmieniony()))
      throw new RuntimeException("Z kasy został wydany NR, a można było wydać normalny");
    r.serialNumberCheck2(kasa, reszta);
    // cz2
    reszta = k.rozlicz(5, List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE)));
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 15)
      throw new RuntimeException("Nieprawidlowa reszta");
    r.serialNumberCheck2(k.stanKasy(), reszta);

    // test 5: poprawne dobranie NR
    k = new Kasjer();
    k.dostępDoRozmieniacza(r);
    k.dostępDoPoczątkowegoStanuKasy(new Sup(new Pieniadz[] { new Pieniadz(Nominal.Zł1, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł2, Rozmienialnosc.NIE),
        new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł5, Rozmienialnosc.NIE) }));
    reszta = k.rozlicz(9, List.of(new Pieniadz(Nominal.Zł20, Rozmienialnosc.NIE)));
    kasa = k.stanKasy();
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 11 + 20)
      throw new RuntimeException("Nieprawidlowa reszta");
    if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 2)
      throw new RuntimeException("Nieprawidlowa kasa");
    r.serialNumberCheck2(kasa, reszta);

    // test 6: brak reszty
    reszta = k.rozlicz(32, List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł20, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł2, Rozmienialnosc.TAK)));
    kasa = k.stanKasy();
    if (reszta.size() != 0)
      throw new RuntimeException("Nieprawidlowa reszta");
    if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 34)
      throw new RuntimeException("Nieprawidlowa kasa");
    r.serialNumberCheck2(kasa, reszta);

    // test 7: hybrydowa reszta - trzeba wydać 4zł jako 2zl + 2NR
    reszta = k.rozlicz(6, List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE)));
    kasa = k.stanKasy();
    if (reszta.stream().mapToInt(Pieniadz::wartosc).sum() != 4 + 10)
      throw new RuntimeException("Nieprawidlowa suma reszty");
    if (!reszta.containsAll(List.of(new Pieniadz(Nominal.Zł10, Rozmienialnosc.NIE), new Pieniadz(Nominal.Zł2, Rozmienialnosc.NIE))))
      throw new RuntimeException("W reszcie nie ma oczekiwanych monet NR");
    if (kasa.stream().mapToInt(Pieniadz::wartosc).sum() != 30)
      throw new RuntimeException("Nieprawidlowa kasa");
    r.serialNumberCheck2(kasa, reszta);

    System.out.println("All tests passed");
  }
}

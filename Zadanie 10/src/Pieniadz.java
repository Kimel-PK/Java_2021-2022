
public class Pieniadz {
	private final Nominal nominal;
	private final Rozmienialnosc rozmienialnosc;
	private final int numer;
	private static int licznik;

	public Pieniadz(Nominal nominal, Rozmienialnosc rozmienialnosc) {
		this.nominal = nominal;
		this.rozmienialnosc = rozmienialnosc;
		numer = licznik++;
	}

	public int numerSeryjny() {
		return numer;
	}

	public int wartosc() {
		return nominal.wartość();
	}

	public boolean czyMozeBycRozmieniony() {
		return rozmienialnosc.czyMozeBycRozmieniony();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nominal == null) ? 0 : nominal.hashCode());
		result = prime * result + ((rozmienialnosc == null) ? 0 : rozmienialnosc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Pieniadz))
			return false;
		Pieniadz other = (Pieniadz) obj;
		if (nominal != other.nominal)
			return false;
		if (rozmienialnosc != other.rozmienialnosc)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pieniadz [nominal=" + nominal.wartość() + ", rozmienialnosc=" + rozmienialnosc + "]";
	}
}

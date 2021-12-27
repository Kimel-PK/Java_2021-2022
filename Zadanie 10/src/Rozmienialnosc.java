
public enum Rozmienialnosc {
	TAK (true), NIE (false );
	
	boolean mozeBycRozmieniony;
	
	private Rozmienialnosc( boolean mozeBycRozmieniony ) {
		this.mozeBycRozmieniony = mozeBycRozmieniony;
	}
	
	public boolean czyMozeBycRozmieniony() {
		return mozeBycRozmieniony;
	}
}

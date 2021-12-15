import java.util.HashMap;
import java.util.Map;

class Shop implements ShopInterface {
	
	Map<String, Object> blokady = new HashMap<>();
	Map<String, Integer> _stock = new HashMap<>();
	
	public void delivery(Map<String, Integer> goods) {
		
		for (String towar : goods.keySet()) {
			synchronized (this) {
				if (_stock.get(towar) == null) {
					_stock.put(towar, goods.get(towar));
					if (blokady.get(towar) == null) {
						blokady.put (towar, new Object());
					}
				} else {
					_stock.put(towar, _stock.get(towar) + goods.get(towar));
				}
			}
			synchronized (blokady.get(towar)) {
				blokady.get(towar).notifyAll();
			}
		}
	}
	
	public boolean purchase(String productName, int quantity) {
		
		if (blokady.get(productName) == null) {
			blokady.put (productName, new Object());
		}
		
		synchronized (blokady.get(productName)) {
			
			if (_stock.get(productName) != null && _stock.get(productName) >= quantity) {
				_stock.put (productName, _stock.get(productName) - quantity);
				return true;
			} else {
				try {
					blokady.get(productName).wait();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				if (_stock.get(productName) != null && _stock.get(productName) >= quantity) {
					_stock.put (productName, _stock.get(productName) - quantity);
					return true;
				}
			}
			
			return false;
		}
	}
	
	public Map<String, Integer> stock() {
		return _stock;
	}
	
}

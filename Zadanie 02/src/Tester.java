import java.util.ArrayList;
import java.util.List;

public class Tester {
    
    public static void main (String[] args) {
        
        Loops loops = new Loops();
        
        List<Integer> dolneLimity = new ArrayList<>();
        List<Integer> gorneLimity = new ArrayList<>();
        
        dolneLimity.add(2);
        dolneLimity.add(4);
        dolneLimity.add(6);
        
        gorneLimity.add(3);
        gorneLimity.add(5);
        gorneLimity.add(7);
        
        loops.setLowerLimits(dolneLimity);
        loops.setUpperLimits(gorneLimity);
        
        for (List<Integer> zestaw : loops.getResult()) {
			System.out.print("{");
            for (Integer liczba : zestaw) {
				System.out.print(liczba + ",");
			}
			System.out.print("},");
        }
        
    }
    
}

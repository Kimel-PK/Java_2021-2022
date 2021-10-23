import java.util.ArrayList;
import java.util.List;

public class Tester {
    
    public static void main (String[] args) {
        
        Loops loops = new Loops();
        
        List<Integer> dolneLimity = new ArrayList<>();
        List<Integer> gorneLimity = new ArrayList<>();
        
        dolneLimity.add(-2);
        dolneLimity.add(-4);
        dolneLimity.add(-6);
        
        gorneLimity.add(6);
        gorneLimity.add(8);
        gorneLimity.add(10);
        
        loops.setLowerLimits(dolneLimity);
        loops.setUpperLimits(gorneLimity);
        
        System.out.println(loops.getResult());
        
    }
    
}

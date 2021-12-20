import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.*;

class Start {
	
	JButton przyciskZaladuj;
	JFrame ramka;
	Plotno rysunek;
	
	Graf graf = new Graf();
	List<Wezel> wezly = new LinkedList<> ();
	List<Krawedz> krawedzie = new LinkedList<> ();
	Map<Integer, Float> wagi = new HashMap<> ();
	
	public static void main( String[] argv ) {
		Start paint = new Start();
		
		paint.ramka = new JFrame ("Graf");
		paint.ramka.setSize (500, 500);
		paint.ramka.setLocationRelativeTo(null);
		
		paint.przyciskZaladuj = new JButton ("Load");
		paint.przyciskZaladuj.addActionListener (paint.new ZaladujDane ());
		
		paint.rysunek = new Plotno(paint.graf, paint.wezly, paint.krawedzie, paint.wagi);
		
		paint.ramka.getContentPane ().add (BorderLayout.NORTH, paint.przyciskZaladuj);
		paint.ramka.getContentPane ().add (BorderLayout.CENTER, paint.rysunek);
		
		paint.ramka.setVisible (true);
		paint.ramka.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
	
	class ZaladujDane implements ActionListener {
		public void actionPerformed (ActionEvent e) {
	        
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			
			int returnValue = jfc.showOpenDialog(null);
			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				PobierzDane(jfc.getSelectedFile().getAbsolutePath());
				// System.out.println("Odczytano plik");
				rysunek.setBackground(Color.white);
				rysunek.repaint();
			}
		}
	}
	
	void PobierzDane(String plikDanych) {
		
		wezly.clear();
		krawedzie.clear();
		wagi.clear();
		
		Scanner sc = null;
		
		try {
			sc = new Scanner(new File (plikDanych));
		} catch (Exception e) {}
		
		int iloscOperacji = sc.nextInt();
		int x;
		int y;
		int waga;
		
		// odczytaj wezly
		for (int i = 0; i < iloscOperacji; i++) {
			
			x = sc.nextInt();
			y = sc.nextInt();
			
			if (i == 0) {
				graf.lewa = x;
				graf.prawa = x;
				graf.gora = y;
				graf.dol = y;
			}
			
			// okresl bounding box
			if (x < graf.lewa) {
				graf.lewa = x;
			} else if (x > graf.prawa) {
				graf.prawa = x;
			}
			
			if (y < graf.dol) {
				graf.dol = y;
			} else if (y > graf.gora) {
				graf.gora = y;
			}
			
			wezly.add(new Wezel(x, y));
		}
		
		// odczytaj krawedzie
		iloscOperacji = sc.nextInt();
		for (int i = 0; i < iloscOperacji; i++) {
			
			x = sc.nextInt();
			y = sc.nextInt();
			waga = sc.nextInt();
			
			krawedzie.add(new Krawedz(wezly.get(x - 1), wezly.get(y - 1), waga));
		}
		
		for (Wezel wezel : wezly) {
			wezel.x -= graf.lewa;
			wezel.y -= graf.dol;
		}
		
		graf.prawa -= graf.lewa;
		graf.gora -= graf.dol;
		graf.lewa = 0;
		graf.dol = 0;
		
		graf.prawa--;
		
		// symetria punktow
		for (Wezel wezel : wezly) {
			wezel.y -= (int)(2.0 * ((double)wezel.y - (double)(graf.gora - graf.dol) / 2.0));
		}
		
		graf.gora--;
		
		/*
		System.out.println("Bounding Box:");
		System.out.println("G: " + graf.gora);
		System.out.println("P: " + graf.prawa);
		System.out.println("D: " + graf.dol);
		System.out.println("L: " + graf.lewa);
		System.out.println("Proporcje: " + graf.proporcje());
		*/
		
		sc.close();
		
		// okresl znormalizowane wagi
		int wagaMax = 0;
		
		for (Krawedz krawedz : krawedzie) {
			if (krawedz.waga > wagaMax)
				wagaMax = krawedz.waga;
		}
		
		for (Krawedz krawedz : krawedzie) {
			wagi.put(krawedz.waga, (float)(krawedz.waga / (double)wagaMax * 10));
		}
		
	}
	
}

class Plotno extends JPanel {
	
	Graf graf;
	List<Wezel> wezly;
	List<Krawedz> krawedzie;
	Map<Integer, Float> wagi;
	
	public Plotno (Graf _graf, List<Wezel> _wezly, List<Krawedz> _krawedzie, Map<Integer, Float> _wagi) {
		graf = _graf;
		wezly = _wezly;
		krawedzie = _krawedzie;
		wagi = _wagi;
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor (Color.black);
		double wspolczynnikX = 1;
		double wspolczynnikY = 1;
		int margin_x = 0;
		int margin_y = 0;
		
		float gruboscWezla = 2;
		int promienWezla = 20;
		
		double wspolczynnikKrawedz = 1;
		
		if (getWidth() / (double)getHeight() < graf.proporcje()) {
			wspolczynnikX = getWidth() * 0.9 / (double)(graf.prawa + 1);
			margin_x = (int)((double)getWidth() * 0.1 / 2.0);
			
			wspolczynnikY = (getWidth() / graf.proporcje()) * 0.9 / (double)(graf.gora + 1);
			margin_y = (int)((double)(getHeight() - (getWidth() / graf.proporcje())) / 2.0) + (int)((double)getHeight() * 0.1 / 2.0);
			
			gruboscWezla = (float)(getWidth() * 0.005);
			promienWezla = (int)(getWidth() * 0.05);
			
			wspolczynnikKrawedz = (double)(getWidth() * 0.002);
		} else {
			wspolczynnikX = (getHeight() * graf.proporcje()) * 0.9 / (double)(graf.prawa + 1);
			margin_x = (int)((double)(getWidth() - (getHeight() * graf.proporcje())) / 2.0) + (int)((double)getWidth() * 0.1 / 2.0);
			
			wspolczynnikY = getHeight() * 0.9 / (double)(graf.gora + 1);
			margin_y = (int)((double)getHeight() * 0.1 / 2.0);
			
			gruboscWezla = (float)(getHeight() * 0.005);
			promienWezla = (int)(getHeight() * 0.05);
			
			wspolczynnikKrawedz = (double)(getHeight() * 0.002);
		}
		
		for (Wezel wezel : wezly) {
			g2.setStroke(new BasicStroke (gruboscWezla));
			g2.drawOval ((int)(wspolczynnikX * wezel.x) + margin_x - promienWezla / 2, (int)(wspolczynnikY * wezel.y) + margin_y - promienWezla / 2, promienWezla, promienWezla);
		}
		for (Krawedz krawedz : krawedzie) {
			int p1_x = (int)(wspolczynnikX * krawedz.wezelPocz.x) + margin_x;
			int p1_y = (int)(wspolczynnikY * krawedz.wezelPocz.y) + margin_y;
			int p2_x = (int)(wspolczynnikX * krawedz.wezelKon.x) + margin_x;
			int p2_y = (int)(wspolczynnikY * krawedz.wezelKon.y) + margin_y;
			g2.setStroke(new BasicStroke (wagi.get (krawedz.waga) * (float)wspolczynnikKrawedz));
			g2.drawLine(p1_x, p1_y, p2_x, p2_y);
		}
		
	}
	
}

class Graf {
	
	int lewa;
	int prawa;
	int gora;
	int dol;
	public double proporcje () {
		return (double)(prawa - lewa) / (double)(gora - dol);
	}
	
}

class Wezel {
	public int x;
	public int y;
	
	public Wezel (int _x, int _y) {
		x = _x;
		y = _y;
	}
}

class Krawedz {
	Wezel wezelPocz;
	Wezel wezelKon;
	int waga;
	
	public Krawedz (Wezel _wezelPocz, Wezel _wezelKon, int _waga) {
		wezelPocz = _wezelPocz;
		wezelKon = _wezelKon;
		waga = _waga;
	}
}
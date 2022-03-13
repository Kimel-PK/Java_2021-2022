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
	
	public static void main( String[] argv ) {
		Start paint = new Start();
		
		paint.ramka = new JFrame ("Graf");
		paint.ramka.setSize (500, 500);
		paint.ramka.setLocationRelativeTo(null);
		
		paint.przyciskZaladuj = new JButton ("Load");
		paint.przyciskZaladuj.addActionListener (paint.new ZaladujDane ());
		
		paint.rysunek = new Plotno(paint.graf);
		
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
				rysunek.repaint();
			}
		}
	}
	
	void PobierzDane(String plikDanych) {
		
		int lewa = 0;
		int prawa = 0;
		int gora = 0;
		int dol = 0;
		
		graf.wyczysc();
		
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
				lewa = x;
				prawa = x;
				gora = y;
				dol = y;
			}
			
			// okresl bounding box
			if (x < lewa) {
				lewa = x;
			} else if (x > prawa) {
				prawa = x;
			}
			
			if (y < dol) {
				dol = y;
			} else if (y > gora) {
				gora = y;
			}
			
			graf.wezly.add(new Punkt(x, y));
		}
		
		// odczytaj krawedzie
		iloscOperacji = sc.nextInt();
		for (int i = 0; i < iloscOperacji; i++) {
			
			x = sc.nextInt();
			y = sc.nextInt();
			waga = sc.nextInt();
			
			graf.krawedzie.add(new Krawedz(graf.wezly.get(x - 1), graf.wezly.get(y - 1), waga));
		}
		
		graf.srodek = new Punkt ((prawa - lewa) / 2.0 + lewa, (gora - dol) / 2.0 + dol);
		graf.szerokosc = prawa - lewa;
		graf.wysokosc = gora - dol;
		
		sc.close();
		
		// okresl znormalizowane wagi
		int wagaMax = 0;
		
		for (Krawedz krawedz : graf.krawedzie) {
			if (krawedz.waga > wagaMax)
				wagaMax = krawedz.waga;
		}
		
		for (Krawedz krawedz : graf.krawedzie) {
			graf.wagi.put(krawedz.waga, (float)(krawedz.waga / (double)wagaMax * 10));
		}
		
	}
	
}

class Plotno extends JPanel {
	
	Graf graf;
	
	public Plotno (Graf _graf) {
		graf = _graf;
	}
	
	Punkt ObliczPozycjeWOknie (Punkt p1, double sX, double sY, double wspolczynnik) {
		return new Punkt ((int)((p1.x - sX) * wspolczynnik + getWidth() / 2.0), (int)((p1.y - sY) * (-wspolczynnik) + getHeight() / 2.0));
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setColor (Color.black);
		double wspolczynnik = 1;
		
		float gruboscWezla = 2;
		int promienWezla = 20;
		
		double wspolczynnikKrawedz = 1;
		
		if (getWidth() / (double)getHeight() < graf.proporcje()) {
			wspolczynnik = getWidth() / (double)graf.szerokosc;
		} else {
			wspolczynnik = getHeight() / (double)graf.wysokosc;
		}
		
		wspolczynnik *= 0.9;
		
		gruboscWezla = (float)(wspolczynnik * 0.03);
		promienWezla = (int)(wspolczynnik * 0.3);
		
		wspolczynnikKrawedz = wspolczynnik * 0.01;
		
		for (Punkt wezel : graf.wezly) {
			
			Punkt punkt = ObliczPozycjeWOknie (wezel, graf.srodek.x, graf.srodek.y, wspolczynnik);
			
			g2.setStroke(new BasicStroke (gruboscWezla));
			g2.drawOval ((int)(punkt.x - promienWezla / 2.0), (int)(punkt.y - promienWezla / 2.0), promienWezla, promienWezla);
		}
		
		for (Krawedz krawedz : graf.krawedzie) {
			
			Punkt poczatek = ObliczPozycjeWOknie (krawedz.wezelPocz, graf.srodek.x, graf.srodek.y, wspolczynnik);
			Punkt koniec = ObliczPozycjeWOknie (krawedz.wezelKon, graf.srodek.x, graf.srodek.y, wspolczynnik);
			
			g2.setStroke(new BasicStroke (graf.wagi.get (krawedz.waga) * (float)wspolczynnikKrawedz));
			g2.drawLine((int)poczatek.x, (int)poczatek.y, (int)koniec.x, (int)koniec.y);
		}
		
	}
	
}

class Graf {
	int szerokosc;
	int wysokosc;
	Punkt srodek;
	
	List<Punkt> wezly = new LinkedList<> ();
	List<Krawedz> krawedzie = new LinkedList<> ();
	Map<Integer, Float> wagi = new HashMap<> ();
	
	double proporcje () {
		return szerokosc / (double)wysokosc;
	}
	
	void wyczysc () {
		szerokosc = 0;
		wysokosc = 0;
		srodek = null;
		wezly.clear();
		krawedzie.clear();
		wagi.clear();
	}
}

class Krawedz {
	Punkt wezelPocz;
	Punkt wezelKon;
	int waga;
	
	public Krawedz (Punkt _wezelPocz, Punkt _wezelKon, int _waga) {
		wezelPocz = _wezelPocz;
		wezelKon = _wezelKon;
		waga = _waga;
	}
}

class Punkt {
	public double x;
	public double y;
	
	public Punkt (double _x, double _y) {
		x = _x;
		y = _y;
	}
}
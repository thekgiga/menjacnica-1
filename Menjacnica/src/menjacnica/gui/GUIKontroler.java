package menjacnica.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler extends JFrame {

	private JPanel contentPane;
	private static MenjacnicaInterface menjacnica;
	private static MenjacnicaGUI menjacnicaGui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica = new Menjacnica();
					menjacnicaGui = new MenjacnicaGUI();
					menjacnicaGui.setVisible(true);
					menjacnicaGui.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGui,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnicaGui,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGui);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGui);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaGui.prikaziSveValute(menjacnica.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(menjacnicaGui);
		prozor.setLocationRelativeTo(menjacnicaGui.getContentPane());
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI() {
		if (menjacnicaGui.getList().getSelectedValue() != null) {
			ObrisiKursGUI prozor = new ObrisiKursGUI(menjacnicaGui,
					(Valuta) (menjacnicaGui.getList().getSelectedValue()));
			prozor.setLocationRelativeTo(menjacnicaGui.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, Integer sifra,
			String prodajni, String kupovni, String srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);
			
			// Osvezavanje glavnog prozora
			menjacnicaGui.prikaziSveValute(menjacnica.vratiKursnuListu());
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziValutu(JTextField prodajni, JTextField kupovni, JTextField skraceniNaziv,Valuta valuta){
		prodajni.setText(""+valuta.getProdajni());
		kupovni.setText(""+valuta.getKupovni());
		skraceniNaziv.setText(valuta.getSkraceniNaziv());
	}
	
	public static void izvrsiZamenu(Valuta valuta,JRadioButton prodaja, JTextField iznos,JTextField konacni) {
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,
							prodaja.isSelected(), 
							Double.parseDouble(iznos.getText()));
		
			konacni.setText(""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	public static void prikaziValutu(JTextField naziv, JTextField skraceniNaziv,
			JTextField sifra, JTextField prodajni, JTextField kupovni,
			JTextField srednji,Valuta valuta) {
		// Prikaz podataka o valuti
		naziv.setText(valuta.getNaziv());
		skraceniNaziv.setText(valuta.getSkraceniNaziv());
		sifra.setText(""+valuta.getSifra());
		prodajni.setText(""+valuta.getProdajni());
		kupovni.setText(""+valuta.getKupovni());
		srednji.setText(""+valuta.getSrednji());				
	}

	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			menjacnicaGui.prikaziSveValute(menjacnica.vratiKursnuListu());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Create the frame.
	 */
	public GUIKontroler() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}

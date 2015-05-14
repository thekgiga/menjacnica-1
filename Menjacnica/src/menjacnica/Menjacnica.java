package menjacnica;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import menjacnica.sistemskeoperacije.SODodajValutu;
import menjacnica.sistemskeoperacije.SOIzvrsiTransakciju;
import menjacnica.sistemskeoperacije.SOObrisiValutu;
import menjacnica.sistemskeoperacije.SOSacuvajUFajl;
import menjacnica.sistemskeoperacije.SOUcitajIzFajla;
import menjacnica.sistemskeoperacije.SOVratiKursnuListu;

public class Menjacnica implements MenjacnicaInterface {

	@Override
	public void dodajValutu(Valuta valuta) {
		SODodajValutu.dodajValutu(valuta, kursnaLista);

	}

	@Override
	public void obrisiValutu(Valuta valuta) {
		SOObrisiValutu.obrisiValutu(valuta, kursnaLista);
	}

	@Override
	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos) {
		return SOIzvrsiTransakciju.izvrsiTransakciju(valuta, prodaja, iznos);
	}

	@Override
	public LinkedList<Valuta> vratiKursnuListu() {
		return SOVratiKursnuListu.kursnaLista(kursnaLista);
	}

	@Override
	public void ucitajIzFajla(String putanja) {
		SOUcitajIzFajla.ucitajIzFajal(putanja, kursnaLista);
	}

	@Override
	public void sacuvajUFajl(String putanja) {
		SOSacuvajUFajl.sacuvajUFajl(putanja, kursnaLista);
	}

	private LinkedList<Valuta> kursnaLista = new LinkedList<Valuta>();

}

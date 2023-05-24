package jordi.sanchez.javafxprojecte;

import pkgFitxers.Fitxers;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@SuppressWarnings("serial")
public class Nevera implements Serializable {

    //<editor-fold desc="PROPIETATS">
    private int id;
    private String nom;
    private int preu;
    private String descripcio;
    private String dataCaducitat;

    //</editor-fold>

    static final String fitxerCSV = "nevera.csv";
    static final String fitxerBin = "nevera.dat";
    static final Fitxers f = new Fitxers();

    //<editor-fold desc="CONSTRUCTORS">
    public Nevera() {
        this.id=System.identityHashCode(this);
    }
    public Nevera(String nom, int preu,String descripcio,  String dataCaducitat) {
        this.id=System.identityHashCode(this);

        this.nom = nom;
        this.preu = preu;
        this.descripcio = descripcio;
        this.dataCaducitat = dataCaducitat;
    }




    //</editor-fold>


    //<editor-fold desc="GETTERS">
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getPreu() {
        return preu;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getDataCaducitat() {
        return dataCaducitat;
    }
    //</editor-fold>

    //<editor-fold desc="METODES">
    public void guardar() throws IOException {
//        String text;
//        text=getNom()+";"+getCognom()+";"+getEdat()+";"+getSou();
//        f.escriuTextFitxer(fitxerCSV, this.toString(), true);
        f.escriuObjecteFitxer(this,fitxerBin, true);
    }
    public void modificarPersona() throws IOException, InterruptedException {
        Nevera[] persones = retornaPersones();
        boolean afegir = false;
        boolean trobat = false;

        for (int i = 0; i < persones.length; i++) {
            // sobreescrivim el fitxer excloent la persona a modificar
            if (!persones[i].getNom().equals(this.nom) &&
                    !persones[i].getNom().equals(this.nom) || trobat) {
                f.escriuTextFitxer(fitxerBin, persones[i].toString(), afegir);
                afegir = true;
            } else {
                f.escriuTextFitxer(fitxerBin, this.toString(), afegir);
                afegir = true;
                trobat = true;
            }
        }
    }

    @Deprecated
    public Nevera[] retornaPersones() throws IOException, InterruptedException {

        String persones = f.retornaContingutFitxer(fitxerBin);

        String[] files = persones.split("\n");
        int cont = files.length;


        Nevera[] arrayPersones = new Nevera[cont];


        for (int i = 0; i < files.length; i++) {
            String[] dades = files[i].split(";");
            if (dades.length >= 4) {
                Nevera p1 = new Nevera(
                        dades[0],
                        Integer.parseInt(dades[1]),
                        dades[2],
                        dades[3]);

                arrayPersones[i] = p1;
            }
        }
        return arrayPersones;
    }
    public List<Nevera> retornaLlistaPersones() throws IOException, ClassNotFoundException, InterruptedException {
        List<Object> contingut = f.retornaFitxerObjecteEnLlista(fitxerBin);
        List<Nevera> llista = new ArrayList<>();

        for (Object obj : contingut) {
            Nevera nevera = (Nevera) obj;
            llista.add(nevera);
        }

        return llista;
    }


    public List<Nevera> cercarPerNom(String nomACercar) throws IOException, InterruptedException, ClassNotFoundException {
        List<Object> contingut = f.retornaFitxerObjecteEnLlista(fitxerBin);

        List<Nevera> personesTrobades = new ArrayList<>();

        for (Object obj : contingut) {
            Nevera nevera = (Nevera) obj;
            if (nevera.getNom().equals(nomACercar)) {
                personesTrobades.add(nevera);
            }
        }

        return personesTrobades;
    }

    //    public String getFitxer() {
//        return fitxerBin;
//    }
    public void eliminaPersona(String nom) throws IOException, InterruptedException, ClassNotFoundException {
        List<Nevera> persones = retornaLlistaPersones();
        boolean trobat = false;

        Iterator<Nevera> iterator = persones.iterator();
        while (iterator.hasNext()) {
            Nevera persona = iterator.next();
            if (persona.getNom().equals(nom)) {
                iterator.remove();
                trobat = true;
                break;
            }
        }

        if (trobat) {
            f.escriuObjecteFitxer(persones, fitxerBin, false);
        }
    }


    public List<Nevera> retornaLlibresFitxerBinariEnLlista() throws IOException, InterruptedException, ClassNotFoundException{
        Fitxers f=new Fitxers();
        List<Object>objs=f.retornaFitxerObjecteEnLlista(fitxerBin);
        List<Nevera>aliments=converteixALlibre(objs);
        return aliments;
    }
    private List<Nevera> converteixALlibre (List<Object> lObjectes) {
        List<Nevera> aliments = new ArrayList<>();
        Nevera nev = new Nevera();

        for (Object obj : lObjectes) {
            nev = (Nevera) obj;
            aliments.add(nev);
        }
        return aliments;
    }

    @Override
    public String toString() {
        return nom + ";" + preu + ";" + descripcio + ";" + dataCaducitat;
    }
    //</editor-fold>

}

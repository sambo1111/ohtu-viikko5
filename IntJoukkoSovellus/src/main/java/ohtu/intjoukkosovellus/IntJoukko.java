package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] joukonAlkiot;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        joukonAlkiot = new int[KAPASITEETTI];
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti >= 0) {
            joukonAlkiot = new int[kapasiteetti];
            alkioidenLkm = 0;
            this.kasvatuskoko = OLETUSKASVATUS;
        }
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");//heitin vaan jotain :D
        }
        joukonAlkiot = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int lisattavaLuku) {
        if (!kuuluu(lisattavaLuku)) {
            if (alkioidenLkm == 0) {
                joukonAlkiot[0] = lisattavaLuku;
            } else {
                joukonAlkiot[alkioidenLkm] = lisattavaLuku;
            }
            alkioidenLkm++;
            kasvataTaulukonKokoaJosTaynna();
            return true;
        }
        return false;
    }

    private boolean onkoTaynna() {
        if (alkioidenLkm % joukonAlkiot.length == 0) {
            return true;
        }
        return false;
    }

    private static void lisaaKaikki(IntJoukko mihinLisataan, int[]... taulukotJoistaLisataan) {
        for (int[] taulukko : taulukotJoistaLisataan) {
            for (int i = 0; i < taulukko.length; i++) {
                mihinLisataan.lisaa(taulukko[i]);
            }
        }
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == joukonAlkiot[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int poistettavaLuku) {
        int poistettavanLuvunIndeksi = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (poistettavaLuku == joukonAlkiot[i]) {
                joukonAlkiot[poistettavanLuvunIndeksi = i] = 0;
                break;
            }
        }
        return siirraAlkiotTaulukonAlkuun(poistettavanLuvunIndeksi);
    }

    private boolean siirraAlkiotTaulukonAlkuun(int aloitusIndeksi) {
        if (aloitusIndeksi != -1) {
            for (int j = aloitusIndeksi; j < alkioidenLkm - 1; j++) {
                swap(joukonAlkiot, j, j + 1);
            }
            alkioidenLkm--;
            return true;
        }
        return false;
    }

    private void swap(int[] taulukko, int indeksi1, int indeksi2) {
        int tmp;
        tmp = joukonAlkiot[indeksi1];
        joukonAlkiot[indeksi1] = joukonAlkiot[indeksi2];
        joukonAlkiot[indeksi2] = tmp;
    }

    private void kasvataTaulukonKokoaJosTaynna() {
        if (onkoTaynna()) {
            int[] uusi = new int[alkioidenLkm + kasvatuskoko];
            for (int i = 0; i < joukonAlkiot.length; i++) {
                uusi[i] = joukonAlkiot[i];
            }
            joukonAlkiot = uusi;
        }
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String tuotos = "{";
        if (alkioidenLkm > 0) {
            for (int i = 0; i <= alkioidenLkm - 1; i++) {
                tuotos += joukonAlkiot[i];
                if (i < alkioidenLkm - 1) {
                    tuotos += ", ";
                }
            }
        }
        return tuotos + "}";
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = joukonAlkiot[i];
        }
        return taulu;
    }

    public int getAlkioIndeksista(int indeksi) {
        return joukonAlkiot[indeksi];
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko palautettavaJoukko = new IntJoukko();
        lisaaKaikki(palautettavaJoukko, a.toIntArray(), b.toIntArray());
        return palautettavaJoukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko palautettavaJoukko = new IntJoukko();
        for (int i = 0; i < a.mahtavuus(); i++) {
            for (int j = 0; j < b.mahtavuus(); j++) {
                if (a.getAlkioIndeksista(i) == b.getAlkioIndeksista(j)) {
                    palautettavaJoukko.lisaa(b.getAlkioIndeksista(j));
                }
            }
        }
        return palautettavaJoukko;
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko palautettavaJoukko = new IntJoukko();
        lisaaKaikki(palautettavaJoukko, a.toIntArray());
        for (int i = 0; i < b.mahtavuus(); i++) {
            palautettavaJoukko.poista(i);
        }
        return palautettavaJoukko;
    }
}

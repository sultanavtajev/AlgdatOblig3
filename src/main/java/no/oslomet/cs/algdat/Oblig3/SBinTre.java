package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot;                         // p starter i roten
        Node<T> q = null;
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare(verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi, q);                   // oppretter en ny node. Rettet opp slik at forelder blir riktig

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q
        endringer++; //Øker med 1 endring
        antall++;                                // én verdi mer i treet
        return true;
    }

    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) {
                rot = b;
                if (b != null) {
                    b.forelder = null;
                }
            } else if (p == q.venstre) {
                q.venstre = b;
                if (b != null) {
                    b.forelder = q;
                }
            } else {
                q.høyre = b;
                if (b != null) {
                    b.forelder = q;
                }
            }
        } else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null) {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) {
                s.venstre = r.høyre;
                if (r.høyre != null) {
                    r.høyre.forelder = s;
                }
            } else {
                s.høyre = r.høyre;
                if (r.høyre != null) {
                    r.høyre.forelder = s;
                }
            }
        }

        antall--;   //det er nå én node mindre i treet
        endringer++; //Øker med en endring
        return true;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        Deque<Node<T>> kø = new ArrayDeque<>();
        Node<T> p = rot;
        int n = 0;

        while(p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if(cmp < 0)
                p = p.venstre;
            else if(cmp > 0)
                p = p.høyre;
            else {
                // Vi lagrer alle referanser som er lik verdien vi ønsker på.
                kø.push(p);
                p = p.høyre;
            }
        }

        // Dersom inneholdet i kø er større enn 0, har vi funnet verdi(er) i treet.
        while(kø.size() > 0) {
            p = kø.pop();

            Node<T> q = p.forelder;

            if(p.venstre == null || p.høyre == null) {
                Node<T> b = p.venstre != null ? p.venstre : p.høyre;

                if(p == rot) {
                    rot = b;
                    if(b != null)
                        b.forelder = null;
                } else if(q.venstre == p) {
                    q.venstre = b;
                    if(b != null)
                        b.forelder = q;
                } else {
                    q.høyre = b;
                    if(b != null)
                        b.forelder = q;
                }

                p.forelder = p.venstre = p.høyre = null;
                p.verdi = null;
            } else {
                Node<T> r = p.høyre;
                Node<T> s = p;

                while(r.venstre != null) {
                    s = r;
                    r = r.venstre;
                }

                p.verdi = r.verdi;

                if(s != p)
                    s.venstre = r.høyre;
                else
                    s.høyre = r.høyre;

                if(r.høyre != null)
                    r.høyre.forelder = s;

                r.forelder = r.høyre = null;
                r.verdi = null;
            }

            antall--;
            n++;
        }

        endringer++;
        return n;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        if (verdi == null)
            return 0;

        int antall = 0;

        Node<T> p = rot;

        while (p != null) {

            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else {
                if (cmp == 0)
                    antall++;
                p = p.høyre;
            }

        }
        return antall;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void nullstill() {
        if(rot != null) {

            nullstill(rot);

            rot = null;
            antall = 0;
            endringer++;
        }
    }

    private void nullstill(Node<T> p) {
        if (p.venstre != null) nullstill(p.venstre);
        if (p.høyre != null) nullstill(p.høyre);

        p.venstre = null;
        p.høyre = null;
        p.verdi = null;
        p.forelder = null;
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        Objects.requireNonNull(p); //Sjekker om p er "null" verdi

        while(true){
            if(p.venstre !=null){
                p=p.venstre; //p sin venstrebarn
            } else if (p.høyre!=null){
                p=p.høyre; //p sin høyrebarn
            } else {
                return p; //Returner p
            }
        }
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Node<T> a = p.forelder; //Setter a til p sin forelder

        if (a==null){
            return null; //Returner null hvis det ikke er nestePostorden.
        }
        if (a.høyre==p || a.høyre==null){
            return a; //Returner forelder hvis a sin høyrebarn=p eller =null
        } else {
            return førstePostorden(a.høyre); //Kaller på førstePostorden
        }
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postorden(Oppgave<? super T> oppgave) {
        Node<T> p = rot;

        Node<T> forste = førstePostorden(p);
        oppgave.utførOppgave(forste.verdi);
        while(forste.forelder!=null){
            forste = nestePostorden(forste);
            oppgave.utførOppgave(Objects.requireNonNull(forste.verdi));
        }
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p ==null){
            return; //Returner
        }

        postordenRecursive(p.venstre,oppgave); //Rekursivt p sitt venstrebarn
        postordenRecursive(p.høyre,oppgave); //Rekursivt p sitt høyrebarn
        oppgave.utførOppgave(p.verdi); //Oppgaven kjøres
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        ArrayList<T> subTre = new ArrayList<>(); //Etabler array
        Queue<Node<T>> queue = new LinkedList<>(); // Etabler kø av binær-treet
        queue.add(rot); //Legger in "rot" først
        while (!queue.isEmpty()) { //Sjekk at treet ikk er tomt
            Node<T> p = queue.remove(); //Laster inn verdier fra "toppen" av køen
                    subTre.add(p.verdi); //Legger overnevnte verdier inn i arrayet
            if (p.venstre != null) { //Sjekk venstrebarn og legg evt inn i køen
                queue.add(p.venstre);
            }
            if (p.høyre != null) { //Sjekk høyrebarn og legg evt inn i køen
                queue.add(p.høyre);
            }
        }
        return subTre; //Returnerer array
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        SBinTre<K> tre = new SBinTre<>(c); //Etablerer nytt binærtre med comparator
        for (K verdi : data) { //Traverserer treet og legger til verdi hver gang vi finner en ny verdi.
                    tre.leggInn(verdi);
        }
        return tre; //Returner
        //throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public static void main(String[] args) {
        Integer[] a = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6};
        SBinTre<Integer> tre = new SBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) {
            tre.leggInn(verdi);
        }
        System.out.println(tre.antall(5)); // Utskrift: 0
        System.out.println(tre.antall(4)); // Utskrift: 3
        System.out.println(tre.antall(7)); // Utskrift: 2
        System.out.println(tre.antall(10)); // Utskrift: 1
    }


} // ObligSBinTre

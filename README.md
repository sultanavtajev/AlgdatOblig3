# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Sultan Avtajev, S199219, s199219@oslomet.no


# Oppgavebeskrivelse
Oppgave 1:
Denne oppgaven var veldig rett frem og vedig relatert til det vi har gått gjennom i ukeoppgavene. Som beskrevet i 
oppgaveteksten kopierte jeg "Programkode5.2.3 a" og modifiserte denne slik at referansen "forelder" har riktig verdi 
i hver node. Vedlagt programbit er testet og fungerer som forventet. 

Oppgave 2:
Denne oppgaven ble løst ved å bruke igjen store deler av koden til metoden "inneholder()". I den endrede koden er 
det lagt inn en teller som øker for hver forekomst av verdien vi søker etter i treet. Vedlagt programbit er testet og 
fungerer som forventet.

Oppgave 3:
Brukt en del inspirasjon og hjelp fra kompendie. Her sjekker vi først om parameteren "p" som blir sendt inn er 
nullverdi. Fortsetter hvis ikke "null". Returnerer første node post orden med "p" som rot. "nestePostorden" fungere 
på samme måte bare at den returnerer noden som kommer etter p i postorden. 

Oppgave 4:
Konfigurert hjelpemetodene "postorden". Her initialiseres p som rot. Finner første node av metoden førstePostorden 
av "p". Deretter looper while løkka gjennom treet og oppdaterer neste verdi i postorden. I metoden 
"postordenRecursive" sjekker vi først om treet er tomt. Hvis ikke tomt kaller vi rekursivt på metoden med start fra 
rot.  

Oppgave 5:
I "serialize" lages først ett array og en kø. Legger først "rot" inn i køen.
Så lenge treet ikke er tomt, begynnet vi å laste inn verdier fra "toppen" av køen. Legger deretter overnevnte 
verdier inn i arrayet. Sjekker venstrebarn og legger evt inn i køen. Sjekker høyrebarn og legger evt inn i køen. 

I "deserialize" etableres nytt binærtre med comparator. Deretter traverserer vi arayet og legger til verdi i treet hver 
gang vi finner en ny verdi. Til slutt returnerer vi treet. Vedlagt programbit er testet og fungerer som forventet.

Oppgave 6: 
Metoden "fjern" kopierte jeg fra kompendiet som beskrevet i oppgaveteksten. Alle if/else setningene er blitt 
oppdatert slik at pekeren "forelder" får korrekt verdi i alle noder etter en fjerning. I metoden "fjernAlle" går vi 
gjennom treet og lagrer alle referansene til verdien vi øsnker å fjerne. Deretter går vi gjennom alle de lagrede 
referansene og fjerner dem fra treet. Delen med fjerning av verdiene er ganske lik som i "fjern" metoden, men også 
her oppdatert i forhold til at "forelder" får riktige verdier i alle noder. Vedlagt programbit er testet og fungerer 
som forventet.  


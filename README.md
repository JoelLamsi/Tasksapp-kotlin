Sovellusta avattaessa näkyy oletus tehtävälista (lähimmäiset tehtävät opinnoissani).

Tehtäviä voi merkitä tehdyksi ja painikkeesta näyttää vain tehdyt tehtävät. Lisäksi pystyy tehtäviä
suodattaa päivämäärän mukaan.

Käyttäjä voi lisätä tehtävän kuvauksineen. Oletuksena sovellus lisää 'New task...' nimisen tehtävän.
Lisätyt tehtävät eivät tallennu, vaan sovelluksen käynnistäessä uudelleen näkyvät taas
oletustehtävät.

## Datamalli

Task - int id, string title, string description, LocalDate dueDate, int priority, boolean done

Task dataluokka säilöö tiedon tehtävän nimestä, selityksestä, päiväyksestä, tärkeydestä, sekä
tilasta (tehty vai tekemättä).

## Funktiot

- addTask - Lisää tehtävän
- toggleDone - Tehtävän tilan muuttaminen tehtyksi ja tekemättömän välillä
- filterByDone - Suodattaa tehdyt tehtävät
- sortByDueDate - Lajittelee tehtävät päiväyksen mukaan
- removeTask - Poistaa tehtävän

## Composable-tilanhallinta

Composablen käyttö parantaa sovelluksen testattavuutta. Composable voi ajatella rakennuspalikkoina,
joita voi uudelleenkäyttää sovelluksessa. Vain tilan muutoksista riippuvat Composablet piirretään
uudelleen. Kirjoitetun koodin määrä on vähäisempää.

## MVVM (Model-View-ViewModel)

On laajasti käytetty suunittelumalli useissa ohjelmointikielissä. Sovelluksen käyttäjälle näytettävä
näkymä (**View**), sovelluksen tilaa ja toimintalogiikkaa hallinnoiva taso (**ViewModel**) sekä data
(**Model**), eroteaan toisistaan omiksi kokonaisuuksikseen. Tämä selkeä vastuunjako yksinkertaistaaa
sovelluksen hallintaa ja parantaa merkittävästi testattavuutta.

### ViewModel

ViewModel on osa MVVM (Model-View-ViewModel) -arkkitehtuuria, jossa sovelluksen toiminnallisuus
erotetaan käyttöliittymästä. Tämä parantaa koodin ylläpidettvyyttä testattavuutta.

Jetpack Compose -pohjaisissa mobiilisovelluksissa ViewModelin käyttö on suositeltavaa, koska se
säilyy konfiguraatiomuutoksissaa (kuten ruudun kiertämisessä) ja pystyy säilyttämään sovelluksen
tilan luotettavammin kuin esimerkiksi `remember` -delegaatit yksinään. Tämän ansiosta käyttöliittymää
voidaan rakentaa uudelleen ilman, että sovelluksen tila katoaa.

## Navigointi Jetpack Composessa

Jetpack Composessa navigoinnilla tarkoitetaan sovelluksen siirtymistä eri näkymien (**Screens**) 
välillä. Tämän toteutukseen käytetään yleens' `navigation-compose` -kirjastolla, jossa jokaiselle 
näkymälle määritellään reittinsä (**route**).

**NavController** vastaa navigoinnin hallinnasta. Se huolehtii siitä, mikä näkymä on aktiivinen ja 
mahdollistaa siirtymisen näkymästä toiseen.

**NavHost** on komponentti, jonka sisälle määritellään navigointirakenne.

## MVVM ja Navigointi

Soveluksessa MVVM yhdistyy navigointiin siten, että sama ViewModel-instanssi jaetaan useiden näkymien
käytettäväksi, jolloin tilamuutokset myös vaikuttavat kummassakin. Tässä sovelluksessa
Home -näkymä esittää näkymät, jossa näitä voi merkata tehtyiksi ja lisätä uusia tehtäviä. Kalenteri 
-näkymässä tehtävät näkyvät lajiteltuna päivämäärän mukaan. Molemmissa näkymissä tehtäviä on
mahdollista muokata.

## StateFlow

Käyttöliittymässä esiintyy usein useita eri tiloja, joita sovelluksen täytyy pystyä seuraamaan ja
päivittämään. Lisäksi näitä tilamuutoksia saatetaan tarvita useissa eri näkymissä samanaikaisesti.
`StateFlow` tarjoaa tähän ratkaisun. Se mahdollistaa ajantasaisen ja reaktiivisen tilanhallinnan.
StateFlow kerätään ja sitodaan käyttöliittymään `collectAsState` -funktiolla, jolloin viimeisin tila
päivittyy kun UI piirretään uudelleen.
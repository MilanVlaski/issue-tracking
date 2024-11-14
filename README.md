# Projekat 1
> Projektovati sistem za potrebe kompanije u cilju praćenja problema. Kompanija razvija aplikacije (naziv, verzija, opis, godina izdavanja) za koje korisnik pri kupovini bira jedan od četiri tipa podrške (bez podrške, ograničena podrška cijelo radno vrijeme, ograničena podrška pola radnog veremena, i neograničena podrška). Korisnik prijavljuje problem kao niz akcija pri kojima se problem manifestuje. Za svaku akciju se evidentira redni broj akcije i opis akcije. Postoje četiri moguća stanja u kojima se problem može naći (prijavljen, preuzet, u fazi rješavanja, riješen). Na problemu može raditi veći broj inženjera a može biti angažovan i samo jedan. Svaki inženjer, bez obzira da li radi na problemu ili ne, može da objavi odgovor (moguće rješenje problema) i pri tome je neophodno voditi evidenciju o datumu objavljivanja i inžinjeru koji ga je objavio. Međutim samo inženjer koji radi na problemu može odrediti da li se neka zakrpa tj. patch (veličina, datum objave, vrsta komunikacije sa korisnikom) odnosi na taj problem. Svaki korisnik ima pravo da instalira proizvoljne zakrpe (bez obzira da li je prijavio konkretan problem ili ne).

![[Pasted image 20241012095949.png]
# Note
## Postavljanje patcha
Moram na neki način imati vezu između postavljanja zakrpe, i rješavanja problema, i postavljanja nove verzije aplikacije. Ako korisnik instalira patch, on u principu instalira novu aplikaciju.
- Inzinjer postavlja patch
- stavlja problem u rijesen
- time se pravi nova aplikacija za koju je problem vezan i mijenja se samo verzija 1.0 -> 1.0.1.
- Korisnik, prijavilac problema, dobija ovu aplikaciju kao jednu od svojih.
- (Jos uvijek je vlasnik stare, ili se stara brise, ne znam, zanimljivo pitanje.)
Takodje, kad se uvodi zakrpa, ona rjesava problem (mijenja mu stanje), i daje novu verziju aplikacije. Onda se ta verzija salje na korisniku.
Ako uradimo neku pretragu latest verzije, to ce biti naziv aplikacije, njena verzija, plus zadnji patch. Patch je samo broj... To se inace cuva sve kroz git.
# Slučajevi
1. Korisnik kupuje aplikaciju, uz biranje vrste podrške. (Imamo stranicu sa pregledom kupovina?)
2. Korisnik prijavljuje problem na aplikaciji koju je kupio. Sam ili naknadno, upisuje akcije koje su dovele do problema.
    - Halo. Imam problem s aplikacijom.
    - Kojom?
    - Zove se Power Draw. (Drop down sa pretragom)
    - Verzija?
    - Cek... 0.8 /(Dropdown sa pretragom, a varijabla je naziv app)
    - *Traži aplikaciju, i za nju unosi korake.* Kako je došlo do greške?
    - *Opisuje korake*
    - *Unosi korake.* Hvala, pomoći ćemo vam što prije!
1. Ako postoji zakrpa, korisnik je instalira.
2. Inženjer uzima problem.
3. Inženjer pravi zakrpu, ali ko odlučuje da li je problem riješen? Inzenjer, vjerovatno.
    1. Ako radi na problemu, onda se smatra rješiocem problema, i rješ rješavanja problema.
    2. Ako ne, onda ga neko mora odobriti.
4. Inženjer daje odgovor na problem.
5. Inženjer želi da pregleda sve probleme na nekoj aplikaciji, radi odabira. (stanja: Prijavljen, Preuzet, Rješava se, Rješen). Admin, ili CEO, može da pregleda sve probleme i dodijeli ih, ili gleda statistiku i da filtrira.
6. Akcije može unijeti korisnik, sam, na nekoj formi. Ili, pozivom na telefon, osoblje može ispuniti ovu formu.
7. Inženjer može da vidi sve probleme koje rješava, ida nekome od njih doda odgovor.
8. Inženjeri mogu da gledaju sve probleme, ili one kojima su dodijeljeni.
9. Korisnik gleda odgovore na probleme koje je postavio.
10. Korisnik može da instalira zakrpe za sve probleme koje je postavio.
11. Kako vidjeti koliko je brzo inženjer riješio problem? Pogledati vrijeme od kad je predao problem, do vremena kad je dao zakrpu.
# Autorizacija
- Korisnik ne mora da se loginuje. Ima pristup IT podršci ili sličnom, i sajtu.
- Podrška i inženjeri se loginuju i imaju pristup kul stvarima. Mada kao hak, bez logina, mogu unijeti svoje ime ili nesto...
- Admin ili ko vec, radi sve.
  Ovo je sve tema za spring sekjuriti...
 # To do
- [ ] Move Entities into package where necessary. 
- [ ] Add processing of problem actions
- [ ] Implement engineer security
- [ ] Add logout for both engineer and for user
- [ ] Use id's in selenium tests instead. Then try the function that goes through all the hrefs.
- [ ] Then just use browser navigation.
- [x] Change all application paths to use path variable, instead of request parameter
- [x] Handle report problem page with no apps.
- [ ] Add logic so that user can't buy the same app twice (there is already an exception happening on the page.) 
- [ ] By the end, completely refactor the Entities, and the CRUD operations, to reduce the amount of times I have to manually wire up the associations.
  - [ ] Make Purchases have a synthetic ID
- [ ] Add nice handling for when buy page has error/success.
- [ ] Be clever and let the user type, hit enter, and have the action show up somewhere as numbered. Also be able to edit or delete each entry.
- [ ] Be clever and when user goes to `/reportProblem`, if he only has one app, then take him to it
- [ ] Add a dto for RegisterRequest that encodes the password, and maps it to an entity
- [ ] Display login error.
- [ ] Display register error, in case email is not unique.
- [x] Add sign in (Implement spring security so that the purchase page requires a log in.)
  - [x] Add a custom login page
  - [x] Register is simple. Just put it in the db, plus, use a password encoder. (Might still want to keep a default username and password thingy, because it's simpler for manual debugging.)
  - [x] Login redirect works fine. Now I need a register redirect. And I need register to take the user straight to login, or to login the user immediately. However, if it's too much work, just make register redirect to login page. I think every page does this anyway.
  - [x] Make it so email is used instead of username. Add a custom login template? I don't know if I will have to manually do the redirection.
  - [x] Use encryption for the hardcoded password.
  - [x] But for log-in, do a test that puts in the password and username, and preload it in data.sql. Then in tests, put those values in.
- [x] Add `app.js` that can take FormData and send it as json, to the form's action attribute.
- [x] Create a `/purchase` website with a purchase form. It should receive data from the client.
- [x] Find a way to preload database with data. It should 
- [x] Show a list of applications
  - [x] Display apps in a table, or with a logo.
    - [x] Learn thymeleaf a bit
  - [x] Fetch apps from database
    - [x] Write some code that inserts some apps. Calling it from tests would be neat.
      - [x] Add the entity manager and use it to insert
      - [x] Then use it to fetch the tables
  - [x] add the necessary stuff in the controller
- Logout
```html
    <div th:if="${#authentication != null}">
        <!-- Show logout button if authenticated -->
        <form th:action="@{/logout}" method="post">
            <button type="submit">Logout</button>
        </form>
        
        <!-- Optionally show the username if logged in -->
        <p>Welcome, <span th:text="${#authentication.name}"></span></p>
    </div>
```
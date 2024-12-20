# Projekat 1
## Problem
> Projektovati sistem za potrebe kompanije u cilju praćenja problema. Kompanija razvija aplikacije (naziv, verzija, opis, godina izdavanja) za koje korisnik pri kupovini bira jedan od četiri tipa podrške (bez podrške, ograničena podrška cijelo radno vrijeme, ograničena podrška pola radnog veremena, i neograničena podrška). Korisnik prijavljuje problem kao niz akcija pri kojima se problem manifestuje. Za svaku akciju se evidentira redni broj akcije i opis akcije. Postoje četiri moguća stanja u kojima se problem može naći (prijavljen, preuzet, u fazi rješavanja, riješen). Na problemu može raditi veći broj inženjera a može biti angažovan i samo jedan. Svaki inženjer, bez obzira da li radi na problemu ili ne, može da objavi odgovor (moguće rješenje problema) i pri tome je neophodno voditi evidenciju o datumu objavljivanja i inžinjeru koji ga je objavio. Međutim samo inženjer koji radi na problemu može odrediti da li se neka zakrpa tj. patch (veličina, datum objave, vrsta komunikacije sa korisnikom) odnosi na taj problem. Svaki korisnik ima pravo da instalira proizvoljne zakrpe (bez obzira da li je prijavio konkretan problem ili ne)Projektovati sistem za potrebe kompanije u cilju praćenja problema. Kompanija razvija aplikacije (naziv, verzija, opis, godina izdavanja) za koje korisnik pri kupovini bira jedan od četiri tipa podrške (bez podrške, ograničena podrška cijelo radno vrijeme, ograničena podrška pola radnog veremena, i neograničena podrška). Korisnik prijavljuje problem kao niz akcija pri kojima se problem manifestuje. Za svaku akciju se evidentira redni broj akcije i opis akcije. Postoje četiri moguća stanja u kojima se problem može naći (prijavljen, preuzet, u fazi rješavanja, riješen). Na problemu može raditi veći broj inženjera a može biti angažovan i samo jedan. Svaki inženjer, bez obzira da li radi na problemu ili ne, može da objavi odgovor (moguće rješenje problema) i pri tome je neophodno voditi evidenciju o datumu objavljivanja i inžinjeru koji ga je objavio. Međutim samo inženjer koji radi na problemu može odrediti da li se neka zakrpa tj. patch (veličina, datum objave, vrsta komunikacije sa korisnikom) odnosi na taj problem. Svaki korisnik ima pravo da instalira proizvoljne zakrpe (bez obzira da li je prijavio konkretan problem ili ne).

# Running
User pages are at `/`, and the engineers pages are at `/engineer/problems`
1. To run an empty app, run `./gradlew bootRun`
   - There are  5 applications in the shop, a user with email john.doe@example.com, and an engineer john.smith@example.com, with passwords set to "password".
2. To run an app with a local database, run `DB_USERNAME=your_username DB_PASSWORD=your_password SPRING_PROFILES_ACTIVE=local ./gradlew bootRun` on linux, and for Powershell, `$env:DB_USERNAME="your_username"; $env:DB_PASSWORD="your_password"; $env:SPRING_PROFILES_ACTIVE="local"; ./gradlew bootRun`
To set the db up, you will have to use postgres, run the `src/main/resources/schema.sql` to create the tables as well as the script below, to create a user and allow them to access sequences. Optionally, `src/main/resources/data.sql` is a sql script for some dummy data. It runs in the above configuration by default.
```postgresql
CREATE USER youruser WITH PASSWORD 'yourpassword';
GRANT CONNECT ON DATABASE "issue-tracking-pg" TO youruser;
GRANT USAGE ON SCHEMA public TO youruser;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO youruser;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO youruser;


DO $$ 
BEGIN
    EXECUTE (
        SELECT STRING_AGG('GRANT USAGE, SELECT, UPDATE ON SEQUENCE ' || quote_ident(schemaname) || '.' || quote_ident(sequencename) || ' TO youruser', '; ')
        FROM pg_sequences
        WHERE schemaname = 'public'
    );
END $$;
```
 # To do
## Must do
- [x] Engi should be able to see the problem answers as well, not sure how to display it, though. Maybe just display all problems, in the exact same way that the user sees them.
- [x] Engi page should say <h2>Problems</h2>
- [x] Center the filter
- [x] Put english in the database for support type
- [x] Engineer should be able to see own patches as well as other's
- [x] Add filtering to problems page for the engineer. (User's is prefiltered.)
## Error handling
- [ ] Add logic so that user can't buy the same app twice (there is already an exception happening on the page.) 
- [ ] Add nice handling for when buy page has error/success.
- [ ] Display login error.
- [ ] Display register error, in case email is not unique.
## Nice to have
- [ ] FEAT: Add date created to answer.
- [] FEAT: See all engineers, with a count of solved problems, and option to see problems they are working on.
- [ ] Report problem page should have a logo and app name, of the app the problem relates to
- [ ] Actions should be optional
- [ ] FEAT: A problem should display all the engineer's names.
- [ ] A user can purchase an update or patch of an application for free, so Install should be displayed instead of Buy.
- [ ] Add a helper function that fetches the current user.
- [ ] By the end, completely refactor the Entities, and the CRUD operations, to reduce the amount of times I have to manually wire up the associations.
  - [ ] Try replacing all the getters and setters with fluent lombok
      - [ ] Make Purchases have a synthetic ID
- [ ] Add pagination to all listed pages
- [ ] Add number of actions to problem rows. 
- [ ] Consider validation in three ways:
    - Programmatic. Calling `Validation.buildDefaultValidatorFactory();`
    - `@Valid @ModelAttribute entity, BindingResult result` 
    - `@Valid @RequestBody entity`
- [ ] When an engineer posts an answer, they should be able to choose which state this puts it in. It should be prepopulated with the problem's current state, but allow it to be set to solved. 
- [ ] Be clever and when user goes to `/reportProblem`, if he only has one app, then take him to it
- [ ] Add a dto for RegisterRequest that encodes the password, and maps it to an entity
## Bugs
- [x] FEAT: Change state of problem
  - It's a dynamic dropdown (ask chatgpt the simplest way to do it)
  - Populated with values (iterating over the enums english names)
  - When the user selects a thing a POST request is made, through JS, if it succeeds then the value in HTML changes. If not, nothing happens, I suppose.
- [x] FEAT: Filtering based on state
  - Add filter field, maybe a radio or select is best. This field should lead to a page reload most likely.
  - Change the endpoint to take an optional query value, based on which filtering is done.
- If user already has application takes us to error page.
## Done
- [x] Use DTO for Application, and convert it to entity. Later, check if user and engineer registration also misses some fields.
- [x] The `/engineer/problems/mine` page can't do filtering, because the path is no good.
- [x] Same thing when we are redirected after login, and the path is `?continue`. We don't get filtering.
- [x] Reason about how to package and run the app from command line. This will be necessary for a local db operation.
- [x] Create local db and use the app a bunch with it.
- [x] Style the userProblems page separately
- [x] An engineer can set the state of the problem to any available state, when submitting an answer.
- [x] When a problem is resovled with a patch, a user can purchase the resulting app. We do this by simply having a link on the answers page that takes them to the site /applicaton/{appId}/buy. The page itself will have logic to recognize whether it is owned by the user or not, showing an Install button instead of a Buy. Clicking on the button should install it somehow (just an alert for now). This happens with all owned apps. It could even happen when the user is redirected after a buy...
- [x] ON the User problems page, Have the problem be open to begin with, and render the patch properly. Add dummy data to start with.
- [x] Center answer problem page, add a heading to it like (Problem #1)
- [x] There should be an extra <th> in engineer problems page
- [x] The problem state is not being rendered, or missing in the model (engineerProblems.html)
- [x] Remove horizontal padding from the table, and reduce the font on small screens. Remove description column, if necessary.
- [x] Add rendering on successful form submission to answerProblem page
- [x] describeProblem should show something on correct form submission
- [x] Add some margin top to the report a problem button
- [x] Style Report problem. This should be generic table styling, and small logo.
- [x] Style Login and Register pages (generic form styling again)
- [x] Translate problem state to english (probably by using a dto)
- [x] Show user the price of support.
- [x] You can't assign a problem, if it's already yours. So change the My problems page a bit.
- [x] **Production postgres environment** (2 days) (Maybe Google Cloud + Secret manager)
- [x] **Local postgres** (3 days)
- [x] **Styling** (2 days)
- [x] FEAT: Assign a problem to an engineer.
- [x] Make a different header for user and engineer.
- [x] Think about what the user would like to see on the fixes page. Probably, I want to see that my problem is fixed, and then immediately click on it to open it. Ideally, I click something, it does a dropdown, in which I see answers and patches. The answers have descriptions, and engineer's names next to them. The patches have names, and other data, and a fake download link (it would be neat if the link held the name and new version of the application)
- [x] Move Entities into package where necessary. 
- [x] Add processing of problem actions
- [x] Implement engineer security
- [x] Add logout for both engineer and for user
- [x] Change all application paths to use path variable, instead of request parameter
- [x] Handle report problem page with no apps.
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

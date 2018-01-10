# SportEventManager

Create an information system allowing sportsmen to sign up for a sporting event. As an example, let's consider a system for registration of swimmers in a swimming competition. The system should store details about the competition, individual competitors and their results in the competition. It should be able to present people taking part in each competition and the results they achieved. The system should handle multiple types of sport simultaneously. Consider Sport, Sportsman and Event as basic entities in your application.

#### Team Members

Team No. 4

- Petra Halová
- Martin Šmíd
- Jiří Tobiáš (team leader)

REST

To run : `mvn clean install && cd web && mvn tomcat7:run`

- pa165/rest/ : `curl -X GET http://localhost:8080/pa165/rest/`
- pa165/rest/sports : `curl -X GET http://localhost:8080/pa165/rest/sports`
- pa165/rest/sports/{id} : `curl -X POST http://localhost:8080/pa165/rest/sports/{id}`
- pa165/rest/sports/create/{sport} : `curl -X POST http://localhost:8080/pa165/rest/sports/create/{sport}`
- pa165/rest/sports/update/{sport} : `curl -X POST http://localhost:8080/pa165/rest/sports/update/{sport}`
- pa165/rest/users : `curl -i -X http://localhost:8080/pa165/rest/users`
    - `curl -i -X http://localhost:8080/pa165/rest/users?role=SPORTSMEN&limit=0&birthdateBegin=0000-00-00&birthdateEnd=9999-99-99&gender=ALL&sortBy=id`
    - role : ALL, SPORTSMEN, USER, ADMINISTRATOR
    - limit : number of results if limit > 0
    - birthdateBegin : yyyy-mm-dd
    - birthdateEnd : yyyy-mm-dd
    - gender : ALL, MAN, WOMAN
    - sortBy : role, birtdate, gender, firstname, lastname, name, email, id
- pa165/rest/users/{id} : `curl -i -X GET http://localhost:8080/pa165/rest/users/1`
- pa165/rest/users/create : `curl -i -X POST -H "Content-Type: application/json" --data '{"email":"test@test.com", "password":"password", "firstname":"firstname", "lastname":"lastname", "gendre":"MAN", "birthdate":"2000-01-30", "phone":"111222333", "address":"address", "role":"USER"}' http://localhost:8080/pa165/rest/users/create`
- pa165/rest/users/{id}/delete : `curl -i -X POST --data '{"id":1}' http://localhost:8080/pa165/rest/users/1/delete`
- pa165/rest/users/{id}/changePassword : `curl -X POST -H 'Content-Type: application/json' --data '{"id":1, "oldPassword":"sportsmenHeslo", "newPassword":"heslo"}' http://localhost:8080/pa165/rest/users/1`
- pa165/rest/users/{id}/resetPassword : `curl -X POST -H 'Content-Type: application/json' --data '{"id":1, "email":"prvni@gmail.com"}' http://localhost:8080/pa165/rest/users/1/resetPassword`

ROLES - username - password
 - ADMINISTRATOR- admin@gmail.com : admin
 - SPORTSMEN - sportsmen@gmail.com : sportsmenPass
## API Information - Robots Vs Dinosaurs Simulation
"Nubank is assembling an army of remote-controlled robots to fight the dinosaurs and the first step towards that is to 
run simulations on how they will perform. You are tasked with implementing a service that provides an API to support those simulations."

More detailed information about the construction steps of this project [here](doc/project.md).

## Requirements
1. [Java 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
2. [Leiningen](https://leiningen.org/)  
2.1. It was found an error installing Leiningen on Windows. To correct this, in the *lein.bat* file, change:
~~~
old line 157 > powershell -Command "& {param($a,$f) $client = New-Object System.Net.WebClient;  $client.Proxy.Credentials =[System.Net.CredentialCache]::DefaultNetworkCredentials; $client.DownloadFile($a, $f)}" ""%2"" ""%1""
new line 157 > powershell -Command "& {param($a,$f) $client = New-Object System.Net.WebClient; [Net.ServicePointManager]::SecurityProtocol = 'tls12' ; $client.Proxy.Credentials =[System.Net.CredentialCache]::DefaultNetworkCredentials; $client.DownloadFile($a, $f)}" ""%2"" ""%1""
~~~

## How to run the actions
The API is made through consulting REST services. The services can be used direct on your navigator.  
To make the use/test of the API easier, [Postman](https://www.getpostman.com/) was used.
Import this [json](doc/postman_collection.json) to Postman and you will have all the routines. Note that, for the requests that
need parameters, you will need to inform them clicking in the Params button, next to the Send button.
  
1. Create an empty simulation space: `localhost:3000/grid-new`    

2. Display the grid: `localhost:3000/grid-display`  
2.1. During development, sometimes it was used this [link](http://json2table.com/#) to help to view the grid in a table form.  

3. Add robot: `localhost:3000/robot-add?col=VALUE&row=VALUE&direction=VALUE`  
3.1. The facing direction was made using numbers from 1 to 4. The rotation must necessarily follow the numerical sequence 1 to 4, incrementally and clockwise, with position 1 pointing upwards.  
RIGHT representation: 1 = up, 2 = right, 3 = down, 4 = left  
INCORRECT representation e.g. 1 = low, 2 = right, 3 = up, 4 = left  
---1---    
4-----2  
---3---

4. Add dinosaur: `localhost:3000/dinosaur-add?col=VALUE&row=VALUE`  

5. Robot turn direction: `localhost:3000/robot-turn-direction?col=VALUE&row=VALUE&direction=VALUE`  
5.1. Direction can be `left` or `right`  

6. Robot move: `localhost:3000/robot-move?col=VALUE&row=VALUE&direction=VALUE`  
6.1. Direction can be `backward` or `forward`  

7. Robot attack: `localhost:3000/robot-attack?col=VALUE&row=VALUE`

## How to run tests
Unit tests were implemented using [Midje](https://github.com/marick/Midje). `lein midje`  
Integration tests were implemented using Clojure Tests and [Ring Mock](https://github.com/ring-clojure/ring-mock). `lein test` or the `lein midje`, which will run the integration tests too.

## How to run the API
1. Generating jar with embedded Jetty Server  
1.1. `lein uberjar`  
1.2. `java -jar target/file_name.jar`
2. using leiningen  
2.1. `lein trampoline ring server`
# Pat2016_Lukasz_Pilatowski

Prerequisites:
- install: JDK 1.8 [Oracle jdk download link]
- install: Maven 3.x [Maven download link]

## Building with maven

        mvn -T4 clean package

## Running

    java MyApplication.class

## Testing

* Create new actor

		curl -v http://localhost:8080/actors \
			-H "Content-Type: application/json" \
			-d '{"name":"Rowan Atkinson", "birthDate":"06-01-1955"}'

* Create new movie

		curl -v http://localhost:8080/movies \
			-H "Content-Type: application/json" \
			-d ' {
                      "movieName": "test" ,
                      "actors": [
                                 {
                                  "name" : "Rowan Atkinson",
                                  "birthDate" : "06-01-1955"
                                 }
                                ]
			     }'

* Fetch all actors

		curl -v http://localhost:8080/actors


* Fetch all movies

		curl -v http://localhost:8080/movies

* Fetch one actor

		curl -v http://localhost:8080/actors/{id}

* Fetch one movie

		curl -v http://localhost:8080/movies/{id}

* Update actor

		curl -XPUT http://localhost:8000/actors/{id} \
		-H "Content-Type: application/json" \
		-d '{/*updated actor data*/}'

* Update movie

		curl -XPUT http://localhost:8000/movies/{id} \
		-H "Content-Type: application/json" \
		-d '{/*updated movie data*/}'

* Delete actor

		curl -XDELETE http://localhost:8000/actors/{id}

* Delete movie

		curl -XDELETE http://localhost:8000/movies/{id}

[Oracle jdk download link]:http://www.oracle.com/technetwork/java/javase/downloads/index.html
[Maven download link]: http://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache

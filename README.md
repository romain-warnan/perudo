# Perudo

## Setup

```bash
git clone https://github.com/romain-warnan/perudo.git
cd perudo
mvn clean install
java -jar target/perudo-server-0.0.1-SNAPSHOT.jar --server.port=80
```

Check [here](http://localhost)

## Dev setup

```bash
git clone https://github.com/romain-warnan/perudo.git
```

### Backend

```bash
cd perudo
mvn clean install
mvn spring-boot:run
```

### Frontend
```bash
cd perudo/frontend
npm install
npm start
```

Check [here](http://localhost:3001)

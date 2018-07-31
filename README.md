# Perudo

## Setup

```bash
git clone https://github.com/romain-warnan/perudo.git
cd perudo
mvn clean install
java -jar target/perudo-0.0.1-SNAPSHOT.jar --server.port=80
```

Check [here](http://localhost)

## Dev setup

```bash
git clone https://github.com/romain-warnan/perudo.git
```

### Backend

```bash
cd perudo
mvn spring-boot:run
```

### Frontend
```bash
cd perudo/frontend
yarn install
yarn start
```

Check [here](http://localhost:3001)

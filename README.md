# apiserver

A basic Spring Web API server over a PostgresQL DB for tests and experiments.

- to build and test: `./gradlew build`
- to run with a local database:
    - install postgres (e.g. `brew install postgres`, `apt install postgresql`)
    - run `./deploy/deploy-local-db.sh`
    - set password in `src/main/resources/application.yaml` to match password in `./temp/pgsql/pwfile`
    - run `./gradlew bootRun`
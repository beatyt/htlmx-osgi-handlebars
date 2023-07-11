This project sits in the `examples` folder of the osgi enroute examples (https://github.com/osgi/osgi.enroute) and references the parent pom of that project

# Build, Running

In this directory, `examples\htmlx`

```
mvn verify
mvn -am bnd-indexer:index bnd-indexer:index@test-index bnd-resolver:resolve package
mvn package
```

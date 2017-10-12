# functional-tests-in-scala

### Features

##### JSON parser
* JSON serialization/deserialization handled by Jackson
* `java.time.Instant` to ISO 8601 conversion, e.g. `2014-12-03T10:15:30Z`
* serialization (object to JSON)
  * `null` fields are omitted
  * `None` is serialized to `null`
* deserialization (JSON to object)
  * `null` is deserialize to `null` or `None` for `Option`
  * missing properties are deserialized to `null` or `None` for `Option`
  * deserialization fails when unknown field occurs in JSON (to enforce a new assertion or test)
* enums are handled by `@JsonScalaEnumeration` 
* check `com.funtis.commons.json.jackson.JSONParserTest` for some examples

##### Dependency updates check
* run `sbt dependencyUpdates` to check which dependencies can be updated

##### Tags
* use `com.funtis.commons.tags.WIP` tag to run select and run single test
    * create new configuration: Run -> Edit Configuration -> ScalaTest
    * Test Kind: All in package
    * Package: com.funtis
    * Test options: `-n com.funtis.commons.tags.WIP`
    * mark selected test like: `it should "test something" in taggedAs WIP { ... }`

### ToDo
- [ ] Logging: logback.xml for api, web, perf - sbt + intellij
- [ ] sbt jenkins configurable targets
- [ ] add scripts to download latest selenium driver
- [ ] aspect-based logging in commons-web
- [ ] scalatra for mocks
- [ ] reuse parser & http client

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).
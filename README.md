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
* check `utf.commons.json.jackson.JSONParserTest` for some examples

##### Dependency updates check
* run `sbt dependencyUpdates` to check which dependencies can be updated

##### Tags
* use `utf.commons.tags.WIP` tag to run select and run single test
    * create new configuration: Run -> Edit Configuration -> ScalaTest
    * Search for tests: In whole project
    * Test Kind: All in package
    * Package: `utf`
    * Test options: `-n utf.commons.tags.WIP`
    * mark selected test like: `it should "test something" in taggedAs WIP { ... }`
    
##### Logging
* `HttpClient` logs request and response default. In order to see raw request and responses (since Apache HttpClient adds some headers like Connection, User-Agent) refer to https://hc.apache.org/httpcomponents-client-4.5.x/logging.html 

### ToDo
- [ ] logging: logback.xml for api, web, perf - sbt + intellij
- [ ] optional raw request logging (https://hc.apache.org/httpcomponents-client-4.5.x/logging.html) 
- [ ] sbt jenkins configurable targets
- [x] add scripts to download latest selenium driver
- [ ] aspect-based logging in commons-web
- [x] scalatra for mocks
- [x] reuse parser & http client
- [ ] entity + tests + readme
- [ ] FAIL_ON_MISSING_CREATOR_PROPERTIES vs Option[_]

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).
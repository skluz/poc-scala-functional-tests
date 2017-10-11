# functional-tests-in-scala

### Features

##### JSON parser
* JSON serialization/deserialization handled by Jackson
* java.time.Instant to ISO 8601 conversion
* serialization fails when unknown field occurs in response or required field is missing
* None and null are skipped in request

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
- [ ] Document all maintainer processes (triage, release, etc.)
- [ ] sbt jenkins configurable targets
- [ ] add scripts to download latest selenium driver
- [ ] aspect-based logging in commons-web
- [ ] scalatra for mocks
- [ ] reuse parser & http client

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).
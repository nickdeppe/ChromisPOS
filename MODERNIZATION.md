# ChromisPOS Modernization Notes

This branch starts the runtime and touch-UI modernization work from
`BoxOfficeProductSets`.

## Runtime targets

- Java: move in stages from the current Java 8 baseline toward a current LTS JDK.
- Database: validate against MySQL 8.4 LTS before considering newer innovation
  releases.
- Build: replace bundled, version-pinned jars with a dependency-managed build.

## First compatibility slice

- Startup now parses Java versions through `java.specification.version`, so modern
  version strings such as `17`, `21`, or `25` do not fail the Java version check.
- Reflective look-and-feel and database driver loading now uses constructor
  reflection instead of deprecated `Class.newInstance()`.
- MySQL defaults now use Connector/J 8.4.0 and the Connector/J 8+ driver class:
  `com.mysql.cj.jdbc.Driver`.

## Immediate follow-up

Run the application against a disposable MySQL 8.4 database and capture the next
compatibility issue at the schema/query layer. The legacy
`mysql-connector-java-5.1.36.jar` has been removed from `lib`.

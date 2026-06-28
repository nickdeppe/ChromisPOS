# ChromisPOS Modernization Notes

This branch starts the runtime and touch-UI modernization work from
`BoxOfficeProductSets`.

## Runtime targets

- Java: move in stages from the current Java 8 baseline toward a current LTS JDK.
- Java 8 remains pinned for existing production-support copies through their
  startup scripts.
- Java 17 currently builds and runs this modernization branch and is now the
  primary bytecode target.
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
- The obsolete PayPoint `com.sun.net.ssl.internal.ssl.Provider` setup was
  removed so the project compiles with Java 17.

## Build scripts

- `build-java8.bat` is retained for reference, but Java 8 builds are expected to
  fail after the project moved to Java 17 source/target.
- `build-java17.bat` builds with the installed Temurin Java 17 JDK.
- `run-java17.bat` launches `dist/ChromisPOS.jar` with Java 17.

The scripts assume these local install paths:

- `C:\Program Files\Java\jdk1.8.0_202`
- `C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot`
- `C:\Program Files\NetBeans 8.2`

## Known Java 17 warnings

- The tracked `build.xml` overrides ignored NetBeans metadata so Ant compiles
  with `source=17`/`target=17`.
- The tracked `build.xml` now enables `-Xlint:deprecation -Xlint:unchecked` so
  Java 17 warning details are visible during the normal Ant build.
- Java 17 deprecation warnings have been cleared from the normal Ant build.
- Bundled JasperReports viewer/printer compatibility classes still use
  deprecated JasperReports APIs internally, but those warnings are scoped to the
  copied compatibility classes to avoid changing report rendering behavior.
- The first unchecked cleanup batch modernized shared `src-data` Swing value
  models and simple string/list dialogs.
- The second unchecked cleanup batch modernized shared data browsing, dirty
  listener, comparator, and loader helper collections.
- The third unchecked cleanup batch started typed query result conversion in
  admin/system/sales data logic and typed several admin role/resource controls.
- The fourth unchecked cleanup batch typed catalog/box-office list models, tax
  list access, and database/general configuration combo boxes.
- The fifth unchecked cleanup batch typed customer and employee finder list
  models.
- The sixth unchecked cleanup batch typed locale and payment configuration combo
  boxes.
- The seventh unchecked cleanup batch typed peripheral configuration combo
  boxes and printer-list helpers.
- The eighth unchecked cleanup batch typed the Restaurant Setup color controls
  and the nested printer parameter control. All Configuration tabs have now
  been reviewed for unchecked collection warnings.
- The ninth unchecked cleanup batch typed number-key listeners, employee
  break/leave query lists, menu elements, payment maps, and the database cleanup
  selector.
- The tenth unchecked cleanup batch typed CSV column mapping controls,
  category/tax models, and category tracking collections. CSV input now strips
  an optional UTF-8 BOM and preserves selected headers when mapping controls
  refresh. Imports also snapshot the selected tax category and inclusive-price
  option so net price conversion and the saved tax category remain consistent.
  The product editor now refreshes its derived tax-inclusive price after every
  record load, including tax-exempt products. CSV imports now also initialize
  both Box Office flags to `false`; missing values previously left imported
  products half-loaded in the editor and excluded them from normal sales.
- The eleventh unchecked cleanup batch typed report warehouse, stock-level,
  and stock-reason parameter controls. Legacy location query casts are now
  isolated at the `SentenceList` boundary, and the report package no longer
  emits unchecked combo-model warnings.
- The twelfth unchecked cleanup batch typed Box Office product-set, show,
  theatre, feature, and capacity-mode controls. Typed list accessors in
  `DataLogicSales` now keep the legacy query cast boundary out of these screens.
  Box Office show details are now serializable so pending transactions can be
  saved during shutdown; shared-ticket saves also update existing IDs and reject
  incomplete serialized records safely.
- The thirteenth unchecked cleanup batch typed Stock Management and Stock Diary
  warehouse and movement-reason controls. Both screens now use the shared typed
  location accessor in `DataLogicSales` and no longer emit raw combo warnings.
- The fourteenth unchecked cleanup batch typed attribute, attribute-set, and
  attribute-use controls. Their duplicated UI queries now use shared typed
  attribute accessors in `DataLogicSales`.
- Remaining unchecked warnings are concentrated in inventory combo models,
  raw serializer and deserialization calls, and reflection helpers.

## Immediate follow-up

Continue reducing unchecked generic warnings in focused batches, starting with
inventory combo models before changing shared serializer internals.

## Deferred features

- Exclude Box Office products from Stock Management and Stock Diary product
  selection because admission tickets do not carry physical stock.

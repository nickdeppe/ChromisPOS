# ChromisPOS Modernization Notes

This branch starts the runtime and touch-UI modernization work from
`BoxOfficeProductSets`.

## Runtime targets

- Java: move in stages from the current Java 8 baseline toward a current LTS JDK.
- Java 8 remains pinned for existing production-support copies through their
  startup scripts.
- Java 17 builds and runs the production-candidate checkpoint tagged
  `v0.59.0-java17-rc1`.
- Java 21 is now the active modernization target.
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
- `build-java17.bat` remains available for the Java 17 production-candidate
  checkpoint.
- `run-java17.bat` launches `dist/ChromisPOS.jar` with Java 17.
- `build-java21.bat` builds with an installed Java 21 JDK. It checks
  `JAVA21_HOME`, then common Eclipse Adoptium and Oracle/OpenJDK install
  folders.
- `run-java21.bat` launches `dist/ChromisPOS.jar` with Java 21.

The scripts assume these local install paths:

- `C:\Program Files\Java\jdk1.8.0_202`
- `C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot`
- `JAVA21_HOME`, or a Java 21 JDK under `C:\Program Files\Eclipse Adoptium`
  or `C:\Program Files\Java`
- `C:\Program Files\NetBeans 8.2`

## Compiler warnings

- The tracked `build.xml` overrides ignored NetBeans metadata so Ant compiles
  with `source=21`/`target=21`.
- The tracked `build.xml` now enables `-Xlint:deprecation -Xlint:unchecked` so
  warning details are visible during the normal Ant build.
- Java 21 deprecation and unchecked warnings have been cleared from the normal
  Ant build.
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
- The fifteenth unchecked cleanup batch typed category-parent, tax-category,
  customer-tax-category, and parent-tax controls. Shared typed accessors now
  preserve their optional null selections without raw models.
- The sixteenth unchecked cleanup batch typed product, feature, product-list,
  and stock-change import models. Shared typed list accessors now keep their
  database query casts at the `DataLogicSales` boundary.
- The seventeenth unchecked cleanup batch typed the shared `SerializerWrite`
  pipeline. Legacy sentence parameters now cross one documented compatibility
  boundary instead of generating raw calls in every SQL execution path.
- The eighteenth unchecked cleanup batch constrained reflection-backed row
  deserialization to `SerializableRead` classes, removing its raw class and
  constructor call without changing failed-instantiation behavior.
- The nineteenth unchecked cleanup batch typed application bean reflection.
  Both modern `BeanFactory` classes and legacy `AppView` constructors retain
  their existing loading paths without raw `Class` or `Constructor` calls.
- The twentieth unchecked cleanup batch validates serialized ticket and coupon
  line collections, uses typed payment-list copies, and isolates shared-ticket
  query casts at the data-access boundary.
- The twenty-first unchecked cleanup batch types Close Cash payment, category,
  tax, removed-line, and product-sales query results through one local query
  boundary.
- The twenty-second unchecked cleanup batch types the shared QBF comparison
  model and the Box Office product-set, feature, rating, material, and product
  filters. Material products now load through a typed sales-data accessor.
- The twenty-third unchecked cleanup batch types Restaurant floor/place and
  cash-in/cash-out payment-reason models. Floors now load through a shared
  typed sales-data accessor.
- The twenty-fourth unchecked cleanup batch types the database migration
  selector and product finder result list, including its shared renderer.
- The twenty-fifth unchecked cleanup batch types ticket finder results,
  sale/refund and comparison selectors, user choices, and ticket rendering.
- The twenty-sixth unchecked cleanup batch types the legacy SQL browser tree,
  enumeration adapter, table lookup, and result-row model.
- The twenty-seventh unchecked cleanup batch types generic magnetic-card
  track fields while preserving the existing swipe parser state machine.
- The twenty-eighth unchecked cleanup batch types Authorize.Net and BluePay
  emulator XML response maps without changing gateway decisions or requests.
- The twenty-ninth unchecked cleanup batch types Caixa and LinkPoint XML
  response maps while preserving their provider-specific result handling.
- The thirtieth unchecked cleanup batch types PGNET, PayPoint, and Planet
  Authorize URL-encoded response maps, including their nullable values.
- The thirty-first unchecked cleanup batch types live sale ticket display rows
  and XML-defined column metadata without changing rendering or selection.
- The thirty-second unchecked cleanup batch types the raw ESC/POS printer byte
  queue while preserving its existing FIFO behavior and print submission.
- The thirty-third unchecked cleanup batch types promotion query boundaries,
  resource controls, product rows, selection lists, and product rendering.
- The thirty-fourth unchecked cleanup batch types promotion caches and the
  per-transaction promotion execution map. All-products percentage promotions
  now count and discount every eligible sale line, and generated discount lines
  retain the promotion ID needed for recalculation and removal.
- The thirty-fifth unchecked cleanup batch loads report tax logic through the
  shared typed sales-data accessor instead of retaining a raw tax sentence.
- The thirty-sixth unchecked cleanup batch types Jasper viewer save
  contributors, hyperlink listeners and links, reflective constructors, and
  zoom choices.
- The thirty-seventh unchecked cleanup batch types report-field indexes plus
  the main sales panel's tax lists, tax selector, and receipt-report maps.
- The thirty-eighth unchecked cleanup batch types refund ticket lines from
  receipt loading through the catalog switcher and refund controls.
- The thirty-ninth unchecked cleanup batch types product attribute query
  results and the attribute value selector.
- The fortieth unchecked cleanup batch types ticket tax loading and restaurant
  floor and place query results.
- The forty-first unchecked cleanup batch types the remaining product report
  and sales filter category and comparison models.
- The forty-second unchecked cleanup batch types sign correction sets,
  database table vectors, and printer selection, clearing the remaining
  unchecked compiler warnings.
- The Java 21 runtime update replaces deprecated URL and Locale constructors
  with current Java APIs, clearing the new Java 21 deprecation warnings.

## Immediate follow-up

Continue touch UI modernization in focused slices. Start with shared
terminal-wide sizing defaults, then move into the highest-use sale, catalog,
payment, and maintenance screens.

## Touch UI modernization

- The first touch UI slice adds shared Swing defaults for wider scrollbars,
  roomier button margins, larger table/tree/list rows, and more tappable tab
  headers. These defaults are installed after the configured look and feel is
  applied so they affect the main app, standalone configuration window, manual
  sync window, and live look-and-feel changes from Configuration.

## Deferred features

- Exclude Box Office products from Stock Management and Stock Diary product
  selection because admission tickets do not carry physical stock.
- Move Box Office Maintenance items out of the Stock menu and into a dedicated
  Box Office menu.

## Post-modernization roadmap

Begin these projects after the runtime modernization and touch UI phase is
complete:

1. Beautification and branding on dual screen customer display.
2. Shift4 Universal Transaction Gateway integration.
3. New kitchen display integration.
4. Online ticket sales integration.
5. XML and Java syntax highlighting in the Resources editor.
6. Add Cancel buttons to maintenance screens so edits can be abandoned without
   leaving or refreshing the screen.
7. Add Copy buttons to maintenance screens so existing records can be used as
   starting points for new records.
8. Evaluate rebranding the application now that the original Chromis POS
   project is inactive and this modernization significantly changes the product.
9. Add configurable maximum record limits for ticket searches so large
   databases stay responsive during lookup workflows.

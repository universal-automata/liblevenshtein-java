# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).

## [unreleased] - Currently in development

## [2.2.2] - Saturday, April 30, 2016

### Added
- Adds a plain text serializer that serializes dictionaries to
newline-delimited, text files and transducers to property files.
- Adds support for reading dictionaries from streams, paths, files, URIs, and
URLs.
- Adds reference to liblevenshtein CLI.

### Changed
- Restores the sparsity to `DawgNode.isFinal()` by replacing the corresponding
field with a method that always returns `false`, and adding a subclass,
`FinalDawgNode`, that overrides `DawgNode.isFinal()` to always return `true`.
- Moves the generated wiki to `gh-pages`
- Improves the generated, wiki pages.
- Converts the .gitignore to a whitelist from a blacklist

### Fixed
- Issue #40: ProtobufSerializer throws InvalidProtocolBufferException when
  dictionary depth &gt; 100

## [2.2.1] - Monday, April 11, 2016

### Added
- Adds annotations to the position-transition functions.
- Adds AssertJ assertions to the testing suite.

### Changed
- Runs testing suite concurrently.
- Cleans up code with Findbugs and Checkstyle.
- Improves logging messages.

### Removed
- Removes the `shared` submodule.

### Security
- Changes and drops the VersionEye, API key from
[gradle.properties][gradle-properties-2.2.1].

## [2.2.0] - Tuesday, April 5, 2016

### Added
- Adds serializers to-and-from Google Protocol Buffers and Java Bytecode.

## [2.1.4] - Tuesday, April 5, 2016

### Added
- Adds Gradle wrapper, `gradlew`, which should be used instead of the system
`gradle`.
- Adds support for Coverity, VersionEye, Coveralls.
- Adds Github badges for Coverity, VersionEye, Coveralls, Maven Central, and the
MIT license.

## [2.1.3] - Monday, April 4, 2016

### Added
- Displays progress while building DAWGs.

### Fixed
- Fixes issue #6: Dictionary phrases sometimes appear to be dropped

## [2.1.2] - Saturday, July 4, 2015

### Added
- Makes the candidate collections returned from transduction lazy.

### Deprecated
- Deprecates the option to restrict the number of candidates generated.

### Fixed
- Bugfixes and minor changes

## [2.1.1] - Friday, July 3, 2015

### Removed
- Drops object pooling from the factories to make them threadsafe.

### Changed
- Makes the recycle methods of factories optional.

### Fixed
- Bugfixes and minor changes.

## [2.1.0] - Friday, July 3, 2015
- Initial release of the Java port of [liblevenshtein][liblevenshtein].

[unreleased]: https://github.com/universal-automata/liblevenshtein-java/compare/2.2.2...master
[2.2.2]: https://github.com/universal-automata/liblevenshtein-java/compare/2.2.1...2.2.2
[2.2.1]: https://github.com/universal-automata/liblevenshtein-java/compare/2.2.0...2.2.1
[2.2.0]: https://github.com/universal-automata/liblevenshtein-java/compare/2.1.4...2.2.0
[2.1.4]: https://github.com/universal-automata/liblevenshtein-java/compare/2.1.3...2.1.4
[2.1.3]: https://github.com/universal-automata/liblevenshtein-java/compare/2.1.2...2.1.3
[2.1.2]: https://github.com/universal-automata/liblevenshtein-java/compare/2.1.1...2.1.2
[2.1.1]: https://github.com/universal-automata/liblevenshtein-java/compare/2.1.0...2.1.1
[2.1.0]: https://github.com/universal-automata/liblevenshtein-java/compare/bdca39b3261a510095e12daf9af2f1bde846fee4...2.1.0

[liblevenshtein]: https://github.com/universal-automata/liblevenshtein "liblevenshtein"

[gradle-properties-2.2.1]: https://github.com/universal-automata/liblevenshtein-java/blob/2.2.1/gradle.properties "gradle.properties"

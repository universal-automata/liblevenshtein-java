building(groupId, artifactId, version) ::= <<
[Home](Home) > [Building](building) > Java

Run `gradle jar`.  That's it!  Gradle will download the required dependencies,
compile the sources and zip them into a Jar.  You may find the Jar at the
following location: `build/libs/liblevenshtein-$version$.jar`

```
% gradle jar
:compileJava
:processResources UP-TO-DATE
:classes
:jar

BUILD SUCCESSFUL

Total time: 4.116 secs

% tree build/libs
build/libs
└── liblevenshtein-$version$.jar

0 directories, 1 file
```

[liblevenshtein](https://github.com/universal-automata/liblevenshtein) is maintained by [@dylon](https://github.com/dylon)
>>
/* vim: set ft=ghmarkdown: */

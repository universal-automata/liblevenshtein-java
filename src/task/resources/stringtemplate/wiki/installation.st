installation(groupId, artifactId, version, gradleVersion, javaSourceVersion, javaTargetVersion) ::= <<
[Home](Home) > [Installation](installation) > Java

# Gradle

Add a dependency on $groupId$:$artifactId$:$version$ to your project's <code>build.gradle</code>:

```groovy
// ...
dependencies {
  compile '$groupId$:$artifactId$:$version$'
}
// ...
```

# Maven

Add a dependency on $groupId$:$artifactId$:$version$ to your project's POM:

```xml
<project>
  <!-- ... -->
  <dependencies>
    <!-- ... -->
    <dependency>
      <groupId>$groupId$</groupId>
      <artifactId>$artifactId$</artifactId>
      <version>$version$</version>
    </dependency>
    <!-- ... -->
  </dependencies>
  <!-- ... -->
</project>
```

# Git

Install Gradle >= $gradleVersion$ and checkout the Java repository:

```
% git clone https://github.com/universal-automata/liblevenshtein-java.git
Cloning into 'liblevenshtein-java'...
remote: Counting objects: 1340, done.
remote: Compressing objects: 100% (22/22), done.
remote: Total 1340 (delta 5), reused 0 (delta 0), pack-reused 1307
Receiving objects: 100% (1340/1340), 194.09 KiB | 0 bytes/s, done.
Resolving deltas: 100% (523/523), done.
Checking connectivity... done.

% cd liblevenshtein-java

% git submodule init
Submodule 'shared' (https://github.com/universal-automata/liblevenshtein-shared.git) registered for path 'shared'

% git submodule update
Cloning into 'shared'...
remote: Counting objects: 26, done.
Unpacking objects: 100% (26/26), done.
remote: Total 26 (delta 0), reused 0 (delta 0), pack-reused 26
Checking connectivity... done.
Submodule path 'shared': checked out 'a362d426e3f031aaeedb611f1cb35b92785eef00'
```

Then, [build the library](building.java).

[liblevenshtein](https://github.com/universal-automata/liblevenshtein) is maintained by [@dylon](https://github.com/dylon)
>>
/* vim: set ft=ghmarkdown: */

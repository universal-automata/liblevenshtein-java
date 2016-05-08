all(project, cmd, padding) ::= <<
$padding$# Installation

$padding$## Latest, Development Release

Add a Maven dependency on [Artifactory][artifactory-repo].  For example, in a
[Gradle][gradle-home] project, you would modify your `repositories` as follows:

```groovy
repositories {
  maven {
    url '$project.maven.artifactoryRepo$'
  }
}
```

$padding$## Latest, Stable Release

Add a Maven dependency on one of the following:
- [Maven Central][maven-repo]
- [JCenter][jcenter-repo]
- [Bintray][bintray-repo]

$padding$## Maven

$maven(project, cmd)$

$padding$## Apache Buildr

$buildr(project, cmd)$

$padding$## Apache Ivy

$ivy(project, cmd)$

$padding$## Groovy Grape

$grape(project, cmd)$

$padding$## Gradle / Grails

$gradle(project, cmd)$

$padding$## Scala SBT

$sbt(project, cmd)$

$padding$## Leiningen

$lein(project, cmd)$

$padding$## Git

$git(project, cmd)$
>>
/* vim: set ft=ghmarkdown: */

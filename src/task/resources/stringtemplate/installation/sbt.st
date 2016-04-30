sbt(project, cmd) ::= <<
```scala
libraryDependencies += "$project.maven.groupId$" % "$project.maven.artifactId$" % "$project.maven.version$"
```
>>
/* vim: set ft=ghmarkdown: */

maven(project, cmd) ::= <<
```xml
<dependency>
  <groupId>$project.maven.groupId$</groupId>
  <artifactId>$project.maven.artifactId$</artifactId>
  <version>$project.maven.version$</version>
</dependency>
```
>>
/* vim: set ft=ghmarkdown: */

gradle(project, cmd) ::= <<
```groovy
compile '$project.maven.groupId$:$project.maven.artifactId$:$project.maven.version$'
```
>>
/* vim: set ft=ghmarkdown: */

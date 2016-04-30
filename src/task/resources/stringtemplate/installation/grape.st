grape(project, cmd) ::= <<
```groovy
@Grapes(
@Grab(group='$project.maven.groupId$', module='$project.maven.artifactId$', version='$project.maven.version$')
)
```
>>
/* vim: set ft=ghmarkdown: */

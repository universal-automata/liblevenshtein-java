building(project, cmd) ::= <<
[Index](index.md) > [Building](building.md)

Run `$cmd.gradleJar.key$`.  That's it!  Gradle will download the required dependencies,
compile the sources and zip them into a Jar.  You may find the Jar at the
following location: `build/libs/$project.maven.artifactId$-$project.maven.version$.jar`

$shell(project, cmd, ["gradleJar", "treeBuildLibs"])$

$footer(project, cmd)$
>>
/* vim: set ft=ghmarkdown: */

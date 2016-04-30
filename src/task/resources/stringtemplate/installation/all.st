all(project, cmd, padding) ::= <<
$padding$# Installation

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

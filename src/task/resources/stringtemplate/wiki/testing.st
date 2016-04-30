testing(project, cmd) ::= <<
[Index](index) > [Testing](testing)

# Testing

Run `gradle test`:

$shell(project, cmd, ["gradleTest"])$

Note that if you want to ensure you are not testing against build artifacts,
please run `gradle clean test`, instead.  It will be a little slower on
successive runs, but will ensure no old, Java classes are left in the `build`
directory.

# Preparing for release

Before releasing your code, or checking it back into the shared repositories,
you should run all the checks against it and clean up any errors:

$shell(project, cmd, ["gradleCheck"])$

$footer(project, cmd)$
>>
/* vim: set ft=ghmarkdown: */

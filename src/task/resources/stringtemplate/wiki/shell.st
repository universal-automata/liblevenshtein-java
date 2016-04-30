shell(project, cmd, commands) ::= <<
```
$commands:{command|% $cmd.(command).key$$if(cmd.(command).value)$
$cmd.(command).value$
$else$
$endif$}$
```
>>
/* vim: set ft=ghmarkdown: */

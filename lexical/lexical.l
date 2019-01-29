%{
#include<stdio.h>
int word=0,line=0;
%}

%%
"import"|"String"|"args[]" {printf("%s Keywords\n",yytext);}
"public"|"System"|"out"|"println" {printf("%s Keywords\n",yytext);}
"static" {printf("%s Keywords\n",yytext);}
"void" {printf("%s Keywords\n",yytext);}
"class" {printf("%s Keywords\n",yytext);}
[a-zA-z]+" ."+[" "a-zA-z,.]+"*" {printf("%s HeaderFile\n",yytext);}
"<"|">"|"{"|"}"|"("|")" {printf("%s brackets\n",yytext);}
"int" {printf("%s Keywords\n",yytext);}
[a-zA-Z]+ {printf("%s variable\n",yytext);}
; {printf("%s Semi colon\n",yytext);}
"="|"-"|"+"|"*" {printf("%s sign\n",yytext);}
[0-9]+ {printf("%s number\n",yytext);}
[,] {printf("%s Comma\n",yytext);}
\"[a-zA-z|" "]+\" {printf("%s String\n",yytext);}
"." {printf("%s Dot\n",yytext);}
\n {line++;printf("\n");}
[" "\t] {word++;}
%%
int main()
{
yyin=fopen("try.java","r");
yylex();
printf("\n No of lines : %d",line);
printf("\n No of words : %d",word);
return 1;
}
int yywrap()
{return 1;}

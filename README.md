# Programming Language Translation

Solved coding problems for the [Programming Language Translation](https://www.fer.unizg.hr/predmet/ppj) course at **FER**.

## Summary

  - [PJ language overview](#pj-language-overview)
  - [Example of analysis](#example-of-analysis)
    - [Lexical analysis](#lexical-analysis)
    - [Syntax analysis](#syntax-analysis)





## PJ language overview

The lexical and syntax analysis in this project is made for the simple made up programming language _PJ_.


The LL(1)-grammar of the language is given below.

    <program> ::= <lista_naredbi>
    <lista_naredbi> ::= <naredba> <lista_naredbi>
    <lista_naredbi> ::= $
    <naredba> ::= <naredba_pridruzivanja>
    <naredba> ::= <za_petlja>
    <naredba_pridruzivanja> ::= IDN OP_PRIDRUZI <E>
    <za_petlja> ::= KR_ZA IDN KR_OD <E> KR_DO <E> <lista_naredbi> KR_AZ
    <E> ::= <T> <E_lista>
    <E_lista> ::= OP_PLUS <E>
    <E_lista> ::= OP_MINUS <E>
    <E_lista> ::= $
    <T> ::= <P> <T_lista>
    <T_lista> ::= OP_PUTA <T>
    <T_lista> ::= OP_DIJELI <T>
    <T_lista> ::= $
    <P> ::= OP_PLUS <P>
    <P> ::= OP_MINUS <P>
    <P> ::= L_ZAGRADA <E> D_ZAGRADA
    <P> ::= IDN
    <P> ::= BROJ





## Example of analysis

Example of code written in _PJ_ can be seen in the following chunk:

```
a = 0
za i od -3 do 5
    a = a + i
az
```

\
### Lexical analysis
The result of the lexical analysis is displayed as a token, its type and its location in the source code of the program (line index).
For the previous example it is:
```
IDN 1 a
OP_PRIDRUZI 1 =
BROJ 1 0
KR_ZA 2 za
IDN 2 i
KR_OD 2 od
OP_MINUS 2 -
BROJ 2 3
KR_DO 2 do
BROJ 2 5
IDN 3 a
OP_PRIDRUZI 3 =
IDN 3 a
OP_PLUS 3 +
IDN 3 i
KR_AZ 4 az
``` 

\
### Syntax analysis
The result of the syntax analysis is displayed as a generative tree of uniform characters following the language grammar. The generative tree shows how some string formed from the initial unfinished sign of the grammar. 
```
<program>
 <lista_naredbi>
  <naredba>
   <naredba_pridruzivanja>
    IDN 1 a
    OP_PRIDRUZI 1 =
    <E>
     <T>
      <P>
       BROJ 1 0
      <T_lista>
       $
     <E_lista>
      $
  <lista_naredbi>
   <naredba>
    <za_petlja>
     KR_ZA 2 za
     IDN 2 i
     KR_OD 2 od
     <E>
      <T>
       <P>
        OP_MINUS 2 -
        <P>
         BROJ 2 3
       <T_lista>
        $
      <E_lista>
       $
     KR_DO 2 do
     <E>
      <T>
       <P>
        BROJ 2 5
       <T_lista>
        $
      <E_lista>
       $
     <lista_naredbi>
      <naredba>
       <naredba_pridruzivanja>
        IDN 3 a
        OP_PRIDRUZI 3 =
        <E>
         <T>
          <P>
           IDN 3 a
          <T_lista>
           $
         <E_lista>
          OP_PLUS 3 +
          <E>
           <T>
            <P>
             IDN 3 i
            <T_lista>
             $
           <E_lista>
            $
      <lista_naredbi>
       $
     KR_AZ 4 az
   <lista_naredbi>
    $
```

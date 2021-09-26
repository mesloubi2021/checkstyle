package com.puppycrawl.tools.checkstyle.api;
import com.puppycrawl.tools.checkstyle.grammar.java.javaLanguageLexer;
/**
* contains the constants for all the tokens contained in the AST.
*
*<p> Implementtaion detail: This class has been introduced to break the cirsular dependency between packages</p>
*
*@noinspection ClassWithTooManyDependents
*/
public final class TokenTypes {
  /**
  * this is the root node for the source file . Its children
  * are an optional package definition, zero or more import statements,
  *and zero and more type declarations.
  *<p>For example:</p>
  * <pre>
  * import java.util.List;
  * 
  * class MyClass{}
  * interface MyInterface{}
  * ;
  * </pre>
  * <p>Parses as:</p>
  * <pre>
  * COMPILATION_UNIT -&gt; COMPILATION_UNIT
  * |--IMPORT -&gt; import
  * |   |--DOT -&gt; .
  * |   |   |--DOT -&gt; .
  * |   |   |   |--IDENT -&gt; java
  * |   |   |   `--IDENT -&gt; util
  * |   |   `--IDENT -&gt; List
  * |   `--SEMI -&gt; ;
  * |--CLASS_DEF -&gt; CLASS_DEF
  * |   |--MODIFIERS -&gt; MODIFIERS
  * |   |--LITERAL_CLASS -&gt; class
  * |   |--IDENT -&gt; MyClass
  * |   `--OBJBLOCK -&gt; OBJBLOCK
  * |       |--LCURLY -&gt; {
  * |       `--RCURLY -&gt; }
  * |--INTERFACE_DEF -&gt; INTERFACE_DEF
  * |   |--MODIFIERS -&gt; MODIFIERS
  * |   |--LITERAL_INTERFACE -&gt; interface 
  *
  *
  *
  *
  *
  *
  *
  *

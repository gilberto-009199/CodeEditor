package com.gilberto009199.editor.providers;

import com.gilberto009199.editor.assets.IconType;

import java.util.Arrays;

public enum PoliglotType {
	
    JAVASCRIPT(
            IconType.JAVASCRIPT,
    """
// example javascript
let name = prompt("Qual é o seu nome?");
console.log("Olá, " + name);
alert("oi, "+ name);
	""",
            new String[]{
                    "var", "let", "const", "if", "else", "for", "while", "do", "switch",
                    "case", "break", "continue", "function", "return", "class", "this",
                    "new", "true", "false", "null", "undefined", "try", "catch",
                    "throw", "import", "export", "async", "await", "yield"
            },
            new String[]{
                    // Aritméticos
                    "\\+", "-", "\\*", "/", "%",

                    // Atribuição
                    "=", "\\+=", "-=", "\\*=", "/=", "%=",

                    // Comparação
                    "==", "!=", "===", "!==", ">", "<", ">=", "<=",

                    // Lógicos
                    "&&", "\\|\\|", "!",

                    // Bit a bit
                    "&", "\\|", "\\^", "~", "<<", ">>",

                    // Unários
                    "\\+\\+", "--",

                    // Ternário e acesso
                    "\\?", "\\:", "\\.", "\\?\\.",

                    // Especiais
                    "\\.\\.\\.", "\\?\\?", "typeof", "instanceof"
            }
    ),

    PYTHON(
            IconType.PYTHON,
    """
# example python
name = input("Qual seu nome? ")
print("Olá, "+ name)
	""",
            new String[]{
                    "if", "else", "elif", "for", "while", "def", "class", "return",
                    "import", "from", "as", "try", "except", "finally", "with",
                      "True", "False", "None"
            },
            new String[]{
                    // Aritméticos
                    "\\+", "-", "\\*", "/", "//", "%", "\\*\\*", "@",

                    // Atribuição
                    "=", "\\+=", "-=", "\\*=", "/=", "//=", "%=", "\\*\\*=", "@=",
                    "&=", "\\|=", "\\^=", "<<=", ">>=",

                    // Comparação
                    "==", "!=", ">", "<", ">=", "<=",

                    // Lógicos
                    "and", "or", "not",

                    // Bit a bit
                    "&", "\\|", "\\^", "~", "<<", ">>",

                    // Identidade e associação
                    "is", "is not", "in", "not in",

                    // Walrus operator (Python 3.8+)
                    ":=",

                    // Acesso e indexação
                    "\\.", "\\[", "\\]",

                    // Chamada de função
                    "\\(", "\\)",

                    // Separadores
                    ",", ":", ";",

                    // Desempacotamento
                    "\\*", "\\*\\*"
            }
    );
	
	public String example;
	public IconType iconType;
    public String[]  KEYWORDS;
    public String[] OPERATORS;

    PoliglotType(IconType iconType,
                 String example,
                 String[]  KEYWORDS,
                 String[] OPERATORS) {
        this.example = example;
        this.iconType = iconType;
        this.KEYWORDS = KEYWORDS;
        this.OPERATORS = OPERATORS;
	}
	
}

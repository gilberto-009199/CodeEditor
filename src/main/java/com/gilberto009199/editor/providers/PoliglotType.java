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
                    "new", "typeof", "true", "false", "null", "undefined", "try", "catch",
                    "throw", "import", "export", "async", "await", "yield"
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
                    "and", "or", "not", "in", "is", "True", "False", "None"
            }
);
	
	public String example;
	public IconType iconType;
    public String[]  KEYWORDS;

	PoliglotType(IconType iconType, String example, String[]  KEYWORDS) {
        this.example = example;
        this.iconType = iconType;
        this.KEYWORDS = KEYWORDS;
	}
	
}

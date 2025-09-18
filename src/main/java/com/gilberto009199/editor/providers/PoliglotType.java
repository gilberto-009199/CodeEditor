package com.gilberto009199.editor.providers;

import com.gilberto009199.editor.assets.IconType;

public enum PoliglotType {
	
    JAVASCRIPT(
    """
// example javascript
let name = prompt("Qual é o seu nome?");
console.log("Olá, " + name);
alert("oi, "+ name);
	""",
    IconType.JAVASCRIPT
),

    PYTHON(
    """
# example python
name = input("Qual seu nome? ")
print("Olá, "+ name)
	""",
    IconType.PYTHON
);
	
	public String example;
	public IconType iconType;

	PoliglotType(String example, IconType iconType) {
        this.example = example;
        this.iconType = iconType;
	}
	
}

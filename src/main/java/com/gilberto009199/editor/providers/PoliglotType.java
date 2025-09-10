package com.gilberto009199.editor.providers;

public enum PoliglotType {
	
    JAVASCRIPT(
    """
// example javascript
let name = prompt("Qual é o seu nome?");
console.log("Olá, " + name);
alert("oi, "+ name);
	"""),

    PYTHON(
    """
# example python
name = input("Qual seu nome? ")
print("Olá, "+ name)
	""");
	
	public String example;
	
	PoliglotType(String example) {
		this.example = example;
	}
	
}

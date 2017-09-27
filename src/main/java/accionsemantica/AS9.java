package accionsemantica;

public class AS9 extends AccionSemantica {
    public AS9(TablaSimbolos t) {
        super(t);
    }

    @Override
    public String aplicarAccion(char a, int indice) {

        token.append(a);
        //fijarse si las cadenas de 1 linea hay que agregarlas a la tabla de simbolos -> SI
        if (!tablita.contains("CADENA", token.toString()))
            tablita.add("CADENA", token.toString());

//        System.out.println("CADENA:" + " " + token.toString());

        this.indice = indice;

        return "CADENA->" + token.toString();
    }
}

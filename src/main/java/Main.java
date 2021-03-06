import generadorcodigo.Generador;
import lexer.*;
import parser.Parser;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * <h3>
 * Clase utilizada para iniciar la ejecución del compilador.
 * </h3>
 *
 * <p>
 * El funcionamiento se basa en una CLI en la que se solicita ingresar por consola el archivo a compilar, en caso de
 * cancelar la ejecución, ingresar "EXIT". En caso de producirse errores durante la etapa de compilación, se informarán
 * los mismos por consola. En caso de querer volver a compilar, se debe ejecutar nuevamente el archivo por lotes.
 * </p>
 *
 * <p>
 * Para iniciar la ejecución, abrir el archivo por lotes "run-compiler.bat" para mayor facilidad de uso.
 * </p>
 *
 * <p>
 * Otra alternativa de ejecución es, dentro de una consola, correr el siguiente comando:
 * "java -jar LeCompilateurMagnifique.jar"
 * </p>
 *
 * @author Bianco Martín, Di Pietro Esteban, Serrano Francisco
 */
public class Main {

    private static final String DIR_MATRIZ_ESTADOS = "matriz-estados.txt";
    private static final String DIR_MATRIZ_ACC_SEMANTICAS = "matriz-acc-semanticas.txt";

    /*
        // COSAS MENORES
        TODO: Una vez generado el assembler, agregar comentarios a cada línea generada para explicar un poco la movida
        TODO: Generar JavaDoc
        TODO: Empaquetar todo

        TODO: Chequear errores semánticos 6 y 7
     */

    /**
     * Inicio de la ejecución.
     *
     * @param args Argumentos de la aplicación enviados por la línea de comandos.
     */
    public static void main(String[] args) {

//        runCompiler("casos-prueba/Semanticos6.txt");
        runCompiler("test14-m.txt");

//        try {
//            runCompiler(args[0]);
//        } catch (IndexOutOfBoundsException e) {
//            System.err.println("Se debe pasar un archivo como parámetro por la línea de comandos");
//        }
    }

    /**
     * Método privado auxiliar para utilizar el compilador en desarrollo. Esto es efectuar la compilación para
     * un archivo en particular, pasado por parámetro.
     *
     * @param fileDir Ruta del archivo a compilar.
     */
    private static void runCompiler(String fileDir) {
        TablaSimbolos tablaSimbolos = new TablaSimbolos();

        Lexer lexer;
        try {
            lexer = new Lexer(fileDir, DIR_MATRIZ_ESTADOS, DIR_MATRIZ_ACC_SEMANTICAS, tablaSimbolos);
        } catch (FileNotFoundException e) {
            System.err.println("\nArchivo no encontrado");
            return;
        }

        Parser parser = new Parser();
        parser.setLexico(lexer);
        parser.setTablaSimbolos(tablaSimbolos);

        int resultadoParsing = parser.yyparse();

        System.out.println("\nRESULTADO DEL PARSING: " + resultadoParsing);

        System.out.println("\nERRORES");
        for (String errorlexico : tablaSimbolos.getErroresLexicos())
            System.out.println(errorlexico);
        for (String error : parser.getErrores())
            System.out.println(error);

        if (resultadoParsing == 1 || parser.getErrores().size() != 0)
            return;

        System.out.println("\n" + tablaSimbolos);

        System.out.println("\nTERCETOS");
        for (Terceto terceto : parser.getTercetos())
            System.out.println(terceto);

        System.out.println("\nASSEMBLER");
        Generador generador = new Generador(parser.getTercetos(), tablaSimbolos);
        generador.generateAssembler();
        for (String inst : generador.getListaInstrucciones())
            System.out.println(inst);

        generador.buildFile(fileDir + ".asm");
    }
}


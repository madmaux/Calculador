package org.mqu.calculadora.constants;

public enum Operador {
    NA(""),
    SUMA("+"),
    RESTA("-"),
    MULTIPLICACION("*"),
    DIVISION("/"),
    POTENCIA("^"),
    RAIZ_CUADRADA("SQRT"),
    IGUAL("="),
    BORRAR("C"),
    PUNTO(".");

    private final String val;

    Operador(final String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }

    public static Operador getByVal(String val) {
        for(Operador operador: values()){
            if (operador.val.toLowerCase().equals(val.toLowerCase())) {
                return operador;
            }
        }

        throw new IllegalArgumentException(val);
    }
}

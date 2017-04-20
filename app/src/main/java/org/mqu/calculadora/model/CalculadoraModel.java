package org.mqu.calculadora.model;

import static org.mqu.calculadora.constants.Operador.*;
import static java.lang.Math.*;

import org.mqu.calculadora.constants.Operador;

public class CalculadoraModel {
    private Double numero1;
    private Double numero2;
    private Operador operador;

    public CalculadoraModel() {
        this.operador = NA;
    }

    public Double getNumero1() {
        return numero1;
    }

    public void setNumero1(Double numero1) {
        this.numero1 = numero1;
    }

    public Double getNumero2() {
        return numero2;
    }

    public void setNumero2(Double numero2) {
        this.numero2 = numero2;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Double getResult() {
        Double result = new Double(0);
        switch (this.operador) {
            case SUMA:
                result = this.getNumero1() + this.getNumero2();
                break;
            case RESTA:
                result = this.getNumero1() - this.getNumero2();
                break;
            case MULTIPLICACION:
                result = this.getNumero1() * this.getNumero2();
                break;
            case DIVISION:
                result = this.getNumero1() / this.getNumero2();
                break;
            case POTENCIA:
                result = pow(this.numero1, this.numero2);
                break;
            case RAIZ_CUADRADA:
                result = sqrt(this.numero1);
        }
        return result;
    }

    public void limpiar() {
        this.numero1 = null;
        this.numero2 = null;
        this.operador = NA;
    }
}

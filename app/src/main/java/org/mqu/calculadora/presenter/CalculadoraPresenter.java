package org.mqu.calculadora.presenter;

import static org.mqu.calculadora.view.CalculadoraView.*;
import static org.mqu.calculadora.constants.Operador.*;

import com.squareup.otto.Subscribe;

import org.mqu.calculadora.constants.Operador;
import org.mqu.calculadora.model.CalculadoraModel;
import org.mqu.calculadora.view.CalculadoraView;

public class CalculadoraPresenter {
    private CalculadoraModel calculadoraModel;
    private CalculadoraView calculadoraView;

    public CalculadoraPresenter(CalculadoraModel model, CalculadoraView view) {
        this.calculadoraModel = model;
        this.calculadoraView = view;
    }

    @Subscribe
    public void buttonOnCLick(ButtonOnClickEvent event) {
        String valorVisor = this.calculadoraView.getVisor();
        Operador operador = this.calculadoraModel.getOperador();

        if ("0".equals(valorVisor) || operador.getVal().equals(valorVisor)) {
            this.calculadoraView.limpiarVisor();
        }

        if (!esNumero(event.getButtonText())) {
            switch (getByVal(event.getButtonText())) {
                case NA:
                    break;
                case BORRAR:
                    this.calculadoraModel.limpiar();
                    this.calculadoraView.limpiarVisor();
                    this.calculadoraView.setVisor("0");
                    return;
                case PUNTO:
                    if (valorVisor.contains(".")) {
                        return;
                    }
                    break;
                case RAIZ_CUADRADA:
                    this.calculadoraModel.setNumero1(sustituirVacioPorCero(valorVisor));
                    this.calculadoraModel.setOperador(getByVal(event.getButtonText()));
                    this.devolverResultado();
                    return;
                case IGUAL:
                    this.calculadoraModel.setNumero2(sustituirVacioPorCero(valorVisor));
                    this.devolverResultado();
                    return;
                default:
                    Double numero = sustituirVacioPorCero(valorVisor);
                    if (this.calculadoraModel.getNumero1() != null && operador != NA) {
                        this.calculadoraModel.setNumero2(sustituirVacioPorCero(valorVisor));
                        numero = this.calculadoraModel.getResult();
                    }
                    this.calculadoraModel.setNumero1(numero);
                    this.calculadoraModel.setOperador(getByVal(event.getButtonText()));
                    this.calculadoraView.limpiarVisor();
            }
        }
        this.calculadoraView.setVisor(event.getButtonText());
    }

    private void devolverResultado() {
        Double result = this.calculadoraModel.getResult();
        this.calculadoraView.limpiarVisor();
        this.calculadoraView.setVisor(result.toString());
        this.calculadoraModel.limpiar();
    }

    private Double sustituirVacioPorCero(String numero) {
        return "".equals(numero) ? 0 : Double.parseDouble(numero);
    }

    private boolean esNumero(String numero) {
        boolean result = false;
        try {
            Double.parseDouble(numero);
            result = true;
        } catch (NumberFormatException e) {

        }
        return  result;
    }
}

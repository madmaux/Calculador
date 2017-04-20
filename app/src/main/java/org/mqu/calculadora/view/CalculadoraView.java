package org.mqu.calculadora.view;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Bus;

import org.mqu.calculadora.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculadoraView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.visor)
    public TextView txtVisor;

    public CalculadoraView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    @OnClick({R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
            R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonPunto, R.id.buttonIgual,
            R.id.buttonSuma, R.id.buttonResta, R.id.buttonMultiplica, R.id.buttonDivide, R.id.buttonPotencia,
            R.id.buttonRaiz, R.id.buttonC
    })
    public void buttonOnClick(Button button) {
        bus.post(new ButtonOnClickEvent(button.getText().toString()));
    }

    public void setVisor(String valor) {
        this.txtVisor.setText(this.txtVisor.getText().toString().concat(valor));
    }

    public String getVisor() {
        return this.txtVisor.getText().toString();
    }

    public void limpiarVisor() {
        this.txtVisor.setText("");
    }

    public static class ButtonOnClickEvent {
        private final String buttonText;
        public ButtonOnClickEvent(String buttonText) {
            this.buttonText = buttonText;
        }

        public String getButtonText() {
            return this.buttonText;
        }
    }
}

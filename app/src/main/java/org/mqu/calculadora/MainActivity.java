package org.mqu.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.mqu.calculadora.model.CalculadoraModel;
import org.mqu.calculadora.presenter.CalculadoraPresenter;
import org.mqu.calculadora.utils.BusProvider;
import org.mqu.calculadora.view.CalculadoraView;

public class MainActivity extends AppCompatActivity {
    private CalculadoraPresenter calculadoraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.calculadoraPresenter = new CalculadoraPresenter(new CalculadoraModel(), new CalculadoraView(this, BusProvider.getInstance()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.register(this.calculadoraPresenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.unregister(this.calculadoraPresenter);
    }
}

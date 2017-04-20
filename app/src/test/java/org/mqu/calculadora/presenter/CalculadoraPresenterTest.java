package org.mqu.calculadora.presenter;

import static org.mqu.calculadora.view.CalculadoraView.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.mqu.calculadora.constants.Operador.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.mqu.calculadora.model.CalculadoraModel;
import org.mqu.calculadora.view.CalculadoraView;

@RunWith(MockitoJUnitRunner.class)
public class CalculadoraPresenterTest {
    private CalculadoraPresenter testee;

    private CalculadoraModel modelMocked;

    @Mock
    private CalculadoraView viewMocked;

    @Before
    public void init() {
        this.modelMocked = new CalculadoraModel();
        this.testee = new CalculadoraPresenter(modelMocked, viewMocked);
    }

    @Test
    public void presionarNumero() {
        String buttonNumero1 = "1";

        testee.buttonOnCLick(new ButtonOnClickEvent(buttonNumero1));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor("1");
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarMasDeUnNumero() {
        String buttonNumero2 = "2";
        String buttonNumero3 = "3";

        testee.buttonOnCLick(new ButtonOnClickEvent(buttonNumero2));
        testee.buttonOnCLick(new ButtonOnClickEvent(buttonNumero3));

        verify(viewMocked, times(2)).getVisor();
        verify(viewMocked, atLeast(2)).setVisor(anyString());
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarOperadorAritmetico() {
        Double numeroVisor = 5.0;

        when(viewMocked.getVisor()).thenReturn(numeroVisor.toString());

        testee.buttonOnCLick(new ButtonOnClickEvent(SUMA.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor("+");
        verify(viewMocked, atLeast(1)).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);

        assertEquals(modelMocked.getNumero1(), numeroVisor);
    }

    @Test
    public void presionarIgual() {
        Double resultado = 10.0;
        Double numeroVisor = 5.0;
        modelMocked.setNumero1(numeroVisor);
        modelMocked.setOperador(SUMA);

        when(viewMocked.getVisor()).thenReturn(numeroVisor.toString());

        testee.buttonOnCLick(new ButtonOnClickEvent(IGUAL.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(resultado.toString());
        verify(viewMocked, atLeast(1)).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);

        assertNull(modelMocked.getNumero1());
        assertNull(modelMocked.getNumero2());
        assertEquals(modelMocked.getOperador(), NA);
    }

    @Test
    public void presionarNumeroDespuesOperadorAritmetico() {
        Double numeroVisor = 2.0;
        modelMocked.setOperador(SUMA);

        when(viewMocked.getVisor()).thenReturn(SUMA.getVal());

        testee.buttonOnCLick(new ButtonOnClickEvent(numeroVisor.toString()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(numeroVisor.toString());
        verify(viewMocked, atLeast(1)).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarBotonBorrar() {
        testee.buttonOnCLick(new ButtonOnClickEvent(BORRAR.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor("0");
        verify(viewMocked).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);

        assertNull(modelMocked.getNumero1());
        assertNull(modelMocked.getNumero2());
        assertEquals(modelMocked.getOperador(), NA);
    }

    @Test
    public void presionarBotonPuntonConUnPuntoExistenteEnElVisor() {
        String numeroDecimal = "1.0";

        when(viewMocked.getVisor()).thenReturn(numeroDecimal);

        testee.buttonOnCLick(new ButtonOnClickEvent(PUNTO.getVal()));

        verify(viewMocked).getVisor();
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarBotonPuntonSinPuntoEnElVisor() {
        String numero = "1";

        when(viewMocked.getVisor()).thenReturn(numero);

        testee.buttonOnCLick(new ButtonOnClickEvent(PUNTO.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(".");
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarOperadorRaizCuadrada() {
        Double numero = 9.0;

        when(viewMocked.getVisor()).thenReturn(numero.toString());

        testee.buttonOnCLick(new ButtonOnClickEvent(RAIZ_CUADRADA.getVal()));
        verify(viewMocked).getVisor();
        verify(viewMocked).limpiarVisor();
        verify(viewMocked).setVisor("3.0");
        verifyNoMoreInteractions(viewMocked);

        assertNull(modelMocked.getNumero1());
        assertNull(modelMocked.getNumero2());
        assertEquals(modelMocked.getOperador(), NA);
    }
}

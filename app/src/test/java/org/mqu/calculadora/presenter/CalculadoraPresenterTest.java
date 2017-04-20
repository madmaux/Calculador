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
    private static final String SIGNO_SUMA = "+";
    private static final String SIGNO_DECIMAL = ".";
    private static final String NUMERO_0 = "0";
    private static final String NUMERO_1 = "1";
    private static final String NUMERO_2 = "2";
    private static final String NUMERO_3 = "3";
    private static final Double NUMERO_DEC_1 = 1.0;
    private static final Double NUMERO_DEC_2 = 2.0;
    private static final Double NUMERO_DEC_3 = 3.0;
    private static final Double NUMERO_DEC_5 = 5.0;
    private static final Double NUMERO_DEC_9 = 9.0;
    private static final Double NUMERO_DEC_10 = 10.0;
    private static final int ONE_TIME = 1;
    private static final int TWO_TIMES = 2;

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
        testee.buttonOnCLick(new ButtonOnClickEvent(NUMERO_1));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(NUMERO_1);
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarMasDeUnNumero() {
        testee.buttonOnCLick(new ButtonOnClickEvent(NUMERO_2));
        testee.buttonOnCLick(new ButtonOnClickEvent(NUMERO_3));

        verify(viewMocked, times(TWO_TIMES)).getVisor();
        verify(viewMocked, atLeast(TWO_TIMES)).setVisor(anyString());
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarOperadorAritmetico() {
        when(viewMocked.getVisor()).thenReturn(NUMERO_DEC_5.toString());

        testee.buttonOnCLick(new ButtonOnClickEvent(SUMA.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(SIGNO_SUMA);
        verify(viewMocked, atLeast(ONE_TIME)).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);

        assertEquals(modelMocked.getNumero1(), NUMERO_DEC_5);
    }

    @Test
    public void presionarIgual() {
        modelMocked.setNumero1(NUMERO_DEC_5);
        modelMocked.setOperador(SUMA);

        when(viewMocked.getVisor()).thenReturn(NUMERO_DEC_5.toString());

        testee.buttonOnCLick(new ButtonOnClickEvent(IGUAL.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(NUMERO_DEC_10.toString());
        verify(viewMocked, atLeast(ONE_TIME)).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);

        assertNull(modelMocked.getNumero1());
        assertNull(modelMocked.getNumero2());
        assertEquals(modelMocked.getOperador(), NA);
    }

    @Test
    public void presionarNumeroDespuesOperadorAritmetico() {
        modelMocked.setOperador(SUMA);

        when(viewMocked.getVisor()).thenReturn(SUMA.getVal());

        testee.buttonOnCLick(new ButtonOnClickEvent(NUMERO_DEC_2.toString()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(NUMERO_DEC_2.toString());
        verify(viewMocked, atLeast(ONE_TIME)).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarBotonBorrar() {
        testee.buttonOnCLick(new ButtonOnClickEvent(BORRAR.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(NUMERO_0);
        verify(viewMocked).limpiarVisor();
        verifyNoMoreInteractions(viewMocked);

        assertNull(modelMocked.getNumero1());
        assertNull(modelMocked.getNumero2());
        assertEquals(modelMocked.getOperador(), NA);
    }

    @Test
    public void presionarBotonPuntonConUnPuntoExistenteEnElVisor() {
        when(viewMocked.getVisor()).thenReturn(NUMERO_DEC_1);

        testee.buttonOnCLick(new ButtonOnClickEvent(PUNTO.getVal()));

        verify(viewMocked).getVisor();
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarBotonPuntonSinPuntoEnElVisor() {
        when(viewMocked.getVisor()).thenReturn(NUMERO_1);

        testee.buttonOnCLick(new ButtonOnClickEvent(PUNTO.getVal()));

        verify(viewMocked).getVisor();
        verify(viewMocked).setVisor(SIGNO_DECIMAL);
        verifyNoMoreInteractions(viewMocked);
    }

    @Test
    public void presionarOperadorRaizCuadrada() {
        when(viewMocked.getVisor()).thenReturn(NUMERO_DEC_9.toString());

        testee.buttonOnCLick(new ButtonOnClickEvent(RAIZ_CUADRADA.getVal()));
        verify(viewMocked).getVisor();
        verify(viewMocked).limpiarVisor();
        verify(viewMocked).setVisor(NUMERO_DEC_3.toString());
        verifyNoMoreInteractions(viewMocked);

        assertNull(modelMocked.getNumero1());
        assertNull(modelMocked.getNumero2());
        assertEquals(modelMocked.getOperador(), NA);
    }
}

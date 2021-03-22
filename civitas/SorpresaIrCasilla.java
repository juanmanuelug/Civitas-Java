/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author juanm
 */
public class SorpresaIrCasilla extends Sorpresa {
    private Tablero tablero;
    private int valor;
    
    SorpresaIrCasilla(Tablero tablero,int valor,String texto){
        super(texto);
        this.tablero=tablero;
        this.valor=valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            Jugador jugadorActual=todos.get(actual);
            int tirada=tablero.calcularTirada(jugadorActual.getNumCasillaActual(), valor);
            int posicionNueva = tablero.nuevaPosicion(jugadorActual.getNumCasillaActual(), tirada);
            jugadorActual.moverACasilla(posicionNueva);
            tablero.getCasilla(posicionNueva).recibeJugador(actual,todos);
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}

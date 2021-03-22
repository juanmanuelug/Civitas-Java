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
public class SorpresaPorJugador extends Sorpresa{
    private int valor;
    
    SorpresaPorJugador(int valor,String texto){
        super(texto);
        this.valor=valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            SorpresaPagarCobrar pagar = new SorpresaPagarCobrar((valor*(-1)), "Pagar");
            for(int i=0;i<todos.size();i++){
                if(todos.get(i) != todos.get(actual)){
                    pagar.aplicarAJugador(i, todos);
                }
            }
            SorpresaPagarCobrar cobrar = new SorpresaPagarCobrar((valor*(todos.size()-1)), "Cobrar");
            cobrar.aplicarAJugador(actual, todos);
        }
    }
    
    
    @Override
    public String toString(){
        return super.toString();
    }
}

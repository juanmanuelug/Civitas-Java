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
public class SorpresaJugadorEspeculador extends Sorpresa{
    private float fianza;
    
    SorpresaJugadorEspeculador(float fianza,String texto){
        super(texto);
        this.fianza=fianza;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            Jugador especulador = new JugadorEspeculador(todos.get(actual),fianza);            
            todos.set(actual, especulador);           
        }        
    }
  
    @Override
    public String toString(){
        return super.toString();
    }
}

package civitas;

import java.util.*;

public abstract class Sorpresa {
    private String texto;
    
    abstract public void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    
    void informe(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            Diario.getInstance().ocurreEvento("Se aplica la sorpresa " + this.toString()
                + " a " + todos.get(actual).getNombre());
        }
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        boolean esCorrecto = false;
        
        if(actual >= 0 && actual < todos.size()){
            esCorrecto = true;
        }
        
        return esCorrecto;
    }
    

    
    Sorpresa(String texto){
        this.texto=texto;
    }
    
    public String toString(){
        String string= "\n" + texto;
        
        return string;
    }
}

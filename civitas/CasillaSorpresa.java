package civitas;

import java.util.ArrayList;

public class CasillaSorpresa extends Casilla {
    MazoSorpresas mazo;
    Sorpresa sorpresa;
    
    CasillaSorpresa(MazoSorpresas mazo,String nombre){
        super(nombre);
        this.mazo = mazo;
    }
       
    @Override
    void recibeJugador(int iactual,ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            sorpresa = mazo.siguiente();
            sorpresa.aplicarAJugador(iactual, todos);
        }
    }
    
    @Override
    public String toString(){
        String str="\n******************************\n";
        str+="\n " + getNombre() + " | Sorpresa: " + sorpresa;
        str+="\n******************************\n";
        return str;
    }
}

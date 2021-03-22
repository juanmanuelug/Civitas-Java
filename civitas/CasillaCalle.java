package civitas;

import java.util.ArrayList;

public class CasillaCalle extends Casilla {
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        this.tituloPropiedad=titulo;
    }

    @Override
    void recibeJugador(int iactual,ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            Jugador jugador=todos.get(iactual);
            if(!tituloPropiedad.tienePropietario()){
                jugador.puedeComprarCasilla();
            }
            else{
                tituloPropiedad.tramitarAlquiler(jugador);
            }
        }
    }
    
    @Override
    public String toString(){
        String str = tituloPropiedad.toString();
        return str;
    }
}

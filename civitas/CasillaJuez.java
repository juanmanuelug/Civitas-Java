package civitas;

import java.util.ArrayList;

public class CasillaJuez extends Casilla {
    private static int carcel;
    
    CasillaJuez(int numCasillaCarcel, String nombre){
        super(nombre);
        carcel = numCasillaCarcel;
    }
    
    @Override
    void recibeJugador(int iactual,ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }
    
    @Override
    public String toString(){
        String str="\n******************************\n";
        str+="\n " + getNombre() + " | A LA CARCEL (" + carcel + ")";
        str+="\n******************************\n";
        return str;
    }
}

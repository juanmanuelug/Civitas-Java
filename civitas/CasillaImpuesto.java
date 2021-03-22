package civitas;

import java.util.ArrayList;

public class CasillaImpuesto extends Casilla {
    private float importe;
    
    CasillaImpuesto(float cantidad,String nombre){
        super(nombre);
        importe = cantidad;
    }
    
    @Override
    void recibeJugador(int iactual,ArrayList<Jugador> todos){
        if(jugadorCorrecto(iactual,todos)){
            informe(iactual,todos);
            todos.get(iactual).pagaImpuesto(this.importe);
        }
    }
    
    @Override
    public String toString(){
        String str="\n******************************\n";
        str+="\n " + getNombre() + " | Importe: " + importe; 
        str+="\n******************************\n";
        return str;
    }
}

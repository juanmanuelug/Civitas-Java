package civitas;

import java.util.ArrayList;

public class Casilla {
    private String nombre;
    TituloPropiedad tituloPropiedad;
    
    Casilla(String nombre){
        init();
        this.nombre=nombre;
    }
    
    private void init(){
        nombre="";
        tituloPropiedad=null;
    }
    
    public String getNombre(){
        return nombre;
    }
        
    public TituloPropiedad getTituloPropiedad(){
        return this.tituloPropiedad;
    }
    
    public void informe(int iactual,ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento(todos.get(iactual).toString());   
        Diario.getInstance().ocurreEvento(this.toString());     
    }
    
    public Boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos){
        boolean bool = false;
        if(iactual<todos.size() && iactual >= 0){
            bool=true;
        }
        return bool;
    }
    
    void recibeJugador(int iactual,ArrayList<Jugador> todos){
        informe(iactual, todos);
    }
    
    public String toString(){
        String str="\n******************************\n";
        str+="\n " + getNombre(); 
        str+="\n******************************\n";
        return str;
    }
}

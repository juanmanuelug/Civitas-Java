package civitas;

import java.util.ArrayList;
import juegoTexto.VistaTextual;
import juegoTexto.Controlador;

public class TestP3 {
    public static void main(String[] args){
        VistaTextual vista = new VistaTextual();
        
        ArrayList<String> nombres = new ArrayList();
        nombres.add("Juanma");
        nombres.add("Pepe");
        nombres.add("Pablito");
        nombres.add("Carlos");
        
        CivitasJuego juego = new CivitasJuego(nombres);
     
        Dado.getInstance().setDebug(false);
        
        Controlador controlador = new Controlador(juego, vista);
        
        controlador.juega();
    }
}

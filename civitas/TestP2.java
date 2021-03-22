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
public class TestP2 {
    public static void main(String[] args) {
        
        ArrayList<String> nombres=new ArrayList();
        nombres.add("pepe");
        nombres.add("juan");
        nombres.add("sergio");
        nombres.add("pablo");
        CivitasJuego civitas=new CivitasJuego(nombres);
        
        TituloPropiedad uno=new TituloPropiedad("Calle Gibraltar",55,4,400,400,550);
        
        civitas.jugadores.get(0).comprar(uno);
        
        System.out.print(civitas.jugadores.get(0).getPropiedades() + "\n");
        
        civitas.jugadores.get(0).vender(0);   
        
    }
}

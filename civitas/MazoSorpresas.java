package civitas;

import java.util.ArrayList;
import java.util.Collections;

public class MazoSorpresas {
    //Atributos de instancia
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    private ArrayList<Sorpresa> cartasEspeciales;
    private Sorpresa ultimaSorpresa;
    
    //Metodos
    private void init(){
        sorpresas=new ArrayList<>();
        cartasEspeciales=new ArrayList<>();
        barajada=false;
        usadas=0;
        ultimaSorpresa=null;
    }
    
    MazoSorpresas(boolean debug){
        this.debug=debug;
        if(debug){
            Diario.getInstance().ocurreEvento("MazoSorpresas: Modo debug activado");
        }
        init();
    }
    
    MazoSorpresas(){
        debug=false;
        init();
    }
    
    void alMazo(Sorpresa s){
        if(!barajada){
            sorpresas.add(s);
        }
    }
    
    Sorpresa siguiente(){
        if((!barajada || usadas == sorpresas.size()) && !debug){
            usadas=0;
            barajada=true;
            Collections.shuffle(sorpresas);
        }
        usadas++;
        ultimaSorpresa=sorpresas.get(0);
        sorpresas.remove(0);
        sorpresas.add(ultimaSorpresa);
        
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.contains(sorpresa)){
            sorpresas.remove(sorpresa);
            cartasEspeciales.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se inhabilita una carta especial");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        if(cartasEspeciales.contains(sorpresa)){
            cartasEspeciales.remove(sorpresa);
            sorpresas.add(sorpresa);
            Diario.getInstance().ocurreEvento("Se habilita una carta especial");
        }
    }
}

package civitas;

import java.util.ArrayList;

public class Tablero {
    private int numCasillaCarcel;
    private ArrayList<Casilla> casillas;
    private int porSalida;
    private Boolean tieneJuez;
    
    Tablero(int numCasillaCarcel){
        if(numCasillaCarcel>=1){
            this.numCasillaCarcel=numCasillaCarcel;
        }
        else{ 
            this.numCasillaCarcel=1;
        }
        casillas=new ArrayList<>();
        Casilla Salida=new Casilla("Salida");
        casillas.add(Salida);
        porSalida=0;
        tieneJuez=false;
    }
    
    private Boolean correcto(){
        Boolean correcto=false;
        if(casillas.size()>numCasillaCarcel){
            correcto=true;
        }
        return correcto;
    }
    
    private Boolean correcto(int numCasilla){
        Boolean correcto=false;
        if(numCasilla<casillas.size() && correcto()){
            correcto=true;
        }
        return correcto;
    }
    
    int getCarcel(){
        return numCasillaCarcel;
    }
    
    int getPorSalida(){
        int porSalidaAntes=porSalida;
        if(porSalida>0){
            porSalida--;
        }
        return porSalidaAntes;
    }
    
    void aniadeCasilla(Casilla casilla){
        if(casillas.size()==numCasillaCarcel){
            Casilla Carcel=null;
            Carcel=new Casilla("Carcel");
            casillas.add(Carcel);
        }
        casillas.add(casilla);
    }
    
    void aniadeJuez(){
        if(!tieneJuez){
            Casilla juez = new CasillaJuez(numCasillaCarcel, "Juez");
            casillas.add(juez);
            tieneJuez=true;
        }
    }
    
    Casilla getCasilla(int numCasilla){
        Casilla resultado=null;
        if(correcto(numCasilla)){
            resultado=casillas.get(numCasilla);
        }
        return resultado;
    }
    
    int nuevaPosicion(int actual,int tirada){
        int nuevaPos;
        if(!correcto(actual)){
            nuevaPos=-1;
        }else{
            nuevaPos=actual+tirada;
            
            if(nuevaPos>=casillas.size()){
                nuevaPos = nuevaPos % (casillas.size()-1);
                porSalida++;
            }
        }
        return nuevaPos;
    }
    
    int calcularTirada(int origen,int destino){
        int tirada;
        tirada=destino-origen;
        if(tirada<0){
            tirada=tirada + casillas.size();
        }
        return tirada;
    }
}

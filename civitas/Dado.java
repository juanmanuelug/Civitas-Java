package civitas;

import java.util.Random;

public class Dado {
    private Random random;
    private int ultimoResultado;
    private boolean debug;
    
    static final private Dado instance= new Dado();
    static private int SalidaCarcel=5;
    
    private Dado(){
        random=new Random();
        ultimoResultado=-1;
        debug=false;
    }
    
    static Dado getInstance(){
        return instance;
    }
    
    int tirar(){
        if(!debug){
            ultimoResultado = random.nextInt(6)+1;
        }
        else{
            ultimoResultado=1;
        }
        
        return ultimoResultado;
    }
    
    boolean salgoDeLaCarcel(){
        return ((tirar() >= SalidaCarcel));
    }
    
    int quienEmpieza(int n){
        return random.nextInt(n);
    }
    
    void setDebug(boolean d){
        debug=d;
        if(debug){
            Diario.getInstance().ocurreEvento("Dado: Modo debug activado");
        }
        else{
            Diario.getInstance().ocurreEvento("Dado: Modo debug desactivado");
        }
    }
    
    int getUltimoResultado(){
        return ultimoResultado;
    }
}

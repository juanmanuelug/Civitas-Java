package civitas;

public class TituloPropiedad {
    private float alquilerBase;
    private static float factorInteresesHipoteca=1.1F;
    private float factorRevalorizacion;
    private float hipotecaBase;
    Boolean hipotecado;
    String nombre;
    int numCasas;
    int numHoteles;
    float precioCompra;
    float precioEdificar;
    Jugador propietario;
    
    TituloPropiedad(String nombre,float precioBaseAlquiler,float factorRevalorizacion,
                    float baseHipoteca,float precioCompra,float precioEdificar){
        this.nombre=nombre;
        this.alquilerBase=precioBaseAlquiler;
        this.factorRevalorizacion=factorRevalorizacion;
        this.hipotecaBase=baseHipoteca;
        this.precioCompra=precioCompra;
        this.precioEdificar=precioEdificar;
        this.hipotecado=false;
        this.numCasas=0;
        this.numHoteles=0;
        this.propietario=null;
    }    
    
    void actualizarPropietarioPorConversion(Jugador jugador){
        this.propietario=jugador;
    }
    
    Boolean cancelarHipoteca(Jugador jugador){
        boolean result = false;
        
        if(hipotecado){
            if(esEsteElPropietario(jugador)){
                result=jugador.paga(getImporteCancelarHipoteca());
                hipotecado=false;
            }
        }
        
        return result;
    }
    
    int cantidadCasasHoteles(){
        return getNumCasas()+getNumHoteles();
    }
    
    Boolean comprar(Jugador jugador){
        boolean result=false;
        
        if(!tienePropietario()){
            propietario=jugador;
            result=jugador.paga(precioCompra);
        }
        
        return result;
    }
    
    Boolean construirCasa(Jugador jugador){
        boolean result=false;
        
        if(esEsteElPropietario(jugador)){
            result=jugador.paga(precioEdificar);
            numCasas++;
        }
        
        return result;
    }
    
    Boolean construirHotel(Jugador jugador){
        boolean result=false;
        
        if(esEsteElPropietario(jugador)){
            result=jugador.paga(precioEdificar);
            numHoteles++;
        }
        
        return result;
    }
    
    Boolean derruirCasas(int n,Jugador jugador){
        Boolean hecho=false;
        if(esEsteElPropietario(jugador) && getNumCasas()>=n){
            this.numCasas-=n;
            hecho=true;
        }
        return hecho;
    }
    
    private Boolean esEsteElPropietario(Jugador jugador){
        return this.propietario==jugador;
    }
    
    public Boolean getHipotecado(){
        return this.hipotecado;
    }
    
    float getImporteCancelarHipoteca(){
        return (this.hipotecaBase*(1+(this.numCasas*0.5F)+(this.numHoteles*2.5F)))
                *this.factorInteresesHipoteca;
    }
    
    private float getImporteHipoteca(){
        return this.hipotecaBase*(1+(getNumCasas()*0.5F)+(this.getNumHoteles()*2.5F));
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    int getNumCasas(){
        return this.numCasas;
    }
    
    int getNumHoteles(){
        return this.numHoteles;
    }
    
    private float getPrecioAlquiler(){
        float PrecioAlquiler;
        if(!hipotecado && !propietario.isEncarcelado()){
            PrecioAlquiler=this.alquilerBase*(1+(getNumCasas()*0.5F)+(getNumHoteles()*2.5F));
        }else{
            PrecioAlquiler=0;
        }
        return PrecioAlquiler;
    }
    
    float getPrecioCompra(){
        return this.precioCompra;
    }
    
    float getPrecioEdificar(){
        return this.precioEdificar;
    }
    
    private float getPrecioVenta(){
        float precio= getPrecioCompra()+(getPrecioEdificar()*this.factorRevalorizacion);
        return precio;
    }
    
    Jugador getPropietario(){
        return this.propietario;
    }
    
    Boolean hipotecar(Jugador jugador){
        boolean result = false;
        
        if(!hipotecado && esEsteElPropietario(jugador)){
            result=jugador.recibe(getImporteHipoteca());
            hipotecado=true;
        }
        
        return result;
    }
    
    private Boolean propietarioEncarcelado(){
        Boolean encarcelado=false;
        if(this.propietario.isEncarcelado()){
            encarcelado=true;
        }
        return encarcelado;
    }
    
    Boolean tienePropietario(){
        return this.propietario!=null;
    }
    
    public String toString(){
        String resultado = "\n******************************\n";
        resultado+="Nombre: " +nombre ;
        if(tienePropietario()){
            resultado+= "\nPropietario: " + propietario.getNombre();
        }
        else{
            resultado+= "\nPropietario: Nadie";
        }
        resultado+="\nAlquiler base: "+alquilerBase ;
        resultado+="\nFactor de revalorizacion: " +factorRevalorizacion;
        resultado+="\nHipoteca base: "+hipotecaBase;
        resultado+="\nPrecio compra: "+precioCompra;
        resultado+="\nPrecio de edificar: "+precioEdificar;
        resultado+="\nNum Casas: "+numCasas+"\n";
        resultado+="\nNum Hoteles: "+numHoteles;
        String estaHipotecado="\nLa propiedad esta hipotecada";
        String noEstaHipotecado="\nLa propiedad no esta hipotecada";
        resultado += (hipotecado) ? estaHipotecado : noEstaHipotecado;
        resultado+="\nFactor de intereses hipoteca: "+factorInteresesHipoteca+"\n";
        resultado+="******************************\n";
        return resultado;  
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario() && !esEsteElPropietario(jugador)){
            jugador.pagaAlquiler(this.getPrecioAlquiler());
            this.propietario.recibe(this.getPrecioAlquiler());
        }
    }
    
    Boolean vender(Jugador jugador){
        Boolean hecho=false;
        System.out.print(getHipotecado());
        System.out.print(esEsteElPropietario(jugador));
        if(esEsteElPropietario(jugador) && !getHipotecado()){
            hecho=jugador.recibe(getPrecioVenta());
            this.propietario=null;
            this.numCasas=0;
            this.numHoteles=0;
        }
        
        return hecho;
    }
}

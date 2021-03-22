package civitas;

public class JugadorEspeculador extends Jugador {
    private static int FactorEspeculador = 2;
    private float fianza;
    
    JugadorEspeculador(Jugador jugador, float fianza){
        super(jugador);
        this.fianza=fianza;
        
        for(int i=0;i<jugador.getPropiedades().size();i++){
            jugador.getPropiedades().get(i).actualizarPropietarioPorConversion(this);
        }
        
        jugador.especulador = true;
    }
    
    @Override
    protected boolean debeSerEncarcelado(){
        boolean debeSerEncarcelado = false;
        
        if(super.debeSerEncarcelado()){
            if(!puedePagarFianza()){
                debeSerEncarcelado=true;
            }
        
            else{
                pagaFianza();
                Diario.getInstance().ocurreEvento("El jugador especulador " + getNombre() + 
                        " paga la fianza y evita la carcel");
            }
        }
        
        return debeSerEncarcelado;
    }
    
    @Override
    int getHotelesMax(){
        return(HotelesMax*FactorEspeculador);
    }
    
    @Override
    int getCasasMax(){
        return(CasasMax*FactorEspeculador);
    }
    
    private boolean puedePagarFianza(){
        return(getSaldo() >= fianza);
    }
    
    private boolean pagaFianza(){
        boolean result = false;
        
        if(puedePagarFianza()){
            modificarSaldo((-1)*fianza);
            result = true;
        }
        
        return result;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
        return(super.pagaImpuesto(cantidad/FactorEspeculador));
    }
    
    @Override
    boolean puedoEdificarCasa(TituloPropiedad propiedad){
        return(puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumCasas() < getCasasMax());
    }
    
    @Override
    boolean puedoEdificarHotel(TituloPropiedad propiedad){
        return(puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumHoteles() < getHotelesMax() 
                && propiedad.getNumCasas() >= CasasPorHotel);
    }
    
    @Override
    public String toString(){
        String mensaje = "\n*******ESPECULADOR*******\n" + super.toString();
        mensaje += "\n Fianza = " + fianza;
        
        return mensaje;
    }
    
}

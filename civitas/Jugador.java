package civitas;

import java.util.*;

public class Jugador implements Comparable <Jugador>{
    protected static int CasasMax = 4;
    protected static int CasasPorHotel = 4;
    protected boolean encarcelado;
    protected static int HotelesMax = 4;
    private String nombre;
    private int numCasillaActual;
    protected static float PasoPorSalida = 1000;
    protected static float PrecioLibertad = 200;
    private boolean puedeComprar;
    private float saldo;
    private static float SaldoInicial = 7500;
    SorpresaSalirCarcel salvoconducto;
    ArrayList<TituloPropiedad> propiedades;
    boolean especulador;
    
    boolean cancelarHipoteca(int ip){
        boolean result = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad=propiedades.get(ip);
                float cantidad = propiedad.getImporteCancelarHipoteca();
                boolean puedoGastar = puedoGastar(cantidad);
                if(puedoGastar){
                    result = propiedad.cancelarHipoteca(this);
                    if(result){
                        Diario.getInstance().ocurreEvento("El jugador " + nombre +
                                " cancela la hipoteca de la propiedad: '" + propiedad.getNombre() + "'");
                    }
                }
            }
        }
        
        return result;
    }
    
    int cantidadCasasHoteles(){
        int cantidad=0;
        for(int i=0;i<propiedades.size();i++){
            cantidad += propiedades.get(i).cantidadCasasHoteles();
        }
        
        return cantidad;
    }
    
    public int compareTo(Jugador otro){
        Float aux=otro.getSaldo();
        Float thisSaldo=saldo;
        int resultado = thisSaldo.compareTo(aux);
        return resultado;
    }
    
    boolean comprar(TituloPropiedad titulo){
        boolean resultado = false;
        
        if(!encarcelado){
            if(puedeComprar){
                float precio = titulo.getPrecioCompra();
                if(puedoGastar(precio)){
                    resultado = titulo.comprar(this);
                    if(resultado){
                        propiedades.add(titulo);
                        Diario.getInstance().ocurreEvento("El jugador " + this.nombre +
                                " ha comprado la propiedad: '" + titulo.getNombre() +
                                "'");
                    }
                    puedeComprar = false; 
                }
            }
        }
        
        return resultado;
    }
    
    boolean construirCasa(int ip){
        boolean result = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarCasa = false;
                float precio = propiedad.getPrecioEdificar();
                if(puedoGastar(precio)){
                    puedoEdificarCasa = puedoEdificarCasa(propiedad);
                }
                if(puedoEdificarCasa){
                    result = propiedad.construirCasa(this);
                    if(result){
                        Diario.getInstance().ocurreEvento("El jugador " + nombre +
                            " construye casa en la propiedad: '" + propiedad.getNombre() + "'");
                    }
                }
            }
        }
        
        return result;
    }
    
    boolean construirHotel(int ip){
        boolean result = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                boolean puedoEdificarHotel = false;
                float precio = propiedad.getPrecioEdificar();
                if(puedoGastar(precio)){
                    puedoEdificarHotel = puedoEdificarHotel(propiedad);
                }
                if(puedoEdificarHotel){
                    result = propiedad.construirHotel(this);
                    int casasPorHotel = getCasasPorHotel();
                    propiedad.derruirCasas(casasPorHotel, this);
                    Diario.getInstance().ocurreEvento("El jugador " + nombre +
                            " construye hotel en la propiedad: '" + propiedad.getNombre() + "'");
                }
            }
        }
        
        return result;
    }
    
    protected boolean debeSerEncarcelado(){
        boolean debeSerEncarcelado = false;
        if(!encarcelado){
            if(!tieneSalvoconducto()){
                debeSerEncarcelado = true;
            }
            else{
                perderSalvoconducto();
                Diario.getInstance().ocurreEvento("El jugador " + nombre + " usa su salvoconducto "
                        + "y se libra de la carcel");
            }
        }
        
        return debeSerEncarcelado;
    }
    
    boolean enBancarrota(){
        return (saldo < 0);
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            encarcelado = moverACasilla(numCasillaCarcel);
            if(encarcelado){
                Diario.getInstance().ocurreEvento("El jugador " + nombre + " es encarcelado");
            }
        }
        
        return encarcelado;
    }
    
    private boolean existeLaPropiedad(int ip){
        return (propiedades.get(ip) != null);
    }
    
    int getCasasMax(){
        return CasasMax;
    }
    
    int getCasasPorHotel(){
        return CasasPorHotel;
    }
    
    int getHotelesMax(){
        return HotelesMax;
    }
    
    protected String getNombre(){
        return nombre;
    }
    
    int getNumCasillaActual(){
        return numCasillaActual;
    }
    
    private float getPrecioLibertad(){
        return PrecioLibertad;
    }
    
    private float getPremioPasoSalida(){
        return PasoPorSalida;
    }
    
    public ArrayList<TituloPropiedad> getPropiedades(){
        return propiedades;
    }
    
    boolean getPuedeComprar(){
        return puedeComprar;
    }
    
    protected float getSaldo(){
        return saldo;
    }
    
    boolean hipotecar(int ip){
        boolean result = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                result = propiedad.hipotecar(this);
                if(result){
                    Diario.getInstance().ocurreEvento("El jugador " + nombre + 
                            " hipoteca la propiedad: '" + propiedad.getNombre() + "'");
                }
            }
        }
        
        return result;
    }
    
    public boolean isEncarcelado(){
        return encarcelado;
    }
    
    Jugador(String nombre){
        this.nombre=nombre;
        salvoconducto=null;
        propiedades=new ArrayList();
        encarcelado=false;
        numCasillaActual=0;
        saldo=SaldoInicial;
        puedeComprar=false;
        especulador=false;
    }
    
    protected Jugador(Jugador otro){
        this.nombre=otro.nombre;
        this.encarcelado=otro.encarcelado;
        this.numCasillaActual=otro.numCasillaActual;
        this.puedeComprar=otro.puedeComprar;
        this.saldo=otro.saldo;
        this.salvoconducto=otro.salvoconducto;
        this.propiedades=otro.propiedades;
        this.especulador=otro.especulador;
    }
    
    boolean modificarSaldo(float cantidad){
        saldo += cantidad;
        
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " sufre"
                + " una modificación en su saldo (" + cantidad + ")");
        
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        boolean resultado = false;
        
        if(!encarcelado){
            numCasillaActual = numCasilla;
            puedeComprar = false;
            Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " se mueve"
                    + " a la casilla " + numCasilla);
            resultado=true;
        }

        return resultado;
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa){
        boolean obtiene=false;
        
        if(!encarcelado){
            salvoconducto=sorpresa;
            obtiene=true;
        }
        
        return obtiene;
    }
    
    boolean paga(float cantidad){
        return modificarSaldo(cantidad*(-1));
    }
    
    boolean pagaAlquiler(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado){
            resultado = paga(cantidad);
        }
        
        return resultado;
    }
    
    boolean pagaImpuesto(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado){
            resultado = paga(cantidad);
        }
        
        return resultado;
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(PasoPorSalida);
        
        Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " pasa por"
                + " la salida");
        
        return true;
    }
    
    private void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto=null;
    }
    
    boolean puedeComprarCasilla(){
        if(encarcelado){
            puedeComprar=false;
        }
        else{
            puedeComprar=true;
        }
        
        return puedeComprar;
    }
    
    private boolean puedeSalirCarcelPagando(){
        return (saldo >= PrecioLibertad);
    }
    
    boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean puedo = false;
        
        if(puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumCasas() < CasasMax){
            puedo = true;
        }
        
        return puedo;
    }
    
    boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean puedo = false;
        
        if(propiedad.getNumHoteles() < HotelesMax && propiedad.getNumCasas() >= CasasPorHotel){
            puedo = true;
        }
        
        return puedo;
    }
    
    boolean puedoGastar(float precio){
        return (!encarcelado && (saldo >= precio));
    }
    
    boolean recibe(float cantidad){
        boolean resultado = false;
        
        if(!encarcelado){
            resultado = modificarSaldo(cantidad);
        }
        
        return resultado;
    }
    
    boolean salirCarcelPagando(){
        boolean resultado = false;
        
        if(encarcelado && puedeSalirCarcelPagando()){
            paga(PrecioLibertad);
            encarcelado=false;
            Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " sale "
                    + " de la carcel pagando");
            resultado = true;
        }
        
        return resultado;
    }
    
    boolean salirCarcelTirando(){
        boolean resultado = false;
        
        if(encarcelado && Dado.getInstance().salgoDeLaCarcel()){
            encarcelado = false;
            Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " sale "
                    + " de la carcel tirando");
            resultado = true;
        }
        
        return resultado;
    }
    
    boolean tieneAlgoQueGestionar(){
        return (propiedades.size() > 0);
        
    }
    
    boolean tieneSalvoconducto(){
        return (salvoconducto!=null);
    }
    
    public String toString(){
        String mensaje="******************************\n";
        mensaje += nombre + "\n******************************\n\n";
        mensaje += "\n ENCARCELADO = " + encarcelado; 
        mensaje += "\n Numero Casilla Actual: " + numCasillaActual;   
        mensaje += "\n Saldo: " + saldo;
        mensaje += "\n Puede comprar = " + puedeComprar;
        if(salvoconducto != null){
            mensaje += "\n Salvoconducto = true";
        }
        else{
            mensaje += "\n Salvoconducto = false";
        }
        mensaje +="\n******************************\n";
        
        return mensaje;
    }
    
    boolean vender(int ip){
        boolean resultado = false;
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = propiedades.get(ip);
                resultado = propiedad.vender(this);
                if(resultado){
                    propiedades.remove(ip);
                    Diario.getInstance().ocurreEvento("El jugador " + this.nombre +
                            " ha vendido la propiedad: '" + propiedad.getNombre()
                            + "'");
                }
            }
        }
        
        return resultado;
    }
}

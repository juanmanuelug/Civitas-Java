package civitas;

import java.util.ArrayList;
import java.util.Arrays;

public class CivitasJuego {
    int indiceJugadorActual;
    
    EstadosJuego estado;
    GestorEstados gestorEstados;
    MazoSorpresas mazo;
    Tablero tablero;
    ArrayList<Jugador> jugadores;
    
    void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = tablero.getCasilla(posicionNueva);
        contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual,jugadores);
        contabilizarPasosPorSalida(jugadorActual);
    }
    
    public Boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    public CivitasJuego(ArrayList<String> nombres){
        jugadores=new ArrayList();
        for(int i=0;i<nombres.size();i++){
            Jugador uno=new Jugador(nombres.get(i));
            jugadores.add(uno);
        }
        gestorEstados=new GestorEstados();
        estado=gestorEstados.estadoInicial();
        indiceJugadorActual=Dado.getInstance().quienEmpieza(nombres.size());
        mazo=new MazoSorpresas();
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);
    }
   
    public Boolean comprar(){
        Jugador jugadorActual=jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        Casilla casilla = tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = casilla.getTituloPropiedad();
        boolean res = jugadorActual.comprar(titulo);
        
        return res;
    }
    
    public Boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    public Boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        int salida=tablero.getPorSalida();
        while(salida > 0){
            jugadorActual.pasaPorSalida();
            salida--;
        }
    }
    
    public Boolean finalDelJuego(){
        boolean bool=false;
        for(int i=0;i<jugadores.size();i++){
            if(jugadores.get(i).enBancarrota()){
                bool=true;
            }
        }        
        return bool;       
    }
    
    public Casilla getCasillaActual(){
        int casillaActual=jugadores.get(indiceJugadorActual).getNumCasillaActual();
        return tablero.getCasilla(casillaActual);
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public Boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        return getJugadorActual().toString();
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        //Sorpresa 1
        Sorpresa Cobrar=new SorpresaPagarCobrar(500,"Te pagan 500 euros por un premio al informatico con menos futuro");
        mazo.alMazo(Cobrar);
        //Sorpresa 2
        Sorpresa Pagar=new SorpresaPagarCobrar(-500,"Mensaje de un numero privado\n******************\n HAS GANADO UN IPHONE 11\nIntroduce tus datos de la tarjeta de credito para reclamarlo\n******************\n Pierdes 500 euros");
        mazo.alMazo(Pagar);
        //Sorpresa 3
        Sorpresa Ircasilla1=new SorpresaIrCasilla(tablero,6,"Vas a la casilla del Palenque");
        mazo.alMazo(Ircasilla1);
        //Sorpresa 4
        Sorpresa Ircasilla2=new SorpresaIrCasilla(tablero,11,"Te mueves a la casilla Avenida del Ejercito");
        mazo.alMazo(Ircasilla2);
        //Sorpresa 5
        Sorpresa Ircasilla3=new SorpresaIrCasilla(tablero,10,"Te desplazas a la carcel de chill");
        mazo.alMazo(Ircasilla3);
        //Sorpresa 6
        Sorpresa CasaHotel1=new SorpresaPorCasaHotel(-100,"Hacienda viene a cobrar tus propiedades compañero");
        mazo.alMazo(CasaHotel1);
        //Sorpresa 7
        Sorpresa CasaHotel2=new SorpresaPorCasaHotel(100,"Hacienda te paga por tus casitas y hoteles");
        mazo.alMazo(CasaHotel2);
        //Sorpresa 8
        Sorpresa CobrarJugador=new SorpresaPorJugador(100,"Has tenido suerte y tus compañeros te invitan a unas copas");
        mazo.alMazo(CobrarJugador);
        //Sorpresa 9 
        Sorpresa PagarJugador=new SorpresaPorJugador(-100,"Que mal, te toca pagar las copas de tus compañeros");
        mazo.alMazo(PagarJugador);
        //Sorpresa 10
        Sorpresa SalirCarcel=new SorpresaSalirCarcel(mazo, "Un salvoconducto para ti bro");
        mazo.alMazo(SalirCarcel);
        //Sorpresa 11
        Sorpresa Ircarcel=new SorpresaIrCarcel(tablero, "Te encarcelan por evadir impuestos");
        mazo.alMazo(Ircarcel);
        //Sorpresa 12
        Sorpresa Especulador=new SorpresaJugadorEspeculador(150,"Te has convertido en Jugador Especulador");
        mazo.alMazo(Especulador);
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        int casillaCarcel=10;
        //Salida y Carcel
        tablero=new Tablero(casillaCarcel);
        //Calle 1 (1)
        TituloPropiedad uno=new TituloPropiedad("Calle Gibraltar",55f,4f,400f,400f,550f);
        Casilla calleGibraltar=new CasillaCalle(uno);
        tablero.aniadeCasilla(calleGibraltar);
        //Calle 2 (2)
        TituloPropiedad dos=new TituloPropiedad("Calle Canarias",75f,5f,500f,500f,675f);
        Casilla calleCanarias=new CasillaCalle(dos);
        tablero.aniadeCasilla(calleCanarias);
        //Calle 3 (3)
        TituloPropiedad tres=new TituloPropiedad("La Atunara",95f,6f,520f,520f,750f);
        Casilla Atunara=new CasillaCalle(tres);
        tablero.aniadeCasilla(Atunara);
        //Sorpresa 1 (4)
        Casilla mazo1=new CasillaSorpresa(mazo,"Sorpresa 1");
        tablero.aniadeCasilla(mazo1);
        //Calle 4 (5)
        TituloPropiedad cuatro=new TituloPropiedad("Avenida María Guerrero",100f,7f,575f,575f,900f);
        Casilla AvenidaMariaGuerrero=new CasillaCalle(cuatro);
        tablero.aniadeCasilla(AvenidaMariaGuerrero);
        //Calle 5 (6)
        TituloPropiedad cinco=new TituloPropiedad("El Palenque",120f,8f,600f,600f,1000f);
        Casilla ElPalenque=new CasillaCalle(cinco);
        tablero.aniadeCasilla(ElPalenque);
        //Impuesto (7)
        Casilla Impuesto=new CasillaImpuesto(1000f,"Hacienda");
        tablero.aniadeCasilla(Impuesto);
        //Calle 6 (8)
        TituloPropiedad seis=new TituloPropiedad("El Zabal",155f,9f,650f,650f,1200f);
        Casilla ElZabal=new CasillaCalle(seis);
        tablero.aniadeCasilla(ElZabal); 
        //Calle 7 (9)
        TituloPropiedad siete=new TituloPropiedad("Calle Larga",200f,10f,680f,680f,1300f);
        Casilla calleLarga=new CasillaCalle(siete);
        tablero.aniadeCasilla(calleLarga);
        //Carcel(10)
        //Calle 8 (11)
        TituloPropiedad ocho=new TituloPropiedad("Avenida del Ejercito",225f,11f,725f,725f,1450f);
        Casilla AvenidadelEjercito=new CasillaCalle(ocho);
        tablero.aniadeCasilla(AvenidadelEjercito);
        //Sorpresa 2 (12)
        Casilla mazo2=new CasillaSorpresa(mazo,"Sorpresa 2");
        tablero.aniadeCasilla(mazo2);
        //Juez (13)
        tablero.aniadeJuez();
        //Calle 9 (14)
        TituloPropiedad nueve=new TituloPropiedad("Cruz Herrera",260f,11f,850f,850f,1900f);
        Casilla CruzHerrera=new CasillaCalle(nueve);
        tablero.aniadeCasilla(CruzHerrera);
        //Calle 10 (15)
        TituloPropiedad diez=new TituloPropiedad("Venta Melchor",450f,14f,1000f,1000f,2200f);
        Casilla VMelchor=new CasillaCalle(diez);
        tablero.aniadeCasilla(VMelchor);
        //Descanso (16)
        Casilla Descanso=new Casilla("Burguer King");
        tablero.aniadeCasilla(Descanso);
        //Calle 11 (17)
        TituloPropiedad once=new TituloPropiedad("Santa Margarita",750f,20f,1500f,1500f,2800f);
        Casilla SMargarita=new CasillaCalle(once);
        tablero.aniadeCasilla(SMargarita);
        //Sorpresa 3 (18)
        Casilla mazo3=new CasillaSorpresa(mazo,"Sorpresa 3");
        tablero.aniadeCasilla(mazo3);
        //Calle 12 (19)
        TituloPropiedad doce=new TituloPropiedad("La Alcaidesa",1000f,25f,2200f,2200f,4000f);
        Casilla Alcaidesa=new CasillaCalle(doce);
        tablero.aniadeCasilla(Alcaidesa);
    }

    private void pasarTurno(){
        if(indiceJugadorActual<jugadores.size()-1){
            indiceJugadorActual++;
        }
        else{
            indiceJugadorActual=0;
        }        
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> clasificacion= new ArrayList(jugadores.size());
        float [] saldos= new float[jugadores.size()];
        for(int i=0;i<jugadores.size();i++){
            saldos[i]=jugadores.get(i).getSaldo();
        }
        Arrays.sort(saldos);
        Boolean sigue=true;
        for(int i=jugadores.size()-1;i>=0;i--){
            //Buscamos el jugador que tenga ese saldo
            for(int j=0;j<jugadores.size() && sigue;j++){
                if(saldos[i]==jugadores.get(j).getSaldo()){
                    clasificacion.add(jugadores.get(j));
                    sigue=false;
                }
            }
            sigue=true;
        }
        return clasificacion;
    }
    
    public Boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public Boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugadorActual, estado);
        
        if(operacion == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operacion);
        }
        else if(operacion == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operacion);
        }
        
        return operacion;
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        Jugador Actual=getJugadorActual();
        estado=gestorEstados.siguienteEstado(Actual, estado, operacion);
    }
    
    public Boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
}

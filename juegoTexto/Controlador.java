package juegoTexto;
import java.util.*;

import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.OperacionInmobiliaria;
import civitas.SalidasCarcel;
import civitas.Jugador;

public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    public Controlador(CivitasJuego juego,VistaTextual vista){
        this.juego=juego;
        this.vista=vista;
    }
    
    public void juega(){
        vista.setCivitasJuego(juego);
        while(!juego.finalDelJuego()){
            vista.actualizarVista();
            vista.pausa();
            
            OperacionesJuego op=juego.siguientePaso();
            vista.mostrarSiguienteOperacion(op);
            if(!(op==OperacionesJuego.PASAR_TURNO)){
                vista.mostrarEventos();
            }
            if(!juego.finalDelJuego()){
                if(op==OperacionesJuego.COMPRAR){
                    Respuestas res=vista.comprar();
                    if(res==Respuestas.SI){
                        juego.comprar();
                    }
                    juego.siguientePasoCompletado(op);
                }
                else if(op==OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    GestionesInmobiliarias gestion;
                    OperacionInmobiliaria operacion;
                    switch(vista.getGestion()){
                        case 0:
                            gestion = GestionesInmobiliarias.VENDER;
                            operacion = new OperacionInmobiliaria(gestion,vista.getPropiedad());
                            juego.vender(vista.getPropiedad());
                            break;
                        case 1:
                            gestion = GestionesInmobiliarias.HIPOTECAR;
                            operacion = new OperacionInmobiliaria(gestion,vista.getPropiedad());
                            juego.hipotecar(vista.getPropiedad());
                            break;
                        case 2:
                            gestion = GestionesInmobiliarias.CANCELAR_HIPOTECA;
                            operacion = new OperacionInmobiliaria(gestion,vista.getPropiedad());
                            juego.cancelarHipoteca(vista.getPropiedad());
                            break;    
                        case 3:
                            gestion = GestionesInmobiliarias.CONSTRUIR_CASA;
                            operacion = new OperacionInmobiliaria(gestion,vista.getPropiedad());
                            juego.construirCasa(vista.getPropiedad());
                            break;
                        case 4:
                            gestion = GestionesInmobiliarias.CONSTRUIR_HOTEL;
                            operacion = new OperacionInmobiliaria(gestion,vista.getPropiedad());
                            boolean construido=juego.construirHotel(vista.getPropiedad());
                            if(!construido){
                                System.out.println("No se puede construir hotel ");
                            }
                            
                            break;
                        default:
                            gestion = GestionesInmobiliarias.TERMINAR;
                            operacion = new OperacionInmobiliaria(gestion,vista.getPropiedad());
                            juego.siguientePasoCompletado(op);
                            break;
                    }
                }else if(op==OperacionesJuego.SALIR_CARCEL){
                    SalidasCarcel salida = vista.salirCarcel();
                    if(salida==SalidasCarcel.PAGANDO){
                        juego.salirCarcelPagando();
                    }else{
                        juego.salirCarcelTirando();
                    }
                    juego.siguientePasoCompletado(op); 
                }
                
            }else{
                juego.finalDelJuego();
            }
        }
        ArrayList<Jugador> ranking = juego.ranking();
        System.out.println("\n\n******CLASIFICACION FINAL*****\n");
        for(int i=0;i<ranking.size();i++){
            System.out.println(i+1 + "º PUESTO");
            System.out.println(ranking.get(i));
        }
        System.out.println("GRACIAS POR JUGAR :)");
    }    
}

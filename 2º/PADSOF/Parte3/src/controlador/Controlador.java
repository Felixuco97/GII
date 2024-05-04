package controlador;

import controlador.cliente.*;
import controlador.empleado.*;
import controlador.gestor.*;
import controlador.gestor.gestionEmpl.ControlAltaEmpleados;
import controlador.gestor.gestionEmpl.ControlGestionContrasenias;
import controlador.gestor.gestionEmpl.ControlGestionPermisosEmpl;
import controlador.gestor.gestionExpos.*;
import controlador.gestor.gestionObras.*;
import controlador.gestor.gestionSalas.*;
import controlador.gestor.configuracionDescuentos.*;
import modelo.sistema.*;
import vista.*;

/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class Controlador{

    //Usuario no registrado
    private ControlRegistro controlRegistro;
    private ControlInicioSesion controlInicioSesion;
    private ControlBusqueda controlBusqueda;
    private ControlFiltros controlFiltros;
    private ControlInicial controlInicial;


    //Cliente
    private ControlCompraEntradas controlComprarEntrada;
    private ControlInicioCliente controlInicioCliente;
    private ControlNotificaciones controlNotificaciones;
    private ControlPago controlPago;

    //Empleado
    private ControlCambiarDatos controlCambiarDatos;
    private ControlControlAmbiente controlControlAmbiente;
    private ControlCrearNotis controlCrearNotis;
    private ControlInicioEmpleado controlInicioEmpleado;
    private ControlVentaEntradas controlVentaEntradas;

    //Gestor
    private ControlEstadistica controlEstadistica;
    private ControlConfigHorario controlConfigHorario;
    private ControlConfigSort controlConfigSort;
    private ControlDescuento controlDescuento;
    private ControlDescuentoClienteFrecuente ControlDescuentoClienteFrecuente;
    private ControlDescuentoCompraAnticipada ControlDescuentoCompraAnticipada;



    private ControlGestionEmpleados controlGestionDeEmpleados;
    private ControlGestionExposiciones controlGestionDeExposiciones;
    private ControlGestionObras controlGestionDeObras;
    private ControlGestionSalas controlGestionDeSalas;
    private ControlInicioGestor controlInicioGestor;
    private ControlOtrasOpciones controlOtrasOpciones;
    private ControlSortFechaFija controlSortFechaFija;
    private ControlSortFreeDate controlSortFreeDate;
    private ControlSortTimeInterval controlSortTimeInterval;

    //Gestor >> gestionEmpleado
    private ControlAltaEmpleados controlAltaEmpleados;
    private ControlGestionContrasenias controlGestionContrasenias;
    private ControlGestionPermisosEmpl controlGestionPermisosEmpl;

    //Gestor >> gestionExpos
    private ControlAltaExpo controlAltaExpo;
    private ControlAltaExpoPermanente controlAltaExpoPermanente;

    private ControlBajaExpo controlBajaExpo;
    private ControlCancelar controlCancelar;

    //Gestor >> gestionObras
    private ControlAltaObra controlAltaObra;
    private ControlAltaFoto controlAltaFoto;
    private ControlAltaCuadro controlAltaCuadro;
    private ControlAltaEscultura controlAltaEscultura;
    private ControlAltaMontaje controlAltaMontaje;

    private ControlBajaObra controlBajaObra;
    private ControlMapear controlMapear;
    private ControlModEstado controlModEstado;

    //Gestor >> gestionSalas
    private ControlDividir controlDividir;
    private ControlInfo controlInfo;

    private Ventana frame;
    private Sistema sistema;
    
    public Controlador(Ventana frame, Sistema sistema){

        this.frame = frame;
        this.sistema = sistema;

        //Usuario no registrado
        this.controlRegistro = new ControlRegistro(frame, sistema);
        this.controlInicioSesion = new ControlInicioSesion(frame, sistema);
        this.controlBusqueda = new ControlBusqueda(frame, sistema);
        this.controlFiltros = new ControlFiltros(frame, sistema);
        this.controlInicial = new ControlInicial(frame);

        //Cliente
        this.controlComprarEntrada = new ControlCompraEntradas(frame, sistema);
        this.controlInicioCliente = new ControlInicioCliente(frame, sistema);
        this.controlNotificaciones = new ControlNotificaciones(frame);
        this.controlPago = new ControlPago(frame, sistema);

        //Empleado
        this.controlCambiarDatos = new ControlCambiarDatos(frame);
        this.controlControlAmbiente = new ControlControlAmbiente(frame, sistema);
        this.controlCrearNotis = new ControlCrearNotis(frame, sistema);
        this.controlInicioEmpleado = new ControlInicioEmpleado(frame, sistema);
        this.controlVentaEntradas = new ControlVentaEntradas(frame, sistema);

        //Gestor
        this.controlEstadistica = new ControlEstadistica(frame);
        this.controlConfigHorario = new ControlConfigHorario(frame, sistema);
        this.controlConfigSort = new ControlConfigSort(frame);
        this.controlDescuento = new ControlDescuento(frame);
        this.ControlDescuentoClienteFrecuente = new ControlDescuentoClienteFrecuente(frame, sistema);
        this.ControlDescuentoCompraAnticipada = new ControlDescuentoCompraAnticipada(frame, sistema);

        this.controlGestionDeEmpleados = new ControlGestionEmpleados(frame);
        this.controlGestionDeExposiciones = new ControlGestionExposiciones(frame);
        this.controlGestionDeObras = new ControlGestionObras(frame);
        this.controlGestionDeSalas = new ControlGestionSalas(frame);
        this.controlInicioGestor = new ControlInicioGestor(frame, sistema);
        this.controlOtrasOpciones = new ControlOtrasOpciones(frame);
        this.controlSortFechaFija = new ControlSortFechaFija(frame, sistema);
        this.controlSortFreeDate = new ControlSortFreeDate(frame, sistema);
        this.controlSortTimeInterval = new ControlSortTimeInterval(frame, sistema);

        //Gestor >> gestionEmpleados
        this.controlAltaEmpleados = new ControlAltaEmpleados(frame, sistema);
        this.controlGestionContrasenias = new ControlGestionContrasenias(frame, sistema);
        this.controlGestionPermisosEmpl = new ControlGestionPermisosEmpl(frame, sistema);

        //Gestor >> gestionExpos
        this.controlAltaExpo = new ControlAltaExpo(frame, sistema);
        this.controlAltaExpoPermanente = new ControlAltaExpoPermanente(frame, sistema);
        this.controlBajaExpo = new ControlBajaExpo(frame, sistema);
        this.controlCancelar = new ControlCancelar(frame, sistema);

        //Gestor >> gestionObras
        this.controlAltaObra = new ControlAltaObra(frame);
        this.controlAltaFoto = new ControlAltaFoto(frame, sistema);
        this.controlAltaCuadro = new ControlAltaCuadro(frame, sistema);
        this.controlAltaEscultura = new ControlAltaEscultura(frame, sistema);
        this.controlAltaMontaje = new ControlAltaMontaje(frame, sistema);

        this.controlBajaObra = new ControlBajaObra(frame, sistema);
        this.controlMapear = new ControlMapear(frame, sistema);
        this.controlModEstado = new ControlModEstado(frame, sistema);

        //Gestor >> gestionSalas
        this.controlDividir = new ControlDividir(frame, sistema);
        this.controlInfo = new ControlInfo(frame);

    }

    public Ventana getVenta(){
        return frame;
    }

    public Sistema getSistema(){
        return sistema;
    }

    //Usuario no registrado
    public ControlRegistro getControlRegistro(){

        return this.controlRegistro;
    }

    public ControlInicioSesion getControlInicioSesion(){

        return this.controlInicioSesion;
    }

    public ControlBusqueda getControlBusqueda(){

        return this.controlBusqueda;
    }

    public ControlFiltros getControlFiltros(){

        return this.controlFiltros;
    }

    public ControlInicial getControlInicial(){

        return this.controlInicial;
    }


    //Cliente
    public ControlCompraEntradas getControlCompraEntradas(){

        return this.controlComprarEntrada;
    }

    public ControlInicioCliente getControlInicioCliente(){

        return this.controlInicioCliente;
    }

    public ControlNotificaciones getControlNotificaciones(){

        return this.controlNotificaciones;
    }

    public ControlPago getControlPago(){

        return this.controlPago;
    }

    //Empleado
    public ControlCambiarDatos getControlCambiarDatos(){

        return this.controlCambiarDatos;
    }

    public ControlControlAmbiente getControlControlAmbiente(){

        return this.controlControlAmbiente;
    }

    public ControlCrearNotis getControlCrearNotis(){

        return this.controlCrearNotis;
    }
    
    public ControlInicioEmpleado getControlInicioEmpleado(){

        return this.controlInicioEmpleado;
    }

    public ControlVentaEntradas getControlVentaEntradas(){

        return this.controlVentaEntradas;
    }

    //Gestor

    public ControlEstadistica getControlEstadistica(){

        return this.controlEstadistica;
    }

    public ControlConfigHorario getControlConfigHorario(){

        return this.controlConfigHorario;
    }

    public ControlConfigSort getControlConfigSort(){

        return this.controlConfigSort;
    }

    public ControlDescuento getControlDescuento(){

        return this.controlDescuento;
    }

    public ControlDescuentoClienteFrecuente getControlDescuentoClienteFrecuente(){

        return this.ControlDescuentoClienteFrecuente;
    }

    public ControlDescuentoCompraAnticipada getControlDescuentoCompraAnticipada(){

    return this.ControlDescuentoCompraAnticipada;
    }

    public ControlGestionEmpleados getControlGestionDeEmpleados(){

        return this.controlGestionDeEmpleados;
    }

    public ControlGestionExposiciones getControlGestionDeExposiciones(){

        return this.controlGestionDeExposiciones;
    }

    public ControlGestionObras getControlGestionDeObras(){

        return this.controlGestionDeObras;
    }

    public ControlGestionSalas getControlGestionDeSalas(){

        return this.controlGestionDeSalas;
    }

    public ControlInicioGestor getControlInicioGestor(){

        return this.controlInicioGestor;
    }

    public ControlOtrasOpciones getControlOtrasOpciones(){

        return this.controlOtrasOpciones;
    }

    public ControlSortFechaFija getControlSortFechaFija(){

        return this.controlSortFechaFija;
    }

    public ControlSortFreeDate getControlSortFreeDate(){

        return this.controlSortFreeDate;
    }

    public ControlSortTimeInterval getControlSortTimeInterval(){

        return this.controlSortTimeInterval;
    }

    //Gestor >> gestionEmpleados
    public ControlAltaEmpleados getControlAltaEmpleados(){

        return this.controlAltaEmpleados;
    }

    public ControlGestionContrasenias getControlGestionContrasenias(){

        return this.controlGestionContrasenias;
    }

    public ControlGestionPermisosEmpl getControlGestionPermisosEmpl(){

        return this.controlGestionPermisosEmpl;
    }

    //Gestor >> gestionExpos
    public ControlAltaExpo getControlAltaExpo(){

        return this.controlAltaExpo;
    }

    public ControlAltaExpoPermanente getControlAltaExpoPermanente(){

        return this.controlAltaExpoPermanente;
    }

    public ControlBajaExpo getControlBajaExpo(){

        return this.controlBajaExpo;
    }

    public ControlCancelar getControlCancelar(){

        return this.controlCancelar;
    }

    //Gestor >> gestionObras
    public ControlAltaObra getControlAltaObra(){

        return this.controlAltaObra;
    }

    public ControlAltaFoto getControlAltaFoto(){

        return this.controlAltaFoto;
    }

    public ControlAltaCuadro getControlAltaCuadro(){

        return this.controlAltaCuadro;
    }

    public ControlAltaEscultura getControlAltaEscultura(){

        return this.controlAltaEscultura;
    }

    public ControlAltaMontaje getControlAltaMontaje(){

        return this.controlAltaMontaje;
    }



    public ControlBajaObra getControlBajaObra(){

        return this.controlBajaObra;
    }

    public ControlMapear getControlMapear(){

        return this.controlMapear;
    }

    public ControlModEstado getControlModEstado(){

        return this.controlModEstado;
    }

    //Gestor >> gestionSalas
    public ControlDividir getControlDividir(){

        return this.controlDividir;
    }

    public ControlInfo getControlInfo(){

        return this.controlInfo;
    }
}

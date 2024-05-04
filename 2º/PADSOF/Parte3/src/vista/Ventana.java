package vista;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;


import controlador.*;
import controlador.cliente.*;
import controlador.empleado.*;
import controlador.gestor.*;
import controlador.gestor.gestionEmpl.*;
import controlador.gestor.gestionExpos.*;
import controlador.gestor.gestionObras.*;
import controlador.gestor.gestionSalas.*;
import controlador.gestor.configuracionDescuentos.*;
import vista.cliente.*;
import vista.empleado.*;
import vista.gestor.*;
import vista.gestor.gestionEmpl.*;
import vista.gestor.gestionExpos.*;
import vista.gestor.gestionObras.*;
import vista.gestor.gestionSalas.*;
import vista.gestor.configuracionDescuentos.*;
import modelo.sistema.Sistema;
import modelo.usuarios.UsuarioRegistrado;

/**
 * @author Felix López, Fernando Sanchez y Andrés M. Alonso
 */
public class Ventana extends JFrame{

    //VISTAS

    //Usuario no registrado
    private Registro vistaRegistro;
    private InicioSesion vistaInicioSesion;
    private Busqueda vistaBusqueda;
    private FiltrosBusqueda vistaFiltros;
    private Inicial vistaInicial;

    //Cliente
    private ComprarEntrada vistaComprarEntrada;
    private InicioCliente vistaInicioCliente;
    private Notificaciones vistaNotificaciones;
    private Pago vistaPago;

    //Empleado
    private CambiarDatos vistaCambiarDatos;
    private ControlAmbiente vistaControlAmbiente;
    private CrearNotis vistaCrearNotis;
    private InicioEmpleado vistaInicioEmpleado;
    private VentaEntradas vistaVentaEntradas;

    //Gestor
    private ConfigSort vistaConfigSort;
    private Desc vistaDescuento;
    private DescClienteFrecuente getVistaDescuentoClienteFrecuente;
    private DescCompraAnticipada getVistaDescuentoCompraAnticipada;


    private GestionDeEmpleados vistaGestionDeEmpleados;
    private GestionDeExposiciones vistaGestionDeExposiciones;
    private GestionDeObras vistaGestionDeObras;
    private GestionDeSalas vistaGestionDeSalas;
    private InicioGestor vistaInicioGestor;
    private OtrasOpciones vistaOtrasOpciones;
    private Estadistica vistaEstadisticas;
    private ConfigHorario vistaConfigHorario;
    private SortFechaFija vistaSortFechaFija;
    private SortFreeDate vistaSortFreeDate;
    private SortTimeInterval vistaSortTimeInterval;

    //Gestor >> gestionEmpleados
    private AltaEmpleados vistaAltaEmpleados;
    private GestionContrasenias vistaGestionContrasenias;
    private GestionPermisosEmpl vistaGestionPermisosEmpl;

    //Gestor >> gestionExpos
    private AltaExpo vistaAltaExpo;
    private AltaExpoPermanente vistaAltaExpoPermanente;
    private BajaExpo vistaBajaExpo;
    private Cancelar vistaCancelar;

    //Gestor >> gestionObras
    private AltaObra vistaAltaObra;
    private AltaFoto vistaAltaFoto;
    private AltaCuadro vistaAltaCuadro;
    private AltaEscultura vistaAltaEscultura;
    private AltaMontaje vistaAltaMontaje;


    private BajaObra vistaBajaObra;
    private Mapear vistaMapear;
    private ModEstado vistaModEstado;

    //Gestor >> gestionSalas
    private Dividir vistaDividir;
    private Info vistaInfo;

    //CONTROLADORES

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
    private ControlEstadistica controlEstadisticas;
    private ControlConfigHorario controlConfigHorario;
    private ControlSortFechaFija controlSortFechaFija;
    private ControlSortFreeDate controlSortFreeDate;
    private ControlSortTimeInterval controlSortTimeInterval;

    //Gestor >> gestionEmpleados
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
    private ControlAltaEscultura controlAltaEscultura;
    private ControlAltaFoto controlAltaFoto;
    private ControlAltaMontaje controlAltaMontaje;
    private ControlAltaCuadro controlAltaCuadro;

    private ControlBajaObra controlBajaObra;
    private ControlMapear controlMapear;
    private ControlModEstado controlModEstado;

    //Gestor >> gestionSalas
    private ControlDividir controlDividir;
    private ControlInfo controlInfo;

    private JPanel contenidos;

    private UsuarioRegistrado usuarioActual = null;
    private Stack<String> stackPaneles = new Stack<>();


    private String panelActual = "";


    /**
     * Constructor de la clase Ventana.
     * Inicializa la ventana y configura su comportamiento y apariencia.
     * Crea e inicializa todas las vistas necesarias para la aplicación.
     * 
     * @param sistema El sistema que se utilizará en la aplicación.
     */
    public Ventana(Sistema sistema){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    sistema.guardar("resources/sistema.objectData");
                } catch (IOException excp){
                    excp.printStackTrace();
                }
            }
        });
 
        setSize(500, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        contenidos = new JPanel();
        setContentPane(contenidos);
        contenidos.setLayout(new CardLayout());

        //Con todos los atributos: construir y añadir

        //Usuario no registrado
        this.vistaRegistro = new Registro(sistema);
        contenidos.add(vistaRegistro, "Registro");

        this.vistaInicioSesion = new InicioSesion(sistema);
        contenidos.add(vistaInicioSesion, "InicioSesion");

        this.vistaBusqueda = new Busqueda(sistema, this);
        contenidos.add(vistaBusqueda, "Busqueda");

        this.vistaFiltros = new FiltrosBusqueda();
        contenidos.add(vistaFiltros, "Filtros");

        this.vistaInicial= new Inicial(sistema);
        contenidos.add(vistaInicial, "Inicial");

        //Cliente
        this.vistaComprarEntrada = new ComprarEntrada(sistema);
        contenidos.add(vistaComprarEntrada, "ComprarEntrada");

        this.vistaInicioCliente = new InicioCliente(sistema);
        contenidos.add(vistaInicioCliente, "InicioCliente");

        this.vistaNotificaciones = new Notificaciones(sistema, this);
        contenidos.add(vistaNotificaciones, "Notificaciones");

        this.vistaPago = new Pago(sistema);
        contenidos.add(vistaPago, "Pago");

        //Empleado
        this.vistaCambiarDatos = new CambiarDatos(sistema);
        contenidos.add(vistaCambiarDatos, "CambiarDatos");

        this.vistaControlAmbiente = new ControlAmbiente(sistema);
        contenidos.add(vistaControlAmbiente, "ControlAmbiente");

        this.vistaCrearNotis = new CrearNotis(sistema);
        contenidos.add(vistaCrearNotis, "CrearNotis");

        this.vistaInicioEmpleado = new InicioEmpleado(sistema);
        contenidos.add(vistaInicioEmpleado, "InicioEmpleado");

        this.vistaVentaEntradas = new VentaEntradas(sistema);
        contenidos.add(vistaVentaEntradas, "VentaEntradas");

        //Gestor
        this.vistaEstadisticas = new Estadistica(sistema);
        contenidos.add(vistaEstadisticas, "Estadistica");

        this.vistaConfigHorario = new ConfigHorario(sistema);
        contenidos.add(vistaConfigHorario, "ConfigHorario");

        this.vistaConfigSort = new ConfigSort(sistema);
        contenidos.add(vistaConfigSort, "ConfigSort");

        this.vistaDescuento = new Desc(sistema);
        contenidos.add(vistaDescuento, "Descuento");

        
        this.getVistaDescuentoClienteFrecuente = new DescClienteFrecuente(sistema);
        contenidos.add(getVistaDescuentoClienteFrecuente, "DescuentoFrecuente");

        this.getVistaDescuentoCompraAnticipada = new DescCompraAnticipada(sistema);
        contenidos.add(getVistaDescuentoCompraAnticipada, "DescuentoAnticipada");


        this.vistaGestionDeEmpleados = new GestionDeEmpleados(sistema);
        contenidos.add(vistaGestionDeEmpleados, "GestionDeEmpleados");

        this.vistaGestionDeExposiciones = new GestionDeExposiciones(sistema);
        contenidos.add(vistaGestionDeExposiciones, "GestionDeExposiciones");

        this.vistaGestionDeObras = new GestionDeObras(sistema);
        contenidos.add(vistaGestionDeObras, "GestionDeObras");

        this.vistaInicioGestor = new InicioGestor(sistema);
        contenidos.add(vistaInicioGestor, "InicioGestor");

        this.vistaGestionDeSalas = new GestionDeSalas(sistema);
        contenidos.add(vistaGestionDeSalas, "GestionDeSalas");

        this.vistaOtrasOpciones = new OtrasOpciones(sistema);
        contenidos.add(vistaOtrasOpciones, "OtrasOpciones"); //cambio

        this.vistaSortFechaFija = new SortFechaFija(sistema);
        contenidos.add(vistaSortFechaFija, "SortFechaFija");

        this.vistaSortFreeDate = new SortFreeDate(sistema);
        contenidos.add(vistaSortFreeDate, "SortFreeDate");

        this.vistaSortTimeInterval = new SortTimeInterval(sistema);
        contenidos.add(vistaSortTimeInterval, "SortTimeInterval");

        //Gestor >> gestionEmpleados
        this.vistaAltaEmpleados = new AltaEmpleados(sistema);
        contenidos.add(vistaAltaEmpleados, "AltaEmpleados");

        this.vistaGestionContrasenias = new GestionContrasenias(sistema);
        contenidos.add(vistaGestionContrasenias, "GestionContrasenias");

        this.vistaGestionPermisosEmpl = new GestionPermisosEmpl(sistema);
        contenidos.add(vistaGestionPermisosEmpl, "GestionPermisosEmpl");

        //Gestor >> gestionExpos
        this.vistaAltaExpo = new AltaExpo(sistema);
        contenidos.add(vistaAltaExpo, "AltaExpo");

        this.vistaAltaExpoPermanente = new AltaExpoPermanente(sistema);
        contenidos.add(vistaAltaExpoPermanente, "AltaExpoPermanente");

        this.vistaBajaExpo = new BajaExpo(sistema);
        contenidos.add(vistaBajaExpo, "BajaExpo");

        this.vistaCancelar = new Cancelar(sistema);
        contenidos.add(vistaCancelar, "Cancelar");

        //Gestor >> gestionObras
        this.vistaAltaObra = new AltaObra(sistema);
        contenidos.add(vistaAltaObra, "AltaObra");

        this.vistaAltaMontaje = new AltaMontaje(sistema);
        contenidos.add(vistaAltaMontaje, "AltaMontaje");

        this.vistaAltaCuadro = new AltaCuadro(sistema);
        contenidos.add(vistaAltaCuadro, "AltaCuadro");

        this.vistaAltaEscultura = new AltaEscultura(sistema);
        contenidos.add(vistaAltaEscultura, "AltaEscultura");

        this.vistaAltaFoto = new AltaFoto(sistema);
        contenidos.add(vistaAltaFoto, "AltaFoto");


        this.vistaBajaObra = new BajaObra(sistema);
        contenidos.add(vistaBajaObra, "BajaObra");

        this.vistaMapear = new Mapear(sistema);
        contenidos.add(vistaMapear, "Mapear");

        this.vistaModEstado = new ModEstado(sistema);
        contenidos.add(vistaModEstado, "ModEstado");

        //Gestor >> gestionSalas
        this.vistaDividir = new Dividir(sistema);
        contenidos.add(vistaDividir, "Dividir");

        this.vistaInfo = new Info(sistema);
        contenidos.add(vistaInfo, "Info");
    }
/**
 * Establece el controlador para la ventana.
 * Obtiene los controladores de todas las vistas y los asigna a sus respectivas vistas.
 * 
 * @param controlador El controlador que se utilizará en la aplicación.
 */
    public void setControlador(Controlador controlador){

        //GetControl de todos los atributos

        //Usuario no registrado
        this.controlRegistro = controlador.getControlRegistro();
        this.vistaRegistro.setControlador(controlRegistro);
        this.controlInicioSesion = controlador.getControlInicioSesion();
        this.vistaInicioSesion.setControlador(controlInicioSesion);
        this.controlBusqueda = controlador.getControlBusqueda();
        this.vistaBusqueda.setControlador(controlBusqueda);
        this.controlFiltros = controlador.getControlFiltros();
        this.vistaFiltros.setControlador(controlFiltros);
        this.controlInicial = controlador.getControlInicial();
        this.vistaInicial.setControlador(controlInicial);
        //Cliente
        this.controlComprarEntrada = controlador.getControlCompraEntradas();
        this.vistaComprarEntrada.setControlador(controlComprarEntrada);
        this.controlInicioCliente = controlador.getControlInicioCliente();
        this.vistaInicioCliente.setControlador(controlInicioCliente);
        this.controlNotificaciones = controlador.getControlNotificaciones();
        this.vistaNotificaciones.setControlador(controlNotificaciones);

        this.controlPago = controlador.getControlPago();
        this.vistaPago.setControlador(controlPago);

        //Empleado
        this.controlCambiarDatos = controlador.getControlCambiarDatos();
        this.vistaCambiarDatos.setControlador(controlCambiarDatos);
        this.controlControlAmbiente = controlador.getControlControlAmbiente();
        this.vistaControlAmbiente.setControlador(controlControlAmbiente);
        this.controlCrearNotis = controlador.getControlCrearNotis();
        this.vistaCrearNotis.setControlador(controlCrearNotis);
        this.controlInicioEmpleado = controlador.getControlInicioEmpleado();
        this.vistaInicioEmpleado.setControlador(controlInicioEmpleado);
        this.controlVentaEntradas = controlador.getControlVentaEntradas();
        this.vistaVentaEntradas.setControlador(controlVentaEntradas);

        //Gestor
        this.controlConfigHorario = controlador.getControlConfigHorario();
        this.vistaConfigHorario.setControlador(controlConfigHorario);
        this.controlEstadisticas = controlador.getControlEstadistica();
        this.vistaEstadisticas.setControlador(controlEstadisticas);
        this.controlConfigSort = controlador.getControlConfigSort();
        this.vistaConfigSort.setControlador(controlConfigSort);
        this.controlDescuento = controlador.getControlDescuento();
        this.vistaDescuento.setControlador(controlDescuento);

        this.ControlDescuentoClienteFrecuente = controlador.getControlDescuentoClienteFrecuente();
        this.getVistaDescuentoClienteFrecuente.setControlador(ControlDescuentoClienteFrecuente);

        this.ControlDescuentoCompraAnticipada = controlador.getControlDescuentoCompraAnticipada();
        this.getVistaDescuentoCompraAnticipada.setControlador(ControlDescuentoCompraAnticipada);

        this.controlGestionDeEmpleados = controlador.getControlGestionDeEmpleados();
        this.vistaGestionDeEmpleados.setControlador(controlGestionDeEmpleados);
        this.controlGestionDeExposiciones = controlador.getControlGestionDeExposiciones();
        this.vistaGestionDeExposiciones.setControlador(controlGestionDeExposiciones);
        this.controlGestionDeObras = controlador.getControlGestionDeObras();
        this.vistaGestionDeObras.setControlador(controlGestionDeObras);
        this.controlGestionDeSalas = controlador.getControlGestionDeSalas();
        this.vistaGestionDeSalas.setControlador(controlGestionDeSalas);
        this.controlInicioGestor = controlador.getControlInicioGestor();
        this.vistaInicioGestor .setControlador(controlInicioGestor);
        this.controlOtrasOpciones = controlador.getControlOtrasOpciones();
        this.vistaOtrasOpciones.setControlador(controlOtrasOpciones);
        this.controlSortFechaFija = controlador.getControlSortFechaFija();
        this.vistaSortFechaFija.setControlador(controlSortFechaFija);
        this.controlSortFreeDate = controlador.getControlSortFreeDate();
        this.vistaSortFreeDate.setControlador(controlSortFreeDate);
        this.controlSortTimeInterval = controlador.getControlSortTimeInterval();
        this.vistaSortTimeInterval.setControlador(controlSortTimeInterval);

        //Gestor >> gestionEmpleados
        this.controlAltaEmpleados = controlador.getControlAltaEmpleados();
        this.vistaAltaEmpleados.setControlador(controlAltaEmpleados);
        this.controlGestionContrasenias = controlador.getControlGestionContrasenias();
        this.vistaGestionContrasenias.setControlador(controlGestionContrasenias);
        this.controlGestionPermisosEmpl = controlador.getControlGestionPermisosEmpl();
        this.vistaGestionPermisosEmpl.setControlador(controlGestionPermisosEmpl);

        //Gestor >> gestionExpos
        this.controlAltaExpo = controlador.getControlAltaExpo();
        this.vistaAltaExpo.setControlador(controlAltaExpo);
        this.controlAltaExpoPermanente = controlador.getControlAltaExpoPermanente();
        this.vistaAltaExpoPermanente.setControlador(controlAltaExpoPermanente);
        this.controlBajaExpo = controlador.getControlBajaExpo();
        this.vistaBajaExpo.setControlador(controlBajaExpo);
        this.controlCancelar = controlador.getControlCancelar();
        this.vistaCancelar.setControlador(controlCancelar);


        //Gestor >> gestionObras
        this.controlAltaObra = controlador.getControlAltaObra();
        this.vistaAltaObra.setControlador(controlAltaObra);

        this.controlAltaEscultura = controlador.getControlAltaEscultura();
        this.vistaAltaEscultura.setControlador(controlAltaEscultura);

        this.controlAltaFoto = controlador.getControlAltaFoto();
        this.vistaAltaFoto.setControlador(controlAltaFoto);
    
        this.controlAltaCuadro = controlador.getControlAltaCuadro();
        this.vistaAltaCuadro.setControlador(controlAltaCuadro);

        this.controlAltaMontaje = controlador.getControlAltaMontaje();
        this.vistaAltaMontaje.setControlador(controlAltaMontaje);

        this.controlBajaObra = controlador.getControlBajaObra();
        this.vistaBajaObra.setControlador(controlBajaObra);
        this.controlMapear = controlador.getControlMapear();
        this.vistaMapear.setControlador(controlMapear);
        this.controlModEstado = controlador.getControlModEstado();
        this.vistaModEstado.setControlador(controlModEstado);


        //Gestor >> gestionSalas
        this.controlDividir = controlador.getControlDividir();
        this.vistaDividir.setControlador(controlDividir);
        this.controlInfo = controlador.getControlInfo();
        this.vistaInfo.setControlador(controlInfo);

    }

    //Getters
    //Usuario no registrado
    public Registro getVistaRegistro(){

        return this.vistaRegistro;
    }

    public InicioSesion getVistaInicioSesion(){

        return this.vistaInicioSesion;
    }

    public Busqueda getVistaBusqueda(){

        return this.vistaBusqueda;
    }

    public FiltrosBusqueda getVistaFiltros(){

        return this.vistaFiltros;
    }

    public Inicial getVistaInicial(){

        return this.vistaInicial;
    }

    //Cliente
    public ComprarEntrada getVistaCompraEntradas(){

        return this.vistaComprarEntrada;
    }

    public InicioCliente getVistaInicioCliente(){

        return this.vistaInicioCliente;
    }

    public Notificaciones getVistaNotificaciones(){

        return this.vistaNotificaciones;
    }

    public Pago getVistaPago(){

        return this.vistaPago;
    }

    //Empleado
    public CambiarDatos getVistaCambiarDatos(){

        return this.vistaCambiarDatos;
    }

    public ControlAmbiente getVistaControlAmbiente(){

        return this.vistaControlAmbiente;
    }

    public CrearNotis getVistaCrearNotis(){

        return this.vistaCrearNotis;
    }

    public InicioEmpleado getVistaInicioEmpleado(){

        return this.vistaInicioEmpleado;
    }

    public VentaEntradas getVistaVentaEntradas(){

        return this.vistaVentaEntradas;
    }

    //Gestor
    public Estadistica getVistaEstadistica(){

        return this.vistaEstadisticas;
    }

    public ConfigHorario getVistaConfigHorario(){

        return this.vistaConfigHorario;
    }

    public ConfigSort getVistaConfigSort(){

        return this.vistaConfigSort;
    }

    public Desc getVistaDescuento(){

        return this.vistaDescuento;
    }

    public DescClienteFrecuente getVistaDescuentoClienteFrecuente(){

    return this.getVistaDescuentoClienteFrecuente;
    }

    public DescCompraAnticipada getVistaDescuentoCompraAnticipada(){

    return this.getVistaDescuentoCompraAnticipada;
    }

    public GestionDeEmpleados getVistaGestionDeEmpleados(){

        return this.vistaGestionDeEmpleados;
    }

    public GestionDeExposiciones getVistaGestionDeExposiciones(){

        return this.vistaGestionDeExposiciones;
    }

    public GestionDeObras getVistaGestionDeObras(){

        return this.vistaGestionDeObras;
    }

    public GestionDeSalas getVistaGestionDeSalas(){

        return this.vistaGestionDeSalas;
    }

    public InicioGestor getVistaInicioGestor(){

        return this.vistaInicioGestor;
    }

    public OtrasOpciones getVistaOtrasOpciones(){

        return this.vistaOtrasOpciones;
    }

    public SortFechaFija getVistaSortFechaFija(){

        return this.vistaSortFechaFija;
    }

    public SortFreeDate getVistaSortFreeDate(){

        return this.vistaSortFreeDate;
    }

    public SortTimeInterval getVistaSortTimeInterval(){

        return this.vistaSortTimeInterval;
    }

    //Gestor >> gestionEmpleados
    public AltaEmpleados getVistaAltaEmpleados(){

        return this.vistaAltaEmpleados;
    }

    public GestionContrasenias getVistaGestionContrasenias(){

        return this.vistaGestionContrasenias;
    }

    public GestionPermisosEmpl getVistaGestionPermisosEmpl(){

        return this.vistaGestionPermisosEmpl;
    }

    //Gestor >> gestionExpos
    public AltaExpo getVistaAltaExpo(){

        return this.vistaAltaExpo;
    }

    public AltaExpoPermanente getVistaAltaExpoPermanente(){

        return this.vistaAltaExpoPermanente;
    }


    public BajaExpo getVistaBajaExpo(){

        return this.vistaBajaExpo;
    }

    public Cancelar getVistaCancelar(){

        return this.vistaCancelar;
    }

    //Gestor >> gestionObras
    public AltaObra getVistaAltaObra(){

        return this.vistaAltaObra;
    }

    public AltaCuadro getVistaAltaCuadro(){

        return this.vistaAltaCuadro;
    }

    public AltaFoto getVistaAltaFoto(){

        return this.vistaAltaFoto;
    }

    public AltaEscultura getVistaAltaEscultura(){

        return this.vistaAltaEscultura;
    }

    public AltaMontaje getVistaAltaMontaje(){

        return this.vistaAltaMontaje;
    }



    public BajaObra getVistaBajaObra(){

        return this.vistaBajaObra;
    }

    public Mapear getVistaMapear(){

        return this.vistaMapear;
    }

    public ModEstado getVistaModEstado(){

        return this.vistaModEstado;
    }

    //Gestor >> gestionSalas
    public Dividir getVistaDividir(){

        return this.vistaDividir;
    }

    public Info getVistaInfo(){

        return this.vistaInfo;
    }

    public UsuarioRegistrado getUsuarioActual(){
        return this.usuarioActual;
    }

    public void setUsuarioActual(UsuarioRegistrado actual){
        this.usuarioActual = actual;
    }
    /**
     * Muestra el panel correspondiente al identificador de la carta especificada.
     * 
     * @param carta El identificador del panel a mostrar.
     */
    public void mostrarPanel(String carta){
        stackPaneles.push(panelActual); /*pusheamos el panel actual*/
        panelActual = carta;

        CardLayout l = (CardLayout)contenidos.getLayout();
        l.show(contenidos, carta);
    }
    /**
     * Muestra el panel anterior al panel actual, si existe.
     * Si hay un panel anterior en la pila de paneles, lo muestra y actualiza el panel actual.
     */
    public void mostrarPanelAnterior(){
        if (!stackPaneles.isEmpty()){
            String panelAnterior = stackPaneles.pop(); /*obtener panel anterio haaciendo pop */
            panelActual = panelAnterior;

            CardLayout l = (CardLayout)contenidos.getLayout();
            l.show(contenidos, panelAnterior);
        }
    }

}

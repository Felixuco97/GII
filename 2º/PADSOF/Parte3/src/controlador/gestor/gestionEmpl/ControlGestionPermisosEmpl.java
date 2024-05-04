package controlador.gestor.gestionEmpl;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import modelo.sistema.Sistema;
import modelo.usuarios.Empleado;
import modelo.usuarios.Permiso;
import vista.Ventana;
import vista.gestor.gestionEmpl.GestionPermisosEmpl;
/**
 * @author Felix López,Fernando Sanchez y Andrés M. Alonso
 */
public class ControlGestionPermisosEmpl implements ActionListener{
    
    private Ventana frame;
    private GestionPermisosEmpl vista;
    private Sistema sistema;

    public ControlGestionPermisosEmpl(Ventana frame, Sistema sistema){
        this.sistema = sistema;
        this.frame = frame;
        this.vista = frame.getVistaGestionPermisosEmpl();
    }  
    /**
     * Maneja las acciones realizadas en la interfaz de usuario.
     *
     * @param e El evento de acción que desencadenó este método.
     */
    public void actionPerformed(ActionEvent e){

        if(e.getSource().equals((vista.getControladorGuardar()))){
        
        String empleadoSeleccionado = vista.getEmpleadoSeleccionado();
        String accion = vista.getAccion();
        ArrayList<JRadioButton> lista = vista.getRadioButtons(); //para los permisos

        //buscar el empleado en el sistema
        Empleado empleado = null;
        for (Empleado emp : sistema.getEmpleadosRegistrados()) {
            if (emp.getNombre().equals(empleadoSeleccionado)) {
                empleado = emp;
                break;
            }
        }

        if (empleado == null) {
            JOptionPane.showMessageDialog(vista,
            "No se ha encontrado al empleado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //lsista de permisos seleccionados
        ArrayList<Permiso> permisos = new ArrayList<>();

        //comprobar q permisos están seleccionados
        if (lista.get(0).isSelected()) {
            permisos.add(Permiso.VENDER_ENTRADAS); 
        }
        if (lista.get(1).isSelected()) {
            permisos.add(Permiso.MODIFICAR_TH);
        }
        if (lista.get(2).isSelected()) {
            permisos.add(Permiso.NOTIFICAR);
        }

        Permiso[] permisosArray = new Permiso[permisos.size()];
        permisosArray = permisos.toArray(permisosArray);

        //llamar a la función gestionarPermisosEmpleado
        sistema.gestionarPermisosEmpleado(empleado, accion, permisosArray);

        frame.mostrarPanelAnterior();
        vista.update();

        } else if(e.getSource().equals((vista.getControladorAtras()))){
            frame.mostrarPanelAnterior();
            vista.update();
        }
        vista.update();
    }
}

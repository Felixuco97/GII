package main;

import java.awt.EventQueue;
import modelo.*;
import vista.*;
import controlador.*;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {						
					Ventana frame = new Ventana();
					Proyecto modelo = new Proyecto();
					Controlador controlador = new Controlador(frame, modelo);
					frame.setControlador(controlador);
					frame.setVisible(true);
					frame.mostrarPanel("altaTarea");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

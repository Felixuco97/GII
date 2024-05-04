# Información importante
### Claves Gestor
- *Nif* : 03135608D
- *Contrasenia* : $_G3sT0r_$ 
### Clientes
Hay muchos ejemplos de clientes en el fichero usuarioregistrado.csv, pero aqui dejo uno para probar:
- *Nif* : 12345678W
- *Contrasenia* : contrasenia1
- *Num Tarjeta* : 3456345634563456

Para el pago el titulo y el CVV, no importa su valor, ya que nuestra funcion de compra no los tiene en cuenta, es solo para simular una tipica pagina de pago

### Empleado
Al igual que en clientes, en el csv hay mas ejemplos
- *Nif* : 08016051J
- *Contrasenia* : Emple@d0s
- *Num Seg Social* : 111232342133
- *Num de cuenta* : 765409582034
- *Direccion* : Calle de los Limones, 42
- *Permisos* : Vender entradas y Notificaciones

### Entradas
Se guardan en el directorio: src/tickets

### Horario del centro
- *Apertura* : 9:00
- *Cierre* : 18:00


javadoc -classpath resources/TeleChargeAndPaySystem8.jar:resources/TicketSystem.jar:resources/jdatepicker-1.3.4.jar -d EventHub -author src/modelo/usuarios/*.java src/modelo/sistema/*.java src/modelo/centroExposiciones/*.java src/modelo/centroExposiciones/salas/*.java src/modelo/centroExposiciones/obras/*.java src/modelo/centroExposiciones/obras/obrasFisicas/*.java src/modelo/centroExposiciones/exposiciones/*.java src/modelo/centroExposiciones/sorteos/*.java src/vista/*.java src/vista/cliente/*.java src/vista/empleado/*.java src/vista/gestor/*.java src/modelo/utils/*.java src/controlador/*.java src/controlador/cliente/*.java src/controlador/empleado/*.java src/controlador/gestor/*.java src/controlador/gestor/configuracionDescuentos/*.java src/controlador/gestor/gestionEmpl/*.java src/controlador/gestor/gestionExpos/*.java src/controlador/gestor/gestionObras/*.java src/controlador/gestor/gestionSalas/*.java src/main/*.java



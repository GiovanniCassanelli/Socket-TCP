import java.net.*;
import java.io.*;

public class ServidorTCP {

    private int puerto;
    private Socket cliente;
    private DataInputStream in;
    private DataOutputStream out;
    private String mensaje;

    public ServidorTCP(int puerto) {
        this.puerto = puerto;
    }

    public void iniciar() {

        while(true) {
            // Inicia el socket del servidor
            try (ServerSocket servidor = new ServerSocket(puerto)) {
                System.out.println("El servidor está escuchando en el puerto " + puerto);

                // La única forma de cerrar el servidor es terminando el proceso
                while (true) {

                    // El servidor siempre está a la espera de clientes que se conecten
                    try (Socket cliente = servidor.accept()) {
                        System.out.println("Se ha conectado un cliente");

                        // Inicia los flujos de entrada y salida entre servidor y cliente
                        in = new DataInputStream(cliente.getInputStream());
                        out = new DataOutputStream(cliente.getOutputStream());

                        do {
                            // Recibe el siguiente int desde el cliente
                            mensaje = in.readUTF();
                            System.out.println("El cliente dice: " + mensaje);

                            // Interpreta la elección del cliente
                            responder();

                        } while (!mensaje.equals("salir"));

                    }
                }

            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage() + "\n");
            }
        }
    }

    private void responder() {
        if (mensaje.equals("salir")) {
            System.out.println("El cliente se ha desconectado\n");

            try {
                // Cierra los flujos
                in.close();
                out.close();
                cliente.close();
            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage() + "\n");
            }
        }
        else {
            try {
                StringBuilder sb = new StringBuilder(mensaje);

                // Invierte el mensaje del cliente
                out.writeUTF(sb.reverse().toString());
            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage() + "\n");
            }
        }
    }
}

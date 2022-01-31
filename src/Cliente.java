import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class Cliente {

    /*
     *atributos para manejar salida y entrada de texto
     */

    BufferedReader delTeclado;
    DataOutputStream alservidor;
    FileInputStream entrada;

    /*
     *atributos para manejar entrada de archivos
     */
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;
    FileOutputStream destino = null;
    BufferedOutputStream out = null;
    BufferedInputStream in = null;
    boolean respuesta = false;
    int tam = 0;
    String nombreArchivo = null;


        public void iniciar() {

            try {
                //Pedir la IP
                System.out.println("Introduce la direccion IP del servidor de Chatbot");
                System.out.print("[yo]: ");
                String serverAddress = System.console().readLine();

                //Creamos el socket
                Socket socket = new Socket(serverAddress, 9999);

                System.out.println("Presiona 0 para iniciar.");
                System.out.print("[yo]: ");
                    //Solicitar confirmación de inicio:
                    String teclado = System.console().readLine();

                    //Enviar s al servidor
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(teclado);

                    //Recibimos la respuesta del servidor
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String answer = input.readLine();

                    //Imprimir el mensaje
                    System.out.println("[Eurobot]: "+answer);
                System.out.print("[yo]: ");


                //Solicitar nombre
                 String nombre = System.console().readLine();
                //Enviar nombre al servidor
                 out = new PrintWriter(socket.getOutputStream(), true);
                out.println(nombre);


                    //*********************************
                     input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     answer = input.readLine();
                      System.out.println("[Eurobot]: "+answer);

                    while(true){
                        System.out.print("[yo]: ");
                        //Solicitar opción del 1 al 5
                        teclado = System.console().readLine();
                        //Enviar opcion
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(teclado);

                        if(teclado.equals("5")){
                            socket.close();
                            System.out.println("[Eurobot]: "+nombre + ". Hasta pronto!!!, ");
                            return;
                        }
                        //*********************************
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        answer = input.readLine();
                        System.out.println("[Eurobot]: "+ answer);
                        Thread.sleep(50);
                        //*********************************
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        answer = input.readLine();
                        System.out.println("[Eurobot]: "+ answer);

                    }
            }catch (Exception e){
                System.out.println("error " + e.getMessage() + " cliente");
            }

        }

    public static void main(String args[]) {
        Cliente ea = new Cliente();
        ea.iniciar();

    }
}

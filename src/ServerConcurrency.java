import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConcurrency {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = null;
        ServerSocket serverSocket = null;
        Chat chat;
        Integer id = 0;

        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Servidor Iniciado");

            while(true){
                socket = serverSocket.accept();
                id++;
                Chat.Imprimir("\nSe conecto el cliente No." + id + " desde la IP: " + socket.getInetAddress());
                Chat.Imprimir("**************************************************");
                chat = new Chat(socket, id);
                chat.start();
            }

        } catch (IOException e) {
            Chat.Imprimir(e.getMessage() + " [servidor]");
            System.exit(3);
        } finally {
        }

    }

    static class Chat extends Thread {
        Integer id;
        Socket s = null;

        private Chat(Socket socket, int id) {
            this.s = socket;
            this.id = id;
        }

        /*
         *Atributos para manejar salida y entrada de texto
         */
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        public void run() {

            try {

                PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String inputString = input.readLine();
                String nombreCliente;

                if(inputString.equals("0")){
                    Chat.Imprimir("********************ingreso********************");
                    out.println("Bienvenido usuario numero: " + id + ". Soy EuroBot tu asistente virtual... Cual es tu nombre?");
                    nombreCliente = input.readLine();

                    while (true) {
                        Thread.sleep(50);
                        out.println(nombreCliente + ", Selecciona la opcion que deseas: 1.Admision 2.Masters 3.Blog  4.Contacto 5.Finalizar");

                        String opcion = input.readLine();
                        if (isNumeric(opcion)) {
                            switch (opcion) {
                                case "1":
                                    out.println(nombreCliente+" ingresa aqui: https://universidadeuropea.com/admisiones-y-financiacion/proceso-admision/");
                                    break;
                                case "2":
                                    out.println(nombreCliente+" ingresa aqui: https://universidadeuropea.com/masters-universitarios/");
                                    break;
                                case "3":
                                    out.println(nombreCliente+" ingresa aqui: https://universidadeuropea.com/blog/");
                                    break;
                                case "4":
                                    out.println(nombreCliente+" ingresa aqui: https://universidadeuropea.com/contacto/");
                                    break;
                                case "5":
                                    s.close();
                                    return;
                                default:
                                    out.println(nombreCliente+". Opcion no valida");
                            }
                        } else {
                            out.println(nombreCliente+ " "+opcion + " no es un numero.");
                        }

                    }

                }
                Chat.Imprimir("***********************fuera***************************");

                out.println(inputString);
                out.println("Sin respuesta");

            } catch (Exception ex) {
                Imprimir(ex.getMessage() + " Error Cliente id:" + id);
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                    if (s != null) {
                        s.close();
                    }
                    System.out.println("Termino proceso para cliente: " + id);

                } catch (Exception e) {
                    System.out.println(e.getMessage() + " [servidor]");
                }
            }
        }

        public static boolean isNumeric(String str){
            try{
                Integer.parseInt(str);
            }catch (NumberFormatException e){
                return false;
            }
            return true;
        }

        public static void Imprimir(String txt) {
            System.out.println(txt);
        }
    }
}

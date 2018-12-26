package Server;

import Client.Client;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
//import package1.TicTacToe;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import static package1.XSDValidator.*;

public class Server {


    public static final int PORT = 3009;
    @SuppressWarnings("WeakerAccess")
    public static final String quitMsg = "/quit", messagePath = "",
            serverMessageName = "ServerMessage.xml", serverMessagePath = "";
    private static List<ServerClient> clientsList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            //ServerClient.createServerMessage("");
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server started");


            while(true) {
                Socket socket = server.accept();
                System.out.println("Player" + ServerClient.nextID + " connects");
                try {
                    clientsList.add(new ServerClient(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServerClient extends Thread {
        private static int nextID = 1;

        private final int ID;
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;

        private ServerClient(Socket inSocket) throws IOException {
            ID = nextID++;
            socket = inSocket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Player" + ID + "\'s input/output streams initialized.");
            start();
        }

        @Override
        public void run() {
            sendToThis("<Server>: You're Player" + ID + ". To exit this session enter \"" + Server.quitMsg + '\"');
            try {
                while (true) {
                    String msg = in.readLine();

                        try {
                            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                            String filePath = Client.messagePath + msg;
                            Document doc = docBuilder.parse(new File(filePath));

                            validateXMLSchema(Client.schemaPath, filePath);
                            Node root = doc.getDocumentElement();
                            Node message = root.getFirstChild();
                            msg = message.getTextContent();

                            //System.out.println("Player" + this.ID + " send\"" + msg + '\"');
                        } catch (ParserConfigurationException | IOException e) {
                            e.printStackTrace(System.out);
                        } catch (SAXException e) {
                            System.out.println("Validate exception");
                            e.printStackTrace(System.out);
                        }

                        if (msg.equals(Server.quitMsg)) {
                            sendToAll("<Server>: Player" + ID + " quits");
                            sendToThis(Server.quitMsg);
                            this.quitSession();
                            System.out.println("Player" + ID + " disconnected");
                            break;
                        }
                        sendToAll("<Player" + ID + ">: " + msg);


                    }
                } catch (Exception e) {e.printStackTrace(System.out);}
            }

        private void quitSession() {
            try {
                socket.close();
                in.close();
                out.flush();
                out.close();
                this.interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendToAll(String msg) {
            createServerMessage(msg);
            for (ServerClient sc : Server.clientsList) {
                if (sc != this)
                    try {
                        sc.out.write(serverMessageName + '\n');
                        sc.out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void sendToThis(String msg) {
            createServerMessage(msg);
            for (ServerClient sc : Server.clientsList) {
                if (sc == this)
                    try {
                        sc.out.write(serverMessageName + '\n');
                        sc.out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private static void createServerMessage(String msg) {
            try {
                XMLOutputFactory output = XMLOutputFactory.newInstance();
                XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(serverMessagePath + serverMessageName));
                writer.writeStartDocument("1.0");
                writer.writeCharacters("\n");
                writer.writeStartElement("line");
                writer.writeCharacters(msg);
                writer.writeEndElement();
                writer.writeEndDocument();
                writer.flush();
            } catch (IOException | XMLStreamException ex) {
                ex.printStackTrace(System.out);
            }
        }

}}

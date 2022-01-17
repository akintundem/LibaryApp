import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrameworkServer implements Runnable{

    InputStream input;
    OutputStream output;
    ServerSocket serverSocket = null;
    public Socket clientSocket = null;
    FrameworkClientMessageInterface myClientMessageHandler;
    FrameworkUserInterface myUI;
    int portNumber = 7777, backlog = 500;
    boolean doListen = false;
    admin administrator;
    

    public FrameworkServer(int portNumber, int backlog, FrameworkUserInterface myUI) {
        this.portNumber = portNumber;
        this.backlog = backlog;
        this.myUI = myUI;
     }

    public void setClientMessageHandler(FrameworkClientMessageInterface myClientMessageHandler) {
        this.myClientMessageHandler = myClientMessageHandler;
    }
    
    public synchronized void setDoListen(boolean doListen){
        this.doListen = doListen;
    }
    public void startServer() {
        if (serverSocket != null) {
            stopServer();
        } else {
            try {
                serverSocket = new ServerSocket(portNumber, backlog);
                serverSocketStarted();
                //turn on admin
                administrator = new admin("Liberty");
                administrator.runApp();
            } catch (IOException | ParseException e) {
                sendMessageToUI("Cannot create ServerSocket, because " + e +". try again.");
                //System.exit(0);
            } finally {
            }
        }
    }

    public void serverSocketStarted() {
    }
    public void serverSocketStopped() {
    }
    public void serverStartedListening()  {
    }
    public void serverStoppedListening()  {
    }

    public void stopServer() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                serverSocketStopped();
            } catch (IOException e) {
                sendMessageToUI("Cannot close ServerSocket, because " + e +". Exiting program.");
                System.exit(0);
            } finally {
            }

        }
    }

    public void listen() {
        try {
            setDoListen(true);
            serverSocket.setSoTimeout(500);
            Thread myListenerThread = new Thread(this);
            myListenerThread.start();
            serverStartedListening();
        } catch (SocketException ex) {
            Logger.getLogger(FrameworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stopListening() {
        setDoListen(false);
        serverStoppedListening();
    }

    @Override
    public void run() {
        while (true) {
            if (doListen == true) {
                try {
                    clientSocket = serverSocket.accept();
                    FrameworkClientConnection myCC = new FrameworkClientConnection(clientSocket, myClientMessageHandler, this);
                    Thread myCCthread = new Thread(myCC);
                    myCCthread.start();
                    clientConnected(clientSocket.getRemoteSocketAddress().toString());
                } catch (IOException e) {
                    //check doListen.
                } finally {
                }
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {}
            }
        }
    }

    public void clientConnected(String clientIPAddress) {
        sendMessageToUI("Client connected:\n\tRemote Socket Address = " + clientIPAddress + "\n\tLocal Socket Address = " + clientSocket.getLocalSocketAddress());
    }
    
    public void clientDisconnected(String clientIPAddress) {
        sendMessageToUI("\tClient "+ clientIPAddress + " has been disconnected.");
    }
    
                    

    public void setPort(int portNumber) {
        this.portNumber = portNumber;
    }

    public int getPort() {
        return this.portNumber;
    }
    
    public void sendMessageToUI(String theString) {
        myUI.update(theString);
    }
}
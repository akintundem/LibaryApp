public class TimeServerTest {

    public static void main(String[] args) {

        StandardIO myUI =
                new StandardIO();
        TimeServer myServer = new TimeServer(7777, 100, myUI);
        ClientMessageHandler
                myClientMessageHandler = 
                new ClientMessageHandler(myServer);
        myServer.setClientMessageHandler(myClientMessageHandler);
        TimeServerUserCommandHandler
                myUserCommandHandler = 
                new TimeServerUserCommandHandler
                (myUI, myServer);
        myUI.setUserCommandHandler(myUserCommandHandler);
        Thread myUIthread = new Thread(myUI);
        myUIthread.start();     
        myUI.update("1:\tQuit\n2:\tlisten\n3:\tSet Port\n4:\tGet " + "Port\n5:\tStop listening\n6:\tStart Server Socket\n");
    }
}


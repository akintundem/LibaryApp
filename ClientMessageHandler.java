/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferens
 */
public class ClientMessageHandler implements FrameworkClientMessageInterface{

    TimeServer myServer;
    String theCommand = "";
    users bank;
    public ClientMessageHandler(TimeServer myServer) {
        this.myServer = myServer;
    }

    public void handleClientMessage(FrameworkClientConnection myClientConnection, String msg) throws ParseException {
        if (msg.charAt(0)!=0xFFFF) { //Character.toString((char(-1)) = 0xFFFF
            theCommand += msg;
        } else {
            handleCompleteClientMessage(myClientConnection, theCommand);
            theCommand = "";
        }
    }

    public void handleClientMessage(String theExceptionalEvent) {
        myServer.sendMessageToUI(theExceptionalEvent);
    }



    public void handleCompleteClientMessage(FrameworkClientConnection myClientConnection, String theCommand) throws ParseException {
        String[] infoClient = theCommand.split("/");
        for(int i = 0; i < infoClient.length; i++){
            System.out.println(infoClient[i]);
        }
        switch (infoClient[0]) {
            case "newuser":
                //theCommand = newuser/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                myServer.sendMessageToUI("A new customer has been added to the bank");
                myClientConnection.sendStringMessageToClient("A new customer has been added to the bank");
                bank.insert(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8]));
                break;
            case "viewuser":
                //theCommand = viewuser/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                System.out.println("Hi");
                myServer.sendMessageToUI("Searching for customer");
                myClientConnection.sendStringMessageToClient("Searching for customer");
                bank.retrieve(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8]));
                break;
            case "delete":
                //theCommand = delete/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                myServer.sendMessageToUI("Delete customer");
                myClientConnection.sendStringMessageToClient("Delete customer");
                bank.delete(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8]));
                break;
            case "exist":
                //theCommand = exist/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                myServer.sendMessageToUI("Checking Operation on going");
                myClientConnection.sendStringMessageToClient("Checking Operation on going");
                bank.search(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8]));
                break;
            case "withdraw":
                //theCommand = withdraw/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                myServer.sendMessageToUI("Withdrawal Operation on going");
                myClientConnection.sendStringMessageToClient("Withdrawal Operation on going");
                bank.retrieve(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8])).withdrawal(Integer.parseInt(infoClient[9]));
                break;
            case "deposit":
                //theCommand = deposit/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                myServer.sendMessageToUI("Deposit Operation on going");
                myClientConnection.sendStringMessageToClient("Deposit Operation on going");
                bank.retrieve(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8])).deposit(Integer.parseInt(infoClient[9]));
                break;
            case "balance":
                //theCommand = balance/FirstName/UserName/MiddleName/LastName/emailAddress/PhoneNumber/Country/sinNumber/Amount
                myServer.sendMessageToUI("Balance Operation on going");
                myClientConnection.sendStringMessageToClient("Balance Operation on going");
                bank.retrieve(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8])).balance();
                break;
            case "transfer":
                //theCommand = transfer/FirstNameS/UserNameS/MiddleNameS/LastNameS/PhoneNumberS/CountryS/sinNumberS
                //             FirstNameR/UserNameR/MiddleNameR/LastNameR/PhoneNumberR/CountryR/sinNumberR/Amount
                myServer.sendMessageToUI("Transfer Operation on going");
                myClientConnection.sendStringMessageToClient("Transfer Operation on going");
                //bank.transferTransaction();
                break;
            case "savings":
                //theCommand = savings/FirstName/UserName/MiddleName/LastName/PhoneNumber/Country/sinNumber
                //              year/month/day/Amount
                myServer.sendMessageToUI("Saving Operation on going");
                myClientConnection.sendStringMessageToClient("Saving Operation on going");
                bank.retrieve(infoClient[1],infoClient[2],infoClient[3],infoClient[4],infoClient[5],Integer.parseInt(infoClient[6]),infoClient[7],Integer.parseInt(infoClient[8])).saveInPiggyBank(Integer.parseInt(infoClient[9]),0,0,0);
                break;
            default:
                break;
        }
    }
    
    private String byteToString(byte theByte) {
        byte[] theByteArray = new byte[1];
        String theString = null;
        theByteArray[0] = theByte;
        try {
            theString = new String(theByteArray, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FrameworkClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            myServer.sendMessageToUI("Cannot convert from UTF-8 to String; exiting program.");
            System.exit(0);
        } finally {
            return theString;
        }
    }

}



//database for test & android studio for client 
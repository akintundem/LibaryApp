import java.net.UnknownHostException;

public class admin {

    String nameOfApp;
    private int officialSaveBox = 0;
    users ledger;

    public admin (String nameOfApp){
        this.nameOfApp = nameOfApp;
    }

    public void runApp() throws UnknownHostException {
        ledger = new users(100); //start a ledger
    }

    public void withdrawalFromBank(int Amount){
        officialSaveBox-=Amount;
    }
    public void depositTobank(int Amount){
        officialSaveBox += Amount;
    }
}

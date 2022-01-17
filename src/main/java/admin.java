import java.net.UnknownHostException;
import java.text.ParseException;

public class admin {

    String nameOfApp;
    private int officialSaveBox = 0;
    users ledger;

    public admin (String nameOfApp){
        this.nameOfApp = nameOfApp;
    }

    public void runApp() throws UnknownHostException, ParseException {
        ledger = new users(100); //start a ledger
        ledger.insert("Mayokun","mos","Moses","Akintunde","akintundemayokun@gmail.com",2045145629,"Nigeria",8923892,"koko");
        System.out.println(ledger.find("mos","koko"));
        System.out.println(ledger);
    }

    public void withdrawalFromBank(int Amount){
        officialSaveBox-=Amount;
    }
    public void depositTobank(int Amount){
        officialSaveBox += Amount;
    }
}

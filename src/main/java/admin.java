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
        ledger = new users(); //start a ledger
        ledger.insert("Mayokun","mos","Moses","Akintunde","akintundemayokun@gmail.com",2045145629,"Nigeria",8923892,"kokko");
    }

    public void withdrawalFromBank(int Amount){
        officialSaveBox-=Amount;
    }
    public void depositTobank(int Amount){
        officialSaveBox += Amount;
    }
}

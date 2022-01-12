public class admin {

    String nameOfApp;
    private int officialSaveBox = 0;
    users ledger;

    public admin (){
        this.nameOfApp = "Liberty";
    }
    public void runApp(){

    }
    public void withdrawalFromBank(int Amount){
        officialSaveBox-=Amount;
    }
    public void depositTobank(int Amount){
        officialSaveBox += Amount;
    }
}

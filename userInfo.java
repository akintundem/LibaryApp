import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class userInfo {
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int phoneNumber;
    private String country;
    //date of birth
    private int sinNumber;
    private int cashInHand = 0;
    private String userName;
    private int amountWithdrawFromSavings = 0;
    admin bank;

    public int getPiggybankBalance() {
        return piggybankBalance;
    }

    private int piggybankBalance;

    public int getAmountWithdrawFromSavings() {
        return amountWithdrawFromSavings;
    }

    public void setAmountWithdrawFromSavings(int amountWithdrawFromSavings) {
        this.amountWithdrawFromSavings = amountWithdrawFromSavings;
    }

    public admin getBank() {
        return bank;
    }

    public Date getFirstSaveDate() {
        return firstSaveDate;
    }

    public Date getLastSaveDate() {
        return lastSaveDate;
    }

    private Date firstSaveDate;
    private Date lastSaveDate;

    public String getUserName() {
        return userName;
    }

    public userInfo(String firstName,
                    String userName,
                    String middleName,
                    String lastName,
                    String emailAddress,
                    int phoneNumber,
                    String country,
                    int sinNumber){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.userName = userName;
        this.sinNumber = sinNumber;
    }
    public int deposit(int depositAmount){
        cashInHand += depositAmount;
        return cashInHand;
    }

    public int withdrawal(int withdrawalAmount){
        cashInHand -= withdrawalAmount;
        return cashInHand;
    }

    public int balance(){
        return cashInHand;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public int getSinNumber() {
        return sinNumber;
    }

    public int getCashInHand() {
        return cashInHand;
    }

    public void saveInPiggyBank(int Amount,int year, int month, int day) throws ParseException {
        firstSaveDate = new Date();
        lastSaveDate = new SimpleDateFormat("yyyy/MM/dd").parse(year+"/"+month+"/"+day);
        piggybankBalance += Amount;
            withdrawal(Amount);
            bank.depositTobank(Amount);
    }

    public void recieveSavings(int Amount){
        bank.withdrawalFromBank(Amount);
        deposit(Amount);

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String toString(){
        return "Customer Name: " + firstName + " " + lastName + "\n" + "Email Address: " + emailAddress + "\n"
                + "Country: " + country + "\n" + "Account Balance " + balance();
    }
}

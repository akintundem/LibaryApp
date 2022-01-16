import com.mongodb.BasicDBObject;

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
    private int piggybankBalance;
    private Date firstSaveDate;
    private Date lastSaveDate;
    private String Password;
    admin bank;

    public void update(String firstName,String middleName, String lastName, String emailAddress, int phoneNumber,  String country, int sinNumber, int cashInHand, String userName, int amountWithdrawFromSavings,  int piggybankBalance, Date firstSaveDate , Date lastSaveDate){
        users.test.findAndModify(new BasicDBObject("firstName",this.firstName).append("userName",this.userName)
                .append("middleName",this.middleName).append("lastName",this.lastName).append("emailAddress",this.emailAddress)
                .append("phoneNumber",this.phoneNumber).append("country",this.country).append("sinNumber",this.sinNumber).append("cashInHand",this.cashInHand).append("amountWithdrawFromSavings",this.amountWithdrawFromSavings).append("piggybankBalance",this.piggybankBalance).append("firstSaveDate",this.firstSaveDate).append("lastSaveDate",this.lastSaveDate),new BasicDBObject("firstName",firstName).append("userName",userName)
                .append("middleName",middleName).append("lastName",lastName).append("emailAddress",emailAddress)
                .append("phoneNumber",phoneNumber).append("country",country).append("sinNumber",sinNumber).append("cashInHand",cashInHand).append("amountWithdrawFromSavings",amountWithdrawFromSavings).append("piggybankBalance",piggybankBalance).append("firstSaveDate",firstSaveDate).append("lastSaveDate",lastSaveDate));
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        update(firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        update(this.firstName,middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        update(this.firstName,this.middleName,lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        update(this.firstName,this.middleName,this.lastName,emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setCountry(String country) {
        this.country = country;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setSinNumber(int sinNumber) {
        this.sinNumber = sinNumber;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setCashInHand(int cashInHand) {
        this.cashInHand = cashInHand;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setUserName(String userName) {
        this.userName = userName;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }


    public void setPiggybankBalance(int piggybankBalance) {
        this.piggybankBalance = piggybankBalance;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,piggybankBalance,this.firstSaveDate,this.lastSaveDate);

    }

    public void setFirstSaveDate(Date firstSaveDate) {
        this.firstSaveDate = firstSaveDate;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,firstSaveDate,this.lastSaveDate);

    }

    public void setLastSaveDate(Date lastSaveDate) {
        this.lastSaveDate = lastSaveDate;
        update(this.firstName,this.middleName,this.lastName,this.emailAddress,this.phoneNumber,this.country,this.sinNumber,this.cashInHand,this.userName,this.amountWithdrawFromSavings,this.piggybankBalance,this.firstSaveDate,lastSaveDate);

    }

    public int getPiggybankBalance() {
        return piggybankBalance;
    }



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

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public userInfo(String firstName,
                    String userName,
                    String middleName,
                    String lastName,
                    String emailAddress,
                    int phoneNumber,
                    String country,
                    int sinNumber,
                    String Password){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.userName = userName;
        this.sinNumber = sinNumber;
        this.Password = Password;
    }
    public int deposit(int depositAmount){
        cashInHand += depositAmount;
        setCashInHand(cashInHand);
        return cashInHand;
    }

    public int withdrawal(int withdrawalAmount){
        cashInHand -= withdrawalAmount;
        setCashInHand(cashInHand);
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
        setFirstSaveDate(firstSaveDate);
        lastSaveDate = new SimpleDateFormat("yyyy/MM/dd").parse(year+"/"+month+"/"+day);
        setLastSaveDate(lastSaveDate);
        piggybankBalance += Amount;
            withdrawal(Amount);
            bank.depositTobank(Amount);
            setCashInHand(cashInHand-Amount);
            setPiggybankBalance(piggybankBalance+Amount);
            setAmountWithdrawFromSavings(Amount);
    }

    public void recieveSavings(int Amount){
        bank.withdrawalFromBank(Amount);
        deposit(Amount);
        setCashInHand(cashInHand+Amount);
        setPiggybankBalance(piggybankBalance-Amount);
        setAmountWithdrawFromSavings(0);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String toString(){
        return "Customer Name: " + firstName + " " + lastName + "\n" + "Email Address: " + emailAddress + "\n"
                + "Country: " + country + "\n" + "Account Balance " + balance();
    }
}

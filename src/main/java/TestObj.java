public class TestObj {
    private String firstName ;
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

    public TestObj(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSinNumber() {
        return sinNumber;
    }

    public void setSinNumber(int sinNumber) {
        this.sinNumber = sinNumber;
    }

    public int getCashInHand() {
        return cashInHand;
    }

    public void setCashInHand(int cashInHand) {
        this.cashInHand = cashInHand;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAmountWithdrawFromSavings() {
        return amountWithdrawFromSavings;
    }

    public void setAmountWithdrawFromSavings(int amountWithdrawFromSavings) {
        this.amountWithdrawFromSavings = amountWithdrawFromSavings;
    }
}

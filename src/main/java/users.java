import com.mongodb.*;

import javax.swing.text.Document;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class users{
    HashMap<String,userInfo> bankusers = new HashMap<String,userInfo>();
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test;
    TestObj testObj = new TestObj();



    public users() throws UnknownHostException, ParseException {
        startmongoDB();
    }

    public void startmongoDB() throws UnknownHostException, ParseException {
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        this.database = mongoClient.getDB("bankUsers");
        this.test = database.getCollection("bankUser");
        //fill in users from mongo into allUsers
        DBCursor cursor = test.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.one();
            insert((String) obj.get("firstName"),(String) obj.get("userName"),(String)obj.get("middleName"), (String)obj.get("lastName")
                    ,(String)obj.get("emailAddress"),(Integer) obj.get("phoneNumber"),(String) obj.get("country"),(Integer) obj.get("sinNumber"), (String) obj.get("Password"));
            cursor.next();
        }
    }

    public userInfo find(String userName, String Password){
        BasicDBObject val = (BasicDBObject) test.findOne(new BasicDBObject("userName",userName).append("Password",Password));
        return retrieve(val.getString("firstName"),val.getString("userName"),val.getString("middleName"),val.getString("lastName"),val.getString("emailAddress"),val.getInt("phoneNumber"),val.getString("country"),val.getInt("sinNumber"),val.getString("Password"));
    }


    public static DBObject convert(TestObj testObj){
        return new BasicDBObject("firstName",testObj.getFirstName())
                .append("middleName",testObj.getMiddleName())
                .append("lastName",testObj.getLastName())
                .append("emailAddress",testObj.getEmailAddress())
                .append("phoneNumber",testObj.getPhoneNumber())
                .append("country",testObj.getCountry())
                .append("sinNumber",testObj.getSinNumber())
                .append("cashInHand",testObj.getCashInHand())
                .append("userName",testObj.getUserName())
                .append("amountWithdrawFromSavings",testObj.getAmountWithdrawFromSavings())
                .append("Password",testObj.getPassword());
    }


    public void insert(String firstName,
                       String userName,
                       String middleName,
                       String lastName,
                       String emailAddress,
                       int phoneNumber,
                       String country,
                       int sinNumber,
                       String Password) throws ParseException {
        emailAddress= emailAddress.toLowerCase();
        userInfo newUserInfo = new userInfo(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber,Password);
        if(search(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber,Password)) {
            bankusers.put(emailAddress,newUserInfo);
            //Insert to MongoDB
            testObj.setFirstName(firstName);
            testObj.setMiddleName(middleName);
            testObj.setLastName(lastName);
            testObj.setEmailAddress(emailAddress);
            testObj.setPhoneNumber(phoneNumber);
            testObj.setCountry(country);
            testObj.setSinNumber(sinNumber);
            testObj.setCashInHand(0);
            testObj.setUserName(userName);
            testObj.setAmountWithdrawFromSavings(0);
            testObj.setPiggybankBalance(0);
            testObj.setLastSaveDate(new SimpleDateFormat("yyyy/MM/dd").parse(2022 + "/" + 01 + "/" + 01));
            testObj.setFirstSaveDate(new SimpleDateFormat("yyyy/MM/dd").parse(2022 + "/" + 01 + "/" + 01));
            testObj.setPassword(Password);
            test.insert(convert(testObj));
        }
    } // Working

    public void delete(
            String firstName,
            String userName,
            String middleName,
            String lastName,
            String emailAddress,
            int phoneNumber,
            String country,
            int sinNumber,
            String Password
    ) {
        emailAddress = emailAddress.toLowerCase();
        userInfo newUserInfo = new userInfo(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber,Password);
        bankusers.remove(emailAddress,newUserInfo);
        test.findAndRemove(new BasicDBObject("firstName",firstName).append("userName",userName)
        .append("middleName",middleName).append("lastName",lastName).append("emailAddress",emailAddress)
        .append("phoneNumber",phoneNumber).append("country",country).append("sinNumber",sinNumber).append("Passwprd",Password));
    } // working

    public userInfo retrieve(String firstName,
                             String userName,
                             String middleName,
                             String lastName,
                             String emailAddress,
                             int phoneNumber,
                             String country,
                             int sinNumber,
                             String Password){
        emailAddress = emailAddress.toLowerCase();
        userInfo newUserInfo = new userInfo(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber, Password);
        return bankusers.get(emailAddress);
    } // working



    public boolean search(String firstName,
                          String userName,
                          String middleName,
                          String lastName,
                          String emailAddress,
                          int phoneNumber,
                          String country,
                          int sinNumber,
                          String Password){
        return retrieve(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber,Password) != null;
    } //working
    //Liberty features

    public String transferTransaction(userInfo sender, userInfo receiver, int amount){
        if(search(sender.getFirstName(), sender.getUserName(), sender.getMiddleName(),sender.getLastName(),sender.getEmailAddress(),
                sender.getPhoneNumber(),sender.getCountry(),sender.getSinNumber(),sender.getPassword())
                && search(receiver.getFirstName(),receiver.getUserName(),receiver.getMiddleName(),receiver.getLastName(),
                receiver.getEmailAddress(),receiver.getPhoneNumber(),receiver.getCountry(),
                receiver.getSinNumber(),receiver.getPassword())){
            retrieve(sender.getFirstName(),sender.getUserName(),sender.getMiddleName(),sender.getLastName(),sender.getEmailAddress(),
                    sender.getPhoneNumber(),sender.getCountry(),sender.getSinNumber(),sender.getPassword()).withdrawal(amount);
            retrieve(sender.getFirstName(),receiver.getUserName(),sender.getMiddleName(),sender.getLastName(),sender.getEmailAddress(),
                    sender.getPhoneNumber(),sender.getCountry(),sender.getSinNumber(),sender.getPassword()).deposit(amount);
            return "Transaction from " + sender.getUserName() + " to " + receiver.getUserName() + "is successful";
        }
        return "Unsuccessful Transaction";
    } //transfer

    private String manageSavings(userInfo user){
        String status = " ";
        if(user.getLastSaveDate().equals(new Date())){
            user.recieveSavings(user.getAmountWithdrawFromSavings());
            status += user.getFirstName()+ " " + user.getLastName() + " withdrew " + user.getAmountWithdrawFromSavings()
                    + "from their piggy bank on "+ user.getLastSaveDate() + "."
                    + "\n" + user.getFirstName() + "now has "+ user.getPiggybankBalance()
                    +" in their piggy bank";
        }
        if(user.getLastSaveDate().after(new Date())){
            status += user.getFirstName()+ " " + user.getLastName() + "now has "+ user.getPiggybankBalance()
                    +" in their piggy bank";
        }
        return status;
    }

    public String toString(){
        String answer = "";
        Collection<userInfo> collection = bankusers.values();
        for(Iterator<userInfo> iterator = collection.iterator(); iterator.hasNext();){
            answer+=iterator.next() + "\n";
        }
        return answer;
    }
}

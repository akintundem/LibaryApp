import com.mongodb.*;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
class users{
    private userInfo[] allUsers;
    private userInfo[] temp;
    private int size;
    private int count;
    private float loadfactor = 0;
    public static MongoClient mongoClient;
    public static DB database;
    public static DBCollection test;
    TestObj testObj = new TestObj();



    public users(int size) throws UnknownHostException, ParseException {
        size = primeOutput(size);
        this.size = size;
        this.allUsers = new userInfo[size];
        startmongoDB();
    }

    public void startmongoDB() throws UnknownHostException, ParseException {
        this.mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        this.database = mongoClient.getDB("bankUsers");
        this.test = database.getCollection("bankUser");
        System.out.println("Here they are");
        //fill in users from mongo into allUsers
        DBCursor cursor = test.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.one();
            insert((String) obj.get("firstName"),(String) obj.get("userName"),(String)obj.get("middleName"), (String)obj.get("lastName")
                    ,(String)obj.get("emailAddress"),(Integer) obj.get("phoneNumber"),(String) obj.get("country"),(Integer) obj.get("sinNumber"), (String) obj.get("Password"));
            cursor.next();
        }
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

    private int primeOutput(int size){
        while (!prime(size)){
            size++;
        }
        return size;
    }

    private boolean prime(int size){
        int j =2;
        while (j*j <= size){
            if(size%j == 0){
                return false;
            }
            j++;
        }
        return true;
    }

    private int hornerMethod(String emailAddress,int newArraySize){
        final int CONSTANT = 27;
        int sum =0;
        for(int i =0; i<=emailAddress.length()-1; i++){
            if((int) emailAddress.charAt(i)>=96 && (int) emailAddress.charAt(i) <=122) {
                sum += ((int) emailAddress.charAt(i) - 96);
            }
            if(i != emailAddress.length()-1) {
                sum = (sum * CONSTANT)%newArraySize;
            }
        }
        return sum;
    }

    private int compressionMap(userInfo[] userInfos, String emailAddress, int newArraySize){
        final int CONSTANT = 41;
        emailAddress = emailAddress.toLowerCase();
        int IntegerHash = hornerMethod(emailAddress,newArraySize);
        int insert = (IntegerHash%newArraySize)%newArraySize;
        int i =1;
        while (userInfos[insert] != null && userInfos[insert].getEmailAddress().compareTo("N/A") != 0){
            insert = (IntegerHash%newArraySize + (i * (CONSTANT - IntegerHash%CONSTANT)) % newArraySize); //main focus
            i++;
            if(insert > newArraySize-1){
                insert = insert - newArraySize;
            }
        }
        return insert;
    }


    private void inserter(userInfo[] userInfos, String emailAddress, int newArraySize, userInfo userinfo){
        userInfos[compressionMap(userInfos,emailAddress,newArraySize)] = userinfo;
    }

    private boolean initialSearch(int insert, userInfo newUserInfo){
        if(allUsers[insert] != null && allUsers[insert].equals(newUserInfo)) {
            return true;
        }
        return false;
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
        if (!search(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber,Password)) { // if the word is not empty or not a duplicate.
            if(loadfactor < 0.6){
                inserter(allUsers,emailAddress, allUsers.length, newUserInfo);
                count++;
                loadfactor = count/ allUsers.length;
            } else {
                size = primeOutput(allUsers.length * 2);
                temp = new userInfo[size];
                for (int i = 0; i < allUsers.length; i++) {
                    if(allUsers[i] != null){
                        inserter(temp, allUsers[i].getEmailAddress(),size, allUsers[i]);
                    }
                } // used to increase the hash table and rehash all words again.
                allUsers = new userInfo[primeOutput(size)];
                allUsers = temp;
                inserter(allUsers,emailAddress,size, newUserInfo);
                count++;
                loadfactor = count/ allUsers.length;
            }
        }
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
        testObj.setLastSaveDate(new SimpleDateFormat("yyyy/MM/dd").parse(2022+"/"+01+"/"+01));
        testObj.setFirstSaveDate(new SimpleDateFormat("yyyy/MM/dd").parse(2022+"/"+01+"/"+01));
        testObj.setPassword(Password);
        test.insert(convert(testObj));
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
        if (search(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber,Password)) { // if the word is not empty or not a duplicate.
            final int CONSTANT = 41;
            int IntegerHash = hornerMethod(emailAddress, allUsers.length);
            int insert = (IntegerHash% allUsers.length)% allUsers.length;
            int i =1;
            if(allUsers[insert].equals(newUserInfo)){
                allUsers[insert] = new userInfo("N/A","N/A","N/A","N/A","N/A",0,"N/A", 0,"N/A");
                count--;
                loadfactor = count/ allUsers.length;
            } else {
                while (allUsers[insert] != null && allUsers[insert].getEmailAddress().compareTo("N/A") != 0){
                    insert = (IntegerHash% allUsers.length + (i * (CONSTANT - IntegerHash%CONSTANT)) % allUsers.length); //main focus
                    i++;
                    if(insert > allUsers.length-1){
                        insert = insert - allUsers.length;
                    }
                    if(allUsers[insert].equals(newUserInfo)){
                        allUsers[insert] = new userInfo("N/A","N/A","N/A","N/A","N/A",0,"N/A", 0,"N/A");
                        count--;
                        loadfactor = count/ allUsers.length;
                    }
                }
            }
        }
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
        int constant = 41;
        int IntegerHash = hornerMethod(emailAddress, allUsers.length);
        int insert = IntegerHash% allUsers.length;
        int i =1;
        if(initialSearch(insert, newUserInfo)){
            return allUsers[insert];
        } else{
            while (allUsers[insert] != null) {
                insert = (IntegerHash % allUsers.length + (i * (constant - IntegerHash % constant)) % allUsers.length); //main focus
                i++;
                if (insert > allUsers.length - 1) {
                    insert = insert - allUsers.length;
                }

                if (allUsers[insert] != null && allUsers[insert].equals(newUserInfo)) {
                    return allUsers[insert];
                }
            }
        }
        return null;
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


    public boolean Empty(){
        return count==0;
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
        String answer ="";
        for(int i = 0; i < allUsers.length; i++){
            answer += i +"-->"+ allUsers[i] +"\n";
        }
        return answer;
    } // working
}

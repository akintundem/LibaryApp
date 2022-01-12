import java.util.Date;
class users{
    private userInfo[] allUsers;
    private userInfo[] temp;
    private int size;
    private int count;
    private float loadfactor = 0;



    public users(int size){
        size = primeOutput(size);
        this.size = size;
        this.allUsers = new userInfo[size];
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
                       int sinNumber){
        emailAddress= emailAddress.toLowerCase();
        userInfo newUserInfo = new userInfo(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber);
        if (!search(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber)) { // if the word is not empty or not a duplicate.
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
    } // Working

    public void delete(
            String firstName,
            String userName,
            String middleName,
            String lastName,
            String emailAddress,
            int phoneNumber,
            String country,
            int sinNumber
    ) {
        emailAddress = emailAddress.toLowerCase();
        userInfo newUserInfo = new userInfo(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber);
        if (search(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber)) { // if the word is not empty or not a duplicate.
            final int CONSTANT = 41;
            int IntegerHash = hornerMethod(emailAddress, allUsers.length);
            int insert = (IntegerHash% allUsers.length)% allUsers.length;
            int i =1;
            if(allUsers[insert].equals(newUserInfo)){
                allUsers[insert] = new userInfo("N/A","N/A","N/A","N/A","N/A",0,"N/A", 0);
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
                        allUsers[insert] = new userInfo("N/A","N/A","N/A","N/A","N/A",0,"N/A", 0);
                        count--;
                        loadfactor = count/ allUsers.length;
                    }
                }
            }
        }
    } // working

    public userInfo retrieve(String firstName,
                             String userName,
                             String middleName,
                             String lastName,
                             String emailAddress,
                             int phoneNumber,
                             String country,
                             int sinNumber){
        emailAddress = emailAddress.toLowerCase();
        userInfo newUserInfo = new userInfo(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber);
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
                          int sinNumber){
        return retrieve(firstName, userName, middleName, lastName, emailAddress, phoneNumber, country, sinNumber) != null;
    } //working


    public boolean Empty(){
        return count==0;
    } //working

    //Liberty features

    public String transferTransaction(userInfo sender, userInfo receiver, int amount){
        if(search(sender.getFirstName(), sender.getUserName(), sender.getMiddleName(),sender.getLastName(),sender.getEmailAddress(),
                sender.getPhoneNumber(),sender.getCountry(),sender.getSinNumber())
                && search(receiver.getFirstName(),receiver.getUserName(),receiver.getMiddleName(),receiver.getLastName(),
                receiver.getEmailAddress(),receiver.getPhoneNumber(),receiver.getCountry(),
                receiver.getSinNumber())){
            retrieve(sender.getFirstName(),sender.getUserName(),sender.getMiddleName(),sender.getLastName(),sender.getEmailAddress(),
                    sender.getPhoneNumber(),sender.getCountry(),sender.getSinNumber()).withdrawal(amount);
            retrieve(sender.getFirstName(),receiver.getUserName(),sender.getMiddleName(),sender.getLastName(),sender.getEmailAddress(),
                    sender.getPhoneNumber(),sender.getCountry(),sender.getSinNumber()).deposit(amount);
            return "Transaction from " + sender.getUserName() + " to " + receiver.getUserName() + "is successful";
        }
        return "Unsuccessful Transaction";
    } //transfer

    public void savings(){
        for(int i = 0; i < allUsers.length; i++){
            userInfo saver = allUsers[i];
        }
    }//savings

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

    public void purchases(){

        //creation of a store using random pictures and random number as prices to act as an external api so that
        // I can test that buy and selling from the app is possible. 


    }//purchases

    public String toString(){
        String answer ="";
        for(int i = 0; i < allUsers.length; i++){
            answer += i +"-->"+ allUsers[i] +"\n";
        }
        return answer;
    } // working
}

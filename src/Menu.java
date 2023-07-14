import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

class Menu {
    static HashMap<String,Accounts>customer;
    boolean logged_in;
    int total_log_in, total_admin;
    static int total_user;
    String current_user, super_admin;
    int[]  admins, initial_account_balances={500,1000,5000}; //deposit_and_withdrawal_intervals={};
    String[] logged_in_user, log_in_time;
    Scanner cin = new Scanner(System.in);

    Menu()
    {
        customer= new HashMap<>();
        logged_in_user=new String[100];
        log_in_time=new String[100];
        admins=new int[100];

        total_log_in=0;
        total_user=0;
        total_admin = 0;
        logged_in=false;
        current_user="none";
        super_admin="abrar@gmail.com";

        for(int i=0;i<100;++i)
        {
            logged_in_user[i]=null;
            log_in_time[i]=null;
        }
    }

    public void admin_menu()
    {
        System.out.println("1.  Create new account");
        System.out.println("2.  Log in");
        System.out.println("3.  Log out");
        System.out.println("4.  Reset Your Password");
        System.out.println("5.  Send Money");
        System.out.println("6.  View Transactions");
        System.out.println("7.  View Customer List (Admin only)");
        System.out.println("8.  View Logg in History (Admin Only)");
        System.out.println("9.  View Profile");
        System.out.println("10. Deposit money");
        System.out.println("11. Withdraw Money");
        System.out.println("12. View deposits and Withdrawals");
        System.out.println("13. Add or remove an admin (For super admin only)");
        System.out.println("14. Remove user");
        System.out.println("15. Exit\n");
    }

    public void user_menu()
    {
        System.out.println("1.  Create new account");
        System.out.println("2.  Log in");
        System.out.println("3.  Log out");
        System.out.println("4.  Reset Your Password");
        System.out.println("5.  Send Money");
        System.out.println("6.  View Transactions");
        System.out.println("7.  View Profile");
        System.out.println("8.  Deposit money");
        System.out.println("9.  Withdraw Money");
        System.out.println("10. View deposits and Withdrawals");
        System.out.println("11. Exit\n");
    }

    public void create_account()
    {
        if(logged_in)
        {
            System.out.println("Please Sign out of "+current_user+" first\n");
            return;
        }


        System.out.print("Email Account: ");
        String email=cin.next();

        if(!email_validation(email))
        {
            System.out.println("Enter a valid email id\n");
            return;
        }

        if(customer.get(email)==null)
        {
            System.out.print("Password (Suggested: "+generate_pass()+"): ");
            String password=cin.next();

            System.out.print("Username: ");
            cin.nextLine();
            String name=cin.nextLine();

            System.out.println("Select an account plan (Checking/ Savings/ Fixed Deposit): ");
            String plan=cin.nextLine();
            int account_index=get_account_index(plan);


            Accounts account=new Accounts();
            account.name=name;
            account.password=password;
            account.mail=email;
            account.account_id=generate_account_id();
            account.pin_no=generate_pin();
            account.accounts[account_index].balance=initial_account_balances[account_index];
            account.account_creation_time=get_current_time();
            ++account.account_count;

            customer.put(email,account);
            ++total_user;
        }
        else
        {
            Accounts temp_account=customer.get(email);

            System.out.print("Password: ");
            String password=cin.next();

            if(!temp_account.password.equals(password))
            {
                System.out.println("Incorrect Password\n");
                return;
            }


            System.out.println("Select an account plan (Checking/ Savings/ Fixed Deposit): ");
            cin.nextLine();
            String plan=cin.nextLine();
            int account_index=get_account_index(plan);

            if(temp_account.accounts[account_index].balance!=-1)
            {
                System.out.println("Plan already exists\n");
                return;
            }

            temp_account.accounts[account_index].balance=initial_account_balances[account_index];
            ++temp_account.account_count;
        }

        System.out.println("Account Created Successfully\n");
    }

    public void sign_in()
    {
        if(logged_in)
        {
            System.out.println("Please Sign out of "+current_user+" first\n");
            return;
        }

        System.out.print("Email: ");
        String email=cin.next();

        if(customer.get(email)==null)
        {
            System.out.println("Email not found\n"); return;
        }


        System.out.print("Password: ");
        String password=cin.next();

        if(!customer.get(email).password.equals(password))
        {
            System.out.println("Wrong Password\n"); return;
        }

        logged_in=true;
        current_user=email;

        if(current_user.equals(super_admin)) customer.get(current_user).admin=true;

        String time=get_current_time();
        log_in_time[total_log_in]=time;
        logged_in_user[total_log_in++]=current_user;

        System.out.println("Logged in successfully\n");
    }

    public void sign_out()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }

        logged_in=false;
        current_user="none";
        System.out.println("Logged out\n");
    }

    public void reset_pass()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }

        System.out.print("Enter new password: ");
        String new_password=cin.next();

        if(customer.get(current_user).password.equals(new_password))
        {
            System.out.println("New password cannot be the same as current password\n");
            return;
        }

        customer.get(current_user).password=new_password;
        System.out.println("Password reset successfully\n");
    }

    void deposit()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }


        System.out.print("Enter PIN number: ");
        String pin=cin.next();
        if(!customer.get(current_user).pin_no.equals(pin))
        {
            System.out.println("Incorrect PIN number\n");
            return;
        }


        System.out.print("Select account plan (Checking/ Savings/ Fixed Deposit): ");
        cin.nextLine();
        String plan=cin.nextLine();
        int index=get_account_index(plan);

        switch (index)
        {
            case 0 ->
            {
                System.out.print("Deposit amount: ");
                int amount=cin.nextInt();
                customer.get(current_user).accounts[0].balance+=amount;

                int deposit_number=customer.get(current_user).number_of_deposits_and_withdrawal;
                Deposits_and_withdrawal temp_deposit=new Deposits_and_withdrawal();

                temp_deposit.deposit=true;
                temp_deposit.amount=amount;
                temp_deposit.time=get_current_time();

                customer.get(current_user).deposits_and_withdrawals[deposit_number]=temp_deposit;
                ++customer.get(current_user).number_of_deposits_and_withdrawal;
            }
            case 1 ->
            {

            }
            case 2 ->
            {
                if(customer.get(current_user).accounts[2].balance!=-1)
                {
                    System.out.println("No more than one Fixed Deposit is allowed\n");
                }
            }
            default -> {}
        }

        System.out.println("Deposit Successful\n");
    }

    void withdraw()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }


        System.out.print("Enter PIN number: ");
        String pin=cin.next();
        if(!customer.get(current_user).pin_no.equals(pin))
        {
            System.out.println("Incorrect PIN number\n");
            return;
        }


        System.out.print("Select account plan (Checking/ Savings/ Fixed Deposit): ");
        cin.nextLine();
        String plan=cin.nextLine();
        int index=get_account_index(plan);


        switch (index)
        {
            case 0 ->
            {
                System.out.print("Withdrawal amount: ");
                int amount=cin.nextInt();
                customer.get(current_user).accounts[0].balance+=amount;

                int deposit_number=customer.get(current_user).number_of_deposits_and_withdrawal;
                Deposits_and_withdrawal temp_withdrawal=new Deposits_and_withdrawal();

                temp_withdrawal.withdrawal=true;
                temp_withdrawal.amount=amount;
                temp_withdrawal.time=get_current_time();

                customer.get(current_user).deposits_and_withdrawals[deposit_number]=temp_withdrawal;
                ++customer.get(current_user).number_of_deposits_and_withdrawal;
            }
            case 1 ->
            {

            }
            case 2 ->
            {
                if(customer.get(current_user).accounts[2].balance!=-1)
                {
                    System.out.println("No more than one Fixed Deposit is allowed\n");
                }
            }
            default -> {}
        }

        System.out.println("Withdrawal successful\n");
    }

    void view_deposits_and_withdrawals()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }

        System.out.println("****************************************************************");
        if(customer.get(current_user).number_of_deposits_and_withdrawal==0)
        {
            System.out.println("No deposit/ withdrawal has been made\n");
            System.out.println("****************************************************************");
            return;
        }

        for(int i=0;i<customer.get(current_user).number_of_deposits_and_withdrawal;++i)
        {
            customer.get(current_user).deposits_and_withdrawals[i].display();
            System.out.println();
        }

        System.out.println("****************************************************************");
    }

    public void send_money()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }

        System.out.print("Enter PIN number: ");
        String pin=cin.next();

        if(!customer.get(current_user).pin_no.equals(pin))
        {
            System.out.println("Incorrect PIN\n");
            return;
        }


        System.out.print("Recipient Email: ");
        String receiver_email=cin.next();

        if(customer.get(receiver_email)==null)
        {
            System.out.println("Account does not exist\n");
            return;
        }
        else if(receiver_email.equals(current_user))
        {
            System.out.println("Cannot send to your own account\n");
            return;
        }


        System.out.print("Transaction amount: ");
        int amount=cin.nextInt();

        if(amount>customer.get(current_user).accounts[0].balance)
        {
            System.out.println("Insufficient balance in Checking account\n");
            return;
        }


        int transaction_number=customer.get(current_user).transactions;
        customer.get(current_user).business[transaction_number].receiver=receiver_email;
        customer.get(current_user).business[transaction_number].amount=amount;
        customer.get(current_user).business[transaction_number].transaction_time=get_current_time();
        customer.get(current_user).accounts[0].balance-=amount;
        customer.get(current_user).business[transaction_number].balance_after=customer.get(current_user).accounts[0].balance;
        ++customer.get(current_user).transactions;

        transaction_number=customer.get(receiver_email).transactions;
        customer.get(receiver_email).business[transaction_number].sender=current_user;
        customer.get(receiver_email).business[transaction_number].amount=amount;
        customer.get(receiver_email).business[transaction_number].transaction_time=get_current_time();
        customer.get(receiver_email).accounts[0].balance+=amount;
        customer.get(receiver_email).business[transaction_number].balance_after=customer.get(receiver_email).accounts[0].balance;
        ++customer.get(receiver_email).transactions;

        System.out.println("Transaction successful\n");
    }

    public void view_transactions()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }
        System.out.println("****************************************************************");
        System.out.println("Account Holder: "+customer.get(current_user).name+"\n");

        int transaction_number=customer.get(current_user).transactions;
        if(transaction_number==0)
        {
            System.out.println("No transaction has been made\n");
            System.out.println("****************************************************************");
            return;
        }

        for(int i=0;i<transaction_number;++i)
        {
            System.out.println("Transaction: "+ (i+1));
            customer.get(current_user).business[i].display();
            System.out.println("------------------------------");
        }
        System.out.println("****************************************************************");
    }

    void view_user_info()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }

        System.out.println("****************************************************************");
        customer.get(current_user).display();
        System.out.println("****************************************************************");
    }

    public void view_account_user_list()
    {
        if(!logged_in)
        {
            System.out.println("Not logged in\n");
            return;
        }

        System.out.println("****************************************************************");
        customer.forEach((key,value)->value.admin_display());
        System.out.println("****************************************************************");
    }

    public void view_log_in_history()
    {
        System.out.println("*****************************************");
        for(int i=0;i<total_log_in;++i)
        {
            System.out.println("User: "+logged_in_user[i]);
            System.out.println("Logg in time: "+log_in_time[i]);
            System.out.println();
        }
        System.out.println("*****************************************");
    }

    void make_admin()
    {
        if(!current_user.equals(super_admin))
        {
            System.out.println("You don't have this privilege\n");
            return;
        }


        System.out.print("Enter user email id: ");
        String new_admin_email=cin.next();

        System.out.print("Add or remove: ");
        String choice= cin.next();

        if(choice.equals("add")) customer.get(new_admin_email).admin=true;
        else if(choice.equals("remove")) customer.get(new_admin_email).admin=false;

        System.out.println("Operation done successfully\n") ;
    }

    void remove_user()
    {
        System.out.print("Enter user email id: ");
        String new_email=cin.next();

        if(customer.get(new_email)==null)
        {
            System.out.println("Account doesn't exist\n");
            return;
        }

        customer.remove(new_email);
        File file=new File("G:\\Java Khet projects\\Banking System\\Resources\\"+new_email+".txt");
        if(file.delete())
        {
            System.out.println("Sucessfully removed user\n");
            --total_user;
        }
    }

    private String generate_pass()
    {
        StringBuilder s= new StringBuilder();
        char a,b,x,m;
        int i=10,y;
        while(i>0)
        {
            a=(char)(((int)(Math.random()*1000))%25+65);
            b=(char)(((int)(Math.random()*1000))%25+97);
            x=(char)(((int)(Math.random()*1000))%10+48);
            m=(char)(((int)(Math.random()*1000))%48+33);
            y=(int)(Math.random()*1000)%4;
            s.append((y == 0) ? a : (y == 1) ? b : (y==2) ? x:m);
            --i;
        }
        return s.toString();
    }

    private String generate_pin()
    {
        StringBuilder s= new StringBuilder();
        char a;
        int i=5;
        while(i>0)
        {
            a=(char)(((int)(Math.random()*1000))%10+48);
            s.append(a);
            --i;
        }
        return s.toString();
    }

    private String generate_account_id()
    {
        StringBuilder id= new StringBuilder();
        char c;
        int i=4;
        while(i>0)
        {
            c=(char)(((int)(Math.random()*1000))%10+48);
            id.append(c);
            --i;
        }
        id.append('-');
        i=5;
        while(i>0)
        {
            c=(char)(((int)(Math.random()*1000))%10+48);
            id.append(c);
            --i;
        }
        return id.toString();
    }

    private int get_account_index(String s)
    {
        int index=-1;
        switch (s)
        {
            case "Checking" -> index=0;
            case "Savings" -> index=1;
            case "Fixed Deposit" -> index=2;
            default -> {}
        }

        return  index;
    }

    private boolean email_validation(String s)
    {
        String email_regular_expression="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern=Pattern.compile(email_regular_expression);
        if(s==null) return false;
        return pattern.matcher(s).matches();
    }

    private String get_current_time()
    {
        LocalDateTime date_time=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return formatter.format(date_time);
    }
}
public class Accounts
{
    User[] accounts;
    Transaction[] business;
    Deposits_and_withdrawal[] deposits_and_withdrawals;
    int account_count, transactions, number_of_deposits_and_withdrawal;
    String mail, password, pin_no, name, account_id, account_creation_time;
    boolean  admin;

    Accounts()
    {
        accounts=new User[3];
        for(int i=0;i<3;++i) accounts[i]=new User();

        accounts[0].account_type="Checking";
        accounts[1].account_type="Savings";
        accounts[2].account_type="Fixed Deposit";

        business=new Transaction[100];
        for(int i=0;i<100;++i) business[i]=new Transaction();

        deposits_and_withdrawals=new Deposits_and_withdrawal[100];
        for(int i=0;i<100;++i) deposits_and_withdrawals[i]=new Deposits_and_withdrawal();

        account_count=0;
        mail=password=account_id=null;
        pin_no=null;
        admin=false;
        transactions=0;
        number_of_deposits_and_withdrawal=0;
    }

    void display()
    {
        System.out.println("Account information:");
        System.out.println("Name: "+name);
        System.out.println("Email: "+mail);
        System.out.println("PIN number: "+pin_no);
        System.out.println("Account ID: "+account_id);
        System.out.println("Admin: "+admin);

        System.out.println();
        for(int i=0;i<3;++i)
        {
            if(accounts[i].balance!=-1) accounts[i].display();
        }
    }

    void admin_display()
    {
        System.out.println("Account information:");
        System.out.println("Name: "+name);
        System.out.println("Email: "+mail);
        System.out.println("Admin: "+admin);

        System.out.println();
        for(int i=0;i<3;++i)
        {
            if(accounts[i].balance!=-1) accounts[i].display();
        }
        System.out.println("-----------------------------------");
    }
}

class User
{
    String account_type;
    int balance;
    long last_deposit;
    User()
    {
        balance=-1;
        account_type=null;
        last_deposit=0;
    }

    public void display()
    {
        System.out.println("Account type: "+account_type);
        System.out.println("Balance: "+balance);
        System.out.println("\n");
    }
}
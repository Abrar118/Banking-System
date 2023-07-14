class Deposits_and_withdrawal
{
    boolean deposit, withdrawal;
    int amount;
    String time;

    Deposits_and_withdrawal()
    {
        deposit=withdrawal=false;
        time=null;
        amount=-1;
    }

    void display()
    {
        if(deposit) System.out.println("Deposit info: ");
        if(withdrawal) System.out.println("Withdrawal info: ");
        System.out.println("Amount: "+amount);
        System.out.println("Time: "+time);
    }
}

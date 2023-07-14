class Transaction
{
    String sender, receiver, transaction_time;
    int balance_after, amount;

    Transaction()
    {
        sender=receiver=null;
        transaction_time=null;
        balance_after=-1;
        amount=-1;
    }

    void display()
    {
        System.out.println("Transaction info:");
        if(sender!=null) System.out.println("Sender: "+sender);
        if(receiver !=null) System.out.println("Receiver: "+receiver);
        System.out.println("Amount: "+amount);
        System.out.println("Balance after: "+balance_after);
        System.out.println("Time: "+transaction_time);
    }
}
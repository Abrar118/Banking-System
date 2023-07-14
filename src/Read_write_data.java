import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read_write_data extends Menu
{
    static Scanner sc;

    ArrayList<String> get_user_list()
    {
        File folder=new File("G:\\Java Khet projects\\Banking System\\Resources\\");
        File[] list_of_files=folder.listFiles();
        ArrayList<String> lst=new ArrayList<>();

        assert list_of_files != null;
        for(File name: list_of_files)
        {
            if(!name.getName().equals("global.txt")) lst.add(name.getName());
        }
        return lst;
    }

    public void to_txt() throws IOException
    {
        File global=new File("G:\\Java Khet projects\\Banking System\\Resources\\global.txt");
        if(global.canWrite())
        {
            FileWriter cout=new FileWriter(global);

            cout.write(total_user+"\n");
            cout.write(current_user+"\n");

            cout.close();
        }


        ArrayList<String> users=new ArrayList<>();
        customer.forEach((key,value)->users.add(value.mail));

        for(String user: users)
        {
            File account=new File("G:\\Java Khet projects\\Banking System\\Resources\\"+user+".txt");
            boolean is_created=account.createNewFile();

            if(account.canWrite())
            {
                FileWriter cout = new FileWriter(account);

                Accounts temp = customer.get(user);

                cout.write(temp.mail + "\n");
                cout.write(temp.name + "\n");
                cout.write(temp.password + "\n");
                cout.write(temp.pin_no + "\n");
                cout.write(temp.account_id + "\n");
                cout.write(temp.account_creation_time + "\n");
                cout.write(temp.admin + "\n");
                cout.write(temp.account_count + "\n");
                cout.write(temp.transactions + "\n");
                cout.write(temp.number_of_deposits_and_withdrawal + "\n");

                cout.write("\n");

                for (int i = 0; i < 3; ++i)
                {
                    if (temp.accounts[i].balance != -1)
                    {
                        cout.write(temp.accounts[i].account_type + "\n");
                        cout.write(temp.accounts[i].balance + "\n");
                    }
                    else cout.write("none" + "\n");
                }

                cout.write("\n");

                for (int i = 0; i < temp.transactions; ++i)
                {
                    if(temp.business[i].sender==null) cout.write("none"+"\n");
                    else cout.write(temp.business[i].sender+"\n");

                    if(temp.business[i].receiver==null) cout.write("none"+"\n");
                    else cout.write(temp.business[i].receiver+"\n");

                    cout.write(temp.business[i].amount+"\n");
                    cout.write(temp.business[i].balance_after+"\n");
                    cout.write(temp.business[i].transaction_time+"\n");
                }

                cout.write("\n");

                for(int i=0;i<temp.number_of_deposits_and_withdrawal;++i)
                {
                    cout.write(temp.deposits_and_withdrawals[i].deposit+"\n");
                    cout.write((temp.deposits_and_withdrawals[i].withdrawal+"\n"));
                    cout.write((temp.deposits_and_withdrawals[i].amount+"\n"));
                    cout.write((temp.deposits_and_withdrawals[i].time+"\n"));
                }

                cout.close();
            }
        }
    }

    public void from_text() throws IOException
    {
        File global=new File("G:\\Java Khet projects\\Banking System\\Resources\\global.txt");
        if(global.canRead())
        {
            sc=new Scanner(global);
            total_user=sc.nextInt();
            current_user=sc.next();

            if(!current_user.equals("none")) logged_in=true;
        }

        ArrayList<String> users=get_user_list();

        for(String user: users)
        {
            File account=new File("G:\\Java Khet projects\\Banking System\\Resources\\"+user);
            if(account.canRead())
            {
                sc=new Scanner(account);

                Accounts temp=new Accounts();

                temp.mail=sc.next();
                sc.nextLine();
                temp.name=sc.nextLine();
                temp.password=sc.next();
                temp.pin_no=sc.next();
                temp.account_id=sc.next();
                sc.nextLine();
                temp.account_creation_time=sc.nextLine();

                String bool=sc.next();
                temp.admin= bool.equals("true");
                temp.account_count=sc.nextInt();
                temp.transactions=sc.nextInt();
                temp.number_of_deposits_and_withdrawal=sc.nextInt();

                sc.nextLine();

                for(int i=0;i<3;++i)
                {
                    String type=sc.next();

                    if(type.equals("none")) temp.accounts[i].balance=-1;
                    else
                    {
                        temp.accounts[i].account_type=type;
                        temp.accounts[i].balance=sc.nextInt();
                    }
                }

                sc.nextLine();

                for(int i=0;i<temp.transactions;++i)
                {
                    String sender=sc.next();
                    if(!sender.equals("none")) temp.business[i].sender=sender;

                    String receiver=sc.next();
                    if(!receiver.equals("none")) temp.business[i].receiver=receiver;

                    temp.business[i].amount=sc.nextInt();
                    temp.business[i].balance_after=sc.nextInt();
                    temp.business[i].transaction_time=sc.next();
                }

                sc.nextLine();

                for(int i=0;i<temp.number_of_deposits_and_withdrawal;++i)
                {
                    bool=sc.next();
                    temp.deposits_and_withdrawals[i].deposit=bool.equals("true");
                    bool=sc.next();
                    temp.deposits_and_withdrawals[i].withdrawal=bool.equals("true");
                    temp.deposits_and_withdrawals[i].amount=sc.nextInt();
                    sc.nextLine();
                    temp.deposits_and_withdrawals[i].time= sc.nextLine();
                }

                customer.put(temp.mail,temp);
            }
        }
    }
}

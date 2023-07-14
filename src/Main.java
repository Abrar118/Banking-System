import java.io.IOException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Read_write_data io=new Read_write_data();
        io.from_text();

        int choice=0;
        Scanner cin=new Scanner(System.in);

        while(true)
        {
            if(io.logged_in && Menu.customer.get(io.current_user).admin)
            {
                io.admin_menu();
                System.out.println();


                System.out.println("Current User: "+ Menu.customer.get(io.current_user).name+" (Admin)");
                System.out.println();

                System.out.print("Option: ");
                choice=cin.nextInt();

                if(choice==15) break;
                switch (choice)
                {
                    case 1 -> io.create_account();
                    case 2 -> io.sign_in();
                    case 3 -> io.sign_out();
                    case 4 -> io.reset_pass();
                    case 5 -> io.send_money();
                    case 6 -> io.view_transactions();
                    case 7 -> io.view_account_user_list();
                    case 8 -> io.view_log_in_history();
                    case 9 -> io.view_user_info();
                    case 10 -> io.deposit();
                    case 11 -> io.withdraw();
                    case 12 -> io.view_deposits_and_withdrawals();
                    case 13 -> io.make_admin();
                    case 14 -> io.remove_user();
                    default -> {}
                }
                //Thread.sleep(100);
            }
            else
            {
                io.user_menu();
                System.out.println();

                if(io.logged_in)
                {
                    System.out.println("Current User: "+ Menu.customer.get(io.current_user).name);
                    System.out.println();
                }

                System.out.print("Option: ");
                choice=cin.nextInt();

                if(choice==11) break;
                switch (choice)
                {
                    case 1 -> io.create_account();
                    case 2 -> io.sign_in();
                    case 3 -> io.sign_out();
                    case 4 -> io.reset_pass();
                    case 5 -> io.send_money();
                    case 6 -> io.view_transactions();
                    case 7 -> io.view_user_info();
                    case 8 -> io.deposit();
                    case 9 -> io.withdraw();
                    case 10 -> io.view_deposits_and_withdrawals();
                    default -> {}
                }
                //Thread.sleep(100);
            }
        }

        io.to_txt();
        cin.close();
    }
}
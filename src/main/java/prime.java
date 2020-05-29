import java.util.InputMismatchException;
import java.util.Scanner;

public class prime {

	   public static void main(String args[])
	   {
		  Scanner scanner = new Scanner(System.in);
		  System.out.println("Enter the value of t for the no of test cases");
		  int t = scanner.nextInt();
		  while(t>0)
		  {
		   
	      int n = 0;
	      int status = 1;
	      int num = 3;
	      n = scanner.nextInt();    
	      if (n >= 1)
	      {
	         System.out.println("First "+n+" prime numbers are:");
	         System.out.println(2);
	      }

	      for ( int i = 2 ; i <=n ;  )
	      {
	         for ( int j = 2 ; j <= Math.sqrt(num) ; j++ )
	         {
	            if ( num%j == 0 )
	            {
	               status = 0;
	               break;
	            }
	         }
	         if ( status != 0 )
	         {
	            System.out.println(num);
	            i++;
	         }
	         status = 1;
	         num++;
	      }         
	   }
		  t--;
		  
	   }
}

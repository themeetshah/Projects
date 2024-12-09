import java.util.*;
class Menu
{
	void display()
	{
		System.out.println("\n----------------MENU------------------");
		System.out.println("  ITEM\t                   PRICE"); 
		System.out.println(); 
		System.out.println("  Burger\t           50Rs/-"); 
		System.out.println("  Pizza\t                   60Rs/-"); 
		System.out.println("  Sandwich\t           80Rs/-");
		System.out.println("  Misal\t                  100Rs/-");
		System.out.println("  Dosa\t                  110Rs/-");
		System.out.println("  Pasta\t                  120Rs/-");
		System.out.println("  Biryani\t          180Rs/-");
	}
}
class Customer
{
	String name,address,phoneNumber,cardNumber;
}
class Order
{
	Scanner sc=new Scanner(System.in);
	Customer c1=new Customer();
	String[] orh; // Array to store order history
    int index = 0;
	int totalbill=0;
	int totalqty=0;
	String paymentMethod;
	
	void order()
	{
		System.out.print("\nEnter Total Items: ");
		int n=sc.nextInt();
		orh=new String[n];
	}
	void take()
	{
		order();
		if(orh.length==0)
		{
			System.out.println("No items in your order. You completed your order.");
			return;
		}
		System.out.println("\nEnter 1. for Burger");
		System.out.println("Enter 2. for Pizza");
		System.out.println("Enter 3. for Sandwich");
		System.out.println("Enter 4. for Misal");
		System.out.println("Enter 5. for Dosa");
		System.out.println("Enter 6. for Pasta");
		System.out.println("Enter 7. for Biryani");
		System.out.println("Enter 0. to finish the order");
		while(index<orh.length)
		{
			System.out.print("\nEnter item number: ");
			int choice=sc.nextInt();
			if(choice==0)
			{
				System.out.println("You have completed your Order!");
				break;
			}
			else
			{
				switch(choice)
				{
					case 1: System.out.print("Enter Burger QTY.:  ");
				                int bq=sc.nextInt();
						        addOnOrder("Burger ", bq,50);
						        break;
				    case 2: System.out.print("Enter Pizza QTY.:  ");
				               int pq=sc.nextInt();
						       addOnOrder("Pizza  ", pq,60);
						       break;
				   case 3: System.out.print("Enter Sandwich QTY.:  ");
				              int sq=sc.nextInt();
						      addOnOrder("Sandwich", sq,80);
						      break;
				   case 4: System.out.print("Enter Misal QTY.:  ");
				              int mq=sc.nextInt();
					  	      addOnOrder("Misal  ", mq,100);
						      break;
				  case 5: System.out.print("Enter Dosa QTY.:  ");
				              int dq=sc.nextInt();
						      addOnOrder("Dosa   ", dq,110);
						      break;
				  case 6: System.out.print("Enter Pasta QTY.:  ");
				              int paq=sc.nextInt();
						      addOnOrder("Pasta  ", paq,120);
						      break;
				  case 7: System.out.print("Enter Biryani QTY.:  ");
				              int biq=sc.nextInt();
						      addOnOrder("Biryani", biq,180);
						      break;
				  default: System.out.println("\nINVALID CHOICE! Please select valid Item Number.");
				              break;
				}
			}
		}
	}
	void addOnOrder(String item, int qty, int price)
	{
		if(index<orh.length)
		{
			orh[index++]=" "+item+"\t"+qty+"            "+price*qty;
			System.out.println("Added to order: "+item+"x"+qty);
			totalbill+=price*qty;
			totalqty+=qty;
		}
	}
	void dispOrder()
	{
		for(int i=0;i<orh.length;i++)
		{
			if(orh[i]!=null)
			{
				System.out.println(orh[i]);
			}
		}
		System.out.println("----------    -----       -------");
		System.out.println(" Total:\t        "+totalqty+"           "+totalbill);
		System.out.println("----------    -----       -------");
	}
	void orderReceipt() {
        System.out.println("\n----------Customer Receipt----------");
        System.out.println("Name: "+c1.name);
		System.out.println("Address: "+c1.address);
		System.out.println("Phone Number: "+c1.phoneNumber);
		System.out.println("Payment Method: "+paymentMethod);
		System.out.println("\n ITEM\t       QTY         PRICE\n");
        dispOrder();
    }
	void selectPaymentMethod() 
	{
		System.out.println("\nSelect Payment Method:");
		System.out.println("1. Cash");
		System.out.println("2. Card");
		System.out.print("Enter your choice: ");
		int choice = sc.nextInt();
		switch (choice)
		{
			case 1: paymentMethod = "Cash";
                       System.out.println("Payment successful.");
					   break;
			case 2: paymentMethod = "Card";
                       System.out.print("Enter card number: ");
					   String cardNumber = sc.next();
					   if (isValidCardNumber(cardNumber))
					   {
						   System.out.println("Payment successful.");
						}
						else
						{
							System.out.println("Invalid card number. Payment failed.");
							selectPaymentMethod();
						}
						break;
			default:System.out.println("Invalid choice. Please select again.");
                        selectPaymentMethod();
		}
	}
	boolean isValidCardNumber(String cardNumber) 
	{
		boolean b=true;
		if(cardNumber.length()==16)
			{
				for(int i=0; i<cardNumber.length();i++)
				{
					if((cardNumber.charAt(i)>='0')&&(cardNumber.charAt(i)<='9'))
					{
						c1.cardNumber=cardNumber;
						b=true;
					}
					else
					{
						b=false;
						System.out.println("Card Number must be in b/w 0 & 9 ");
						break;
					}
				}
			}
			else
			{
				b=false;
				System.out.println("Card Number must be of 16 digit ");
			}
		return b;
	}
	void getCustomerInfo() 
	{
		System.out.println("\nPlease provide your information:");
		System.out.print("Name: ");
		c1.name = sc.next();
		System.out.print("Address: ");
		c1.address = sc.next();
		boolean b=true;
		while(b)
		{
			System.out.print("Phone Number: ");
			String pN = sc.next();
			if(pN.length()==10)
			{
				for(int i=0; i<pN.length();i++)
				{
					if((pN.charAt(i)>='0')&&(pN.charAt(i)<='9'))
					{
						c1.phoneNumber=pN;
						b=false;
					}
					else
					{
						b=true;
						System.out.println("Phone Number must be in b/w 0 & 9 ");
						break;
					}
				}
			}
			else
			{
				System.out.println("Phone Number must be of 10 digit ");
			}
		}
    }
}
class BillingSystem 
{
    public static void main(String[] args) 
	{
        Menu m1 = new Menu();
		m1.display();
		Order o1 = new Order();
		o1.take();
		System.out.println("\n\n-----------Order Summary-----------\n");
        System.out.println("  ITEM\t       QTY         PRICE\n");
		o1.dispOrder();
		if(o1.orh.length!=0)
		{
			o1.getCustomerInfo();
			o1.selectPaymentMethod();
			o1.orderReceipt();
		}
		System.out.println("\nThank you for visiting us. Have a Good Day!");
	}
}
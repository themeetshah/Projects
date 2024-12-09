import java.util.*;
class Passenger 
{
    String pfname, pname, bookingId;
    int pseats;
    Passenger()
	{
        bookingId = "";
    }
    void cancelSeats(int Seats) 
	{
        pseats -= Seats;
    }
}
class Flight 
{
    String fname, destination;
    int seats;
    void bookSeats(int Seats)
	{
        if (Seats > 0 && Seats <= seats) 
		{
            seats -= Seats;
        }
		else 
		{
            System.out.println("Sorry, not enough available seats for the requested number.");
        }
    }
    void cancelSeats(int Seats)
	{
        seats += Seats;
    }
}
class Payment
{
    Scanner sc = new Scanner(System.in);
    boolean processPayment(String paymentMethod)
	{
		boolean temp=false;
        switch (paymentMethod)
		{
            case "card": System.out.print("Enter Card Number: ");
							   String cardNumber = sc.next();
							   System.out.print("Enter PIN (6 digits): ");
							   String pinc = sc.next();
							   if (isValidCardNumber(cardNumber))
							   {
									if (pinc.length()==6)
									{
										System.out.println("Payment successful via card.");
										temp=true;
									}
									else
									{
										System.out.println("Invalid PIN format. Payment failed.");
									}
								}
								else
								{
									System.out.println("Invalid card number. Payment failed.");
								}
								break;
            case "account": System.out.print("Enter Account Number: ");
									String accountNumber = sc.next();
									System.out.print("Enter PIN (4 digits): ");
									String pina = sc.next();
									if (isValidAN(accountNumber))
									{
										if (pina.length()==4)
										{
											System.out.println("Payment successful via account.");
											temp=true;
										}
										else
										{
											System.out.println("Invalid PIN format. Payment failed.");
										}
									}
									else
									{
										System.out.println("Invalid card number. Payment failed.");
									}
									break;
            default: System.out.println("Invalid payment method.");
        }
		return temp;
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
	
	boolean isValidAN(String accNumber) 
	{
		boolean b=true;
		if(accNumber.length()==10)
			{
				for(int i=0; i<accNumber.length();i++)
				{
					if((accNumber.charAt(i)>='0')&&(accNumber.charAt(i)<='9'))
					{
						b=true;
					}
					else
					{
						b=false;
						System.out.println("Account Number must be in b/w 0 & 9 ");
						break;
					}
				}
			}
			else
			{
				b=false;
				System.out.println("Account Number must be of 10 digit ");
			}
		return b;
	}
}

class Reservation 
{
	Scanner sc = new Scanner(System.in);
    Flight[] flights;
    Passenger[] pass = new Passenger[1000];
    String[] tran = new String[1000];
    int k = 0;
	int j = 0;
	int bid=100;
    boolean flightsInitialized = false;
	
    void PassInitialize()
	{
        for (int i = 0; i < pass.length; i++)
		{
            pass[i] = new Passenger();
        }
    }
	
    void initializeFlights() 
	{
        System.out.print("Enter the number of flights: ");
        int numFlights = sc.nextInt();
        flights = new Flight[numFlights];
        for (int i = 0; i < numFlights; i++) 
		{
            flights[i] = new Flight();
            System.out.println("\n---Enter details for Flight " + (i + 1) + "---");
            System.out.print("Enter Flight ID: ");
            flights[i].fname = sc.next();
            System.out.print("Enter Flight Destination: ");
            flights[i].destination = sc.next();
            System.out.print("Enter Available Seats: ");
            flights[i].seats = sc.nextInt();
        }
		flightsInitialized = true;
        System.out.println("Flights initialized successfully.");
    }

    void display()
	{
        if (!flightsInitialized)
		{
            System.out.println("Please initialize flights first.");
        }
		else
		{
            System.out.println("\n-----------Available Flights-----------\n");
            for (int i = 0; i < flights.length; i++)
			{
                System.out.println(flights[i].fname + " - " + flights[i].destination + " - " + flights[i].seats + " seats available");
            }
        }
    }
    
	void reserve(String rf, String rn, int rs, String paymentMethod)
	{
		for (int i = 0; i < flights.length; i++)
		{
			if (flights[i].fname.equals(rf))
			{
				if (flights[i].seats >= rs)
				{
					Payment paymentProcessor = new Payment();
					boolean paymentStatus = paymentProcessor.processPayment(paymentMethod);
					if (paymentStatus)
					{
						flights[i].bookSeats(rs);
						pass[j].pfname = rf;
						pass[j].pname = rn;
						pass[j].pseats = rs;
						pass[j].bookingId = generateBookingId(rf, rn);
						tran[k] = "Type: Reservation, Flight Name: " + rf + ", Passenger Name: " + rn + ", Seats Booked: " + rs +" - Booking ID: " + pass[j].bookingId + " - Payment Method: " + paymentMethod;
						System.out.println("Booking successful! Enjoy your flight. Booking ID: " + pass[j].bookingId);
						j++;
						k++;
					}
					else
					{
						System.out.println("Payment failed. Reservation not completed.");
					}
					return;
				}
				else
				{
					System.out.println("Sorry, not enough available seats for the requested number.");
					return;
				}
			}
		}
		System.out.println("Invalid flight number. Please try again.");
	}

    void cancel(String bookingId, int cs) 
	{
        for (int j = 0; j < pass.length; j++) 
		{
            if (pass[j] != null && pass[j].bookingId != null && pass[j].bookingId.equals(bookingId)) 
			{
                for (int i = 0; i < flights.length; i++) 
				{
                    if (flights[i].fname.equals(pass[j].pfname)) 
					{
                        if (pass[j].pseats >= cs) 
						{
                            flights[i].cancelSeats(cs);
                            pass[j].cancelSeats(cs);
                            tran[k] = "Type: Cancellation, Flight Name: " + pass[j].pfname + ", Passenger Name: " + pass[j].pname +", Seats Canceled: " + cs + " - Booking ID: " + pass[j].bookingId;
                            k++;
                            if (pass[j].pseats == 0)
							{
                                removePassenger(j);
                               System.out.println("Passenger removed!");
                            }
                            System.out.println("Cancellation successful. We hope to serve you again!");
                            return;
                        } 
						else
						{
                            System.out.println("Invalid cancellation request. Not enough seats booked.");
                            return;
                        }
                    }
                }
                System.out.println("Cancellation.");
            }
        }
        System.out.println("Invalid cancellation request. Please check your details and try again.");
    }

    void removePassenger(int index)
	{
        for (int i = index; i < pass.length - 1; i++) 
		{
            pass[i] = pass[i + 1];
        }
        pass[pass.length - 1] = null;
    }

    void transactions()
	{
        System.out.println("Transactions:");
        for (int k1 = 0; k1 < tran.length; k1++) 
		{
            if (tran[k1] != null) 
			{
                System.out.println("Transaction " + (k1 + 1) + ": " + tran[k1]);
            }
        }
    }

    String generateBookingId(String flightId, String customerName) 
	{
        char firstChar = customerName.charAt(0);
		bid++;
        return flightId + firstChar + bid;
    }
}

class ARS
{
    public static void main(String[] args)
	{
        Scanner sc = new Scanner(System.in);
        Reservation r1 = new Reservation();
		System.out.println("\n------Welcome to the Airline Reservation System------");
		while(true)
		{
			System.out.println("\nChoose your role:");
			System.out.println("1. Admin");
			System.out.println("2. Customer");
			System.out.println("3. Exit");
			System.out.print("Enter your choice: ");
			int c = sc.nextInt();
			switch(c)
			{
			case 1: System.out.print("Enter Password: ");
						String pass = sc.next();
						if(pass.equals("Admin"))
						{
							System.out.println("\nChoose action: ");
							System.out.println("1. Initialize Flights: ");
							System.out.println("2. Show Transactions: ");
							System.out.print("Enter choice: ");
							int action=sc.nextInt();
							switch(action)
							{
								case 1: r1.initializeFlights();
											r1.PassInitialize();
											break;	
								case 2: r1.transactions();
											break;
							}
						}
						else
						{
							System.out.println("Wrong Password!! Can't grant access.");
						}
						break;
						
			case 2: if (r1.flightsInitialized)
						{
							boolean b=true;
							while (b)
							{
								System.out.println("\n----------Airline Reservation System----------\n");
								System.out.println("1. Display Available Flights");
								System.out.println("2. Make Reservation");
								System.out.println("3. Cancel Reservation");
								System.out.println("4. Exit");
								System.out.print("Enter your choice: ");
								int choice = sc.nextInt();
								switch (choice)
								{
									case 1: r1.display();
												break;
									case 2: System.out.println("\n---Enter details for Reservation---");
												System.out.print("Enter your Flight ID: ");
												String rf = sc.next();
												System.out.print("Enter your name: ");
												String rn = sc.next();
												System.out.print("Enter number of Seats: ");
												int rs = sc.nextInt();
												System.out.print("Select payment method (card/account): ");
												String paymentMethod = sc.next().toLowerCase();
												r1.reserve(rf, rn, rs, paymentMethod);
												break;
									case 3: System.out.println("\n---Enter details for Cancellation---");
												System.out.print("Enter your Booking ID: ");
												String bookingId = sc.next();
												System.out.print("Enter number of Seats: ");
												int cs = sc.nextInt();
												r1.cancel(bookingId, cs);
												break;
									case 4: System.out.println("Thank you for using our service.");
												b=false;
												break;
									default: System.out.println("\nINVALID CHOICE!\n");
												break;
								}
							}
						}
						else
						{
							System.out.println("Please initialize flights first.");
						}
						break;
				case 3: System.out.println("Thank you for using our service.");
							System.exit(0);
							break;
				default: System.out.println("Invalid Choice!!!");
			}
		}
    }
}
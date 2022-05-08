import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import items.Item;
import items.DressUp;
import items.HiringException;
import items.PlayEquipment;
import items.Toy;

/*
 * Stage D implements all the requirements of Assignment 3 for the
 * development of the ChildzPlay Item Management System
 */

public class StageD {

	private final static Scanner sc = new Scanner(System.in);

	private ArrayList<Item> holdings = new ArrayList<>();

	private int currentHoldings;

	private String filename = "database.txt";
	private File database = new File(filename);

	public static void main(String[] args) {
		StageD stageD = new StageD();
		stageD.initialize();
		stageD.Menu();
	}
	
	// Initialize the program. Load data from database if it exists
	private void initialize() {
		if (!database.exists()) {
			System.out.println("Starting with an empty system");
			System.out.println("");
			this.currentHoldings = 0;
		}

		loadDataFromFile();
	}

	// Reads data from file
	private void loadDataFromFile() {
		Scanner fr = null;
		try {
			fr = new Scanner(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.out.println("* database.txt file not found *");
		}

		if (fr != null) {
			restoreObjects();
			System.out.println("Loading details from file");
			fr.close();
		}
	}
	
	// Menu system logic, calls appropriate methods
	private void Menu() {
		String choice = null;
		do {
			displayMenu();
			choice = sc.nextLine().toUpperCase();

			switch (choice) {
			case "A":
				addItems();
				break;
			case "B":
				hireItems();
				break;
			case "C":
				returnItems();
				break;
			case "D":
				showItemInformation();
				break;
			case "E":
				showSummary();
				break;
			case "X":
				saveDatabase();
				System.out.println("Writing data to file...Done");
				System.out.println("");
				System.out.println("* End of Program *");
				break;
			default:
				System.out.println("");
				System.out.println("* Invalid choice. Please try again: ");
			}
		} while (!choice.toUpperCase().contentEquals("X"));
		sc.close();
	}

	// Exports data to file
	private void saveDatabase() {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(filename);
		} catch (FileNotFoundException e) {
			System.out.println("I/O error");
		}

		if (pw != null) {
			for (int i = 0; i < this.currentHoldings; i++) {
				this.holdings.get(i).exportData(pw);
			}
			pw.close();
		}
	}

	// Loads data from file
	private void restoreObjects() {
		Scanner fileReader = null;
		String line = null;
		Item tempItem = null;

		try {
			fileReader = new Scanner(new FileReader(filename));
			while (fileReader.hasNext()) {
				line = fileReader.nextLine(); 
				if (line.equals("Toy")) { 
					tempItem = new Toy(fileReader);
				} else if (line.equals("DressUp")) {
					tempItem = new DressUp(fileReader);
				} else if (line.equals("PlayEquipment")) {
					tempItem = new PlayEquipment(fileReader);
				}
				this.holdings.add(tempItem);
				this.currentHoldings++;
			}

			fileReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	// Draw menu
	private void displayMenu() {
		System.out.println("");
		System.out.println("");
		System.out.println("|---------------------------------|");
		System.out.println("| ChildzPlay Item Management Menu |");
		System.out.println("|                                 |");
		System.out.println("|     A: Add an item              |");
		System.out.println("|     B: Hire an item             |");
		System.out.println("|     C: Return an item           |");
		System.out.println("|     D: Show item information    |");
		System.out.println("|     E: Show all Items           |");
		System.out.println("|                                 |");
		System.out.println("|     X: Exit                     |");
		System.out.println("|---------------------------------|");
		System.out.print(" Please enter your choice: ");
	}

	// Adds items to program
	private void addItems() {
		// Get Title and Description from user and validate it
		String itemTitle = "";
		itemTitle = validateTitle(itemTitle);
		
		String itemDescription = "";
		itemDescription = validateDescription(itemDescription);

		// Draw menu for item selection
		System.out.println("");
		System.out.println("Which of the following items do you wish to add?");
		System.out.println("  D: Dress Up");
		System.out.println("  T: Toy");
		System.out.println("  P: Play Equipment");
		System.out.print("Choose: ");

		// Validate user input and create chosen item
		createItem(itemTitle, itemDescription);

		System.out.println("");
		System.out.println("Added item " + 
							this.holdings.get(currentHoldings).getItemID());
		
		// Increment current holdings to the next index in holdings
		this.currentHoldings++;
	}

	// Get user to enter the title and validate it
	private String validateTitle(String itemTitle) {
		System.out.print("Please enter the item title: ");
		itemTitle = sc.nextLine();
		
		while (itemTitle.isBlank()) {
			System.out.println("* Error - You must enter a title for the item *");
			itemTitle = sc.nextLine();
		}
		
		return itemTitle;
	}
	
	// Get user to enter the description and validate
	private String validateDescription(String itemDescription) {
		System.out.print("Please enter the item description: ");
		itemDescription = sc.nextLine();
		
		while (itemDescription.isBlank()) {
			System.out.println("* Error - You must enter a description "
								+ "for the item *");
			itemDescription = sc.nextLine();
		}
		
		return itemDescription;
	}

	// Create the item as chosen by user
	private void createItem(String itemTitle, String itemDescription) {
		// Get user to chose item type to create
		char choice = sc.nextLine().toUpperCase().charAt(0);
		
		while ((choice != 'D') && (choice != 'T') && (choice != 'P')) {
			System.out.println("* Error - Please choose D/T/P *");
			choice = sc.nextLine().toUpperCase().charAt(0);
		}

		// Create a Dress-Up item 	
		if (choice == 'D') {
			String dressSize = null;
			dressSize = validateDressSize(dressSize);
			
			String dressGenre = null;
			dressGenre = validateDressGenre(dressGenre);
			
			int dressPieces = 0;
			dressPieces = validateDressPieces(dressPieces);
			
			this.holdings.add(new DressUp(itemTitle, itemDescription, 
								dressSize, dressGenre, dressPieces));
		}
		
		// Create a Toy item
		if (choice == 'T') {
			boolean valid = false;
			String toyCategory = null;

			// Attempt to add a new toy but catch errors if the category is invalid
			while (!valid) {
				System.out.print("Please choose the category [construction, "
									+ "ride-on, sport]: ");
				toyCategory = sc.nextLine();
				try {
					this.holdings.add(new Toy(itemTitle, itemDescription, 
												toyCategory));
					valid = true;
					
				} catch (IllegalArgumentException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		// Create a Play equipment item
		if (choice == 'P') {
			double equipmentWeight = 0;
			equipmentWeight = validateEquipmentWeight(equipmentWeight);
			
			int equipmentHeight = 0;
			equipmentHeight = validateEquipmentHeight(equipmentHeight);
			
			int equipmentWidth = 0;
			equipmentWidth = validateEquipmentWidth(equipmentWidth);
			
			int equipmentDepth = 0;
			equipmentDepth = validateEquipmentDepth(equipmentDepth);
			
			double equipmentWeeklyCost = 0;
			equipmentWeeklyCost = validateEquipmentWeeklyCost(equipmentWeeklyCost);

			this.holdings.add(new PlayEquipment(itemTitle, itemDescription, 
								equipmentWeight, equipmentHeight, equipmentWidth, 
								equipmentDepth, equipmentWeeklyCost));
		}
	}
	
	// Get user to enter the size and validate
	private String validateDressSize(String dressSize) {
		System.out.print("Enter the size: ");
		dressSize = sc.nextLine();
		
		while (dressSize.isBlank()) {
			System.out.println("* Error - You must enter a size *");
			dressSize = sc.nextLine();
		}
		
		return dressSize;
	}

	// Get user to enter the genre and validate
	private String validateDressGenre(String dressGenre) {
		System.out.print("Enter the genre: ");
		dressGenre = sc.nextLine();
		
		while (dressGenre.isBlank()) {
			System.out.println("* Error - You must enter a genre *");
			dressGenre = sc.nextLine();
		}
		
		return dressGenre;
	}

	// Get user to enter the number of pieces and validate
	private int validateDressPieces(int dressPieces) {
		System.out.print("Enter the number of pieces: ");
		dressPieces = Integer.parseInt(sc.nextLine());
		
		while (dressPieces <= 0) {
			System.out.println("* Error - Must have at least 1 piece *");
			dressPieces = Integer.parseInt(sc.nextLine());
		}
		
		return dressPieces;
	}

	// Get user to enter the weight and validate
	private double validateEquipmentWeight(double equipmentWeight) {
		System.out.print("Please enter the weight (kg): ");
		equipmentWeight = Double.parseDouble(sc.nextLine());
		
		while (equipmentWeight <= 0) {
			System.out.println("* Error - Weight must be greater than 0 *");
			equipmentWeight = Double.parseDouble(sc.nextLine());
		}
		
		return equipmentWeight;
	}

	// Get user to enter the height and validate
	private int validateEquipmentHeight(int equipmentHeight) {
		System.out.print("Please enter the height (cm): ");
		equipmentHeight = Integer.parseInt(sc.nextLine());
		
		while (equipmentHeight <= 0) {
			System.out.println("* Error - Height must be greater than 0 *");
			equipmentHeight = Integer.parseInt(sc.nextLine());
		}
		
		return equipmentHeight;
	}
	
	// Get user to enter the width and validate
	private int validateEquipmentWidth(int equipmentWidth) {
		System.out.print("Please enter the width (cm): ");
		equipmentWidth = Integer.parseInt(sc.nextLine());
		
		while (equipmentWidth <= 0) {
			System.out.println("* Error - Width must be greater than 0 *");
			equipmentWidth = Integer.parseInt(sc.nextLine());
		}
		
		return equipmentWidth;
	}
	
	// Get user to enter the depth and validate
	private int validateEquipmentDepth(int equipmentDepth) {
		System.out.print("Please enter the depth (cm): ");
		equipmentDepth = Integer.parseInt(sc.nextLine());
		
		while (equipmentDepth <= 0) {
			System.out.println("* Error - Depth must be greater than 0 *");
			equipmentDepth = Integer.parseInt(sc.nextLine());
		}
		
		return equipmentDepth;
	}
	
	// Get user to enter the weekly cost and validate
	private double validateEquipmentWeeklyCost(double equipmentWeeklyCost) {
		System.out.print("Please enter the weekly cost: ");
		equipmentWeeklyCost = Double.parseDouble(sc.nextLine());
		
		while (equipmentWeeklyCost <= 0) {
			System.out.println("* Error - Cost must be greater than 0 *");
			equipmentWeeklyCost = Double.parseDouble(sc.nextLine());
		}
		
		return equipmentWeeklyCost;
	}

	// Attempt to hire out an item
	private void hireItems() {
		int itemID = 0;
		int foundIndex = -1;
		String customerID = null;
		int numWeeks = 0;

		// Prompt user for itemID
		System.out.print("Please enter the ID number of the item "
							+ "you wish to hire: ");
		itemID = Integer.parseInt(sc.nextLine());

		// Find that itemID in the array
		foundIndex = findItem(itemID);

		// If item not found display error
		if (foundIndex == -1) {
			System.out.print("* Error - Item " + itemID + " not found *");
			return;
		}

		// Get user to enter the customerID and number of weeks for hire
		customerID = validateCustomerID(customerID);
		numWeeks = enterHireWeeks(numWeeks);

		// Hire the item or display error if already hired
		try {
			this.holdings.get(foundIndex).hireItem(customerID, numWeeks);
			System.out.println("Hire successfull");
		}

		catch (HiringException e) {
			System.out.println("* Error - " + e.getMessage() + " *");
		}
	}

	// Prompt user for customer ID and validate
	private String validateCustomerID(String customerID) {
		System.out.print("Please enter the customer ID: ");
		customerID = sc.nextLine();
		
		while (customerID.isBlank()) {
			System.out.print("* Error - You need to enter a customer ID *");
			customerID = sc.nextLine();
		}
		
		return customerID;
	}
	
	// Prompt user for hire weeks and validate
	private int enterHireWeeks(int numWeeks) {
		System.out.print("How many weeks would you like to hire this item for? ");
		numWeeks = Integer.parseInt(sc.nextLine());
		
		while (numWeeks <= 0) {
			System.out.print("* Error - You must enter a value greater than 0 *");
			numWeeks = Integer.parseInt(sc.nextLine());
		}
		
		return numWeeks;
	}

	// Attempt to return hired items
	private void returnItems() {
		int itemID = 0;
		int foundIndex = 0;

		// Get the item ID
		System.out.print("Enter the ID number of the item to return: ");
		itemID = Integer.parseInt(sc.nextLine());

		// Check if item exists
		foundIndex = findItem(itemID);
		if (foundIndex == -1) {
			System.out.print("* Error - Item " + itemID + " not found *");
			return;
		}

		// Try to return the item, error if not successful
		try {
			this.holdings.get(foundIndex).returnItem();
			System.out.println("Item " + itemID 
								+ " has been successfully returned");
		}
		
		catch (HiringException e) {
			System.out.println("* Error - " + e.getMessage() + " *");
		}
	}

	// Finds items by ID
	private int findItem(int itemID) {
		// search for itemID, return index if found or a -1 if not found
		for (int i = 0; i < this.currentHoldings; i++) {
			if (this.holdings.get(i).findItem(itemID)) {
				return i;
			}
		}
		return -1;
	}

	// Show item details
	private void showItemInformation() {
		int itemID = 0;
		int foundIndex = -1;

		// Enter the ID number of the item
		System.out.print("Enter the ID number of the item: ");
		itemID = Integer.parseInt(sc.nextLine());

		// find ID number in Items and show item info. Error if not found
		foundIndex = findItem(itemID);

		if (foundIndex == -1) {
			System.out.println("* Error - Item " + itemID + " not found *");
			return;
		}
		
		this.holdings.get(foundIndex).showItem();
	}

	// List a summary of all items
	private void showSummary() {
		System.out.println("");
		System.out.println("==== ChildzPlay Loan Summary ====");
		System.out.println("");
		System.out.printf("%7s  %-20s %-10s\n", "Item ID", "Title", "Available");
		System.out.println("---------------------------------------");
		
		for (int i = 0; i < this.currentHoldings; i++) {
			this.holdings.get(i).listItem();
		}
		
		System.out.println("");
		System.out.printf("%-20s %s %d\n", "Quantity hired", ":", 
							Item.getHireCount());
		System.out.printf("%-20s %s%.2f\n", "Total income", ": $", 
							Item.getTotalIncome());
	}
}
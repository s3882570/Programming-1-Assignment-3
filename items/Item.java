package items;

import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Item stores the common variables to all 3 subclasses
 * It also controls the hiring and returning of all items
 */
public abstract class Item {

	protected static int nextID = 100;
	protected static int itemCount = 0;
	private static int hireCount = 0;
	private static double totalIncome = 0;

	private int itemID;
	private String itemTitle;
	private String itemDescription;
	private double itemCost;

	private String customerID;
	private int numWeeks;
	private boolean isItemHired;

	public abstract double returnPrice();

	public Item(String itemTitle, String itemDescription) {
		// Assign the ID number to the item starting with 100
		this.itemID = Item.nextID;
		this.itemTitle = itemTitle;
		this.itemDescription = itemDescription;
		
		this.itemCost = 0;
		this.customerID = "";
		this.numWeeks = 0;
		this.isItemHired = false;
	}

	// Reads data from scanner into Item
	public Item(Scanner sc) {
		Item.nextID = Integer.parseInt(sc.nextLine());
		Item.hireCount = Integer.parseInt(sc.nextLine());
		Item.totalIncome = Double.parseDouble(sc.nextLine());
		this.customerID = sc.nextLine();
		this.itemID = Integer.parseInt(sc.nextLine());
		this.itemTitle = sc.nextLine();
		this.itemDescription = sc.nextLine();
		this.isItemHired = Boolean.parseBoolean(sc.nextLine());
	}

	// Attempts to hire out the item - throws exception if unsuccessful
	public void hireItem(String customerID, int numWeeks) throws HiringException {
		// Check if item is hired out already
		if (this.isItemHired) {
			throw new HiringException("* Error - Item has already been hired *");
		}

		this.customerID = customerID;
		this.numWeeks = numWeeks;

		// Set the item as hired
		this.isItemHired = true;

		this.itemCost = returnPrice();

		// Update total income
		Item.totalIncome += this.itemCost;

		// Update hire count
		Item.hireCount++;

		// Display receipt
		hireReceipt();
	}

	// Display hire receipt
	public void hireReceipt() {
		System.out.println("");
		System.out.println("*** ChildzPlay Hire Receipt ***");
		System.out.println("");
		System.out.printf("%-15s : %s\n", "Customer ID", this.customerID);
		System.out.printf("%-15s : %d\n", "Item ID", this.itemID);
		System.out.printf("%-15s : %s\n", "Title", this.itemTitle);
		System.out.printf("%-15s : %d %s\n", "Hire Period", this.numWeeks, "weeks");
		System.out.printf("%-15s : $%.2f\n", "Total Cost", this.itemCost);
		System.out.println("");
	}

	// Attempts to return the item - throws exception if unsuccessful
	public void returnItem() throws HiringException {
		if (!this.isItemHired) {
			throw new HiringException("Item not hired out");
		}
		// Decrement the hire count and reset item availability
		Item.hireCount--;
		this.isItemHired = false;
	}

	// Displays the item information
	public void showItem() {
		System.out.println("");
		System.out.printf("%-15s: %s\n", "ID", this.itemID);
		System.out.printf("%-15s: %s\n", "Title", this.itemTitle);
		System.out.printf("%-15s: %s\n", "Description", this.itemDescription);
		System.out.printf("%-15s: %s\n", "For hire", isItemForHire());
		if (this.isItemHired) {
			System.out.printf("%-15s: %s\n", "Hired to", this.customerID);
		}
	}

	// Lists the item details on one line
	public void listItem() {
		System.out.printf("%-7d  %-20s %-10s\n", this.itemID, 
							this.itemTitle, isItemForHire());
	}

	// Find the item
	public boolean findItem(int itemID) {
		// Returns true if item can be found
		if (itemID == this.itemID) {
			return true;
		}
		return false;
	}

	// Displays a yes/no if the item is available to hire or not
	public String isItemForHire() {
		String answer = "Yes";
		if (isItemHired) {
			answer = "No";
		}
		return answer;
	}

	// Exports Class data to file
	public void exportData(PrintWriter pw) {
		pw.println(this.getClass().getSimpleName());
		pw.println(Item.nextID);
		pw.println(Item.hireCount);
		pw.println(Item.totalIncome);
		pw.println(this.customerID);
		pw.println(this.itemID);
		pw.println(this.itemTitle);
		pw.println(this.itemDescription);
		pw.println(this.isItemHired);
	}

	// Getters and Setters
	public int getItemID() {
		return itemID;
	}

	public int getNumWeeks() {
		return numWeeks;
	}

	public static int getHireCount() {
		return Item.hireCount;
	}

	public static double getTotalIncome() {
		return Item.totalIncome;
	}
}
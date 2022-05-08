package items;

import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Toy items are created by this class
 */
public final class Toy extends Item {

	private String toyCategory = null;
	
	// The costs for hiring each category are constants and determined by client
	private final static double CONSTRUCTION_TOY_COST = 5.45;
	private final static double RIDEON_TOY_COST = 8;
	private final static double SPORT_TOY_COST = 6.5;

	public Toy(String itemTitle, String itemDescription, String category) {
		super(itemTitle, itemDescription);
		
		// Ensure user enters a valid caregory when creating this item otherwise 
		// it throws an IllegalArgumentException
		if (!category.equals("construction") 
				&& !category.equals("ride-on") 
				&& !category.equals("sport")) {
			throw new IllegalArgumentException("* Error - Invalid category *");
		}
		
		this.toyCategory = category;
		
		Item.nextID++;
		Item.itemCount++;
	}
	
	// This reads the data from file to recreate this object
	public Toy(Scanner sc) {
		super(sc);
		this.toyCategory = sc.nextLine();
	}

	// Calculates the cost for hiring a toy
	@Override
	public double returnPrice() {
		double toyPrice = 0;
		
		if (this.toyCategory.equals("construction")) {
			toyPrice = super.getNumWeeks() * CONSTRUCTION_TOY_COST;
		}
		if (this.toyCategory.equals("ride-on")) {
			toyPrice = super.getNumWeeks() * RIDEON_TOY_COST;
		}
		if (this.toyCategory.equals("sport")) {
			toyPrice = super.getNumWeeks() * SPORT_TOY_COST;
		}
		return toyPrice;
	}

	// Displays the details about the item
	@Override
	public void showItem() {
		super.showItem();
		System.out.printf("%-15s: %s\n","Category",this.toyCategory);
		if (toyCategory.equals("construction")) {
			System.out.printf("%-15s: $%.2f\n","Price/Week",CONSTRUCTION_TOY_COST);
		}
		
		if (toyCategory.equals("ride-on")) {
			System.out.printf("%-15s: $%.2f\n","Price/Week",RIDEON_TOY_COST);
		}

		if (toyCategory.equals("sport")) {
			System.out.printf("%-15s: $%.2f\n","Price/Week",SPORT_TOY_COST);
		}
		
		System.out.println("");
	}
	
	// Exports item data to file
	@Override
	public void exportData(PrintWriter pw) {
		super.exportData(pw);
		pw.println(this.toyCategory);
	}
}
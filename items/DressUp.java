package items;

import java.io.PrintWriter;
import java.util.Scanner;

/*
 * DressUp items are created by this class
 */
public final class DressUp extends Item {

	// Laundry Fee is a constant
	private final static double LAUNDRY_FEE = 3.0;

	private double pricePerWeek;
	private String dressUpSize;
	private String dressUpGenre;
	private int dressUpPieces;

	public DressUp(String itemTitle, String itemDescription, String size, 
					String genre, int pieces) {

		super(itemTitle, itemDescription);

		this.dressUpSize = size;
		this.dressUpGenre = genre;
		this.dressUpPieces = pieces;

		this.pricePerWeek = this.dressUpPieces * 3.50;

		/*
		 * The ItemID for the next item is based on nextID. This is incremented in
		 * this subclass so that this change only happens if the subclass item is
		 * successfully created. If we leave this in Item then this value will
		 * increment unnecessarily. itemCount is also incremented here for the same
		 * reasons.
		 */		
		Item.nextID++;
		Item.itemCount++;
	}

	// This reads the data from file to recreate this object
	public DressUp(Scanner sc) {
		super(sc);
		this.dressUpSize = sc.nextLine();
		this.dressUpGenre = sc.nextLine();
		this.dressUpPieces = Integer.parseInt(sc.nextLine());
		this.pricePerWeek = Double.parseDouble(sc.nextLine());
	}

	// Calculates the cost for hiring a dress up item
	@Override
	public double returnPrice() {

		double dressUpCost = 0;
		
		dressUpCost = (this.pricePerWeek * super.getNumWeeks()) + LAUNDRY_FEE;

		return dressUpCost;
	}

	// Displays the details about the item
	@Override
	public void showItem() {
		super.showItem();
		System.out.printf("%-15s: %s\n", "Size", this.dressUpSize);
		System.out.printf("%-15s: %s\n", "Genre", this.dressUpGenre);
		System.out.printf("%-15s: %d\n", "Pieces", this.dressUpPieces);
		System.out.printf("%-15s: $%.2f\n", "Price/Week", this.pricePerWeek);
		System.out.printf("%-15s: $%.2f\n", "Laundry Fee", LAUNDRY_FEE);
		System.out.println("");
	}

	// Exports item data to file
	@Override
	public void exportData(PrintWriter pw) {
		super.exportData(pw);
		pw.println(this.dressUpSize);
		pw.println(this.dressUpGenre);
		pw.println(this.dressUpPieces);
		pw.println(this.pricePerWeek);
	}
}
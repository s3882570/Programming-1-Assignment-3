package items;

import java.io.PrintWriter;
import java.util.Scanner;

/*
 * PlayEquipment items are created by this class
 */
public final class PlayEquipment extends Item {

	private double weightKg;
	private int heightCm;
	private int widthCm;
	private int depthCm;
	private double weeklyHireCost;

	public PlayEquipment(String itemTitle, String itemDescription, 
			double weight, int height, int width, int depth,double weeklyCost) {

		super(itemTitle, itemDescription);
		
		this.weightKg = weight;
		this.heightCm = height;
		this.widthCm = width;
		this.depthCm = depth;
		this.weeklyHireCost = weeklyCost;

		Item.nextID++;
		Item.itemCount++;
	}

	// This reads the data from file to recreate this object
	public PlayEquipment(Scanner sc) {
		super(sc);
		this.weightKg = Double.parseDouble(sc.nextLine());
		this.heightCm = Integer.parseInt(sc.nextLine());
		this.widthCm = Integer.parseInt(sc.nextLine());
		this.depthCm = Integer.parseInt(sc.nextLine());
		this.weeklyHireCost = Double.parseDouble(sc.nextLine());
	}

	// Calculates the cost for hiring a play equipment
	@Override
	public double returnPrice() {

		double playEquipmentCost = 0;
		
		playEquipmentCost = super.getNumWeeks() * this.weeklyHireCost;

		return playEquipmentCost;
	}

	// Displays the details about the item
	@Override
	public void showItem() {
		super.showItem();
		System.out.printf("%-15s: %.2f kg\n", "Weight", this.weightKg);
		System.out.printf("%-15s: %d cm\n", "Height", this.heightCm);
		System.out.printf("%-15s: %d cm\n", "Width", this.widthCm);
		System.out.printf("%-15s: %d cm\n", "Depth", this.depthCm);
		System.out.printf("%-15s: $%.2f\n", "Cost/Week", this.weeklyHireCost);
		System.out.println("");
	}

	// Exports item data to file
	@Override
	public void exportData(PrintWriter pw) {
		super.exportData(pw);
		pw.println(this.weightKg);
		pw.println(this.heightCm);
		pw.println(this.widthCm);
		pw.println(this.depthCm);
		pw.println(this.weeklyHireCost);
	}
}
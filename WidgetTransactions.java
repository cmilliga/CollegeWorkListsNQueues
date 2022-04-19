package com.bleh.www;

import java.io.*;
import java.util.*;
/**
 * This Class processes all Widget transactions: It parses information brought in from the input file sorts it into lists as needed
 * and then takes the information from those lists as needed and performs actions based on the information for each Sale from the
 * input file. It then Prints all this information out.
 * @author connor
 *
 */
public class WidgetTransactions {
/**
 * This method checks for available Promotions on a Sale by referencing the Promotions List. It also keeps
 * track of how many uses of said Promotion are left.
 * @param promotions the reference of the Promotions object that is being checked and decremented.
 * @return the discount to be applied to a Sale.
 */
	public static double getAvailPromotion(LinkedList<Promotion> promotions) {
		double discount = 0;
		if(promotions.size()>0) {
			int c = promotions.get(0).getRemainingUses();
			discount = promotions.get(0).getDiscount();
			if (c==2) {
				promotions.get(0).setRemainingUses(1);
			}else if(c==1) {
				promotions.remove(0);
			}
		}
		return discount;
	}
	/**
	 * This method prints out sale information: Quantity of the Sale, Receipts used and their sale price, then the total
	 * for each sale. It also lets the user know if there were not enough Widgets to sell for that sale and how many.
	 * @param receipts is the list made of Receipt instances which hold receipt information.
	 * @param sQuantity is the quantity of widgets requested for a sale.
	 * @param discount is the percentage discount if there is one.
	 * @param ps is the print stream being used.
	 */
	public static void processSale (LinkedList<Receipt> receipts, int sQuantity, double discount, PrintStream ps) {
		ps.println(sQuantity+" Widgets sold");
		int remainingWidgets = sQuantity;
		double totalSale = 0.0;
		while (remainingWidgets>0 && receipts.size()>0) {
			int rQuant = receipts.get(0).getQuant();
			double rPrice = receipts.get(0).getPrice();
			double finalUnitPrice = rPrice*1.3*(1.0-discount);
			double finalPrice = finalUnitPrice*rQuant;
			totalSale += finalPrice;
			ps.printf("%d at %.2f each\t Sales: $%.2f\n", rQuant,finalUnitPrice,finalPrice);
			
			if (rQuant==remainingWidgets) {
				receipts.remove(0);
				remainingWidgets = 0;
			}else if(rQuant<remainingWidgets) {
				receipts.remove(0);
				remainingWidgets -= rQuant;
			}else if(rQuant>remainingWidgets) {
				receipts.get(0).setQuant(rQuant-remainingWidgets);
				remainingWidgets = 0;
			}
		}
		
		ps.printf("Total Sale:\t$ %.2f\n", totalSale);
		
		if(remainingWidgets>0) {
			ps.println("Remainder of "+remainingWidgets+" Widgets not available\n");
		}
		ps.println();
	}
	/**
	 * This is the main method where command line arguments can be passed. It will contain the loop that reads
	 * in all of the input data a parses Receipts, Sales, and Promotions.
	 * @param args are the command line arguments.
	 * @throws Exception is for the applicable Exception (i.e. invalid input).
	 */
	public static void main(String[] args) throws Exception{
		Scanner sourceTransaction = new Scanner(new FileInputStream (new File ("XYZTransactions.txt")));
		PrintStream ps = new PrintStream(new FileOutputStream(new File("XYZWidget_TransHistory.txt")));
		//no need for Sales list as it can be used as the "action" for the two other lists.
		LinkedList<Receipt> receipts = new LinkedList<Receipt>();
		LinkedList<Promotion> promotions = new LinkedList<Promotion>();
		//This loop determines the differences between Receipts, Promotions, and Sales in that order.
		while(sourceTransaction.hasNext()) {
			String recordType = sourceTransaction.next();
			if (recordType.equals("R")) {
				int receiptQuantity = sourceTransaction.nextInt();
				double receiptPrice = sourceTransaction.nextDouble();
				if (sourceTransaction.hasNextLine()) {
					sourceTransaction.nextLine();}
				Receipt tempR = new Receipt(receiptQuantity, receiptPrice);
				receipts.add(tempR);
				double totalWidgetCost = receiptQuantity*receiptPrice;
				ps.printf("Receipt Total: $%.2f\n\n",totalWidgetCost);
			}else if(recordType.equals("P")) {
				double promotionDiscount = sourceTransaction.nextDouble();
				if (sourceTransaction.hasNextLine()) {
					sourceTransaction.nextLine();}
				double finalPD = promotionDiscount/100.0;
				Promotion tempP = new Promotion(finalPD);
				promotions.add(tempP);
				ps.println("Discount percentage for the next two sales is: "+promotionDiscount+"%\n");
			}else if(recordType.equals("S")) {
				int salesQuantity = sourceTransaction.nextInt();
				if (sourceTransaction.hasNextLine()) {
					sourceTransaction.nextLine();}
				double discount = getAvailPromotion(promotions);
				processSale(receipts, salesQuantity, discount, ps);
			}
		}
		//For loop to just return the remaining widgets that are in stock.
		ps.println("Remaining Inventory");
		for(Receipt r : receipts) {
			int rq = r.getQuant();
			double rp = r.getPrice();
			ps.println("Stock left: "+rq+"\tPrice per item: $"+rp);
		}
	}

}

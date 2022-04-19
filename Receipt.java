package com.bleh.www;
/**
 * This class allows the creation of Receipts.
 * @author connor
 *
 */
public class Receipt {
	private int quant_;
	private double price_;
	/**
	 * This is the custom Constructor for a Receipt.
	 * @param quant is the quantity of items coming in.
	 * @param price is the cost per item.
	 */
	Receipt(int quant, double price){
		quant_ = quant;
		price_ = price;
	}
	/**
	 * This is the getter method for the quantity of items coming in.
	 * @return the quantity of items.
	 */
	public int getQuant() {
		return quant_;
	}
	/**
	 * This is the setter for the quantity of item, which can change 
	 * with a sale.
	 * @param quantity is the number of items quant_ should be changed to.
	 */
	public void setQuant(int quantity) {
		quant_= quantity;
	}
	/**
	 * This is the getter method for the price per item.
	 * @return the price per item.
	 */
	public double getPrice() {
		return price_;
	}
}

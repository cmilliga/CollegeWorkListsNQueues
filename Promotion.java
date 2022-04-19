package com.bleh.www;
/**
 * This class allows for the creation of Promtions.
 * @author connor
 *
 */
public class Promotion {
	private double discount_;
	private int remainingUses_;
	/**
	 * This is the constructor for a Promotion instance. It has
	 * a preset value for uses to allow us to remove it from
	 * more than 2 sales.
	 * @param discount is the percentage discount to be applied.
	 */
	Promotion(double discount) {
		discount_ = discount;
		remainingUses_ = 2;
	}
	/**
	 * Is the getter for the discount value.
	 * @return the percentage discount.
	 */
	public double getDiscount() {
		return discount_;
	}
	/**
	 * Is the getter for the remaining uses of the promotion.
	 * @return the count of uses.
	 */
	public int getRemainingUses() {
		return remainingUses_;
	}
	/**
	 * Is the setter for uses of the promotion.
	 * @param remainingUses is the Uses to be assigned or changed.
	 */
	public void setRemainingUses(int remainingUses) {
		remainingUses_ = remainingUses;
	}
}

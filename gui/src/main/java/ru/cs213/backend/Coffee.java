package ru.cs213.backend;

import java.util.ArrayList;

public class Coffee extends MenuItem implements Customizable {
	private final int SHORT = 0;
	private final int TALL = 1;
	private final int GRANDE = 2;
	private final int VENTI = 3;
	private final double SIZE_INCREASE = 0.5;
	private final double ADDIN_INCREASE = 0.2;
	private final double BASE_COST = 1.99;
	
	private ArrayList<CoffeeAddIns> addIns;
	private int size;
	
	public Coffee() {
		super();
		addIns = new ArrayList<CoffeeAddIns>();
		size = SHORT;
		itemPrice();
	}
	
	public Coffee(int size) {
		super();
		addIns = new ArrayList<CoffeeAddIns>();
		this.size = size;
		itemPrice();
	}
	
	public boolean add(Object obj) {
		if(!(obj instanceof CoffeeAddIns)) {
			return false;
		}
		addIns.add((CoffeeAddIns)obj);
		return true;
	}
	
	public boolean remove(Object obj) {
		if(!(obj instanceof CoffeeAddIns)) {
			return false;
		}
		CoffeeAddIns addIn = (CoffeeAddIns)obj;
		int idx = addIns.indexOf(addIn);
		if(idx == -1) {
			return false;
		}
		addIns.remove(idx);
		return true;
	}
	
	@Override
	public double itemPrice() {
		super.price = BASE_COST + addIns.size() * ADDIN_INCREASE + size * SIZE_INCREASE;
		return super.price;
	}

	public Coffee copy(){
		Coffee cpy =  new Coffee(this.size);
		for(int i = 0; i < addIns.size(); i++){
			cpy.add(addIns.get(i));
		}
		cpy.itemPrice();
		return cpy;
	}
	@Override
	public String toString(){
		String ret;
		if(size == SHORT){
			ret = "Short ";
		}else if(size == TALL){
			ret = "Tall ";
		}else if(size == GRANDE){
			ret = "Grande ";
		}else{
			ret = "Venti ";
		}
		ret = ret + "Coffee with ";
		if(addIns.isEmpty()){
			ret = ret + "NO add ins. ";
		}else{
			for(int i = 0; i < addIns.size(); i++){
				if(i < addIns.size() - 1) {
					ret = ret + addIns.get(i) + ", ";
				}else{
					ret = ret + addIns.get(i);
				}
			}
			ret = ret + ". ";
		}
		ret = ret + "Cost = " + itemPrice();
		return ret;
	}
	public int getSize(){
		return this.size;
	}
	public void setSize(int size){
		this.size = size;
	}
	public String[] getAddInsAsStr(){
		String[] ret = new String[addIns.size()];
		for(int i = 0; i < addIns.size(); i++){
			ret[i] = "" + addIns.get(i);
		}
		return ret;
	}
}

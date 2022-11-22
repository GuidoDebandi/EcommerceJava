package com.globallogic.demo.entity.operator;

public enum StockOperator {
	
	SUMAR("+"){
		@Override public int calculateStock(int stock, int quantity) {
			return stock+quantity;
		}
	},
	DESCONTAR("-"){
		@Override public int calculateStock(int stock, int quantity) {
			return stock-quantity;
		}
	};
	private final String indicator;
	
	
	
	private StockOperator(String indicator) {
		this.indicator = indicator;
	}



	public String getIndicator() {
		return indicator;
	}



	public abstract int calculateStock(int stock, int quantity);
	
	
	
}

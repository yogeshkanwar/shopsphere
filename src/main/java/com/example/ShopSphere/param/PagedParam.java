package com.example.ShopSphere.param;

import javax.validation.constraints.NotNull;

public class PagedParam {
	@NotNull
	private int page;
	@NotNull
	private int size;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	

}

package com.example.ShopSphere.dsa;

import java.util.ArrayList;

public class CustomStack {
	private ArrayList<Integer> list;
	
	public CustomStack() {
		list = new ArrayList<>();
	}
	
	public void push(int value) {
		list.add(value);
	}
	
	public void pop(CustomStack stack) {
		int size = size();
		list.remove(size - 1);
		System.out.println("\n stack after pop an element ");
		displayStack(stack);
	}
	
	public void displayStack(CustomStack stack) {
		if(isEmpty()) {
			System.out.println("Stack is empty");
		}
		for(int i = list.size()-1 ; i >= 0 ; i--) {
			System.out.println(list.get(i));
		}
	}
		
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public int size() {
		return list.size();
	}
	

	public static void main(String[] args) {
		CustomStack stack = new CustomStack();
		stack.push(19);
		stack.push(12);
		stack.push(15);
		stack.push(20);
		stack.push(1);
		
//		stack.displayStack(stack);
//		stack.pop(stack);
		
		String s1 = "Java";
		String s2 = "Java";
		System.out.println(s1 == s2);
	}
	

}

package com.example.ShopSphere.dsa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LinkedList {
	static Node start;
	
	public LinkedList() {
		this.start = null;
	}
	
	public void add(int value) {
		Node node = new Node(value);
		if(start == null) {
			start = node;
		} else {
			Node current = start;
			while(current.next != null) {
				current=current.next;
			}
			current.next = node;
		}
	}
	
	public static void print(LinkedList list) {
		Node current = start;
		while(current != null) {	
			System.out.print(" "+ current.value);
			current = current.next;
		}	
	}
	
	
	private static LinkedList reverse(LinkedList list) {
		Node current = start;
		Node previous = null;
		
		while(current != null) {
			Node temp = current.next;
			current.next = previous;
			previous = current;
			current = temp;
		}
		start = previous;
		System.out.println("\n reverse of linked list is ");
		print(list);
		return list;
	}
	
	private static Node findMiddle(LinkedList list) {
		Node slowPointer = start;
        Node fastPointer = start;

        while (fastPointer.next != null && fastPointer.next.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        System.out.println("\n Mid value is " + slowPointer.value);		
        return slowPointer;
	}
	
	public static void isPalindrom(LinkedList list) {
		Node firstNodeCurrent = start;
        boolean isPalindrom = true;
		Node middle = findMiddle(list);
		start = middle.next;
		
		LinkedList reverseList = reverse(list);
		print(reverseList);
		
		Node current = reverseList.start;
		while(current != null) {
			if(current.value != firstNodeCurrent.value) {
				isPalindrom  = false;
				break;
			}
			current = current.next;
			firstNodeCurrent = firstNodeCurrent.next;		
		}
		
		System.out.println("\nLinkedList is palindrom :  " +isPalindrom);
	}
	
	public static void uniqueStringCount(String s) {
		Map<Character,Integer> map = new HashMap<>();
		int maxCount = 0;
		int count = 0;

		for(int i=0; i<s.length(); i++) {
			if(!map.containsKey(s.charAt(i))) {
				map.put(s.charAt(i), i);
				count++;
				if(count > maxCount) {
					maxCount = count;
				}
			} else {		
				map.clear();
				if(count > maxCount) {
					maxCount = count;
				}
				count = 0;
			}
			  
		}
		System.out.println(maxCount);
	}
	
	public static void testSet() {
		Set<Integer> set = new HashSet<>();
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(1);
		
		System.out.println(set);
	}
	
    
	
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(20);	
		list.add(10);

		
//		print(list);
//		//reverse(list);
////		findMiddle(list);
//		isPalindrom(list);
		
//		String s = "nnnf";
//		uniqueStringCount(s);
		testSet();
		
	}

}

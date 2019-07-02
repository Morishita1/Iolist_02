package com.biz.iolist.service;

import java.util.Scanner;

public class MainService {

	ProductService productService=null;
	Scanner scan=null;
	
	public MainService() {

	productService=new ProductService();
	scan=new Scanner(System.in);
	
	
	}
	
	public void start() {
		
		while(true) {
			productService.viewProduct();
			System.out.println("===================================================");
			System.out.println("1.상품등록    2.상품수정    3.상품삭제    4.종료");
			System.out.println("---------------------------------------------------");
			
			System.out.print("업무선택 >>");
			String strnum=scan.nextLine();
			int intnum=Integer.valueOf(strnum);
			if(intnum==1)
				productService.insertPRO();
			if(intnum==2)
				productService.updatePRO();
			if(intnum==3)
				productService.deletePRO();
			if(intnum==4)
				break;
		}
		System.out.println("---------------------------------------------------");
	}
	
}

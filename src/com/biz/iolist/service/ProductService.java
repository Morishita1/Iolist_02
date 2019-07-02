package com.biz.iolist.service;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.config.DBConnection;
import com.biz.iolist.dao.ProductDao;
import com.biz.iolist.model.ProductVO;

/*
 * 상품정보의 등록, 수정, 삭제 method
 */
public class ProductService {

	SqlSession sqlSession= null;
	Scanner scan=null;
	ProductDao proDao=null;
	
	public ProductService() {

	sqlSession=DBConnection.getSqlSessionFactory().openSession(true);
	proDao=(ProductDao) sqlSession.getMapper(ProductDao.class);
	scan=new Scanner(System.in);
	
	
	}
	public void viewProduct() {
		
		System.out.println("========================================================");
		System.out.println("우리동네 제일마트 - 상품정보");
		System.out.println("--------------------------------------------------------");
		System.out.println("상품코드\t상품이름\t매입단가\t매출단가");
		System.out.println("--------------------------------------------------------");
		List<ProductVO> proList=proDao.selectAll();
		for(ProductVO vo:proList) {
			System.out.print(vo.getP_code()+"\t\t");
			System.out.print(vo.getP_name()+"\t\t");
			System.out.print(vo.getP_iprice()+"\t\t");
			System.out.println(vo.getP_oprice());
			
		}
		System.out.println("--------------------------------------------------------");
	}
	
	public boolean insertPRO() {
		
		System.out.println("======================");
		System.out.println("상품정보 등록");
		System.out.println("----------------------");
		System.out.print("상품코드 >>");
		String strPcode=scan.nextLine();
		System.out.print("상품이름 >>");
		String strPname=scan.nextLine();
		System.out.print("매입단가 >>");
		String strPiprice=scan.nextLine();
		int intPiprice=Integer.valueOf(strPiprice);
		System.out.print("매출단가 >>");
		String strPoprice=scan.nextLine();
		int intPoprice=Integer.valueOf(strPoprice);
		
		ProductVO vo=new ProductVO(strPcode,strPname,intPiprice,intPoprice);
		if(proDao.insert(vo)>0) return true;
		else return false;
	
	}
	public boolean updatePRO() {
		System.out.println("====================");
		System.out.println("상품정보 수정");
		System.out.println("--------------------");
		System.out.print("수정할 상품코드 >>");
		String strPcode=scan.nextLine();
		ProductVO vo=proDao.findByCode(strPcode);
		if(vo==null) {
			System.out.println("수정할 상품이 없습니다.");
			return false;
		}
		
		System.out.printf("상품이름 (%s) >>",vo.getP_name());
		String strPname=scan.nextLine();
		if(strPname.isEmpty()) strPname=vo.getP_name();
		System.out.printf("매입단가 (%d) >>",vo.getP_iprice());
		String strPiprice=scan.nextLine();
		int intPiprice=0;
		if(strPiprice.isEmpty()) intPiprice=vo.getP_iprice();
		else intPiprice=Integer.valueOf(strPiprice);
		System.out.printf("매출단가 (%d) >>",vo.getP_oprice());
		String strPoprice=scan.nextLine();
		int intPoprice=0;
		if(strPoprice.isEmpty()) intPoprice=vo.getP_oprice();
		else intPoprice=Integer.valueOf(strPoprice);
		
		vo.setP_name(strPname);
		vo.setP_iprice(intPiprice);
		vo.setP_oprice(intPoprice);
		
		if(proDao.update(vo)>0) return true;
		else return false;
	}
	public boolean deletePRO() {
		
		System.out.print("삭제할 상품정보 >> ");
		String strPcode=scan.nextLine();
		ProductVO vo=proDao.findByCode(strPcode);
		System.out.println(vo);
		System.out.print("정말삭제하시겠습니까(YES)");
		String confirm=scan.nextLine();
		if(confirm.equals("YES")) {
			if(proDao.delete(strPcode)>0) return true;
			else return false;
		}
		return false;
		
	}
}

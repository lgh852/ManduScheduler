package com.manduScheduler.main.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manduScheduler.main.common.JDBCTemplate;

public class SchedulerService {

	public void insertProductInfo(Connection conn, List<Map<String, String>> items) throws Exception{
		
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO TB_PRODUCT_INFO ";
			   sql += "(";
			   sql += 	" PRODUCT_TITLE";
			   sql += 	",PRODUCT_LINK";
			   sql += 	",PRODUCT_IMAGE";
			   sql += 	",LPRICE";
			   sql += 	",HPRICE";
			   sql += 	",MALLNAME";
			   sql += 	",PRODUCT_ID";
			   sql += 	",PRODUCT_TYPE";
			   sql += 	",BRAND";
			   sql += 	",MAKER";
			   sql += 	",CATEGORY1";
			   sql += 	",CATEGORY2";
			   sql += 	",CATEGORY3";
			   sql += 	",CATEGORY4";
			   sql += ")";

			   
			   for(int i = 0; i < items.size(); i++ ) {
				   Map<String,String> map = items.get(i);
				   sql += "SELECT";
				   sql += 	"'"+map.get("title");
				   sql += 	"' ,'"+map.get("link");
				   sql += 	"' ,'"+map.get("image");
				   sql += 	"' ,'"+map.get("lprice");
				   sql += 	"' ,'"+map.get("hprice");
				   sql +=	"' ,'"+map.get("mallName");
				   sql +=	"' ,'"+map.get("productId");
				   sql +=	"' ,'"+map.get("productType");
				   sql +=	"' ,'"+map.get("brand");
				   sql +=	"' ,'"+map.get("maker");
				   sql +=	"' ,'"+map.get("category1");
				   sql +=	"' ,'"+map.get("category2");
				   sql +=	"' ,'"+map.get("category3");
				   sql +=	"' ,'"+map.get("category4")+"'";
				   sql +=	" FROM DUAL";
				   if( i != items.size()-1) {
					   sql +=   " UNION ALL ";
				   }
			   }
			   System.out.println("query : "+sql);
			  
		try {
			
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			//pstmt.setInt(1, b.getBkPr ice());
			
			//result = pstmt.executeUpdate();
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
	}

}

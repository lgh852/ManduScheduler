package com.manduScheduler.main.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.manduScheduler.main.common.JDBCTemplate;

public class SchedulerService {

	public void insertProductInfo(Connection conn, List<Map<String, Object>> items) throws Exception{
		
		PreparedStatement pstmt = null;
		
		String sql = "select * from tb_product_info";
		
		try {
			
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			//pstmt.setInt(1, b.getBkPrice());
			
			//result = pstmt.executeUpdate();
			pstmt.executeQuery();
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
	}

}

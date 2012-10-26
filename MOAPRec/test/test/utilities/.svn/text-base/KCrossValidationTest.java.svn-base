package test.utilities;

import java.sql.SQLException;

import recommender.evaluation.KFoldCrossValidation;

public class KCrossValidationTest {
	public static void main(String[] args) throws SQLException, Exception{
		String query = "" +
				" select userid,poiid,trajid,start_time,end_time from stop_cpoi_1200s_150m s  " +
				" where (extract(epoch from end_time)-extract(epoch from start_time)) <= 36000";
		
		KFoldCrossValidation.KFoldCrossValidation(query,"cdataset");
	}
}	

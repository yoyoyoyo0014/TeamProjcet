package team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
    	
    	try 
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    		
    		// 비교할 시간 (문자열) 
    		String timeStr1 = "06:50";
    		String timeStr2 = "12:10";
    		
    		// 문자열 -> Date 
    		Date date1 = sdf.parse(timeStr1);
    		Date date2 = sdf.parse(timeStr2);
			
    		// Date -> 밀리세컨즈 
    		long timeMil1 = date1.getTime();
    		long timeMil2 = date2.getTime();
			
    		// 비교 
    		long diff = timeMil2 - timeMil1;
			
    		long diffSec = diff / 1000;
    		long diffMin = diff / (1000 * 60);
			
    		System.out.println("시간 차이(초) : " + diffSec + "초");
    		System.out.println("시간 차이(분) : " + diffMin + "분");
     	} 
    	catch (ParseException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	    
}
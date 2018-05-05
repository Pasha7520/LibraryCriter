package util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Order;

public class MathUtil {
	
	public double findPersent(double price,double persent){ 
		return price*(persent/100);
	}
	public int fingAVGDays(int [] mas){
		int AvgDay=0;
		for(int i=0;i < mas.length;i++){
			AvgDay += mas[i];
		}
		
		
		return AvgDay/mas.length;
		
	}
	public int AVGTimeReadingBook(List<Order> list,int size){
		int [] mas = new int[size];
		int i = 0;
		
		for(Order o :list){
			
		int day = 0;
		LocalDate l1 = o.getActualDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate l2 = o.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period period = Period.between(l2, l1);
		
		if(period.getYears() > 0){
			day = period.getYears()*365;
			if(period.getMonths() > 0){
				day = day+(period.getMonths()*31);
			}
			day = day+period.getDays();
		}
		else if(period.getMonths() > 0){
			day = (period.getMonths()*31);
			day = day+period.getDays();
		}
		else{
			day =period.getDays();
		}
		mas[i++] = day;
		
		}
		
		
		
		return fingAVGDays(mas);
		
	}
	
}

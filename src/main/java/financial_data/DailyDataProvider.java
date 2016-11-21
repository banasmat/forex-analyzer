package financial_data;

import java.util.Date;
import java.util.LinkedHashMap;

public interface DailyDataProvider {

	public LinkedHashMap<Date, Double> getDate();
	
}

package enumRiddles;

enum Day {
	   MONDAY(0),
	   TUESDAY(1),
	   WEDNESDAY(2),
	   THURSDAY(3),
	   FRIDAY(4),
	   SATURDAY(5),
	   SUNDAY(6);

	   public Day next(){return map.get((day + 1)%7);}

	   private static java.util.HashMap<Integer, Day> map = new java.util.HashMap<Integer, Day>();

	   private int day;

	   int getDayNumber() {
	      return day+1;
	   }

	   private Day() {
		   this.day = 1;
	   }

	   static {
	        for (Day day : Day.values()) {
	            map.put(day.day, day);
	        }
	    }

	   private Day(int day) {
		   this.day = day;
	   }
}

	public class DayTest {
	   public static void main(String[] args) {
	      for (Day day : Day.values()) {
	         System.out.printf("%s (%d), next is %s\n", day, day.getDayNumber(), day.next());
	      }
	   }
	}

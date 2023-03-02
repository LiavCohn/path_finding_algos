import java.util.Comparator;

public class NodeComparator implements Comparator<MyCube>{
              
       // Overriding compare()method of Comparator - ascending order
       public int compare(MyCube s1, MyCube s2) 
       {
    	   if (s1.f_score < s2.f_score)
    		   return -1;
           else if (s1.f_score > s2.f_score)
        	   return 1;
           return 0;
                
       }
}

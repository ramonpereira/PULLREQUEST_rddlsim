package rddl.evaluate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainFixPolicyEvaluator {
	
	public static double[] getRewards(String LOG_FILE,int SIZE){
		double[] values=new double[SIZE];
		try (BufferedReader br = Files.newBufferedReader(Paths.get(LOG_FILE))) {
		    String line;
		    String input = "";
		    String pattern = "ROUND END, reward = (-([0-9]*[.])?[0-9]+)";
		    Pattern p = Pattern.compile(pattern);
		    int count=0;
		    while ((line = br.readLine()) != null) {
			    	Matcher m = p.matcher(line);
			    	if(m.find()){
			    	String value = m.group(1);
			    	System.out.print(value+",");
			    	values[count]=Double.parseDouble(value);
			    	count++;
		    	}
		    }
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return values;
	}
	
	public static double getMean(double[] m) {
	    double sum = 0;
	    for (int i = 0; i < m.length; i++) {
	        sum += m[i];
	    }
	    return sum / m.length;
	}
	
    public static double getVariance(double mean,double[] data)
    {
        double temp = 0;
        for(double a :data)
            temp += (a-mean)*(a-mean);
        return temp/data.length;
    }

    public static double getStdDev(double[] data)
    {
    	double mean=getMean(data);
        return Math.sqrt(getVariance(mean,data));
    }
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String logfile="logs/HVAC_log.txt";
		int size=30;
		double[] values=getRewards(logfile,size);
		System.out.println("");
		System.out.println("Mean:"+getMean(values));
		System.out.println("STD:"+getStdDev(values));
	}

}

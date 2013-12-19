package util.sigar;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class SigarUtil {
	
	private static Sigar sigar=new Sigar();
    
    public static Cpu getCpuInfo() throws SigarException{
    	return sigar.getCpu();
    }
    
    public static void main(String[] args) throws SigarException {
    	
	}
}

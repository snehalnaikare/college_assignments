import java.util.*;
import java.io.*;
class asm {

	static String[][] mtab = {
            {"STOP", "ADD", "SUB", "MULT" ,"MOVER" ,"MOVEM","COMP","BC","DIV","READ","PRINT"},
            {"00","01","02","03","04","05","06","07","08","09","10"}
        };

	static String[][] cctab = {
            {"LT", "LE", "EQ", "GT" ,"GE" ,"ANY"},
            {"01","02","03","04","05","06"}
        };

	static String[][] rtab = {
            {"AREG", "BREG", "CREG", "DREG" },
            {"01","02","03","04"}
        };

	static int LC=0;
	
	static Map<String, String> symmap = new HashMap<>();//symtab<S0,variable_name>
	static Map<String, Integer> symtab = new HashMap<>();//symtab<variable_name,add(lc)>
	
	static Map<String, Integer> littab = new HashMap<>();//littab<L0,value>
	static Map<Integer, Integer> litadd = new HashMap<>();//littab<value,add>
	
	static int st_cntr=0;
	static int lt_cntr=0;

	
    public static void main(String[] args) throws Exception {

try{
	String fileName = "source.txt";//source code txt file
	File file = new File(fileName);
	FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);
	String line;
	BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));//intermadiate code txt file
	while((line = br.readLine()) != null)
	{
        //process each line
	LC=LC+1;
        System.out.println(line);
        String ic=parse_line(line);
         if(ic!=""){
        		writer.write(ic+"\n");
        	     }
	}
	writer.close();
	
	//write symtab in symtab file	
	BufferedWriter writer1 = new BufferedWriter(new FileWriter("symtab.txt"));
	symmap.forEach((S,V) -> {int add=symtab.get(V);
				System.out.println(S+"\t"+V+"\t"+add);
				try{
				writer1.write(S+"\t"+V+"\t"+add+"\n");
				}catch(Exception e){e.printStackTrace();}
				});
	writer1.close();
	
	//write littab in littab file
	BufferedWriter writer2 = new BufferedWriter(new FileWriter("littab.txt"));
	littab.forEach((L,V) -> {int add=litadd.get(V);
				System.out.println(L+"\t"+V+"\t"+add);
				try{
				writer2.write(L+"\t"+V+"\t"+add+"\n");
				}catch(Exception e){e.printStackTrace();}
				});
	writer2.close();
}catch(Exception e){e.printStackTrace();}
	
    }
	public static String parse_line(String line)throws IOException 
	{
		String[] words=line.split("\\s");//splits the string based on whitespaces
		String pars="";  
		if(words[0].equals("START"))//process start
			{
			 LC=Integer.parseInt(words[1]);
			 System.out.println("LC="+Integer.parseInt(words[1]));
			} 
		else if(words[0].equals("ORIGIN"))//process ORIGIN
			{
			 LC=Integer.parseInt(words[1]);
			}
		else if(words[0].equals("LTORG"))//process LTORG
			{
			 littab.forEach((L,V)->{litadd.put(V,LC);LC=LC+1;});
			}
		else if(words[1].equals("EQU"))//process EQU
			{
			 int addr=symtab.get(words[2]);
			 symtab.put(words[0],addr);
			}
		else{ 
			
			int i=0; 
			for(String w:words)
			{  
			 String wcode="";
			 for(i=0;i<mtab[0].length;i++)//replace code with mneumonic table
				{
				if(mtab[0][i].equals(w))
					{
					wcode=mtab[1][i];
					break;
					}
				}  
			for(i=0;i<cctab[0].length;i++)//replace code with condition table
				{
				if(cctab[0][i].equals(w))
					{
					wcode=cctab[1][i];
					break;
					}
				}  
			for(i=0;i<rtab[0].length;i++)//replace code with register table
				{
				if(rtab[0][i].equals(w))
					{
					wcode=rtab[1][i];
					break;
					}
				}  
			if(wcode.equals(""))//only literals and symbols are remaining
				{
					if(w.charAt(0)=='=')//literal processing
					{
						wcode="literal";
						w=w.substring(1);
						littab.put("L"+Integer.toString(lt_cntr),Integer.parseInt(w));
						wcode="L"+Integer.toString(lt_cntr);
						lt_cntr=lt_cntr+1;
					}
					else//symbol processing
					{
						if(symtab.containsKey(w))
						{
						wcode="-";
						System.out.println("Error:symbol already exists.");
						}
						else
						{
						symmap.put("S"+Integer.toString(st_cntr),w);
						symtab.put(w,LC);
						wcode="S"+Integer.toString(st_cntr);
						st_cntr=st_cntr+1;
						}
					}	
				}
			if(pars==""){pars=pars+wcode;}
			else{pars=pars+" "+wcode;}
		
			}
			System.out.println(pars);
			
	           }
               return pars; 
		 
	}

}

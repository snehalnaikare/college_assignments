import java.util.*;
import java.io.*;
class pass2
{
public static void main(String[] args) throws Exception {

try{
	String fileName = "output.txt";//output of pass one assembler
	File file = new File(fileName);
	FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);
	String line;
	BufferedWriter writer = new BufferedWriter(new FileWriter("machine_code.txt"));//machine_code txt file
	while((line = br.readLine()) != null)
	{
        //process the line
        System.out.println(line);
        String mc=parse_line(line);
        writer.write(mc+"\n");
	}
	writer.close();
   }catch(Exception e){e.printStackTrace();}
}
public static String parse_line(String line)throws IOException 
	{
		String[] words=line.split("\\s");//splits the string based on whitespace
		String pars="";
		String wcode=""; 
		if(words.length>4)//accept only 4 enries per line
			{
			System.out.println("Error : more that 4 entries per line;");
			}
		else
			{
				for(int i=0;i<words.length;i++)
				{
					if(words[i].charAt(0)=='L'||words[i].charAt(0)=='S')
					{
					  
					  if(words[i].charAt(0)=='L')
					   {wcode=litprocess(words[i]);}			
					  else
					   {wcode=symprocess(words[i]);}
					}
					else
					{
					wcode=words[i];
					}
					pars=pars+"\t"+wcode;
				}	
			}
		return pars;
	}
public static String litprocess(String lit)throws IOException //function for retriving value from littab file
{
	String ret="";
try
	{
	String fileName = "littab.txt";
	File file = new File(fileName);
	FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);
	String line;
	while((line = br.readLine()) != null)
	{
        //find the literal and return the addr
        String[] word=line.split("\\s");
        if(word[0].equals(lit))
        {ret= word[2];}
	}
	}catch(Exception e){e.printStackTrace();}
	return ret;
}

public static String symprocess(String sym)throws IOException //function for retriving value from symtab file
{
String ret="";
try
	{
	String fileName = "symtab.txt";
	File file = new File(fileName);
	FileReader fr = new FileReader(file);
	BufferedReader br = new BufferedReader(fr);
	String line;
	while((line = br.readLine()) != null)
	{
        //find the symbol and return the addr
        String[] words=line.split("\\s");
        if(words[0].equals(sym))
        {ret= words[2];System.out.println(words[2]);}
	}
	}catch(Exception e){e.printStackTrace();}
	return ret;
}
}


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class filtercopy {
    ArrayList wytyczne[][] = new ArrayList[2][7]; //0-1: directory-file; 0-6:equals,startsWith,contains,endsWith,NOTStartsWith,NOTContains,NOTEndsWith
    String newDirectoryName;
    
    public filtercopy () {
        for (int i=0;i<2;i++) for (int j=0;j<7;j++) wytyczne[i][j] = new ArrayList();
        try {
            BufferedReader config = new BufferedReader(new FileReader("FilterConfig.txt"));
            String result; int index=-1;
            do {
                result = config.readLine(); if (result==null) break;
                if (result.split(":").length>1) index++;
                else {
                    if (index<0) newDirectoryName=result.split("=")[1];
                    else {
                        String results[] = result.split("#");
                        for (int i=0;i<results.length;i++) wytyczne[index][i].add(results[i]);
                    }
                }
            } while (result!=null);
            
            File resultsDirectory = new File(newDirectoryName); resultsDirectory.mkdir();
            File workDirectory = new File(".");
            for (int i=0;i<workDirectory.listFiles().length;i++) {
                File actualDirectory = workDirectory.listFiles()[i];
                if (actualDirectory.isDirectory()) for (int j=0;j<wytyczne[0][0].size();j++) {
                    if (!wytyczne[0][0].get(j).toString().equals("!") && 
                        !actualDirectory.getName().equals(wytyczne[0][0].get(j).toString())) continue;
                    if (!wytyczne[0][1].get(j).toString().equals("!") && 
                        !actualDirectory.getName().startsWith(wytyczne[0][1].get(j).toString())) continue;
                    if (!wytyczne[0][2].get(j).toString().equals("!") && 
                        !actualDirectory.getName().contains(wytyczne[0][2].get(j).toString())) continue;
                    if (!wytyczne[0][3].get(j).toString().equals("!") && 
                        !actualDirectory.getName().endsWith(wytyczne[0][3].get(j).toString())) continue;
                    if (!wytyczne[0][4].get(j).toString().equals("!") && 
                        actualDirectory.getName().startsWith(wytyczne[0][4].get(j).toString())) continue;
                    if (!wytyczne[0][5].get(j).toString().equals("!") && 
                        actualDirectory.getName().contains(wytyczne[0][5].get(j).toString())) continue;
                    if (!wytyczne[0][6].get(j).toString().equals("!") && 
                        actualDirectory.getName().endsWith(wytyczne[0][6].get(j).toString())) continue;                    
                    for (int k=0;k<actualDirectory.listFiles().length;k++) for (int l=0;l<wytyczne[1][0].size();l++) {
                        if (!wytyczne[1][0].get(l).toString().equals("!") && 
                            !actualDirectory.listFiles()[k].getName().equals(wytyczne[1][0].get(l).toString())) continue;
                        if (!wytyczne[1][1].get(l).toString().equals("!") && 
                            !actualDirectory.listFiles()[k].getName().startsWith(wytyczne[1][1].get(l).toString())) continue;
                        if (!wytyczne[1][2].get(l).toString().equals("!") && 
                            !actualDirectory.listFiles()[k].getName().contains(wytyczne[1][2].get(l).toString())) continue;
                        if (!wytyczne[1][3].get(l).toString().equals("!") && 
                            !actualDirectory.listFiles()[k].getName().endsWith(wytyczne[1][3].get(l).toString())) continue; 
                        if (!wytyczne[1][4].get(l).toString().equals("!") && 
                            actualDirectory.listFiles()[k].getName().startsWith(wytyczne[1][4].get(l).toString())) continue;
                        if (!wytyczne[1][5].get(l).toString().equals("!") && 
                            actualDirectory.listFiles()[k].getName().contains(wytyczne[1][5].get(l).toString())) continue;
                        if (!wytyczne[1][6].get(l).toString().equals("!") && 
                            actualDirectory.listFiles()[k].getName().endsWith(wytyczne[1][6].get(l).toString())) continue;
                        
                        FileInputStream in = new FileInputStream(actualDirectory.listFiles()[k]);
                        File newDirectory = new File(resultsDirectory.getAbsolutePath()+"/"+actualDirectory.getName()); newDirectory.mkdir();
                        File outFile = new File(newDirectory.getAbsolutePath()+"/"+actualDirectory.listFiles()[k].getName()); outFile.createNewFile();
                        FileOutputStream out = new FileOutputStream(outFile);
                        byte[] buffer = new byte[1024]; int read;
                        while ((read=in.read(buffer))!=-1) out.write(buffer,0,read);
                        in.close(); out.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new filtercopy();
    }
}

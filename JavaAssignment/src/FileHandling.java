import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandling implements Serializable {
    private final String fileName;
    private final File file;

    public FileHandling(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while creating file " + fileName);
        }
    }

    private List<String> getHeader() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String firstLine = br.readLine();
            br.close();
            return Arrays.asList(firstLine.split(","));
        } catch (IOException e) {
            System.out.println("Error occurred while getting header");
        }
        return new ArrayList<>();
    }

    private int columnNumber() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String firstLine = br.readLine();
            String[] headerCol = firstLine.split(",");
            br.close();
            return headerCol.length;
        } catch (IOException e) {
            System.out.println("Error occurred while counting column");
            return -1;
        }
    }

        public void addRow(String... data) {
            try {
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);

                if (data.length == columnNumber() && data[0].length() < 15) {
                    String[] myArr = Arrays.stream(data).toArray(String[]::new);
                    pw.println(String.join(",", myArr));
                } else {
                    throw new IOException();
                }
                pw.close();
            } catch (IOException e) {
                System.out.println("Error occurred while adding row");
            }
        }

    public String searchRow(String colName, String data) {
        try {
            int colIndex = getHeader().indexOf(colName);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine = br.readLine(); // Jump the header line
            while ((currentLine = br.readLine()) != null) {
                String[] tempArr = currentLine.split(",");
                if (tempArr[colIndex].equals(data)) {
                    br.close();
                    return currentLine;
                }
            }
            br.close();
            return null;
        } catch (IOException e) {
            System.out.println("Error occurred while searching row");
        }
        return null;
    }

    public void editRow(String colName,String data,String editCol,String newContent){
        try {
            int colIndex = getHeader().indexOf(colName);
            FileReader fr = new FileReader(fileName);
            int editCoIndex = getHeader().indexOf(editCol);
            BufferedReader br = new BufferedReader(fr);

            String currentLine = br.readLine(); // Jump the header line
            String[] tempArr={};

            while ((currentLine = br.readLine()) != null) {
                tempArr = currentLine.split(",");
                if (tempArr[colIndex].equals(data)) {
                    tempArr[editCoIndex] = tempArr[editCoIndex].replace(tempArr[editCoIndex],newContent);
                    break;
                }
            }
            br.close();
            deleteRow(colName,data);
            addRow(tempArr);
        } catch (IOException e) {
            System.out.println("Error occurred while editing row");
        }
    }

    public void editRow(String colName1,String data1, String colName2,String data2,String editCol,String newContent){
        try {
            int colIndex1 = getHeader().indexOf(colName1);
            int colIndex2 = getHeader().indexOf(colName2);
            FileReader fr = new FileReader(fileName);
            int editCoIndex = getHeader().indexOf(editCol);
            BufferedReader br = new BufferedReader(fr);

            String currentLine = br.readLine(); // Jump the header line
            String[] tempArr={};
            while ((currentLine = br.readLine()) != null) {
                tempArr = currentLine.split(",");
                if (tempArr[colIndex1].equals(data1) && tempArr[colIndex2].equals(data2)) {
                    tempArr[editCoIndex] = tempArr[editCoIndex].replace(tempArr[editCoIndex],newContent);
                    break;
                }
            }
            br.close();
            deleteRow(colName1,data1,colName2,data2);
            addRow(tempArr);
        } catch (IOException e) {
            System.out.println("Error occurred while editing row");
        }
    }


    public void deleteRow(String colName, String data) {
        try {
            int colIndex = getHeader().indexOf(colName);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = "";
            int deletedRowNum = 0;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[colIndex].equals(data)) {
                    found = true;
                    deletedRowNum++;
                    continue;
                }
                pw.println(line);
            }
            pw.close();
            br.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));
                if (deletedRowNum > 1) {
                    System.out.printf("%d rows was deleted.%n", deletedRowNum);
                } else {
                    System.out.println("1 row was deleted");
                }
            } else {
                tempFile.delete();
                System.out.println("Data Not Found");
            }
        } catch (IOException e) {
            System.out.println("Error occurred while deleting row");
        }
    }

    public void deleteRow(String colName1, String data1,String colName2,String data2) {
        try {
            int colIndex1 = getHeader().indexOf(colName1);
            int colIndex2 = getHeader().indexOf(colName2);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String line = "";
            int deletedRowNum = 0;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[colIndex1].equals(data1)  && line.split(",")[colIndex2].equals(data2)) {
                    found = true;
                    deletedRowNum++;
                    continue;
                }
                pw.println(line);
            }
            pw.close();
            br.close();
            if (found) {
                file.delete();
                tempFile.renameTo(new File(fileName));
                if (deletedRowNum > 1) {
                    System.out.printf("%d rows was deleted.%n", deletedRowNum);
                } else {
                    System.out.println("1 row was deleted");
                }
            } else {
                tempFile.delete();
                System.out.println("Data Not Found");
            }
        } catch (IOException e) {
            System.out.println("Error occurred while deleting row");
        }
    }


    public void printData() {
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] dataRow = line.split(",");
            System.out.println("=".repeat(40));
            for (String data : dataRow) {
                System.out.printf("%-10s", data);
            }
            System.out.println();
            System.out.println("=".repeat(40));
            while ((line = br.readLine()) != null) {
                dataRow = line.split(",");
                for (String data : dataRow) {
                    System.out.printf("%-10s", data);
                }
                System.out.println();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error occurred while printing data");
        }
    }

    public static User login(String userID, String userPassword) {
        try {
            FileInputStream fis = new FileInputStream("Users.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                User temp = null;
                Object obj = ois.readObject();
                if (obj != null) {
                    temp = (User) obj;
                    if (userID.equals(temp.getUserID()) && userPassword.equals(temp.getUserPassword())) {
                        return temp;
                    }
                } else {
                    return null;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

//    public String generateID(){
//        try{
//            FileReader fr = new FileReader(fileName);
//            BufferedReader br = new BufferedReader(fr);
//            String line = br.readLine();
//            ArrayList<String> ids = new ArrayList<>();
//
//            int largesttNumericPart = 0;
//            while ((line = br.readLine()) != null){
//                String[] datarow = line.split(",");
//                ids.add(datarow[0]);
//            }
//            br.close();
//            String prefix = ids.get(0).substring(0,1);
//            for (String id:ids){
//                if(id.startsWith(prefix)){
//                    String numericPart = id.substring(prefix.length());
//                    int numericValue = Integer.parseInt(numericPart);
//                    if(numericValue > largesttNumericPart){
//                        largesttNumericPart = numericValue;
//                    }
//                }
//            }
//
//            String newNumericPart = String.format("%03d",largesttNumericPart+1);
//            return prefix+ newNumericPart;
//
//
//        } catch (IOException e) {
//            System.out.println("Error occurred while generateID");
//        }return "wrongID";
//    }

    // Define the regular expression pattern to find the prefix
//    private static String findPrefix(String line) {
//
//        String regexPattern = "^(S|SM|I|PR|PO)\\d{3}$\n";
//
//        // Find the first occurrence of the pattern using regex
//        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regexPattern);
//        java.util.regex.Matcher matcher = pattern.matcher(line);
//
//        // Extract and return the matched prefix
//        if (matcher.find()) {
//            return matcher.group();
//        }
//
//        // Return an empty string if no match is found (or handle as appropriate)
//        return "";
//    }
}

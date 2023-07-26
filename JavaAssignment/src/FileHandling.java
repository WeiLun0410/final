import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

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
            if (data.length == columnNumber()) {
//                String[] myArr = Arrays.stream(data).toArray(String[]::new);
                String[] myArr = new String[data.length];
                for (int i = 0; i < myArr.length; i++) {
                    myArr[i] = data[i].trim();
                }
                pw.println(String.join(",", myArr));
                pw.close();
            } else {
                throw new IOException();
            }
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

    public void deleteRow(String colName, String data) {
        try {
            int colIndex = getHeader().indexOf(colName);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            File tempFile = new File("Temp.txt");
            FileWriter fw = new FileWriter(tempFile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pr = new PrintWriter(bw);
            String line = "";
            int deletedRowNum = 0;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[colIndex].equals(data)) {
                    found = true;
                    deletedRowNum++;
                    continue;
                }
                pr.println(line);
            }
            pr.close();
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
//            - Used to identify the price column and add RM in front
            ListIterator<String> headerIterator = getHeader().listIterator();
            int priceColumnIndex = -1;
            int count = 0;
            while (headerIterator.hasNext()) {
                var element = headerIterator.next();
                if (element.toLowerCase().contains("price")) {
                    priceColumnIndex = count;
                    break;
                } else {
                    count++;
                }
            }
//            - Start Printing Row By Row in the text file
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] dataRow = line.split(",");
//            - Printing Heading in the text file
            System.out.println(ConsoleTextDisplay.equalSignDivider(16));
            for (String data : dataRow) {
                System.out.print(ConsoleTextDisplay.alignMiddle(data, 16));
            }
            System.out.println();
            System.out.println(ConsoleTextDisplay.equalSignDivider(16));
//            - Start printing data row by row
            while ((line = br.readLine()) != null) {
                dataRow = line.split(",");
                for (int i = 0; i < dataRow.length; i++) {
                    if (i != priceColumnIndex) {
                        System.out.print(ConsoleTextDisplay.alignMiddle(dataRow[i], 16));
                    } else {
//                        - Add RM at the front if it is a price column
                        System.out.print(ConsoleTextDisplay.alignMiddle("RM " + dataRow[i], 16));
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error occurred while printing data");
        }
    }


}
package com.batch.flux_batch_consumer.processor;
import com.batch.flux_batch_consumer.model.SuviElimentation;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class FileItemProcessor implements ItemProcessor<SuviElimentation, SuviElimentation> {

//    private Set<String> recordIds = new HashSet<>();
//    private int actualLineCount = 0;
//    private int duplicateCount = 0;

    @Override
    public SuviElimentation process(SuviElimentation item) throws Exception {
//        actualLineCount++;

//        if (item.getLineType().equals("T")) {
//            // Validate total number of lines
//            int totalLines = Integer.parseInt(item.getLine().substring(1));
//            if (totalLines != actualLineCount) {
//                throw new Exception("Line count mismatch in tail.");
//            }
//        } else {
            // Check for duplicate recordId
//            if (recordIds.contains(item.getRecordId())) {
//                duplicateCount++;
//            } else {
//                recordIds.add(item.getRecordId());
//            }
//        }

        return item;
    }
}



/********************* approach 1 **************************/
//public class FileItemProcessor implements ItemProcessor<SuviElimentationDetails, SuviElimentationDetails> {
//
//    // Static variables to keep track of processed lines
//    private Set<String> uniqueLines = new HashSet<>();
//    private boolean headerFound = false;
//    private boolean tailFound = false;
//    private int lineCount = 0;
//    private int duplicateCount = 0;
//
//    @Override
//    public SuviElimentationDetails process(SuviElimentationDetails item) throws Exception {
//
//        // Check if header exists
//        if (item.getHeader() != null && item.getHeader().startsWith("H")) {
//            headerFound = true;
//
//            // Set the Flux Name from the header (XMS SRR)
//            item.setFluxName(item.getHeader().substring(item.getHeader().length() - 6));
//        } else {
//            throw new Exception("Header missing. Rejecting the file.");
//        }
//
//        // Process each line in FluxData
//        List<FluxData> fluxDataList = item.getFluxDataList();
//
//        for (FluxData fluxData : fluxDataList) {
//            String lineType = fluxData.getLineType().name();
//
//            // Process only the lines starting with 'M' (Data lines)
//            if ("M".equals(lineType)) {
//                String[] fields = extractFieldsFromLine(fluxData);  // Assumed method for extracting fields
//                String recordId = fields[1];  // Second column for duplicate check
//
//                if (!uniqueLines.add(recordId)) {
//                    // Line is a duplicate
//                    duplicateCount++;
//                    fluxData.setRecordId(recordId);  // Set unique identifier for record
//                }
//
//                // Map the remaining fields to FluxData object
//                fluxData.setCustomerId(fields[2]);
//                fluxData.setClassificationCode(fields[3]);
//                fluxData.setCustomerCode(fields[4]);
//                fluxData.setLineCode(fields[5]);
//                fluxData.setFileDate(fields[6]);
//                fluxData.setAmount(fields[7]);
//                fluxData.setRegionType(fields[8]);
//                fluxData.setCode1(fields[9]);
//                // Additional fields mapping here...
//
//                lineCount++;  // Increment the processed line count
//            }
//        }
//
//        // Check for tail line
//        if (item.getTail() != null && item.getTail().startsWith("T")) {
//            tailFound = true;
//
//            // Validate line count with tail
//            int tailLineCount = Integer.parseInt(item.getTail().substring(1));
//            if (tailLineCount != lineCount) {
//                throw new Exception("Line count mismatch in tail. Rejecting the file.");
//            }
//
//        } else {
//            throw new Exception("Tail missing. Rejecting the file.");
//        }
//
//        // Set additional details for SuviElimentationDetails object
//        item.setCreatedAt(Instant.now());
//        item.setUpdatedAt(Instant.now());
//        item.setProcessedTime(Instant.now());
//
//        SuviElimentation suviElimentation = new SuviElimentation();
//
//        return item;
//    }
//
//    // Helper method to extract fields from a flux line
//    private String[] extractFieldsFromLine(FluxData fluxData) {
//        // Assuming the line is stored as some string within the FluxData object
//        String line = fluxData.getRecordId();  // Assuming recordId holds the raw line
//        return line.split(";");
//    }
//}


/***************** appraoch 2 ******************/

//
//
//import com.batch.flux_batch_consumer.model.FluxData;
//import org.springframework.batch.item.ItemProcessor;
//import com.batch.flux_batch_consumer.model.SuviElimentationDetails;
//
//import java.io.Flushable;
//import java.time.Instant;
//import java.util.HashSet;
//import java.util.Set;
//
//public class FileItemProcessor implements ItemProcessor<SuviElimentationDetails, SuviElimentationDetails> {
//
//    @Override
//    public SuviElimentationDetails process(String line) throws Exception {
//
//        private Set<String> uniqueLines = new HashSet<>(); // To track duplicates
//        private boolean headerFound = false;
//        private boolean tailFound = false;
//        private int lineCount = 0;
//        private int duplicateCount = 0;
//
//        @Override
//        public SuviElimentationDetails process(String line) throws Exception {
//            SuviElimentationDetails details = new SuviElimentationDetails();
//            lineCount++;
//
//            // Check for header
//            if (line.startsWith("H")) {
//                headerFound = true;
//                details.setHeader(line);
//
//                // Extract Flux name from header (6 characters from position 20)
//                String fluxName = line.substring(20, 26);
//                details.setFluxName(fluxName);
//
//            } else if (line.startsWith("T")) {
//                // Check for tail
//                tailFound = true;
//                details.setTail(line);
//
//                // Extract total lines count from tail (after 'T')
//                int expectedLineCount = Integer.parseInt(line.substring(1));
//                if (expectedLineCount != lineCount - 2) { // Exclude header and tail in line count
//                    throw new Exception("Line count mismatch. Expected: " + expectedLineCount + " Actual: " + (lineCount - 2));
//                }
//
//            } else if (line.startsWith("M")) {
//                // Process the main records
//                String[] columns = line.split(";");
//
//                // Check for duplicate records by comparing the 2nd column
//                String recordId = columns[1];
//                if (uniqueLines.contains(recordId)) {
//                    duplicateCount++;
//                } else {
//                    uniqueLines.add(recordId);
//                }
//
//                // Set other fields
//                details.setBatchName(columns[0]);
//                details.setProcessedTime(java.time.Instant.now()); // Add processed time
//            } else {
//                throw new Exception("Invalid line format: " + line);
//            }
//
//            // Handle end of file processing
//            if (headerFound && tailFound) {
//                // Check if file is valid or invalid based on duplicates, etc.
//                if (duplicateCount > 0) {
//                    // Mark status as INVALID in SuviElimentation if duplicates are found
//                    // You may need to pass this information back in some form, either via a flag or status update
//                }
//                // Return the processed details to writer
//                return details;
//            }
//
//            return null;
//        }
//
//
//        /*********/
////        SuviElimentationDetails suviElimentationDetails = new SuviElimentationDetails();
////        // Initialize a static variable to keep track of unique lines
////        Set<String> uniqueLines = new HashSet<>();
////        boolean headerFound = false;
////        boolean tailFound = false;
////        int lineCount = 0;
////        int duplicateCount = 0;
////
////        // Check for header
////        if (line.getHeader().startsWith("H")) {
////
////            headerFound = true;
////            //  Check for tail
////            if (line.getTail().startsWith("T")) {
////                tailFound = true;
////
////                // Extract Flux name
////                line.setFluxName(line.getHeader().substring(line.getHeader().length() - 6));
////
////                // Process other lines starting with M
////
////
////                // count the number of lines
////                // Check line count
//////                int tailLineCount = Integer.parseInt(item.getTail().substring(1));
//////                if (tailLineCount != lineCount) {
//////                    throw new Exception("Line count mismatch in tail.");
//////                }
////
////
////            } else {
////                // reject the file
////            }
////        }else{
////            // reject the file
////        }
////
////        // save the result in database
////
////
////        }
//
//
//
//        /*****************************/
////        // Check for tail
////        else if (item.getLine().startsWith("T")) {
////            tailFound = true;
////            // Check line count
////            int tailLineCount = Integer.parseInt(item.getLine().substring(1));
////            if (tailLineCount != lineCount) {
////                throw new Exception("Line count mismatch in tail.");
////            }
////        }
////
////        // Process other lines
////        else {
////            // Check for duplicate lines
////            if (uniqueLines.contains(item.getLine())) {
////                duplicateCount++;
////                item.setFileStatus("INVALID");
////            } else {
////                uniqueLines.add(item.getLine());
////            }
////        }
////
////        // Check if header and tail were found
////        if (!headerFound) {
////            throw new Exception("Header not found.");
////        }
////        if (!tailFound) {
////            throw new Exception("Tail not found.");
////        }
////
////        // Set processed time
////        item.setProcessedTime(Instant.now());
////
////        // Save to database
////        saveToDatabase(item);
////
////        return item;
//    }
//
//}
//
//
////import java.io.BufferedReader;
////        import java.io.FileReader;
////        import java.util.*;
////        import java.time.Instant;
////
////public class FluxFileProcessor {
////
////    public static void main(String[] args) throws Exception {
////        String filePath = "path_to_your_flux_file.txt"; // Update with actual file path
////        processFluxFile(filePath);
////    }
////
////    public static void processFluxFile(String filePath) throws Exception {
////        BufferedReader reader = new BufferedReader(new FileReader(filePath));
////        String line;
////        int totalLineCount = 0;
////        int validRecords = 0;
////        int duplicateRecords = 0;
////        String fluxName = null;
////        Map<String, Boolean> uniqueIdMap = new HashMap<>();
////        boolean headerFound = false;
////        boolean tailFound = false;
////        String tailLine = null;
////
////        // Start reading the file line by line
////        while ((line = reader.readLine()) != null) {
////            totalLineCount++;
////
////            // Header check (condition 1)
////            if (line.startsWith("H")) {
////                headerFound = true;
////                // Extract flux name from position 20 to 25
////                fluxName = line.substring(19, 25).trim();
////                System.out.println("Flux name: " + fluxName);
////            }
////
////            // Tail check (condition 2)
////            else if (line.startsWith("T")) {
////                tailFound = true;
////                tailLine = line;
////            }
////
////            // Record processing (middle records starting with "M")
////            else if (line.startsWith("M")) {
////                String[] fields = line.split(";");
////                String uniqueId = fields[1];
////
////                // Check for duplicate records (condition 5)
////                if (uniqueIdMap.containsKey(uniqueId)) {
////                    duplicateRecords++;
////                } else {
////                    uniqueIdMap.put(uniqueId, true);
////                    validRecords++;
////                }
////                // Save the line to the database as a new record
////                saveLineToDatabase(fields);
////            }
////        }
////        reader.close();
////
////        // Check if header and tail exist
////        if (!headerFound) {
////            throw new Exception("File rejected: Header not found.");
////        }
////        if (!tailFound) {
////            throw new Exception("File rejected: Tail not found.");
////        }
////
////        // Condition 4: Tail should contain the total number of lines
////        int lineCountFromTail = Integer.parseInt(tailLine.substring(1));
////        if (lineCountFromTail != totalLineCount) {
////            throw new Exception("File rejected: Tail line count mismatch.");
////        }
////
////        // Final file processing status
////        System.out.println("File processing complete.");
////        System.out.println("Valid records: " + validRecords);
////        System.out.println("Duplicate records: " + duplicateRecords);
////        System.out.println("Total records: " + totalLineCount);
////        System.out.println("Status: " + (duplicateRecords > 0 ? "INVALID" : "VALID"));
////
////        // Save file processing summary to the database
////        saveFileSummary(fluxName, totalLineCount, validRecords, duplicateRecords, duplicateRecords > 0 ? "INVALID" : "VALID");
////    }
////
////    // Simulated database saving methods
////    private static void saveLineToDatabase(String[] fields) {
////        // Logic to save line data to PostgreSQL
////        System.out.println("Saving line to database: " + Arrays.toString(fields));
////    }
////
////    private static void saveFileSummary(String fluxName, int totalLineCount, int validRecords, int duplicateRecords, String status) {
////        // Logic to save file summary to PostgreSQL
////        System.out.println("Saving file summary to database:");
////        System.out.println("Flux Name: " + fluxName);
////        System.out.println("Total Records: " + totalLineCount);
////        System.out.println("Valid Records: " + validRecords);
////        System.out.println("Duplicate Records: " + duplicateRecords);
////        System.out.println("Status: " + status);
////        System.out.println("Processed At: " + Instant.now());
////    }
////}

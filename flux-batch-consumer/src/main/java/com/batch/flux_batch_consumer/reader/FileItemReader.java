package com.batch.flux_batch_consumer.reader;

import com.batch.flux_batch_consumer.enumClasses.LineType;
import com.batch.flux_batch_consumer.enumClasses.Status;
import com.batch.flux_batch_consumer.model.SuviElimentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemReader;
import com.batch.flux_batch_consumer.model.SuviElimentationDetails;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FileItemReader extends StepExecutionListenerSupport implements ItemReader<SuviElimentation> {

    public BufferedReader reader;
    private boolean fileProcessed = false; // Flag to mark file processing completion

    public static Logger logger = LoggerFactory.getLogger(FileItemReader.class);

    private static String currentFilePath;

    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    public FileItemReader(String filePath) throws IOException {
        currentFilePath = filePath;
        this.reader = new BufferedReader(new FileReader(filePath));
    }

    @Override
    public SuviElimentation read() throws Exception {
        if (fileProcessed) {
            return null; // Stop further reading after file has been processed
        }

        SuviElimentation suviElimentation = new SuviElimentation();
        // Set the checksum in the SuviElimentation entity
        if(stepExecution != null) {
            suviElimentation.setShsum(stepExecution.getJobExecution().getExecutionContext().getString("checksum"));
        }
        suviElimentation.setDuplicateRecords(0L);
        List<SuviElimentationDetails> details = new ArrayList<>();
        Long actualNumberOfRecords = 0L;

        List<String> recordIdList = new ArrayList<>();
        String firstLine = reader.readLine();

        if (firstLine != null && firstLine.startsWith("H")) {
            // Method to process header line
            processHeaderLine(firstLine, suviElimentation);
            actualNumberOfRecords++;

            // proceed further for data lines reading and validation
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    actualNumberOfRecords++;
                    processRecordLine(firstLine, line, suviElimentation, details, recordIdList);
                }
            }

            // Final file validation, processing logic and logging
            finalizeProcessing(suviElimentation, actualNumberOfRecords, details);
        } else {
            handleMissingHeader(suviElimentation);
        }
        fileProcessed=true;
        return suviElimentation; // Return null to signal end of file reading
    }

    /**
     * Method to process header line and save into SuviElimentation object
     * @param firstLine
     * @param suviElimentation
     */
    public void processHeaderLine(String firstLine, SuviElimentation suviElimentation) {
        suviElimentation.setFileName(currentFilePath.substring(currentFilePath.length() - 10));
        suviElimentation.setFilePath(currentFilePath);
        suviElimentation.setCreatedAt(Instant.now());
    }

    /**
     * Method to process record line, validate it and save into SuviElimentationDetails object
     * @param firstLine
     * @param line
     * @param suviElimentation
     * @param details
     * @param recordIdList
     */
    public void processRecordLine(String firstLine, String line, SuviElimentation suviElimentation, List<SuviElimentationDetails> details, List<String> recordIdList) {
        SuviElimentationDetails suviElimentationDetail = new SuviElimentationDetails();

        if (!line.startsWith("T")) {
            // Process record line and handle fields
            String[] fields = line.split(";");
            suviElimentationDetail.setElimentationId(suviElimentation.getId());
            suviElimentationDetail.setLineType(LineType.M);
            suviElimentationDetail.setRecordId(fields[1]);
            suviElimentationDetail.setFluxName(firstLine.substring(firstLine.length() - 7));

            // Duplicate check
            checkForDuplicates(suviElimentationDetail, recordIdList, suviElimentation);

            // Map the remaining fields
            mapRemainingFields(suviElimentationDetail, fields);

            details.add(suviElimentationDetail);
        }

        // Check if last line is tail or not
        handleMissingTail(line, suviElimentation);
    }

    /**
     * Method to check for duplicate records
     * @param suviElimentationDetail
     * @param recordIdList
     * @param suviElimentation
     */
    public void checkForDuplicates(SuviElimentationDetails suviElimentationDetail, List<String> recordIdList, SuviElimentation suviElimentation) {
        if (recordIdList.contains(suviElimentationDetail.getRecordId())) {
            suviElimentation.setDuplicateRecords(suviElimentation.getDuplicateRecords() + 1);
        } else {
            recordIdList.add(suviElimentationDetail.getRecordId());
        }
    }

    /**
     * Method to map the remaining fields of the record line to suviElimentationDetail object
     * @param suviElimentationDetail
     * @param fields
     */
    public void mapRemainingFields(SuviElimentationDetails suviElimentationDetail, String[] fields) {
        // Map the remaining fields here
        suviElimentationDetail.setCustomerId(fields[2]);
        suviElimentationDetail.setClassificationCode(fields[3]);
        suviElimentationDetail.setCustomerCode(fields[4]);
        suviElimentationDetail.setLineCode(fields[5]);
        suviElimentationDetail.setFileDate(fields[6]);
        suviElimentationDetail.setAmount(fields[7]);
        suviElimentationDetail.setRegionType(fields[8]);
        suviElimentationDetail.setCode1(fields[9]);
        suviElimentationDetail.setCode2(fields[10]);
        suviElimentationDetail.setCode3(fields[11]);
        if (!fields[12].trim().isEmpty()) {
            suviElimentationDetail.setNum1(Integer.parseInt(fields[12]));
        }
        if (!fields[13].trim().isEmpty()) {
            suviElimentationDetail.setNum2(Integer.parseInt(fields[13]));
        }
        if (!fields[14].trim().isEmpty()) {
            suviElimentationDetail.setNum3(Integer.parseInt(fields[14]));
        }
        if (!fields[15].trim().isEmpty()) {
            suviElimentationDetail.setNum4(Integer.parseInt(fields[15]));
        }
        suviElimentationDetail.setDataType(fields[16]);
        if (!fields[17].trim().isEmpty()) {
            suviElimentationDetail.setDataValue(Integer.parseInt(fields[17]));
        }
        if (!fields[18].trim().isEmpty()) {
            suviElimentationDetail.setDataValue1(Integer.parseInt(fields[18]));
        }
        if (!fields[19].trim().isEmpty()) {
            suviElimentationDetail.setDataValue2(Integer.parseInt(fields[19]));
        }
        if (!fields[20].trim().isEmpty()) {
            suviElimentationDetail.setDataValue3(Integer.parseInt(fields[20]));
        }
        if (!fields[21].trim().isEmpty()) {
            suviElimentationDetail.setDataValue4(Integer.parseInt(fields[21]));
        }
        if (!fields[22].trim().isEmpty()) {
            suviElimentationDetail.setDataValue5(Integer.parseInt(fields[22]));
        }
        suviElimentationDetail.setRechargeType(fields[23]);
        suviElimentationDetail.setRechargeCode(Integer.parseInt(fields[24]));
        suviElimentationDetail.setRechargeId(fields[25]);
        suviElimentationDetail.setCode4(fields[26]);
        if (!fields[27].trim().isEmpty()) {
            suviElimentationDetail.setNum5(Integer.parseInt(fields[27]));
        }
        if (!fields[28].trim().isEmpty()) {
            suviElimentationDetail.setRechargeValue(Long.parseLong(fields[28]));
        }
        if (!fields[29].trim().isEmpty()) {
            suviElimentationDetail.setCode5(Integer.parseInt(fields[29]));
        }
    }

    /**
     * Method to finalize the processing of the file and updating the status of the file
     * @param suviElimentation
     * @param actualNumberOfRecords
     * @param details
     */
    public void finalizeProcessing(SuviElimentation suviElimentation, Long actualNumberOfRecords, List<SuviElimentationDetails> details) {
        if (suviElimentation.getDuplicateRecords() > 0L) {
            suviElimentation.setStatus(Status.DUPLICATE);
            suviElimentation.setStatusDescription("Duplicate records found");
            logger.warn("Duplicate records found");
            suviElimentation.setDuplicateRecords(suviElimentation.getDuplicateRecords());
        } else {
            suviElimentation.setStatus(Status.IN_PROGRESS);
            suviElimentation.setStatusDescription("File is progressing, no duplicate records till now");
            suviElimentation.setSuviElimentationDetailsList(details);
            logger.info("File is progressing");
        }

        if (actualNumberOfRecords.equals(suviElimentation.getTotalRecords())) {
            suviElimentation.setTotalRecords(actualNumberOfRecords);
            suviElimentation.setCorrectRecords(actualNumberOfRecords - suviElimentation.getDuplicateRecords());
            suviElimentation.setStatus(Status.SUCCESS);
            suviElimentation.setStatusDescription("File processed successfully");
            logger.info("File processed successfully");
            suviElimentation.setSuviElimentationDetailsList(details);

            // Mark the file as fully processed
            fileProcessed = true;
        } else {
            suviElimentation.setStatus(Status.INVALID);
            suviElimentation.setStatusDescription("Total number of records in file does not match the expected number of records");
            logger.error("Total number of records in file does not match the expected number of records");
            closeReader();
            fileProcessed = true;
        }
    }

    /**
     * Method to handle missing header line and reject the file
     * @param suviElimentation
     */
    public void handleMissingHeader(SuviElimentation suviElimentation) {
        suviElimentation.setStatus(Status.INVALID);
        suviElimentation.setStatusDescription("Header line is missing");
        logger.error("Header line is missing");
        closeReader();
        fileProcessed = true;
    }

    /**
     * Method to handle missing tail line and reject the file
     * @param line
     * @param suviElimentation
     */
    public void handleMissingTail(String line, SuviElimentation suviElimentation) {
        // Check if this is the last line and it does not start with "T"
        try {
            // Mark the current position before checking the next line
            reader.mark(1024);
            // if this is the last line
            if (reader.readLine() == null) {
                // if last line starts with "T take the count of total records expected
                if (line.startsWith("T")) {
                    suviElimentation.setTotalRecords((long) Integer.parseInt(line.substring(1)));
                }
                // if last line is not starting with "T" reject the file
                else{
                    suviElimentation.setStatus(Status.INVALID);
                    suviElimentation.setStatusDescription("Last line does not start with T");
                    logger.error("Last line does not start with T");
                    closeReader();
                    fileProcessed = true;
                }

            }
            else{
                // Reset the reader to the marked position
                reader.reset();
            }
        } catch (IOException e) {
            logger.error("Error reading line", e);
        }
    }

    /**
     * Method to close the reader
     */
    public void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            logger.error("Error closing reader", e);
        }
    }

}





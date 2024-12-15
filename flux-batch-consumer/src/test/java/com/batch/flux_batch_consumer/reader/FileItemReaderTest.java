package com.batch.flux_batch_consumer.reader;

import com.batch.flux_batch_consumer.enumClasses.Status;
import com.batch.flux_batch_consumer.model.SuviElimentation;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FileItemReaderTest {

    @Mock
    private StepExecution stepExecution;

    @Mock
    private JobExecution jobExecution;


    @Mock
    private BufferedReader reader;

    @Mock
    private FileItemReader fileItemReader;

    @Mock
    private Logger logger;

    private static final String FILE_PATH = "C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2227.2301121637";

    @Test
    void testRead_HeaderAndDataLinesOK() throws Exception {
        // Mock file lines to simulate processing
        String headerLine = "HheaderData";
        String recordLine1 = "M;230112RX210474297;MC0050;08;647108506191511;692357001;20230112;151537;T21;D3;LA;D3;01;158;0;0;SRR;0;1;1;01;01;02;GA;5;36180;F;;;99";
        String tailLine = "T3";

        // Mock BufferedReader behavior to return lines, then null after the last line
        when(reader.readLine())
                .thenReturn(headerLine, recordLine1, tailLine, null);

        // Instantiate the reader
        fileItemReader = new FileItemReader("C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2228.2301121637");  // Provide a mock file path
        fileItemReader.reader = reader;  // Inject the mocked BufferedReader

        // First call should return the SuviElimentation object
        SuviElimentation result = fileItemReader.read();

        // Assert that the object is correctly populated
        assertNotNull(result);
        assertEquals("2301121637", result.getFileName());

        // Verify that the reader processed all lines
        verify(reader, times(4)).readLine();

        // Second call should return null because file has been processed
        SuviElimentation result2 = fileItemReader.read();
        assertNull(result2);  // Ensure the second read returns null
    }

//    @Test
//    void testRead_HeaderAndDataLines() throws Exception {
//        // Mock file lines
////        String headerLine = "H;headerData";
////        String recordLine1 = "M;record1;field2;field3;field4;field5;field6;field7";
////        String tailLine = "T1";
//
//        // Mock BufferedReader behavior
////        when(reader.readLine()).thenReturn(headerLine, recordLine1, tailLine, null);
////        Mockito.when(jobExecution.getExecutionContext().getString(ArgumentMatchers.any())).thenReturn("testChecksum");
////        Mockito.when(jobExecution.getExecutionContext().getString("checksum")).thenReturn("testChecksum");
////        Mockito.when(stepExecution.getJobExecution()).thenReturn(jobExecution);
//
//
//        fileItemReader = new FileItemReader("C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2227.2301121637");  // Provide the required file path
//        fileItemReader.reader = reader;  // Inject the mocked BufferedReader
//
//
//        SuviElimentation result = fileItemReader.read();
//
//        assertNotNull(result);
////        assertEquals("testChecksum", result.getShsum());
//        assertEquals(FILE_PATH.substring(FILE_PATH.length() - 10), result.getFileName());
//        assertEquals(Instant.now().toEpochMilli(), result.getCreatedAt().toEpochMilli(), 1000); // Assert with margin for time difference
//        assertEquals(Status.SUCCESS, result.getStatus());
//        verify(reader).readLine(); // Ensure reader was called
//    }
//
//    @Test
//    void testRead_NoHeaderLine() throws Exception {
//        // No header line, simulating missing header
//        when(reader.readLine()).thenReturn(null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        assertNotNull(result);
//        assertEquals(Status.INVALID, result.getStatus());
//        assertEquals("Header line is missing", result.getStatusDescription());
//    }
//
//    @Test
//    void testRead_LastLineWithoutTail() throws Exception {
//        // Mock file with no tail line
//        String headerLine = "H;headerData";
//        String recordLine = "M;record1;field2;field3;field4;field5;field6;field7";
//
//        when(reader.readLine()).thenReturn(headerLine, recordLine, null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        assertNotNull(result);
//        assertEquals(Status.INVALID, result.getStatus());
//        assertEquals("Last line does not start with T", result.getStatusDescription());
//    }
//
//    @Test
//    void testRead_DuplicateRecords() throws Exception {
//        String headerLine = "H;headerData";
//        String recordLine1 = "M;record1;field2;field3;field4;field5;field6;field7";
//        String recordLine2 = "M;record1;field2;field3;field4;field5;field6;field7"; // Duplicate
//
//        when(reader.readLine()).thenReturn(headerLine, recordLine1, recordLine2, "T2", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        assertNotNull(result);
//        assertEquals(1L, result.getDuplicateRecords());
//        assertEquals(Status.DUPLICATE, result.getStatus());
//        assertEquals("Duplicate records found", result.getStatusDescription());
//    }
//
//    @Test
//    void testCloseReader() throws IOException {
//        doNothing().when(reader).close();
//        fileItemReader.closeReader();
//        verify(reader, times(1)).close();
//    }
}

//
//import com.batch.flux_batch_consumer.enumClasses.Status;
//import com.batch.flux_batch_consumer.model.SuviElimentation;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//
//import static org.mockito.Mockito.times;
//
//
//@SpringBootTest
//public class FileItemReaderTest {
//    @Mock
//    private BufferedReader reader;
//
//    @InjectMocks
//    private FileItemReader fileItemReader;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        MockitoAnnotations.openMocks(this);
////        fileItemReader = new FileItemReader("C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2227.2301121637");
////        fileItemReader.setReade(reader);
//    }
//
//    @Test
//    void testReadFileSuccessfully() throws Exception {
////        fileItemReader = new FileItemReader("C:\\Users\\anuverma\\Downloads\\NA076008.0684.SRRXMS.2227.2301121637");
////        Mockito.when(fileItemReader.read()).thenReturn("H1234567", "M1234567", "T0000002", null);
//        Mockito.when(reader.readLine()).thenReturn("H1234567", "M1234567", "T0000002", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(Status.SUCCESS, result.getStatus());
////        Assertions.assertEquals(reader, times(4)).readLine();
//    }
//
//    @Test
//    void testReadFileMissingHeader() throws Exception {
//        Mockito.when(reader.readLine()).thenReturn("M1234567", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(Status.INVALID, result.getStatus());
////        Assertions.assertEquals(reader, times(2)).readLine();
//    }
//
//    @Test
//    void testReadFileMissingTail() throws Exception {
//        Mockito.when(reader.readLine()).thenReturn("H1234567", "M1234567", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(Status.INVALID, result.getStatus());
////        Assertions.assertEquals(reader, times(3)).readLine();
//    }
//
//    // test method to check the duplicate records
//    @Test
//    void testReadFileDuplicateRecords() throws Exception {
//        Mockito.when(reader.readLine()).thenReturn("H1234567", "M1234567", "T0000002", "H1234567", "M1234567", "T0000002", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(Status.DUPLICATE, result.getStatus());
//    }
//
//    // test method for the case when last line is not starting with T
//    @Test
//    void testInvalidTailEndKO() throws Exception {
//        Mockito.when(reader.readLine()).thenReturn("H1234567", "M1234567", "T0000002", "H1234567", "M1234567", "M0000002", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(Status.INVALID, result.getStatus());
//    }
//
//    //test method to check if actual no of records matches total no of records
//    @Test
//    void testTotalRecordsOK() throws Exception {
//        Mockito.when(reader.readLine()).thenReturn("H1234567", "M1234567", "T0000003", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(3, result.getTotalRecords());
//    }
//
//    @Test
//    void testTotalRecordsKO() throws Exception {
//        Mockito.when(reader.readLine()).thenReturn("H1234567", "M1234567", "T0000002", null);
//
//        SuviElimentation result = fileItemReader.read();
//
//        Assertions.assertNotNull(result);
//        Assertions.assertNotEquals(3, result.getTotalRecords());
//    }
//
//}
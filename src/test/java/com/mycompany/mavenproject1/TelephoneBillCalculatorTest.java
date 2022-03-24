/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Epakf
 */
public class TelephoneBillCalculatorTest {
    
    public TelephoneBillCalculatorTest() {
    }

    @Test
    public void testCalculate() {
        System.out.println("calculate");
        String phoneLog = 
                """
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
                420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01
                420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57""";    
        
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        BigDecimal expResult = new BigDecimal(19.6);
        BigDecimal result = instance.calculate(phoneLog);
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetPhoneCallStart() {
        System.out.println("GetPhoneCallStart");
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        String expResult = "13-01-2020 18:10:15";
        String result = instance.GetPhoneCallStart(phoneLog);
        assertEquals(expResult, result);
        
        
    }

    @Test
    public void testGetPhoneCallEnd() {
        System.out.println("GetPhoneCallEnd");
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        String expResult = "13-01-2020 18:12:57";
        String result = instance.GetPhoneCallEnd(phoneLog);
        assertEquals(expResult, result);
        
        
    }

    @Test
    public void testGetPhoneCallNumber() {
        System.out.println("GetPhoneCallNumber");
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        String expResult = "420774577453";
        String result = instance.GetPhoneCallNumber(phoneLog);
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetPhoneCallLength() {
        System.out.println("GetPhoneCallLength");
        String phoneCallStart = "13-01-2020 18:10:15";
        String phoneCallEnd = "13-01-2020 18:14:57";
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        Long expResult = 4L;
        Long result = instance.GetPhoneCallLength(phoneCallStart, phoneCallEnd);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetArrayOfEntries() {
        System.out.println("GetArrayOfEntries");
        String phoneLog = 
                """
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n
                420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01\n
                420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01\n
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57\n
                420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57""";  
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        String[] expResult = {
                "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57",
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01",
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01",
                "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57",
                "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57"};
        String[] result = instance.GetArrayOfEntries(phoneLog);
        assertArrayEquals(expResult, result);
        
    }

    @Test
    public void testGetPhoneCallStartDateTime() {
        System.out.println("GetPhoneCallStartDateTime");
        String phoneCall = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57";
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        String callStart = phoneCall.split(",")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime expResult = LocalDateTime.parse(callStart, formatter);
        LocalDateTime result = instance.GetPhoneCallStartDateTime(phoneCall);
        assertEquals(expResult, result);
    }

//    @Test
//    public void testGetPhoneCallHour() {
//        System.out.println("GetPhoneCallHour");
//        String phoneCallStart = "13-01-2020 18:10:15";
//        TelephoneBillCalculator instance = new TelephoneBillCalculator();
//        int expResult = 18;
//        int result = instance.GetPhoneCallHour(phoneCallStart);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    @Test
    public void testGetAllPhoneNumbers() {
        System.out.println("GetAllPhoneNumbers");
        String[] entries = {
                "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57",
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01",
                "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:01",
                "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57",
                "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57"};
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        String[] expResult = {"420774577453",
        "420776562353",
        "420776562353",
        "420774577453",
        "420774577453"};
        String[] result = instance.GetAllPhoneNumbers(entries);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testFindMostFrequentNumber() {
        System.out.println("FindMostFrequentNumber");
        String[] numbers = {"420774577453", "420774577453", "421774577453", "421774577456", 
            "420772577456", "421776575413", "421774577456", "421774577456"};
        TelephoneBillCalculator instance = new TelephoneBillCalculator();
        Long expResult = 421774577456L;
        Long result = instance.FindMostFrequentNumber(numbers);
        assertEquals(expResult, result);
    }

    
    
}

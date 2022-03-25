package com.mycompany.mavenproject1;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
/**
 *
 * @author Epakf
 */
public class TelephoneBillCalculator implements ITelephoneBillCalculator {

    private final double DISCOUNT_VALUE = 0.2;
    private double rate;
    private int discountedMinutes = 0;
    private double totalPrice = 0;
    private int numberWithMostCalls;
    
    @Override
    public BigDecimal calculate(String phoneLog) {    
        String[] calls = GetArrayOfEntries(phoneLog);
        String[] allPhoneNumbers = GetAllPhoneNumbers(calls);
        Long mostCalledNumber = FindMostFrequentNumber(allPhoneNumbers);
        
        for(String call : calls) {
            LocalDateTime callStart = GetPhoneCallStartDateTime(call);
            Long callLength = GetPhoneCallLength(GetPhoneCallStart(call), GetPhoneCallEnd(call));
        
            if(mostCalledNumber.toString().equals(GetPhoneCallNumber(call))){
                continue;
            }
            for(int i = 0; i <= callLength; i++) {
                
                int currentHour = callStart.getHour();
                if(i > 4) {
                    discountedMinutes += 1;
                }
                if(currentHour >= 8 && currentHour <= 16 ) {
                    rate = 1;
                }
                else {
                    rate = 0.5;
                }
                totalPrice += 1 * rate;

                callStart = callStart.plusMinutes(1L);
            }
        }
        EvaluateDiscount();    
        return new BigDecimal(totalPrice);
    }
    
    
    public String[] GetArrayOfEntries(String phoneLog) {
        return phoneLog.split("\\n+");
    }
    
    
    private void EvaluateDiscount() {
        totalPrice -= discountedMinutes * DISCOUNT_VALUE;
    }
    
    
    public String GetPhoneCallStart(String phoneCall) {
        return phoneCall.split(",")[1];
    }
    
    
    public LocalDateTime GetPhoneCallStartDateTime(String phoneCall) {
        String callStart = phoneCall.split(",")[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(callStart, formatter);
    }
    
    
    public String GetPhoneCallEnd(String phoneCall) {
        return phoneCall.split(",")[2];
    }
    
    
    public String GetPhoneCallNumber(String phoneCall) {
        return phoneCall.split(",")[0];
    }
    
    
//    public int GetPhoneCallHour(String phoneCallStart) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
//        LocalDateTime currentHour = LocalDateTime.parse(phoneCallStart, formatter);
//        String currentHourS = currentHour.toString();
//        
//        return Integer.parseInt(currentHourS);
//    }
    
    
    public Long GetPhoneCallLength(String phoneCallStart, String phoneCallEnd) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(phoneCallStart, formatter);
        LocalDateTime end = LocalDateTime.parse(phoneCallEnd, formatter);
        
        Duration duration = Duration.between(start, end);
        return duration.toMinutes();
    }
    
    
    public String[] GetAllPhoneNumbers(String[] entries) {
        String[] numbers = new String[entries.length];
        
        for(int i = 0; i < entries.length; i++) {
            numbers[i] = GetPhoneCallNumber(entries[i]);
        } 
        return numbers;
    }
    
    
    public Long FindMostFrequentNumber(String[] numbers) {
        // Convert the String array to Int array first
        Long[] iNumbers = new Long[numbers.length];
        for(int i = 0; i < numbers.length; i++) {
            iNumbers[i] = Long.parseLong(numbers[i]);
        }
        // Map our unique phoneNumbers to HashMap as keys
        HashMap<Long, Integer> numberFrequency = new HashMap<>();
        // Key is the number, value is starting as 0, since we are going to iterate over iNumbers anyway
        for(var number : iNumbers) {
            numberFrequency.put(number, 0);
        }
        //Now we need to interate over iNumbers Array, foreach number in iNumber, 
        //if numberFrequency.get(number) is Equal to number, we += 1 the value of numberFrequency[key]
        
        for(var number : iNumbers) {
            for(var uniqueNumber : numberFrequency.entrySet()) {
                if(number.longValue() == uniqueNumber.getKey().longValue()) {
                    Long key = uniqueNumber.getKey();
                    numberFrequency.put(key, numberFrequency.get(key) + 1);
                }
            }
        }
        List<Long> mostCalledNumbers = new ArrayList<>();
        // Now we need to iterate over the Map
        int maxValueInMap=(Collections.max(numberFrequency.values()));
        
        for (Entry<Long, Integer> entry : numberFrequency.entrySet()) {  
            if (entry.getValue()== maxValueInMap) {
                mostCalledNumbers.add(entry.getKey());     
            }
        }
        mostCalledNumbers.sort(null);
        Collections.reverse(mostCalledNumbers);      
        return mostCalledNumbers.get(0);
    }
}

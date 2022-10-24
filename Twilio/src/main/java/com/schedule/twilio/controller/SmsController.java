package com.schedule.twilio.controller;

import com.schedule.twilio.dto.MessageAndTime;
import com.schedule.twilio.dto.NumberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@Component
public class SmsController {

    List<NumberDTO>numberDTOS;
    //this variable check sendSMS method are already execute or no
    boolean isSend=false;

    int setMonth;
    int setDay;
    int setHour;
    int setMinute;
    String message;




    @PostMapping("/saveNumberList")
    public boolean setMobileNumbersToArray(@RequestBody List<NumberDTO>numbers){
        for (NumberDTO obj:numbers){
            System.out.println(obj.getNumber());
        }
        numberDTOS=numbers;

        if(numberDTOS.size()>0){
            return true;
        }
        return false;
    }
    @PostMapping("/saveTimeAndMessage")
    public void setTimeAndMessage(@RequestBody MessageAndTime messageAndTime){

        this.setMonth=messageAndTime.getMonth()+1;
        this.setDay=messageAndTime.getDay();
        this.setHour=messageAndTime.getHour();
        this.setMinute=messageAndTime.getMinute();
        this.message=messageAndTime.getMessage();
    }


    public ResponseEntity<String> sendSMS() {
        for (NumberDTO number:numberDTOS){
            try {
                Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));
                Message.creator(new PhoneNumber("\""+number.getNumber()+"\""), new PhoneNumber("61482084025"), message).create();
            }catch (Exception e){
                System.out.println(e);
            }
        }
        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }

    @Scheduled(fixedRate = 5000)
    public void time() throws FileNotFoundException {

        LocalDate date = LocalDate.now();
        int month = date.getMonth().getValue();
        int day=date.getDayOfMonth();

        LocalTime time = LocalTime.now();
        int hour=time.getHour();
        int minute=time.getMinute();

        System.out.println("======================= number list =======================");
        try {
            for (NumberDTO obj:numberDTOS){
                System.out.println(obj.getNumber());
            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("================= front end details ============================");
        System.out.println("month : "+setMonth);
        System.out.println("day : "+setDay);
        System.out.println("hour : "+setHour);
        System.out.println("minute : "+setMinute);



        System.out.println("================= back end details ============================");
        System.out.println("month : "+month);
        System.out.println("day : "+day);
        System.out.println("hour : "+hour);
        System.out.println("minute : "+minute);
        System.out.println("message  :"+ message);


        if(setMonth==month && setDay == day){
            if(setHour==hour && setMinute==minute){
                if(isSend==false){
                    System.out.println("message send now");

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date dates = new Date();
                    System.out.println(formatter.format(dates));

                    // call method here

                    isSend=true; // set message send already
                }
            }
        }
    }
    //set all variable of clear
    @GetMapping("/clearAll")
    public boolean clearAll(){
        numberDTOS=new ArrayList<>();
        this.setHour=0;
        this.setMinute=0;
        this.setMonth=0;
        this.setDay=0;
        this.isSend=false;
        return true;
    }

}

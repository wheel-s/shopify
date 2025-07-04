package com.toshiro.dreamshops;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Home {

        @RequestMapping("/")
        @ResponseBody
        public String great (){
            System.out.print("done....");
            return "I am done";
        }

    }





package com.example.AWS.controller;


import com.example.AWS.util.S3Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class S3Controller {


    @GetMapping("")
    public String showHomePage(){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(String description, @RequestParam("file")MultipartFile file, Model model){
        System.out.println("Description : "+ description);
        String fileName = file.getOriginalFilename();

        System.out.println("FileName : "+ fileName);

        String message = "";
        try{
            S3Util.uploadFile(fileName, file.getInputStream());
            message = "Your file upload ok!";
        }catch (Exception ex){
            message = "Error upload with issue: "+ ex.getMessage();
        }

        model.addAttribute("message", message);
        return "message";
    }
}

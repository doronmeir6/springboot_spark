package com.spark.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;

import java.util.List;
import java.util.Map;

@RestController
public class WordCountController {

    @Autowired
    WordCountService wordCountService;

    @RequestMapping(method = RequestMethod.GET, path = "/wordcount")
    public String count(@RequestParam(required = true) String path) {
        return wordCountService.getCount(path);
    }


}

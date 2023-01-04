package com.example.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.workshop12.exception.RanNoException;
import com.example.workshop12.models.Generate;

@Controller
@RequestMapping(path = "/rand")
public class GenRandNoController {

    /**
     * Redirect to the generate.html
     * and show the input form
     */
    @GetMapping(path = "/show")
    public String showRandForm(Model model) {
        // Instantiate the generate object
        // bind the noOfRandNo to the text field
        Generate g = new Generate();
        // associate the bind var to the view/page
        model.addAttribute("generateObj", g);
        return "generate";
    }

    @PostMapping(path = "/generate")
    public String postRandNum(@ModelAttribute Generate generate, Model model) {
        this.randomizeNum(model, generate.getNumberVal());
        return "result";
    }

    private void randomizeNum(Model model, int noOfGenerateNo) {
        int maxGenNo = 30;
        String[] imgNumbers = new String[maxGenNo + 1];

        // Validate only accept gt 0 lte 30
        if (noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo) {
            throw new RanNoException();
        }

        // generate all the number images store it
        // to the imgNumbers array
        //this is to convert the numbers to the path name number0.jpg
        for (int i = 0; i < maxGenNo + 1; i++) {
            imgNumbers[i] = "number" + i + ".jpg";
        }

        List<String> selectedImg = new ArrayList<String>();
        Random rnd = new Random();
        //hashset in this way to make sure they are all unique numbers
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        while (uniqueResult.size() < noOfGenerateNo) {
            Integer resultOgfRand = rnd.nextInt(maxGenNo);
            uniqueResult.add(resultOgfRand);
        }

        //iterator is like a looping
        Iterator<Integer> it = uniqueResult.iterator();
        Integer currElem = null;
        while (it.hasNext()) {
            currElem = it.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        model.addAttribute("numberRandomNum", noOfGenerateNo);
        model.addAttribute("randNumResult", selectedImg.toArray());

    }
}

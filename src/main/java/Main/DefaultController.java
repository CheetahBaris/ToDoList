package Main;

import Main.model.Stuff;
import Main.model.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class DefaultController {
    @Autowired
    private StuffRepository stuffRepository;
    @Value("${someParameter.value}")
    private Integer someParameter;
    @RequestMapping("/")
    public String index(Model model){
        Iterable<Stuff> stuffIterable = stuffRepository.findAll();
        ArrayList<Stuff> stuffs = new ArrayList<>();
        for (Stuff stuff : stuffIterable){
            stuffs.add(stuff);
        }
        model.addAttribute("stuffs", stuffs);
        model.addAttribute("stuffCount", stuffs.size());
        model.addAttribute("someParameter",someParameter);
        return  "index";
    }
}

package com.walkersorlie.restservice.Controller;

import com.walkersorlie.restservice.DataBlock.CurrentlyDataBlock;
import com.walkersorlie.restservice.DataBlock.HourlyDataBlock;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 *
 * @author Walker Sorlie
 */
@Controller
public class HomeController {
    
    private final WebClient client = WebClient.create("http://localhost:8080");
    

    @RequestMapping(value = "/")
    public String index(Model model) {
        Flux<CurrentlyDataBlock> currently = client.get()
                .uri("/api/currently_collection?size=4")
                .retrieve()
                .bodyToFlux(CurrentlyDataBlock.class);
        
        model.addAttribute("currently", currently);
        
        Flux<HourlyDataBlock> hourly = client.get()
                .uri("/api/hourly_collection?size=8")
                .retrieve()
                .bodyToFlux(HourlyDataBlock.class);
        
        model.addAttribute("hourly", hourly);
        
        return "index";
    }
}

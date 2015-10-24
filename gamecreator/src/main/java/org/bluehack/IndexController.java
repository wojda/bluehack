package org.bluehack;

import org.bluehack.game.CreateNewGameUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping("/creator")
    public String creator() {
        return "creator";
    }

    @RequestMapping("/game/create")
    public String createGame() {
        return new CreateNewGameUseCase().create()
                .transform(gameId -> "redirect:/play/" + gameId)
                .or("redirect:/anavailable");
    }

    @RequestMapping("/play/{gameId}")
    public String play(@PathVariable String gameId, Map<String, Object> model) {
        model.put("gameId", gameId);
        return "play";
    }

    @RequestMapping("/anavailable")
    public String anavailable() {
        return "anavailablegame";
    }
}
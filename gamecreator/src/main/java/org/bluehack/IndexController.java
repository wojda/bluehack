package org.bluehack;

import org.bluehack.game.CreateNewGameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
public class IndexController {
    private CreateNewGameUseCase createNewGame;

    @Autowired
    public IndexController(CreateNewGameUseCase createNewGame) {
        this.createNewGame = createNewGame;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/order/{gameId}")
    public String order(@PathVariable String gameId, Map<String, Object> model) {
        model.put("gameId", gameId);
        return "order";
    }

    @RequestMapping("/confirm/{gameId}")
    public String confirm(@PathVariable String gameId, Map<String, Object> model) {
        model.put("gameId", gameId);
        return "confirm";
    }
    
    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping("/creator")
    public String creator() {
        return "creator";
    }


    @RequestMapping(value="/game/create", method= RequestMethod.POST)
    public String createGame(@RequestParam("background_image") MultipartFile backgroundImage,
                            @RequestParam("droplet_image") MultipartFile dropletImage,
                            @RequestParam("basket_image") MultipartFile basketImage) throws IOException {

        return createNewGame(backgroundImage, dropletImage, basketImage)
                .map(gameId -> "redirect:/play/" + gameId)
                .orElseGet(() -> "redirect:/anavailable");
    }

    private Optional<String> createNewGame(MultipartFile backgroundImage, MultipartFile dropletImage, MultipartFile basketImage) throws IOException {
        if(backgroundImage.isEmpty()) {
            return createNewGame.create();
        } else {
            return createNewGame.create(backgroundImage.getInputStream(), dropletImage.getInputStream(), basketImage.getInputStream());
        }
    }

    @RequestMapping("/play/{gameId}")
    public String play(@PathVariable String gameId, Map<String, Object> model) {
        model.put("gameId", gameId);
        return "play";
    }

    @RequestMapping("/unavailable")
    public String unavailable() {
        return "unavailablegame";
    }
}
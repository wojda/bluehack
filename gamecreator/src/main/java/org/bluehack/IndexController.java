package org.bluehack;

import org.bluehack.game.CreateNewGameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

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

    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }

    @RequestMapping("/creator")
    public String creator() {
        return "creator";
    }


    @RequestMapping(value="/game/create", method= RequestMethod.POST)
    public String createGame(@RequestParam("droplet_image") MultipartFile dropletImage,
                             @RequestParam("basket_image") MultipartFile basketImage) {

        try {
            byte[] dropletImageByteArray = dropletImage.getBytes();
            InputStream dropletImageinputStream = new ByteArrayInputStream(dropletImageByteArray);

            byte[] basketImageByteArray = basketImage.getBytes();
            InputStream basketImageinputStream = new ByteArrayInputStream(dropletImageByteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return createNewGame.create()
                .transform(gameId -> "redirect:/play/" + gameId)
                .or("redirect:/anavailable");
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
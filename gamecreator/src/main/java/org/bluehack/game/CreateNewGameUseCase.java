package org.bluehack.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.zeroturnaround.zip.ZipUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Component
public class CreateNewGameUseCase {
    private Logger logger = LoggerFactory.getLogger(CreateNewGameUseCase.class);
    private final ResourceLoader resourceLoader;

    @Autowired
    public CreateNewGameUseCase(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Optional<String> create(InputStream backgroundImage, InputStream dropletImage, InputStream basketImage) {
        String gameId = generateNewGameId();

        return copyGameTemplateToDestination(gameId)
                .flatMap(gamePath -> replaceImages(gamePath, backgroundImage, dropletImage, basketImage))
                .map(gamePath -> gameId);
    }

    private String generateNewGameId() {
        return UUID.randomUUID().toString();
    }

    private Optional<Path> copyGameTemplateToDestination(String gameId) {
        Resource gameTemplate = resourceLoader.getResource("classpath:games/dist.zip");

        String tempDirectory = System.getProperty("java.io.tmpdir");
        Path gameDirectory = Paths.get(tempDirectory, "games", gameId);

        try {
            ZipUtil.unpack(gameTemplate.getInputStream(), gameDirectory.toFile());

        } catch (Exception e) {
            logger.error("Cannot unzip game template", e);
            return Optional.empty();
        }
        return Optional.of(gameDirectory);
    }

    private Optional<Path> replaceImages(Path gamePath, InputStream backgroundImage, InputStream dropletImage, InputStream basketImage) {
        Path backgroundImageTarget = gamePath.resolve(Paths.get("dist", "assets", "bg.png"));
        Path dropletImageTarget = gamePath.resolve(Paths.get("dist", "assets", "drop.png"));
        Path playerImageTarget = gamePath.resolve(Paths.get("dist", "assets", "player.png"));
        try {
            Files.copy(backgroundImage, backgroundImageTarget, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(dropletImage, dropletImageTarget, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(basketImage, playerImageTarget, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Cannot replace game assets", e);
            return Optional.empty();
        }

        return Optional.of(gamePath);
    }

}

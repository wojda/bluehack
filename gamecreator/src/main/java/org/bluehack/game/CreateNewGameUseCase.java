package org.bluehack.game;

import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.zeroturnaround.zip.ZipUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class CreateNewGameUseCase {
    private Logger logger = LoggerFactory.getLogger(CreateNewGameUseCase.class);
    private final ResourceLoader resourceLoader;

    @Autowired
    public CreateNewGameUseCase(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Optional<String> create() {
        String gameId = generateNewGameId();

        return copyGameTemplateToDestination(gameId)
                .transform(gamePath -> gameId);
    }

    private Optional<Path> copyGameTemplateToDestination(String gameId) {
        Resource gameTemplate = resourceLoader.getResource("classpath:games/dist.zip");

        String tempDirectory = System.getProperty("java.io.tmpdir");
        Path gameDirectory = Paths.get(tempDirectory, "games", gameId);

        try {
            ZipUtil.unpack(gameTemplate.getInputStream(), gameDirectory.toFile());

        } catch (Exception e) {
            logger.error("Cannot unzip game template", e);
            return Optional.absent();
        }
        return Optional.of(gameDirectory);
    }

    private String generateNewGameId() {
        return UUID.randomUUID().toString();
    }
}

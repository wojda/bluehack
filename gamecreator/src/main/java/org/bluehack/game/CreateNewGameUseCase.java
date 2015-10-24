package org.bluehack.game;

import com.google.common.base.Optional;
import net.lingala.zip4j.core.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class CreateNewGameUseCase {
    private Logger logger = LoggerFactory.getLogger(CreateNewGameUseCase.class);
    private URL source = this.getClass().getClassLoader().getResource("games/dist.zip");

    public Optional<String> create() {
        String gameId = generateNewGameId();

        return copyGameTemplateToDestination(gameId)
                .transform(gamePath -> gameId);
    }

    private Optional<Path> copyGameTemplateToDestination(String gameId) {
        String tempDirectory = System.getProperty("java.io.tmpdir");
        Path gameDirectory = Paths.get(tempDirectory, "games", gameId);

        try {
            ZipFile zipFile = new ZipFile(new File(source.toURI()));
            zipFile.extractAll(gameDirectory.toString());

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

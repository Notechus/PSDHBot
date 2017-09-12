package com.notechus.discord.psdhbot.verticle;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.notechus.discord.psdhbot.handler.PSDHBot;
import com.notechus.discord.psdhbot.type.Config;
import com.notechus.discord.psdhbot.type.InputUnavailableException;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author notechus.
 */
public class BotVerticle extends AbstractVerticle {

    private static final Logger log = LoggerFactory.getLogger(BotVerticle.class);
    private static final String CONFIG_FILE = "config.yml";
    private Config config;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        this.config = parseConfig();
        log.info("Parsed config file");

        AudioInputStream audioInputStream = captureVoice(config);

        if (audioInputStream == null) {
            throw new InputUnavailableException();
        }

        Observable.just(new PSDHBot(config, audioInputStream))
                .subscribeOn(Schedulers.io())
                .subscribe(this::createClient, error -> {
                    log.error("Could not connect to the Discord", error);
                    System.exit(0);
                });

        startFuture.succeeded();
    }

    private AudioInputStream captureVoice(Config config) {
        log.info("Trying to create audio input stream");
        AudioFormat format = new AudioFormat(config.getSampleRate(), config.getSampleSizeBits(),
                2, true, true);
        AudioInputStream stream = null;
        DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);

        try {
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetLine.open(format);
            targetLine.start();
            stream = new AudioInputStream(targetLine);
            log.info("Initialized input stream");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return stream;
    }

    private Config parseConfig() throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader(new FileReader(CONFIG_FILE));
        return reader.read(Config.class);
    }

    private void createClient(PSDHBot bot) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(config.getApiToken());
        IDiscordClient iDiscordClient = clientBuilder.login();
        log.info("Connected PSDHBot to the Discord");
        if (iDiscordClient != null) {
            iDiscordClient.getDispatcher().registerListener(bot);
        }
    }
}

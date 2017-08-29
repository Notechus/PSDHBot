package com.notechus.discord.psdhbot.cli;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.notechus.discord.psdhbot.handler.PSDHBot;
import com.notechus.discord.psdhbot.type.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import javax.sound.sampled.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author notechus.
 */
public class CommandLineClient implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CommandLineClient.class);
    private static final String CONFIG_FILE = "config.yml";
    private PSDHBot bot;
    private Config config;

    public CommandLineClient() {
        try {
            this.config = parseConfig();
        } catch (FileNotFoundException | YamlException e) {
            e.printStackTrace();
        }
        if (this.config == null) {
            throw new RuntimeException("Could not parse config file.");
        }
        log.debug("Parsed configuration: {}", config);
        this.bot = new PSDHBot(this.config, captureVoice());
    }

    @Override
    public void run() {
        log.info("Initialized input device");
        IDiscordClient client = createClient(config.getApiToken());
        log.info("Connected PSDHBot to the Discord");
        if (client != null) {
            EventDispatcher dispatcher = client.getDispatcher();
            dispatcher.registerListener(this.bot);
        } else {
            log.error("Could not connect to the Discord");
            System.exit(0);
        }
    }

    private IDiscordClient createClient(String token) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        try {
            return clientBuilder.login();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Mixer.Info prepareMicro() {
        Optional<Mixer.Info> first = Arrays.stream(AudioSystem.getMixerInfo())
                .filter(mixer -> mixer.getName().equals(config.getInputDevice())).findFirst();
        return first.orElse(null);
    }

    private AudioInputStream captureVoice() {
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return stream;
    }

    private Config parseConfig() throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader(new FileReader(CONFIG_FILE));
        return reader.read(Config.class);
    }
}

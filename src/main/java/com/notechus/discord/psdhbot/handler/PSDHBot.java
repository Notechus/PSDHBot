package com.notechus.discord.psdhbot.handler;

import com.notechus.discord.psdhbot.type.Config;
import com.notechus.discord.psdhbot.type.GuildNotFoundException;
import com.notechus.discord.psdhbot.type.VoiceChannelUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * @author notechus.
 */
public class PSDHBot {
    private static final Logger log = LoggerFactory.getLogger(PSDHBot.class);
    private final Config config;
    private IGuild myGuild;
    private AudioPlayer player;
    private IVoiceChannel voiceChannel;
    private AudioInputStream micro;

    public PSDHBot(Config config, AudioInputStream micro) {
        this.micro = micro;
        this.config = config;
    }

    @EventSubscriber
    public void onReady(ReadyEvent event) throws IOException {
        initGuild(event.getClient());
        initAndJoinChannel();
        initPlayerAndPlay();
    }

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) {
        IMessage message = event.getMessage();
        if (message.getContent().startsWith("!commander")) {
            passToCommander(message.getContent().substring(10), message.getAuthor().getName());
        } else if (message.getContent().startsWith("!com")) {
            passToCommander(message.getContent().substring(4), message.getAuthor().getName());
        }
    }

    private void initAndJoinChannel() {
        Optional<IVoiceChannel> channelOptional = myGuild.getVoiceChannels().stream()
                .filter(channel -> channel.getName().equals(config.getChannel()))
                .findFirst();

        voiceChannel = channelOptional.orElseThrow(VoiceChannelUnavailableException::new);
        voiceChannel.join();
        log.info("Connected to {} channel", voiceChannel.getName());
    }

    private void initGuild(IDiscordClient discordClient) {
        Optional<IGuild> guildOptional = discordClient.getGuilds().stream()
                .filter(guild -> guild.getName().equals(config.getGuild())).findFirst();
        myGuild = guildOptional.orElseThrow(GuildNotFoundException::new);
        log.info("Connected to guild: " + myGuild.getName());
    }

    private void passToCommander(String message, String author) {
        log.info("Got message from " + author + ": " + message);
    }

    private void initPlayerAndPlay() throws IOException {
        player = AudioPlayer.getAudioPlayerForGuild(myGuild);
        player.clear();
        player.setVolume(config.getVolume());
        player.queue(micro);
    }

    private void playFile(String path) throws IOException {
        File f = new File(path);
        try {
            player.queue(f);
        } catch (UnsupportedAudioFileException e) {
            log.error(e.getMessage(), e);
        }
    }
}

package com.notechus.discord.psdhbot.type;

import java.io.Serializable;

/**
 * @author notechus.
 */
public class Config implements Serializable {
    private String username;
    private String apiToken;
    private String guild;
    private String channel;
    private String inputDevice;
    private float volume;
    private float sampleRate;
    private int sampleSizeBits;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInputDevice() {
        return inputDevice;
    }

    public void setInputDevice(String inputDevice) {
        this.inputDevice = inputDevice;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public float getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(float sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getSampleSizeBits() {
        return sampleSizeBits;
    }

    public void setSampleSizeBits(int sampleSizeBits) {
        this.sampleSizeBits = sampleSizeBits;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Config{");
        sb.append("username='").append(username).append('\'');
        sb.append(", apiToken='").append(apiToken).append('\'');
        sb.append(", guild='").append(guild).append('\'');
        sb.append(", channel='").append(channel).append('\'');
        sb.append(", inputDevice='").append(inputDevice).append('\'');
        sb.append(", volume=").append(volume);
        sb.append(", sampleRate=").append(sampleRate);
        sb.append(", sampleSizeBits=").append(sampleSizeBits);
        sb.append('}');
        return sb.toString();
    }
}

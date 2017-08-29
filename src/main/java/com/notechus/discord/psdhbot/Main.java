package com.notechus.discord.psdhbot;


import com.notechus.discord.psdhbot.cli.CommandLineClient;

/**
 * @author notechus.
 */
public class Main {

    public static void main(String[] args) {
        new Thread(new CommandLineClient()).start();
    }
}
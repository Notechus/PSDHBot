package com.notechus.discord.psdhbot;


import com.notechus.discord.psdhbot.verticle.BotVerticle;
import io.vertx.rxjava.core.RxHelper;
import io.vertx.rxjava.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.logging.Level;

/**
 * @author notechus.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("io.vertx.core.impl.BlockedThreadChecker").setLevel(Level.OFF);
        Observable<String> deployed = RxHelper.deployVerticle(Vertx.vertx(), new BotVerticle());

        deployed.subscribe(id ->
        {
            log.info("Successfully deployed PSDH Bot");
            java.util.logging.Logger.getLogger("io.vertx.core.impl.BlockedThreadChecker").setLevel(Level.ALL);
        }, err -> {
            log.error("Error during deployment: {}", err);
        });
    }
}
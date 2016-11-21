/*
 * Copyright (C) 2015-2016  ChalkPE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pe.chalk.clsbot;

import pe.chalk.telegram.TelegramBot;
import pe.chalk.telegram.handler.UpdateHandler;
import pe.chalk.telegram.method.MeGetter;
import pe.chalk.telegram.method.TextMessageSender;
import pe.chalk.telegram.type.Update;
import pe.chalk.telegram.type.message.Message;
import pe.chalk.telegram.type.message.TextMessage;
import pe.chalk.telegram.type.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;

/**
 * @author ChalkPE <amato0617@gmail.com>
 * @since 2015-09-22
 */
public class CLSBot implements UpdateHandler {
    public static final List<String> ALIAS = Arrays.asList("/cls", "/clear", "/친");
    public static final String BLANKS = String.join("    ", Collections.nCopies(64, "\n"));

    public static void main(String[] args) throws IOException {
        final Properties properties = new Properties();
        try(BufferedReader propertiesReader = Files.newBufferedReader(Paths.get("CLSBot.properties"), StandardCharsets.UTF_8)){
            properties.load(propertiesReader);
        }

        new CLSBot(properties.getProperty("bot.token"));
    }

    private final TelegramBot bot;
    private final User me;
    private final Map<Integer, Long> timestamp = new HashMap<>();

    private CLSBot(final String token){
        this.bot = new TelegramBot(token);
        this.me = new MeGetter().get(bot);

        this.bot.initLogger(Level.ALL);
        this.bot.addHandler(this);
        this.bot.start();
    }

    public void handleMessage(List<Update> updates){
        updates.forEach(update -> {
            try {

                final Message message = update.getMessage();
                if(!(message instanceof TextMessage)) return;

                final String text = ((TextMessage) message).getText();
                if(ALIAS.stream().noneMatch(alias -> text.toLowerCase().startsWith(alias))) return;

                final int chatId = message.getChat().getId();
                if(!message.hasFrom()) return;

                final String[] commands = text.split(" ");
                if(commands[0].contains("@")){
                    final String[] command = commands[0].split("@");
                    if(!command[1].equalsIgnoreCase(me.getUsername())) return;
                    commands[0] = command[0];
                }
                if(ALIAS.stream().noneMatch(alias -> alias.equalsIgnoreCase(commands[0]))) return;

                final long time = System.currentTimeMillis();
                if(timestamp.containsKey(chatId) && (time - timestamp.get(chatId)) < 1500) return;

                final String user = Objects.isNull(message.getFrom().getUsername()) ? message.getFrom().getFullName() : "@".concat(message.getFrom().getUsername());
                new TextMessageSender(chatId, String.format("%s used %s%s\n\n%s %s", user, BLANKS, text, "#Christmas", DateCounter.count(2016, Calendar.DECEMBER, 25))).send(bot);

                timestamp.put(chatId, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

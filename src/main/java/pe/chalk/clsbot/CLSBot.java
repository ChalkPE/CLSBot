/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Chalk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pe.chalk.clsbot;

import de.vivistra.telegrambot.model.message.Message;
import de.vivistra.telegrambot.model.message.MessageType;
import de.vivistra.telegrambot.model.message.TextMessage;
import de.vivistra.telegrambot.receiver.IReceiverService;
import de.vivistra.telegrambot.receiver.Receiver;
import de.vivistra.telegrambot.sender.Sender;
import de.vivistra.telegrambot.settings.BotSettings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author ChalkPE <amato0617@gmail.com>
 * @since 2015-09-22
 */
public class CLSBot implements IReceiverService {
    public static final String BLANKS = String.join("    ", Collections.nCopies(64, "\n"));

    public static void main(String[] args){
        new CLSBot();
    }

    private Map<Integer, Long> timestamp = new HashMap<>();

    public CLSBot(){
        Properties properties = new Properties();
        try(BufferedReader propertiesReader = Files.newBufferedReader(Paths.get("CLSBot.properties"), StandardCharsets.UTF_8)){
            properties.load(propertiesReader);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }

        BotSettings.setApiToken(properties.get("bot.token").toString());
        Receiver.subscribe(this);
    }

    public void received(Message message){
        if(message.getMessageType() != MessageType.TEXT_MESSAGE){
            return;
        }

        String msg = message.getMessage().toString();
        if(!msg.toLowerCase().startsWith("/cls") && !msg.startsWith("/친")){
            return;
        }

        String[] cmd = msg.split(" ");
        if(cmd[0].contains("@") && !cmd[0].substring(cmd[0].indexOf("@") + 1).equalsIgnoreCase("CLS_BOT")){
            return;
        }

        long time = System.currentTimeMillis();
        int recipient = message.isFromGroupChat() ? message.getGroupChat().getId() : message.getSender().getId();
        if(timestamp.containsKey(recipient) && (time - timestamp.get(recipient)) < 1500){
            return;
        }

        String user = message.getSender().getUserName();
        user = !user.isEmpty() ? ("@" + user) : (message.getSender().getFirstName() + " " + message.getSender().getLastName());

        int days = CLSBot.getSuneungDays();
        String extra = "\n\n#Christmas" + ((days == -1) ? " #eve" : ((days == 0) ? "" : String.format(" D%+d", days)));

        Sender.send(new TextMessage(recipient, user + " used " + BLANKS + msg + extra));
        timestamp.put(recipient, time);
    }

    public static int getSuneungDays(){
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.set(2015, Calendar.DECEMBER, 25);
        Date dDay = calendar.getTime();

        return (int) ((today.getTime() - dDay.getTime()) / (1000 * 60 * 60 * 24));
    }
}

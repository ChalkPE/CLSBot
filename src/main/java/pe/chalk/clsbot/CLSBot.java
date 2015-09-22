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
import java.util.Properties;

/**
 * @author ChalkPE <amato0617@gmail.com>
 * @since 2015-09-22
 */
public class CLSBot implements IReceiverService {
    public static final String MESSAGE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n.";

    public static void main(String[] args){
        new CLSBot();
    }

    public CLSBot(){
        BufferedReader propertiesReader = null;
        Properties properties = new Properties();

        try{
            propertiesReader = Files.newBufferedReader(Paths.get("CLSBot.properties"), StandardCharsets.UTF_8);
            properties.load(propertiesReader);
        }catch(IOException e){
            e.printStackTrace();
            return;
        }finally{
            if(propertiesReader != null){
                try{
                    propertiesReader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        BotSettings.setApiToken(properties.get("bot.token").toString());
        Receiver.subscribe(this);
    }

    public void received(Message message){
        if(message.getMessageType() == MessageType.TEXT_MESSAGE){
            int recipient = message.isFromGroupChat() ? message.getGroupChat().getId() : message.getSender().getId();

            if(message.getMessage().toString().split(" |@")[0].equals("/cls"))
                Sender.send(new TextMessage(recipient, message.getSender().getUserName() + ": /cls" + MESSAGE));
        }
    }
}

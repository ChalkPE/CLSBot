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

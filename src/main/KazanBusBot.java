package main;

import entity.Location;
import entity.Naberezhnaya;
import main.Time.Belinskii29Time;
import main.service.BelinskiiModeService;
import main.service.LocationModeService;
import main.service.NaberezhnayaModeService;
import main.station.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KazanBusBot extends TelegramLongPollingBot {
    private final LocationModeService locationModeService = LocationModeService.getInstance();
    private final BelinskiiModeService belinskiiModeService = BelinskiiModeService.getInstance();
    private final NaberezhnayaModeService naberezhnayaModeService = NaberezhnayaModeService.getInstance();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                handleCallback(update.getCallbackQuery());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasMessage()) {
            try {
                handleMessage(update.getMessage());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleCallback(CallbackQuery callbackQuery) throws Exception {
        Message message = callbackQuery.getMessage();
        String[] param = callbackQuery.getData().split(":");
        if (param[0].equals("LOCATION")) {
            Location newLocation = Location.valueOf(param[1]);
            locationModeService.setOriginalLocation(message.getChatId(), newLocation);
            List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
            Location originalLocation = locationModeService.getOriginalLocation(message.getChatId());
            for (Location location : Location.values()) {
                buttons.add(
                        Arrays.asList(
                                InlineKeyboardButton.builder()
                                        .text(getLocationButton(originalLocation, location))
                                        .callbackData("LOCATION:" + location)
                                        .build()
                        ));
            }
            execute(EditMessageReplyMarkup.builder()
                    .chatId(message.getChatId().toString())
                    .messageId(message.getMessageId())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                    .build());
            switch (newLocation) {
                case ул_Белинского:
                    List<List<InlineKeyboardButton>> buttonsBelinskii = new ArrayList<>();
                    entity.Belinskii originalBelinskii = belinskiiModeService.getOriginalBelinskii(message.getChatId());
                    for (entity.Belinskii belinskii : entity.Belinskii.values()) {
                        buttonsBelinskii.add(
                                Arrays.asList(
                                        InlineKeyboardButton.builder()
                                                .text(getBelinskiiButton(originalBelinskii, belinskii))
                                                .callbackData("BELINSKII:" + belinskii)
                                                .build()));
                    }
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Belinskii29Early.getBelinskii29()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Belinskii29Later.getBelinskii29Later()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Belinskii62Early.getBelinskii62()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Belinskii62Later.getBelinskii62Later()).build());
                    execute(SendMessage.builder().text("Уведомить за 10 минут до прибытия автобуса?").chatId(message.getChatId().toString()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonsBelinskii).build()).build());
                    break;
                case Комб_Здоровье:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(ZdorovieEarly.getZdorovie()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(ZdorovieLater.getZdorovieLater()).build());
                    break;
                case ул_Набережная:
                    List<List<InlineKeyboardButton>> buttonsNaberezhnaya = new ArrayList<>();
                    entity.Naberezhnaya originalNaberezhnaya = naberezhnayaModeService.getOriginalNaberezhnaya(message.getChatId());
                    for (entity.Naberezhnaya naberezhnaya : entity.Naberezhnaya.values()) {
                        buttonsNaberezhnaya.add(
                                Arrays.asList(
                                        InlineKeyboardButton.builder()
                                                .text(getNaberezhnayaButton(originalNaberezhnaya, naberezhnaya))
                                                .callbackData("NABEREZHNAYA:" + naberezhnaya)
                                                .build()));
                    }
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Naberezhnaya45Early.getNaberezhnaya45()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Naberezhnaya45Later.getNaberezhnaya45Later()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Naberezhnaya49Early.getNaberezhnaya49()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Naberezhnaya49Later.getNaberezhnaya49Later()).build());
                    execute(SendMessage.builder().text("Уведомить за 10 минут до прибытия автобуса?").chatId(message.getChatId().toString()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonsNaberezhnaya).build()).build());
                    break;
                case пл_Восстания_работа:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaJob45Early.getVosstaniyaJob45()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaJob45Later.getVosstaniyaJob45Later()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaJob49Early.getVosstaniyaJob49()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaJob49Later.getVosstaniyaJob49Later()).build());
                    break;
                case пл_Восстания_дом:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaHome29Early.getVosstaniyaHome29()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaHome29Later.getVosstaniyaHome29Later()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaHome62Early.getVosstaniyaHome62()).build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(VosstaniyaHome62Later.getVosstaniyaHome62Later()).build());
                    break;
            }
        } else if (param[0].equals("BELINSKII")) {
            entity.Belinskii newBelinskii = entity.Belinskii.valueOf(param[1]);
            belinskiiModeService.setOriginalBelinskii(message.getChatId(), newBelinskii);
            List<List<InlineKeyboardButton>> buttonsBelinskii = new ArrayList<>();
            entity.Belinskii originalBelinskii = belinskiiModeService.getOriginalBelinskii(message.getChatId());
            for (entity.Belinskii belinskii : entity.Belinskii.values()) {
                buttonsBelinskii.add(
                        Arrays.asList(
                                InlineKeyboardButton.builder()
                                        .text(getBelinskiiButton(originalBelinskii, belinskii))
                                        .callbackData("BELINSKII:" + belinskii)
                                        .build()));
            }
            execute(EditMessageReplyMarkup.builder()
                    .chatId(message.getChatId().toString())
                    .messageId(message.getMessageId())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonsBelinskii).build())
                    .build());
            switch (newBelinskii) {
                case Автобус_29:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Договорились").build());
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Belinskii29Time.period29Early()).build());
                    break;
                case Автобус_62:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("62 скоро").build());
                    break;
                case Нет_необходимости:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Ну нет, так нет").build());
                    break;
            }
        } else if (param[0].equals("NABEREZHNAYA")) {
            Naberezhnaya newNaberezhnaya = Naberezhnaya.valueOf(param[1]);
            naberezhnayaModeService.setOriginalNaberezhnaya(message.getChatId(), newNaberezhnaya);
            List<List<InlineKeyboardButton>> buttonsNaberezhnaya = new ArrayList<>();
            entity.Naberezhnaya originalNaberezhnaya = naberezhnayaModeService.getOriginalNaberezhnaya(message.getChatId());
            for (Naberezhnaya naberezhnaya : Naberezhnaya.values()) {
                buttonsNaberezhnaya.add(
                        Arrays.asList(
                                InlineKeyboardButton.builder()
                                        .text(getNaberezhnayaButton(originalNaberezhnaya, naberezhnaya))
                                        .callbackData("NABEREZHNAYA:" + naberezhnaya)
                                        .build()));
            }
            execute(EditMessageReplyMarkup.builder()
                    .chatId(message.getChatId().toString())
                    .messageId(message.getMessageId())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttonsNaberezhnaya).build())
                    .build());
            switch (newNaberezhnaya) {
                case Автобус_45:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("45 скоро").build());
                    break;
                case Автобус_49:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("49 скоро").build());
                    break;
                case Нет_необходимости:
                    execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Ну нет, так нет").build());
                    break;
            }
        }
    }
    private void handleMessage(Message message) throws TelegramApiException, IOException {
        //handle command
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                switch (command) {
                    case "/location":
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        Location originalLocation = locationModeService.getOriginalLocation(message.getChatId());
                        for (Location location : Location.values()) {
                            buttons.add(
                                    Arrays.asList(
                                            InlineKeyboardButton.builder()
                                                    .text(getLocationButton(originalLocation, location))
                                                    .callbackData("LOCATION:" + location)
                                                    .build()
                                    ));
                        }
                        execute(SendMessage.builder()
                                .text("Выберите пункт отправления:")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
                        return;
                }
            }
        }
    }

//    private void handleCallback2(CallbackQuery callbackQuery) throws Exception {
//        Message message = callbackQuery.getMessage();
//        String[] param = callbackQuery.getData().split(":");
//
//    }

    private String getLocationButton(Location saved, Location current) {
        return saved == current ? current + " ✅" : current.name();
    }

    private String getBelinskiiButton(entity.Belinskii saved, entity.Belinskii current) {
        return saved == current ? current + " ✅" : current.name();
    }

    private String getNaberezhnayaButton(entity.Naberezhnaya saved, entity.Naberezhnaya current) {
        return saved == current ? current + " ✅" : current.name();
    }

    @Override
    public String getBotUsername() {
        return "BelinskyBusBot";
    }

    @Override
    public String getBotToken() {
        return "5866059669:AAGUtxqA39HT8_F6SLurQcofqljpT3FvXYo";
    }

    public static void main(String[] args) throws TelegramApiException {
        KazanBusBot bus = new KazanBusBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bus);
    }
}

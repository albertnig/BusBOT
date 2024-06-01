//package main;
//
//import entity.Location;
//import main.service.LocationConversionService;
//import main.service.LocationModeService;
//import org.telegram.telegrambots.bots.DefaultAbsSender;
//import org.telegram.telegrambots.bots.DefaultBotOptions;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.MessageEntity;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//
//import java.io.IOException;
//import java.util.*;
//
//public class Bus extends TelegramLongPollingBot {
//
////    private final LocationModeService locationModeService = LocationModeService.getInstance();
////    private final LocationConversionService locationConversionService = LocationConversionService.getInstance();
//
//    @Override
//    public String getBotUsername() {
//        return "BelinskyBusBot";
//    }
//
//    @Override
//    public String getBotToken() {
//        return "5866059669:AAGUtxqA39HT8_F6SLurQcofqljpT3FvXYo";
//    }
//
//    public static void main(String[] args) throws TelegramApiException {
//        Bus bus = new Bus();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        telegramBotsApi.registerBot(bus);
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasCallbackQuery()) {
//            try {
//                handleCallback(update.getCallbackQuery());
//            } catch (TelegramApiException e) {
//                throw new RuntimeException(e);
//            }
//        } else if (update.hasMessage()) {
//            try {
//                handleMessage(update.getMessage());
//            } catch (TelegramApiException | IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
////    private void handleCallback(CallbackQuery callbackQuery) throws TelegramApiException {
////        Message message = callbackQuery.getMessage();
////        String[] param = callbackQuery.getData().split(":");
////        String action = param[0];
////        Location newLocation = Location.valueOf(param[1]);
////        switch (action) {
////            case "ORIGINAL":
////                locationModeService.setOriginalLocation(message.getChatId(), newLocation);
////                break;
//////            case "TARGET":
//////                locationModeService.setTargetLocation(message.getChatId(), newLocation);
//////                break;
////        }
////        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
////        Location originalLocation = locationModeService.getOriginalLocation(message.getChatId());
//////        Location targetLocation = locationModeService.getTargetLocation(message.getChatId());
////        for (Location location : Location.values()) {
////            buttons.add(Arrays.asList(InlineKeyboardButton.builder().text(getLocationButton(originalLocation, location)).callbackData("ORIGINAL" + location).build()/*, InlineKeyboardButton.builder().text(getLocationButton(targetLocation, location)).callbackData("Target:" + location).build()*/));
////        }
////        execute(EditMessageReplyMarkup.builder().chatId(message.getChatId().toString()).messageId(message.getMessageId()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build()).build());
////    }
////
//    private void handleMessage(Message message) throws TelegramApiException, IOException {
//        //handle command
//        if (message.hasText() && message.hasEntities()) {
//            Optional<MessageEntity> commandEntity =
//                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
//            if (commandEntity.isPresent()) {
//                String command = message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
//                switch (command) {
//                    case "/location":
//                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//                        Location originalLocation = locationModeService.getOriginalLocation(message.getChatId());
////                        Location targetLocation = locationModeService.getTargetLocation(message.getChatId());
//                        for (Location location : Location.values()) {
//                            buttons.add(Arrays.asList(InlineKeyboardButton.builder().text(getLocationButton(originalLocation, location)).callbackData("ORIGINAL" + location).build()/*, InlineKeyboardButton.builder().text(getLocationButton(targetLocation, location)).callbackData("TARGET:" + location).build()*/));
//                        }
//                        execute(SendMessage.builder().text("Пункт отправления:").chatId(message.getChatId().toString()).replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build()).build());
//                        return;
//                }
//            }
//        }
//        if (message.hasText()) {
//            String messageText = message.getText();
//            Optional<Double> value = parseDouble(messageText);
//            Location originalLocation = locationModeService.getOriginalLocation(message.getChatId());
////            Location targetLocation = locationModeService.getTargetLocation(message.getChatId());
//            double ratio = locationConversionService.getConversionRatio(originalLocation, originalLocation);
//            if (value.isPresent()) {
//                execute(SendMessage.builder().chatId(message.getChatId().toString()).text(String.format("%4.2f %s is %4.2f %s", value.get(), originalLocation, (ratio - value.get())/*, targetLocation*/)).build());
//                return;
//            }
//        }
//    }
////
////    private Optional<Double> parseDouble(String messageText) {
////        try {
////            return Optional.of(Double.parseDouble(messageText));
////        } catch (Exception e) {
////            return Optional.empty();
////        }
////    }
////
////    private String getLocationButton(Location saved, Location current) {
////        return saved == current ? current + " ✅" : current.name();
////    }
//}

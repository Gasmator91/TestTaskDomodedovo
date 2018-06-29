package main.java;

import unisoft.ws.FNSNDSCAWS2;
import unisoft.ws.FNSNDSCAWS2Port;
import unisoft.ws.fnsndscaws2.request.NdsRequest2;
import unisoft.ws.fnsndscaws2.response.NdsResponse2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NalogServiceWorker {
    private static final FNSNDSCAWS2 WS = new FNSNDSCAWS2();
    private static final FNSNDSCAWS2Port PORT = WS.getFNSNDSCAWS2Port();

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    //Список признаков состояния
    private static final List<String> STATE_LIST = new ArrayList<String>(
            Arrays.asList(
                    "Налогоплательщик зарегистрирован в ЕГРН и имел статус действующего в указанную дату", //0
                    "Налогоплательщик зарегистрирован в ЕГРН, но не имел статус действующего в указанную дату", //1
                    "Налогоплательщик зарегистрирован в ЕГРН", //2
                    "Налогоплательщик с указанным ИНН зарегистрирован в ЕГРН, КПП не соответствует ИНН или не указан",//3
                    "Налогоплательщик с указанным ИНН не зарегистрирован в ЕГРН",//4
                    "Некорректный ИНН",//5
                    "Недопустимое количество символов ИНН",//6
                    "Недопустимое количество символов КПП",//7
                    "Недопустимые символы в ИНН",//8
                    "Недопустимые символы в КПП",//9
                    "КПП не должен использоваться при проверке ИП", //10
                    "Некорректный формат даты", //11
                    "Некорректная дата (ранее 01.01.1991 или позднее текущей даты)" //12
            )
    );

    private static String getStateDescription(int state){
        return state + " - " +STATE_LIST.get(state);
    }

    /**
     *  Возвращает статус ЮЛ/ФЛ
     * @param inn - ИНН
     * @return статус 
     */
    public static String getDescriptionState(String inn){
        String date = dateFormat.format(new Date());

        NdsRequest2 request = new NdsRequest2();
        NdsRequest2.NP np = new NdsRequest2.NP();

        np.setINN(inn);
        np.setDT(date);
        request.getNP().add(np);
        NdsResponse2 response = PORT.ndsRequest2(request);

        List<NdsResponse2.NP> resultNPList = response.getNP();
        int state = Integer.valueOf(resultNPList.get(0).getState());

        return getStateDescription(state);
    }
}

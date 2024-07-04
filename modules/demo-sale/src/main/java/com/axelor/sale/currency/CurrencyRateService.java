//package com.axelor.sale.currency;
//
//import com.axelor.rpc.Request;
//import com.axelor.sale.db.CurrencyRate;
//import com.axelor.db.JpaSupport;
//import com.google.inject.Inject;
//import com.google.inject.persist.Transactional;
//
//import org.apache.poi.xslf.model.geom.Context;
//import org.eclipse.birt.report.engine.executor.buffermgr.Cell;
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.StringReader;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import static com.axelor.db.JPA.save;
//
//public class CurrencyRateService extends JpaSupport {
//
//    @Inject
//    private Context context;x
//
//    private static final String CURRENCY_URL = "https://api.example.com/currency-rates";
//
//    @Transactional
//    public void fetchAndSaveCurrencyRates() {
//        try {
//            // Выполнение HTTP-запроса
//            Cell.Content content = Request(CURRENCY_URL).execute().returnContent();
//            String xmlResponse = content.asString();
//
//            // Парсинг XML ответа
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));
//
//            NodeList nodeList = document.getElementsByTagName("CurrencyRate");
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                String currencyCode = document.getElementsByTagName("currencyCode").item(i).getTextContent();
//                String currencyName = document.getElementsByTagName("currencyName").item(i).getTextContent();
//                BigDecimal rate = new BigDecimal(document.getElementsByTagName("rate").item(i).getTextContent());
//                BigDecimal rateChange = new BigDecimal(document.getElementsByTagName("rateChange").item(i).getTextContent());
//                LocalDateTime currencyDate = LocalDateTime.now(); // Предполагаем, что дата - это текущая дата
//
//                // Создание или обновление записи
//                CurrencyRate currencyRate = find(CurrencyRate.class)
//                        .where("self.currencyCode = ?", currencyCode)
//                        .fetchOne();
//
//                if (currencyRate == null) {
//                    currencyRate = new CurrencyRate();
//                    currencyRate.setCode(currencyCode);
//                }
//
//                currencyRate.setName(currencyName);
//                currencyRate.setRate(rate);
//                currencyRate.setRateChange(rateChange);
////                currencyRate.se(currencyDate);
//
//                save(currencyRate);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}

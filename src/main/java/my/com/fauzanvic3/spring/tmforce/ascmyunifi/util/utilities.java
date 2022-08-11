/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.com.fauzanvic3.spring.tmforce.ascmyunifi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author iinmacsdev
 */
public class utilities {

    static final Logger log = Logger.getLogger("utilities");

    public utilities() {
    }

    protected LinkedList listOfDates = new LinkedList();

    public LinkedList getDatesInRange(String _startDate, String _endDate) throws ParseException {
        Date startDate = new java.text.SimpleDateFormat("MM/dd/yyyy").parse(_startDate);
        Date endDate = new java.text.SimpleDateFormat("MM/dd/yyyy").parse(_endDate);
        Date begin = new Date(startDate.getTime());
        if (!listOfDates.isEmpty()) {
            listOfDates.clear();
        }
        listOfDates.add(new Date(begin.getTime()));

        while (begin.compareTo(endDate) < 0) {
            begin = new Date(begin.getTime() + 86400000);
            listOfDates.add(new Date(begin.getTime()));
        }
        return listOfDates;
    }

    public LinkedList getListOfDates() {
        return listOfDates;
    }

    public void setListOfDates(LinkedList listOfDates) {
        this.listOfDates = listOfDates;
    }

    public Boolean validateDate(String _aDate) {
        Date dbDate = null;
        DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            dbDate = inputFormat.parse(_aDate);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public static String nullToBlank(Object param) {

        if (param == null) {
            return "";
        }

        if (param instanceof String) {
            return (String) param;
        } else if (param instanceof BigDecimal) {
            return ((BigDecimal) param).toString();
        } else if (param instanceof Date) {
            return dateToStr((Date) param);
        } else {
            return String.valueOf(param);
        }

    }

    public static boolean isBlank(String param) {

        if (param == null || param.isEmpty()) {
            return true;
        } else if (param.trim().isEmpty()) {
            return true;
        }

        return false;

    }

    public static java.util.Date strToDate(String dateStr) {
        java.util.Date dbdate = null;
        try {
            SimpleDateFormat dbFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            dbdate = dbFormat.parse(dateStr);
        } catch (ParseException ex) {

        }

        return dbdate;
    }

    public static String dateToStr(java.util.Date _date) {
        String dateStr = "";

        if (_date != null) {

            try {
                SimpleDateFormat dbFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                dateStr = dbFormat.format(_date);
            } catch (Exception ex) {
                dateStr = "";
            }
        }

        return dateStr;
    }

    public static String toString(Object jsonFormat) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonFormat);
        } catch (JsonProcessingException ex) {
            return "{}";
        }
    }

    private static class NullX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class NullHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static Response triggerModelPost(String endpoint, MultivaluedMap<String, Object> innerMapMutiValue, Object object) {

        Client client = null;
        if (endpoint.contains("https://")) {
            try {
                SSLContext context = SSLContext.getInstance("TLS");
                TrustManager[] trustManagerArray = {new utilities.NullX509TrustManager()};
                context.init(null, trustManagerArray, null);
                client = ClientBuilder.newBuilder()
                        .hostnameVerifier(new utilities.NullHostnameVerifier())
                        .sslContext(context)
                        .build();
            } catch (KeyManagementException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        } else {
            client = ClientBuilder.newClient();
        }
        WebTarget webTarget = client.target(endpoint);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).headers(innerMapMutiValue);
        Response response = invocationBuilder.post(Entity.entity(object, MediaType.APPLICATION_JSON));

        return response;
    }

    public static Response triggerModelGet(String endpoint, MultivaluedMap<String, Object> innerMapMutiValue) {

        Client client = null;
        if (endpoint.contains("https://")) {
            try {
                SSLContext context = SSLContext.getInstance("TLS");
                TrustManager[] trustManagerArray = {new utilities.NullX509TrustManager()};
                context.init(null, trustManagerArray, null);
                client = ClientBuilder.newBuilder()
                        .hostnameVerifier(new utilities.NullHostnameVerifier())
                        .sslContext(context)
                        .build();
            } catch (KeyManagementException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        } else {
            client = ClientBuilder.newClient();
        }
        WebTarget webTarget = client.target(endpoint);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).headers(innerMapMutiValue);
        Response response = invocationBuilder.get();

        return response;
    }
    
    public static Response triggerModelPut(String endpoint, MultivaluedMap<String, Object> innerMapMutiValue, Object object) {

        Client client = null;
        if (endpoint.contains("https://")) {
            try {
                SSLContext context = SSLContext.getInstance("TLS");
                TrustManager[] trustManagerArray = {new utilities.NullX509TrustManager()};
                context.init(null, trustManagerArray, null);
                client = ClientBuilder.newBuilder()
                        .hostnameVerifier(new utilities.NullHostnameVerifier())
                        .sslContext(context)
                        .build();
            } catch (KeyManagementException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        } else {
            client = ClientBuilder.newClient();
        }
        WebTarget webTarget = client.target(endpoint);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).headers(innerMapMutiValue);
        Response response = invocationBuilder.put(Entity.entity(object, MediaType.APPLICATION_JSON));

        return response;
    }

}

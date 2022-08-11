/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;
import my.com.fauzanvic3.spring.tmforce.ascmyunifi.resource.ProgressUpdateResource;
import my.com.fauzanvic3.spring.tmforce.ascmyunifi.service.ProgressUpdateService;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainRequest;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateMainResponse;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateReplyHeader;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateRequest;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateResponse;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiProgressUpdateSummary;
import my.com.tmrnd.tmforce.common.api.json.model.dac.MyUnifiRequestHeader;
import my.com.tmrnd.tmforce.common.api.json.model.dac.ProgressUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 * @author Fauzan
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProgressUpdateResource.class)
public class AscmyunifiTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProgressUpdateService service;
    
//    MyUnifiProgressUpdateMainRequest mainRequest = new MyUnifiProgressUpdateMainRequest();
    String goodRequest = "{\n" +
    "    \"Request\": {\n" +
    "        \"RequestHeader\": {\n" +
    "            \"DateTimeSent\": \"06/10/2022 17:28:02\",\n" +
    "            \"EventName\": \"evOrderProgress\",\n" +
    "            \"MessageID\": \"F0LpY0vyZIGbJrSE\",\n" +
    "            \"SourceSystem\": \"TIBCO\"\n" +
    "        },\n" +
    "        \"Summary\": {\n" +
    "            \"TTNumber\" : \"3-847182184\"\n" +
    "        }\n" +
    "    }\n" +
    "}";
    
    String expected = "{\n" +
"    \"Response\": {\n" +
"        \"ReplyHeader\": {\n" +
"            \"ErrCd\": \"0\",\n" +
"            \"ErrMsg\": \"Success\"\n" +
"        },\n" +
"        \"ProgressUpdate\": [\n" +
"            {\n" +
"                \"TICKET_ID\": \"T-0000002347\",\n" +
"                \"ACTIVITY_ID\": \"A-0000002867\",\n" +
"                \"CTT_NUMBER\": \"3-847182184\",\n" +
"                \"OLD_STATUS\": \"Pending Assign\",\n" +
"                \"NEW_STATUS\": \"Assigned\",\n" +
"                \"LOG_DATETIME\": \"14/10/2021 09:46:32\",\n" +
"                \"ACTIVITY_TYPE\": \"LOCAL_LOOP\"\n" +
"            },\n" +
"            {\n" +
"                \"TICKET_ID\": \"T-0000002347\",\n" +
"                \"ACTIVITY_ID\": \"A-0000002867\",\n" +
"                \"CTT_NUMBER\": \"3-847182184\",\n" +
"                \"OLD_STATUS\": \"Pending Assign\",\n" +
"                \"NEW_STATUS\": \"Pending Assign\",\n" +
"                \"LOG_DATETIME\": \"14/10/2021 09:46:09\",\n" +
"                \"ACTIVITY_TYPE\": \"LOCAL_LOOP\"\n" +
"            },\n" +
"            {\n" +
"                \"TICKET_ID\": \"T-0000002347\",\n" +
"                \"ACTIVITY_ID\": \"A-0000002867\",\n" +
"                \"CTT_NUMBER\": \"3-847182184\",\n" +
"                \"OLD_STATUS\": \"-\",\n" +
"                \"NEW_STATUS\": \"Pending Assign\",\n" +
"                \"LOG_DATETIME\": \"24/09/2021 11:36:40\",\n" +
"                \"ACTIVITY_TYPE\": \"LOCAL_LOOP\"\n" +
"            },\n" +
"            {\n" +
"                \"TICKET_ID\": \"T-0000002347\",\n" +
"                \"ACTIVITY_ID\": \"A-0000002867\",\n" +
"                \"CTT_NUMBER\": \"3-847182184\",\n" +
"                \"NEW_STATUS\": \"Open\",\n" +
"                \"LOG_DATETIME\": \"24/09/2021 11:36:17\",\n" +
"                \"ACTIVITY_TYPE\": \"LOCAL_LOOP\"\n" +
"            }\n" +
"        ]\n" +
"    }\n" +
"}";
    
//    @Test
//    public void testProgressUpdate() throws Exception{
//        
//        MyUnifiProgressUpdateMainRequest mainRequest = new MyUnifiProgressUpdateMainRequest();
//        MyUnifiProgressUpdateRequest Request = new MyUnifiProgressUpdateRequest();
//        MyUnifiRequestHeader RequestHeader = new MyUnifiRequestHeader();
//        MyUnifiProgressUpdateSummary Summary = new MyUnifiProgressUpdateSummary();
//        
//        Summary.setTTNumber("3-847182184");
//        RequestHeader.setDateTimeSent("06/10/2022 17:28:02");
//        RequestHeader.setEventName("evOrderProgress");
//        RequestHeader.setMessageID("F0LpY0vyZIGbJrSE");
//        RequestHeader.setEventName("TIBCO");
//        Request.setRequestHeader(RequestHeader);
//        Request.setSummary(Summary);
//        mainRequest.setRequest(Request);
//        
//        MyUnifiProgressUpdateMainResponse goodResponse = new MyUnifiProgressUpdateMainResponse();
//        MyUnifiProgressUpdateResponse Response = new MyUnifiProgressUpdateResponse();
//        MyUnifiProgressUpdateReplyHeader ReplyHeader = new MyUnifiProgressUpdateReplyHeader();
//        List<ProgressUpdate> ProgressUpdate = new ArrayList<>();
//        
//        ReplyHeader.setErrCd("0");
//        ReplyHeader.setErrMsg("Success");
//        Response.setReplyHeader(ReplyHeader);
//        Response.setProgressUpdate(ProgressUpdate);
//        goodResponse.setResponse(Response);
//                
//        MyUnifiProgressUpdateMainResponse result = service.progressUpdate("123", mainRequest);
//        
//        JSONAssert.assertEquals(expected, result.toString(), false);
//    }
    
}

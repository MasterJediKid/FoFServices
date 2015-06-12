package com.masterjedikid.personal.fof.service.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * @author MasterJediKid
 */
public class BungieDotNetEndpoints {
    
    public static String getMemberDataByMemberId(String memberId) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.bungie.net/Platform/Destiny/TigerXbox/Account/" + memberId);
        JSONObject member = new JSONObject(getResponseString(client.execute(get)));

        return member.getJSONObject("data").toString(); 
    }
    
    public static String getMemberDataByGamerTag(String gamerTag) throws Exception {
        return getMemberDataByMemberId(getMemberBungieId(gamerTag.replace(" ", "%20")));
    }
    
    public static String getMemberBungieId(String gamerTag) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.bungie.net/Platform/Destiny/TigerXbox/Stats/GetMembershipIdByDisplayName/" + gamerTag.replace(" ", "%20"));
        
        return getResponseString(client.execute(get));
    }
    
    public static String getClanGroupIdNumber(String clanName) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.bungie.net/platform/Group/Name/" + clanName);

        JSONObject clan = new JSONObject(getResponseString(client.execute(get)));
        return clan.getJSONObject("detail").get("groupId").toString();
    }
        
    public static String getAggregateActivityForCharacter(String memberId, String characterId) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.bungie.net/platform/Destiny/Stats/AggregateActivityStats/TigerXbox/" + memberId + "/" + characterId); //+ "/?definitions=true");
        

        JSONObject data = new JSONObject(getResponseString(client.execute(get)));
        return data.toString();
    }
    
    public static String getActivityHistoryForCharacter(String memberId, String characterId) throws Exception {
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://www.bungie.net/platform/Destiny/Stats/ActivityHistory/TigerXbox/" + memberId + "/" + characterId); //+ "/?definitions=true");
        

        JSONObject data = new JSONObject(getResponseString(client.execute(get)));
        return data.toString();
    }
    
    public static String getResponseString(HttpResponse response) throws Exception {
        JSONObject user = new JSONObject(IOUtils.toString(response.getEntity().getContent())); 
        return user.get("Response").toString();
    }
}

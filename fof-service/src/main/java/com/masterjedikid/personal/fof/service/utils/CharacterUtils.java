package com.masterjedikid.personal.fof.service.utils;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Master JediKid
 */
public class CharacterUtils {
    
    public static Long getActivityTimeForChars(String memberID, String charID, Long activityHash){

        try {
            JSONObject response  = new JSONObject(BungieDotNetEndpoints.getAggregateActivityForCharacter(memberID, charID));
            JSONArray activities = response.getJSONObject("data").getJSONArray("activities");
            for(int i = 0; i < activities.length(); i++) {
                if(activities.getJSONObject(i).getLong("activityHash") == activityHash) {
                    return activities.getJSONObject(i).getJSONObject("values").getJSONObject("activitySecondsPlayed").getJSONObject("basic").getLong("value") /3600;
                }
            }
        }
        catch(Exception ex) {
            //Probably should log
        }  
        
        return 0L;
    }
        
    public static List<String> getMemberCharIDs(String idHash) {       
        List<String> characters = new ArrayList();         
        JSONArray characterArray;
        String character;   
        
        // Pull member data
        try {
             characterArray = new JSONObject(BungieDotNetEndpoints.getMemberDataByMemberId(idHash)).getJSONArray("characters");                   
        }
        catch(Exception ex) {
            System.out.println("Problem with pulling character data: " + ex);
            return characters;
        }

        // Pull char ids
        for(int i = 0; i < characterArray.length(); i++) {
            try {
                character = characterArray.getJSONObject(i).getJSONObject("characterBase").getString("characterId");
                if(character != null)
                    characters.add(character);
            }
            catch(Exception ex) {
                //skip char
            }
        }

        return characters;
    }
}

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
    
    public static Long getStatForChars(String memberID, String charID, String stat, String memberName){

        Long statTotal = 0L;
        try {
            JSONObject response  = new JSONObject(BungieDotNetEndpoints.getStatsForCharacter(memberID, charID));
            if(response.getJSONObject("story").has("allTime"))
                statTotal += response.getJSONObject("story").getJSONObject("allTime").getJSONObject(stat).getJSONObject("basic").getLong("value");
            if(response.getJSONObject("raid").has("allTime"))
                statTotal += response.getJSONObject("raid").getJSONObject("allTime").getJSONObject(stat).getJSONObject("basic").getLong("value");
            if(response.getJSONObject("patrol").has("allTime"))
                statTotal += response.getJSONObject("patrol").getJSONObject("allTime").getJSONObject(stat).getJSONObject("basic").getLong("value");
            if(response.getJSONObject("allPvP").has("allTime"))
                statTotal += response.getJSONObject("allPvP").getJSONObject("allTime").getJSONObject(stat).getJSONObject("basic").getLong("value");
            if(response.getJSONObject("allStrikes").has("allTime"))
                statTotal += response.getJSONObject("allStrikes").getJSONObject("allTime").getJSONObject(stat).getJSONObject("basic").getLong("value");
        }
        catch(Exception ex) {
            System.out.println("Problem with stat pull for " + memberName + " : " + ex);
        }  
        
        return statTotal;
    }
        
    public static List<String> getMemberCharIDs(String memberName, String idHash) {       
        List<String> characters = new ArrayList();         
        JSONArray characterArray;
        String character;   
        
        // Pull member data
        try {
             characterArray = new JSONObject(BungieDotNetEndpoints.getMemberDataByMemberId(idHash)).getJSONArray("characters");                   
        }
        catch(Exception ex) {
            System.out.println("Problem with pulling character data for " + memberName + " : " + ex);
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

package com.masterjedikid.personal.fof.service.datastructures;

import com.masterjedikid.personal.fof.service.utils.BungieDotNetEndpoints;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Master JediKid
 */
public class MemberCharacters {
    
    private String memberName;
    private String memberID;
    private List<String> charactersIDs;
    
    private Long normCETotalTime = 0L;
    private Long hardCETotalTime = 0L;
    private Long normVoGTotalTime = 0L;
    private Long hardVoGTotalTime = 0L;
    private Long playTime = 0L;
    
    private Long suicides = 0L;
    
    public MemberCharacters(String memberName, String memberID, List<String> charactersIDs) {
        this.memberName = memberName;
        this.memberID = memberID;
        this.charactersIDs = charactersIDs;
    }
    
    public String getMemberName() {
        return this.memberName;
    }
    
    public String getMemberID() {
        return this.memberID;
    }
    
    public String nexCharacter() {
        if(charactersIDs.isEmpty())
            return null;
        
        synchronized (this) {
            if(charactersIDs.size() > 0) {
                String s = charactersIDs.get(0);
                charactersIDs.remove(0);
                return s;
            }
            
            return null;
        }
    }
    
    public void printCharacterIDs() {
        System.out.println("***"+ charactersIDs.size() +"***");
        for(String charID : charactersIDs) {
            System.out.println(charID);
        }
    }
    
    public void addPlayTime(Long time) {
        synchronized (this) {
            playTime += time;
        }
    }
        
    public void addNormCETime(Long time) {
        synchronized (this) {
            normCETotalTime += time;
        }
    }
    
    public void addHardCETime(Long time) {
        synchronized (this) {
            hardCETotalTime += time;    
        }
    }
    
    public void addNormVoGTime(Long time) {
        synchronized (this) {
            normVoGTotalTime += time;
        }
    }
    
    public void addHardVoGTime(Long time) {
        synchronized (this) {
            hardVoGTotalTime += time;
        }
    }
    
    public void addSuicides(Long deaths) {
        synchronized (this) {
            suicides += deaths;
        }
    }
    
    public Long getPlayTime() {
        return playTime;
    }
    
    public Long getNormCETime() {
        return normCETotalTime;
    }
    
    public Long getHardCETime() {
        return hardCETotalTime;
    }
    
    public Long getNormVoGTime() {
        return normVoGTotalTime;
    }
    
    public Long getHardVoGTime() {
        return hardVoGTotalTime;
    }
    
    public Long getSuicides() {
        return suicides;
    }
}

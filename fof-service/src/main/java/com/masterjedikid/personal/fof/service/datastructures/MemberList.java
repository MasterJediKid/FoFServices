package com.masterjedikid.personal.fof.service.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Master JediKid
 */
public class MemberList {
    
    private List<String> memberNames = new ArrayList();
    private Map<String, MemberCharacters> memberList = new HashMap();
    
    public MemberList(List<String> memberNames) {
        this.memberNames = memberNames;
    }
    
    public String nextMemberName() {
        if(memberNames.isEmpty())
            return null;
        
        synchronized (this) {
            if(memberNames.size() > 0) {
                String s = memberNames.get(0);
                memberNames.remove(0);
                return s;
            }
            
            return null;
        }
    }
    
    public void addMemberData(String member, MemberCharacters chars) {
        synchronized (this) {
            memberList.put(member, chars);
        }
    }
    
    public Map<String, MemberCharacters> getMemberData() {
        return memberList;
    }
    
    public void print() {
        System.out.println("***"+ memberList.size() +"***");
        for(Map.Entry<String, MemberCharacters> member : memberList.entrySet()) {
            System.out.println(member.getKey() + " :");
            
            System.out.println("   Play time: " + member.getValue().getPlayTime());
            System.out.println("   NM VoG time: " + member.getValue().getNormVoGTime());
            System.out.println("   HM VoG time: " + member.getValue().getHardVoGTime());
            System.out.println("   NM CE time: " + member.getValue().getNormCETime());
            System.out.println("   HM CE time: " + member.getValue().getHardCETime());
            
            
        }
    }
    
}

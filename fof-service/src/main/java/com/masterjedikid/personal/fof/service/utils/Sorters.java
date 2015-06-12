package com.masterjedikid.personal.fof.service.utils;

import com.masterjedikid.personal.fof.service.datastructures.MemberCharacters;
import com.masterjedikid.personal.fof.service.datastructures.MemberList;
import com.masterjedikid.personal.fof.service.main.Service;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Master JediKid
 */
public class Sorters {
    
    public static Map<String, Long> sortPlayTime(MemberList members) {

        Map<String,Long> unsortedMembers = new HashMap();
        
        for(Map.Entry<String, MemberCharacters> member : members.getMemberData().entrySet()) {
            unsortedMembers.put(member.getKey(), member.getValue().getPlayTime());
        }

        Map<String,Long> sortedMembers = new TreeMap(new TimeComparator(unsortedMembers));
        sortedMembers.putAll(unsortedMembers);
        return sortedMembers;
    }
    
    public static Map<String, Long> sortNMCE(MemberList members) {

        Map<String,Long> unsortedMembers = new HashMap();
        
        for(Map.Entry<String, MemberCharacters> member : members.getMemberData().entrySet()) {
            unsortedMembers.put(member.getKey(), member.getValue().getNormCETime());
        }

        Map<String,Long> sortedMembers = new TreeMap(new TimeComparator(unsortedMembers));
        sortedMembers.putAll(unsortedMembers);
        return sortedMembers;
    }
    
    public static Map<String, Long> sortHMCE(MemberList members) {

        Map<String,Long> unsortedMembers = new HashMap();
        
        for(Map.Entry<String, MemberCharacters> member : members.getMemberData().entrySet()) {
            unsortedMembers.put(member.getKey(), member.getValue().getHardCETime());
        }

        Map<String,Long> sortedMembers = new TreeMap(new TimeComparator(unsortedMembers));
        sortedMembers.putAll(unsortedMembers);
        return sortedMembers;
    }
    
    public static Map<String, Long> sortNMVog(MemberList members) {

        Map<String,Long> unsortedMembers = new HashMap();
        
        for(Map.Entry<String, MemberCharacters> member : members.getMemberData().entrySet()) {
            unsortedMembers.put(member.getKey(), member.getValue().getNormVoGTime());
        }

        Map<String,Long> sortedMembers = new TreeMap(new TimeComparator(unsortedMembers));
        sortedMembers.putAll(unsortedMembers);
        return sortedMembers;
    }
    
    public static Map<String, Long> sortHMVog(MemberList members) {

        Map<String,Long> unsortedMembers = new HashMap();
        
        for(Map.Entry<String, MemberCharacters> member : members.getMemberData().entrySet()) {
            unsortedMembers.put(member.getKey(), member.getValue().getHardVoGTime());
        }

        Map<String,Long> sortedMembers = new TreeMap(new TimeComparator(unsortedMembers));
        sortedMembers.putAll(unsortedMembers);
        return sortedMembers;
    }
    
    public static Map<String, Long> sortSuicides(MemberList members) {

        Map<String,Long> unsortedMembers = new HashMap();
        
        for(Map.Entry<String, MemberCharacters> member : members.getMemberData().entrySet()) {
            unsortedMembers.put(member.getKey(), member.getValue().getSuicides());
        }

        Map<String,Long> sortedMembers = new TreeMap(new TimeComparator(unsortedMembers));
        sortedMembers.putAll(unsortedMembers);
        return sortedMembers;
    }
    
    
    public static class TimeComparator implements Comparator<String> {
    
        Map<String,Long> base;
        public TimeComparator(Map<String, Long> base) {
            this.base = base;
        }
        
        // Note: this comparator imposes orderings that are inconsistent with equals.    
        public int compare(String a, String b) {
            if (base.get(a) >= base.get(b)) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }
    
    
}

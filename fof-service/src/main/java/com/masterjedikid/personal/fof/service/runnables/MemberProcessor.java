package com.masterjedikid.personal.fof.service.runnables;

import com.masterjedikid.personal.fof.service.datastructures.MemberCharacters;
import com.masterjedikid.personal.fof.service.datastructures.MemberList;
import com.masterjedikid.personal.fof.service.utils.BungieDotNetEndpoints;
import com.masterjedikid.personal.fof.service.utils.CharacterUtils;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Master JediKid
 */
public class MemberProcessor implements Runnable {

    MemberList memberList;
    private static final int NTHREDS = 3;
    
    public MemberProcessor(MemberList members) {
        this.memberList = members;
    }
    
    @Override
    public void run() {
        String member = memberList.nextMemberName();
        
        try {
            memberList.addMemberData(member, processCharacters(member));
        } 
        catch (Exception ex) {
            System.out.println("Problem adding " + member + " data : " + ex);
            return;
        }
    }
    
    public MemberCharacters processCharacters(String memberName) throws InterruptedException, Exception {
        
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        String memberID = BungieDotNetEndpoints.getMemberBungieId(memberName);
        List charIDs = CharacterUtils.getMemberCharIDs(memberName, memberID);
        int charCount = charIDs.size();
        MemberCharacters chars = new MemberCharacters(memberName, memberID, charIDs);
        
        
        for (int i = 0; i < charCount; i++) {
            Runnable worker = new CharacterProcessor(chars);
            executor.execute(worker);
        }

        executor.shutdown();
        
        // Wait until all threads are finish
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        return chars;
    }
}

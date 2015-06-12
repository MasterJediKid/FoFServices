
package com.masterjedikid.personal.fof.service.main;

import com.masterjedikid.personal.fof.service.datastructures.MemberCharacters;
import com.masterjedikid.personal.fof.service.datastructures.MemberList;
import com.masterjedikid.personal.fof.service.runnables.MemberProcessor;
import com.masterjedikid.personal.fof.service.utils.BungieDotNetEndpoints;
import com.masterjedikid.personal.fof.service.utils.Sorters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author MAster JediKid
 */
public class Service {
    
    private static final int NTHREDS = 60;
    private MemberList memberData;
    
    public void run(String token) throws InterruptedException, Exception {
        
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
//        List<String> members = getClanMemberList("The%20Federation%20of%20Fathers");
        List<String> members = getSlackMemberList(token);
//        List<String> members = new ArrayList();
//        members.add("Adm Wright Meow");
        memberData = new MemberList(members);
        
        for (int i = 0; i < members.size(); i++) {
            Runnable worker = new MemberProcessor(memberData);
            executor.execute(worker);
        }
        
        executor.shutdown();
        
        // Wait until all threads are finish
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println("***Sorting " + memberData.getMemberData().size() + " Members***");
        System.out.println("***Play Times***");
        System.out.println(Sorters.sortPlayTime(memberData));
//        
//        System.out.println("***Normal VoG Raid Times***");
//        System.out.println(Sorters.sortNMVog(memberData));
//        
//        System.out.println("\n***Hard VoG Raid Times***");
//        System.out.println(Sorters.sortHMVog(memberData));
//        
//        System.out.println("\n***Normal CE Raid Times***");
//        System.out.println(Sorters.sortNMCE(memberData));
//        
//        System.out.println("\n***Hard CE Raid Times***");
//        System.out.println(Sorters.sortHMCE(memberData));
//        System.out.println("***Suicides***");
//        System.out.println(Sorters.sortSuicides(memberData));
    }
    
    
    
    public static List<String> getSlackMemberList(String token) {
        return getSlackMemberNames(new JSONObject(new RestTemplate().getForObject("https://slack.com/api/users.list?token=" + token, String.class)).getJSONArray("members"));
    }
    
    private static List<String> getSlackMemberNames(JSONArray array) {
        List<String> memberNames = new ArrayList();
        
        
        for(int i = 0 ; i < array.length(); i++) {
            try {
                JSONObject slackMember = array.getJSONObject(i);
                if(!slackMember.getBoolean("deleted") && !slackMember.getBoolean("is_bot") && !slackMember.getBoolean("deleted")) 
                    memberNames.add(slackMember.getJSONObject("profile").getString("first_name"));
            }
            catch(Exception ex) {
                // skip member
                System.out.println(array.getJSONObject(i).toString() + ex);
            }
        }
        
        return memberNames;
    }
    
    private static List getClanMemberList(String clanName) throws Exception {
        List<String> memberNames = getClanMembers(BungieDotNetEndpoints.getClanGroupIdNumber(clanName));
        
        return memberNames;
    }
    
    private static List<String> getClanMembers(String clanId) {
        List<String> memberList = new ArrayList();
        JSONObject member;
        boolean hasMore = true;
        int page = 1;
        HttpClient client = new DefaultHttpClient();        
        try {
            while(hasMore) {
                HttpGet get = new HttpGet("http://www.bungie.net/Platform/Group/" + clanId + "/MembersV3/?currentPage=" + page);
                JSONObject response = new JSONObject(BungieDotNetEndpoints.getResponseString(client.execute(get)));
                JSONArray members = response.getJSONArray("results");

                for(int i = 0; i < members.length(); i++ ) {
                    try {
                        member = members.getJSONObject(i).getJSONObject("user");
                        memberList.add(member.get("xboxDisplayName").toString());
                    }
                    catch(Exception ex) {
                        // Ignore
                    }
                }

                hasMore = response.getBoolean("hasMore");
                page++;
            }
        }
        catch(Exception ex) { 
            System.out.println("Problemwith pulling data for " + clanId + " : " + ex);
        }
        return memberList;
    }
    
}

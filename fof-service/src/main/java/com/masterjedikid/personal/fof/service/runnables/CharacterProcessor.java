package com.masterjedikid.personal.fof.service.runnables;

import com.masterjedikid.personal.fof.service.datastructures.MemberCharacters;
import com.masterjedikid.personal.fof.service.utils.CharacterUtils;

/**
 * @author Master JediKid
 */
public class CharacterProcessor implements Runnable {

    MemberCharacters structure;
    
    
    CharacterProcessor(MemberCharacters structure) {
        this.structure = structure;
    }
    
    @Override
    public void run() {
        String charID = structure.nexCharacter();
        String member = structure.getMemberID();
        
        structure.addHardCETime(CharacterUtils.getActivityTimeForChars(member, charID, 1836893119L));
        structure.addHardVoGTime(CharacterUtils.getActivityTimeForChars(member, charID, 2659248068L));
        structure.addNormCETime(CharacterUtils.getActivityTimeForChars(member, charID, 1836893116L));
        structure.addNormVoGTime(CharacterUtils.getActivityTimeForChars(member, charID, 2659248071L));
        structure.addPlayTime(CharacterUtils.getStatForChars(member, charID, "secondsPlayed", structure.getMemberName()) / 3600);
        
        structure.addSuicides(CharacterUtils.getStatForChars(member, charID, "secondsPlayed", structure.getMemberName()));
    } 
}

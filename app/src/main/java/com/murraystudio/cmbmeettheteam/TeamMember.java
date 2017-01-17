package com.murraystudio.cmbmeettheteam;

/**
 * Author: Shamus Murray
 *
 * This class holds the data of the JSON team member.
 */

public class TeamMember {
    String avatarURL;
    String bio;
    String firstName;
    String lastName;
    String title;


    String getAvatarURL(){
        return avatarURL;
    }

    String getBio(){
        return bio;
    }

    String getFirstName(){
        return firstName;
    }

    String getLastName(){
        return lastName;
    }

    String getTitle(){
        return title;
    }
}

package com.murraystudio.cmbmeettheteam;

/**
 * This is a class that holds the data of the JSON team member
 */

public class TeamMember {
    String avatarURL;
    String bio;
    String firstName;
    int ID;
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

    int getID(){
        return ID;
    }

    String getLastName(){
        return lastName;
    }

    String getTitle(){
        return title;
    }
}

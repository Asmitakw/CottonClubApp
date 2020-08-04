package com.swarveda.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Asmita on 5/7/2017.
 */

public class AppSession {
    private static final String TAG = "tag";
    private static SharedPreferences sharedPreferences;
    private static AppSession appSession;
    private static String SAVE_DEVICE_ID = "save_device_id";
    private static String IS_LOGIN = "is_login";
    private static String IS_INTRO = "is_intro";


    private static String SAVE_MEMBER_ID = "member_id";
    private static String SAVE_COORDINATOR_ID = "coordinator_id";
    private static String SAVE_FULLNAME = "fullname";
    private static String SAVE_REGISTRATION_DATE = "registration_date";
    private static String SAVE_GENDER = "gender";
    private static String SAVE_DOB = "dob";
    private static String SAVE_OCCUPATION = "occupation";
    private static String SAVE_CONTACT_NUMBER = "number";
    private static String SAVE_EMAIL = "email";
    private static String SAVE_ADDRESS = "address";
    private static String SAVE_ROLE_ID = "role_id";
    private static String SAVE_ROLE_NAME = "role_name";
    private static String SAVE_TOWN_ID = "town_id";
    private static String SAVE_TOWN_NAME = "town_name";
    private static String SAVE_TALUKA_ID = "taluka_id";
    private static String SAVE_TALUKA_NAME = "taluka_name";
    private static String SAVE_DISTRICT_ID = "district_id";
    private static String SAVE_DISTRICT_NAME = "district_name";
    private static String SAVE_STATE_ID = "state_id";
    private static String SAVE_STATE_NAME = "state_name";
    private static String SAVE_LANGUAGE_ID = "language_id";
    private static String SAVE_LANGUAGE = "language";
    private static String SAVE_IS_VOLUNTERR = "is_voluteer";
    private static String SAVE_MEMBER_COUNT = "member_count";
    private static String SAVE_VY_CONNECTION = "vy_connection";
    private static String SAVE_EDUCATION = "education";
    private static String SAVE_PROFESSIONAL_EXPERTISE = "professional_exp";
    private static String SAVE_TIME_SPEND_VY = "vy_time";
    private static String SAVE_SECTOR = "sector";
    private static String SAVE_AREA = "area";
    private static String SAVE_SATSANG_TYPE = "satsang_count";
    private static String SAVE_LANGUAGES_KNOWN = "languages_known";

    public static AppSession getInstance() {
        if (sharedPreferences == null) {
            appSession = new AppSession();
        }
        return appSession;
    }

    public void clearSharedPreference(Context context) {
        String deviceId = getDeviceId(context);
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        saveDeviceId(context, deviceId);
    }


    //Save Member's Login Details

    //Coordinator ID
    public void saveCoordinatorId(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_COORDINATOR_ID, username);
        editor.commit();
    }

    public String getCoordinatorId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_COORDINATOR_ID, "");

    }

    //Languages Known
    public void saveLanguagesKnown(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_LANGUAGES_KNOWN, username);
        editor.commit();
    }

    public String getLanguagesKnown(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_LANGUAGES_KNOWN, "");

    }

    //Save satsang type
    public void setSatsangType(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_SATSANG_TYPE, username);
        editor.commit();
    }

    public String getSatsangType(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_SATSANG_TYPE, "");

    }

    //Member Count
    public void saveMemberCount(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_MEMBER_COUNT, username);
        editor.commit();
    }

    public String getMemberCount(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_MEMBER_COUNT, "");

    }

    //Save member id
    public void saveMemberId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_MEMBER_ID, userId);
        editor.commit();
    }

    public String getMemberId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_MEMBER_ID, "");

    }

    //Registration Date
    public void saveRegistrationDate(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_REGISTRATION_DATE, username);
        editor.commit();
    }

    public String getRegistrationDate(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_REGISTRATION_DATE, "");

    }

    //Save FullName
    public void saveFullName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_FULLNAME, username);
        editor.commit();
    }

    public String getFullName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_FULLNAME, "");

    }

    //Gender
    public void saveGender(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_GENDER, username);
        editor.commit();
    }

    public String getGender(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_GENDER, "");
    }


    //Connection
    public void setSaveVyConnection(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_VY_CONNECTION, username);
        editor.commit();
    }

    public String getVyConnection(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_VY_CONNECTION, "");
    }

    //Education
    public void setEducation(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_EDUCATION, username);
        editor.commit();
    }

    public String getEducation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_EDUCATION, "");
    }

    //Professional Exp
    public void setProfessionalExpertise(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_PROFESSIONAL_EXPERTISE, username);
        editor.commit();
    }

    public String getProfessionalExpertise(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_PROFESSIONAL_EXPERTISE, "");
    }


    //Time
    public void setTimeSpent(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_TIME_SPEND_VY, username);
        editor.commit();
    }

    public String getTimeSpent(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_TIME_SPEND_VY, "");
    }

    //Sector
    public void setSector(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_SECTOR, username);
        editor.commit();
    }

    public String getSector(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_SECTOR, "");
    }

    //Area
    public void setArea(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_AREA, username);
        editor.commit();
    }

    public String getArea(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_AREA, "");
    }


    //DOB
    public void saveDob(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DOB, username);
        editor.commit();
    }

    public String getDob(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DOB, "");
    }

    //Occupation
    public void saveOccupation(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_OCCUPATION, username);
        editor.commit();
    }

    public String getOccupation(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_OCCUPATION, "");
    }

    //Save Mobile
    public void saveMobile(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_CONTACT_NUMBER, username);
        editor.commit();
    }

    public String getContact(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_CONTACT_NUMBER, "");

    }

    //Save Email
    public void saveEmail(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_EMAIL, username);
        editor.commit();
    }

    public String getEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_EMAIL, "");
    }

    //Address
    public void saveAddress(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_ADDRESS, username);
        editor.commit();
    }

    public String getAddress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_ADDRESS, "");
    }

    //Role ID
    public void saveRoleID(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_ROLE_ID, username);
        editor.commit();
    }

    public String getRoleID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_ROLE_ID, "");
    }

    //ROLE NAME
    public void saveRoleName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_ROLE_NAME, username);
        editor.commit();
    }

    public String getRoleName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_ROLE_NAME, "");
    }

    //Town Id
    public void saveTownID(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_TOWN_ID, username);
        editor.commit();
    }

    public String getTownID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_TOWN_ID, "");
    }


    //Town Name
    public void saveTownName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_TOWN_NAME, username);
        editor.commit();
    }

    public String getTownName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_TOWN_NAME, "");
    }

    //Taluka ID
    public void saveTalukaID(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_TALUKA_ID, username);
        editor.commit();
    }

    public String getTalukaID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_TALUKA_ID, "");
    }

    //Taluka Name
    public void saveTalukaName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_TALUKA_NAME, username);
        editor.commit();
    }

    public String getTalukaName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_TALUKA_NAME, "");
    }

    //District ID
    public void saveDistrictID(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DISTRICT_ID, username);
        editor.commit();
    }

    public String getDistrictID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DISTRICT_ID, "");
    }

    //District Name
    public void saveDistrictName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DISTRICT_NAME, username);
        editor.commit();
    }

    public String getDistrictName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DISTRICT_NAME, "");
    }


    //State ID
    public void saveStateID(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_STATE_ID, username);
        editor.commit();
    }

    public String getSatetID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_STATE_ID, "");
    }

    //State Name
    public void saveStateName(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_STATE_NAME, username);
        editor.commit();
    }

    public String getSatetName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_STATE_NAME, "");
    }

    //Language ID
    public void saveLanguageID(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_LANGUAGE_ID, username);
        editor.commit();
    }

    public String getLanguageID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_LANGUAGE_ID, "");
    }

    //Language
    public void saveLanguage(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_LANGUAGE, username);
        editor.commit();
    }

    public String getLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_LANGUAGE, "");
    }

    //Voulunteer
    public void saveVolunteer(Context context, boolean isVolunteer) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SAVE_IS_VOLUNTERR, isVolunteer);
        editor.commit();
    }

    public boolean getVolunteer(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(SAVE_IS_VOLUNTERR, false);
    }


    /* Save Login details*/
    public void saveLoginStatus(Context context, boolean isLogin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }


    public boolean getLoginStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    //Save intro screen status
    public void saveIntroStatus(Context context, boolean isIntro) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_INTRO, isIntro);
        editor.commit();
    }


    public boolean getIntroStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_INTRO, false);
    }


    /*Save device Id*/
    public void saveDeviceId(Context context, String deviceId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DEVICE_ID, deviceId);
        editor.commit();
    }

    public String getDeviceId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DEVICE_ID, "");
    }

}

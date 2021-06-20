package com.iservice.agency.data;

import com.iservice.agency.data.local.prefs.PreferencesService;
import com.iservice.agency.data.local.sqlite.DbService;
import com.iservice.agency.data.remote.ApiService;


public interface Repository {

    /**
     * ################################## Preference section ##################################
     */
    String getToken();
    void setToken(String token);
    PreferencesService getSharedPreferences();


    /**
     * ################################## Sqlite section ##################################
     */
    DbService getSqliteService();



    /**
     *  ################################## Remote api ##################################
     */
    ApiService getApiService();


}

package com.leyou.game.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig friendDaoConfig;
    private final DaoConfig phoneContactDaoConfig;
    private final DaoConfig crowdDaoConfig;
    private final DaoConfig searchHistoryDaoConfig;

    private final FriendDao friendDao;
    private final PhoneContactDao phoneContactDao;
    private final CrowdDao crowdDao;
    private final SearchHistoryDao searchHistoryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> daoConfigMap) {
        super(db);

        friendDaoConfig = daoConfigMap.get(FriendDao.class).clone();
        friendDaoConfig.initIdentityScope(type);

        phoneContactDaoConfig = daoConfigMap.get(PhoneContactDao.class).clone();
        phoneContactDaoConfig.initIdentityScope(type);

        crowdDaoConfig = daoConfigMap.get(CrowdDao.class).clone();
        crowdDaoConfig.initIdentityScope(type);

        searchHistoryDaoConfig = daoConfigMap.get(SearchHistoryDao.class).clone();
        searchHistoryDaoConfig.initIdentityScope(type);

        friendDao = new FriendDao(friendDaoConfig, this);
        phoneContactDao = new PhoneContactDao(phoneContactDaoConfig, this);
        crowdDao = new CrowdDao(crowdDaoConfig, this);
        searchHistoryDao = new SearchHistoryDao(searchHistoryDaoConfig, this);

        registerDao(Friend.class, friendDao);
        registerDao(PhoneContact.class, phoneContactDao);
        registerDao(Crowd.class, crowdDao);
        registerDao(SearchHistory.class, searchHistoryDao);
    }

    public void clear() {
        friendDaoConfig.getIdentityScope().clear();
        phoneContactDaoConfig.getIdentityScope().clear();
        crowdDaoConfig.getIdentityScope().clear();
        searchHistoryDaoConfig.getIdentityScope().clear();
    }

    public FriendDao getFriendDao() {
        return friendDao;
    }

    public PhoneContactDao getPhoneContactDao() {
        return phoneContactDao;
    }

    public CrowdDao getCrowdDao() {
        return crowdDao;
    }

    public SearchHistoryDao getSearchHistoryDao() {
        return searchHistoryDao;
    }

}
